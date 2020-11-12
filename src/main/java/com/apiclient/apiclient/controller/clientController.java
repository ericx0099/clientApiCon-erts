package com.apiclient.apiclient.controller;

import com.apiclient.apiclient.entities.*;
import com.apiclient.apiclient.service.ArtistaService;
import com.apiclient.apiclient.service.ClientService;
import com.apiclient.apiclient.service.EventsService;
import com.apiclient.apiclient.service.UbicacioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class clientController {
    private final ClientService cs;
    private final UbicacioService us;
    private final ArtistaService as;
    private final EventsService es;
    Client client;
    Ubicacio ubicacio;
    Artista artista;
    PasswordChecker passwordChecker;
    public clientController(ClientService cs, UbicacioService us, ArtistaService as, EventsService es) {
        this.cs = cs;
        this.us = us;
        this.as = as;
        this.es = es;
    }
  /*  @RequestMapping("/eventsDeArtista")
    public String eventsDeArtista(@ModelAttribute Artista artista, Model model){
        model.addAttribute("artista",artista);
        List<Event> eventsDeArtista = as.getEventsByArtista(artista.getId());
        model.addAttribute("eventsDeArtista",eventsDeArtista);



    }*/
    @RequestMapping("/getEvents")
    public String getEvents(Model model){
        List<Event> llistaEvents = es.getEvents();
        List<Artista> llistaArtistes = as.getArtistes();
        List<Ubicacio> ubicacions = us.getUbicacions();

        model.addAttribute("llistaEvents",llistaEvents);
        model.addAttribute("llistaArtistes",llistaArtistes);
        model.addAttribute("ubicacions",ubicacions);
        return "getEvents";
    }
    @RequestMapping("/addEvent")
    public String addEvent(@RequestParam("inputDataEvent") String inputDataEvent,@RequestParam("idArt") int idArt,@ModelAttribute("event") Event event,@ModelAttribute("ubicacio") Ubicacio ubicacio,Model model){
        boolean checkPais = true;
        for (int i =0;i<ubicacio.getPais().length();i++){
            if(!Character.isLetter(ubicacio.getPais().charAt(i))){
                checkPais = false;
                break;
            }
        }
        boolean checkCiutat = true;
        for (int i =0;i<ubicacio.getCiutat().length();i++){
            if(!Character.isLetter(ubicacio.getCiutat().charAt(i))){
                checkCiutat = false;
                break;
            }
        }
        if(checkCiutat && checkPais){

            us.saveUbicacio(ubicacio);
        }
        int idu=0;
        List<Ubicacio> ubis = us.getUbicacions();
        if(!ubis.isEmpty()){
            for(Ubicacio u:ubis){
                if(u.getCarrer().equalsIgnoreCase(ubicacio.getCarrer()) &&
                        u.getCiutat().equalsIgnoreCase(
                                ubicacio.getCiutat()) &&
                        u.getPais().equalsIgnoreCase(
                                ubicacio.getPais())){

                    idu=u.getId();
                }
            }
        }

        inputDataEvent = inputDataEvent+":00";
        String dataa = inputDataEvent.replace("T","");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(dataa,formatter);
        event.setDataInici(ldt);
        Event ev = null;
        if(idArt == (int)idArt && idu !=0 ){
            ev = es.saveEventByIds(event,idArt,idu);
        }

        return "getEvents";
    }


    @RequestMapping("/openModalAddEvent")
    public String openModalAddEvent(@RequestParam("idArt") int idArt,  Model model ){

        boolean showModalAddEvent = true;
        Event event = new Event();
        Artista artista = new Artista();

        List<Artista> llistaArtistes= as.getArtistes();
        model.addAttribute("llistaArtistes",llistaArtistes);
        model.addAttribute("artista",artista);
        model.addAttribute("idArt",idArt);
        model.addAttribute("event",event);
        model.addAttribute("showModalAddEvent",showModalAddEvent);
        Ubicacio ubicacio = new Ubicacio();
        model.addAttribute("ubicacio",ubicacio);
        return "addArtista";
    }

    @RequestMapping("/eventsOfArtist")
    public String eventsOfArtist(Model model){
        List<Artista> llistaArtistes= as.getArtistes();
        model.addAttribute("llistaArtistes",llistaArtistes);
        List<Event> llistaEvents = null;
        Artista art = new Artista();
        List<Event> events=null;
        try{
            events = es.getEvents();
        } catch (Exception e) {
           System.out.println("ERROR AGAFANT EVENTS A /eventsOfArtist: "+e.getMessage());
        }

        List<Artista> artistesAmbEvents = new ArrayList<>();
        if(events!=null){
            for(Event e:events){

                for(Artista a:llistaArtistes){

                    if(String.valueOf(e.getIdArtista()).equals(String.valueOf(a.getId()))){

                        artistesAmbEvents.add(a);
                    }
                }
            }
        }

        boolean showEvents = false;

        model.addAttribute("showEvents",showEvents);
        model.addAttribute("art",art);
        model.addAttribute("llistaEvents",llistaEvents);
        model.addAttribute("artistesAmbEvents",artistesAmbEvents);
        return "eventsOfArtist";
    }
    @RequestMapping("/searchArtistes")
    public String searchArtistes(@ModelAttribute Artista art,Model model){

        Artista artSelect = null;
        List<Artista> llistaArtistes = as.getArtistes();
        System.out.println("NOM: "+art.getNom());
        for(Artista a:llistaArtistes){
            if(a.getNom().equalsIgnoreCase(art.getNom())){
                artSelect=a;
            }
        }
        List<Event> llistaEvents = null;
        List<Ubicacio> ubicacions = us.getUbicacions();
        model.addAttribute("ubicacions",ubicacions);
        if(artSelect!=null){
            try{
                llistaEvents= as.getEventsByArtista(artSelect.getId());
            } catch (Exception e) {
                llistaEvents = null;
               System.out.println("error trobant events de artista a /searchArtistes - "+e.getMessage()) ;
            }

        }
        model.addAttribute("art",art);
        model.addAttribute("llistaArtistes",llistaArtistes);
        model.addAttribute("llistaEvents",llistaEvents);
        boolean showEvents = false;
        if(llistaEvents==null){
            System.out.println("llista events is nul");
        }else{
            showEvents=true;
        }

        model.addAttribute("showEvents",showEvents);
        return "eventsOfArtist";
    }

    @RequestMapping("/addArtista")
    public String addArtista(Model model){
        artista= new Artista();
        Event event = new Event();
        model.addAttribute("event",event);
        List<Artista> llistaArtistes = as.getArtistes();
        model.addAttribute("artista",artista);
        model.addAttribute("llistaArtistes",llistaArtistes);
        boolean showModalAddEvent = false;
        model.addAttribute("showModalAddEvent",showModalAddEvent);
        Ubicacio ubicacio = new Ubicacio();
        model.addAttribute("ubicacio",ubicacio);
        return "addArtista";
    }
    @RequestMapping("/addClient")
    public String afegirClient(Model model){
        passwordChecker = new PasswordChecker();
        String pass1 ="";
        String pass2="";
        client = new Client();
        client.setPassword("");
        ubicacio = new Ubicacio();
        model.addAttribute("passwordChecker",passwordChecker);
        model.addAttribute("client",client);
        model.addAttribute("ubicacio",ubicacio);
        boolean oModal = false;
        model.addAttribute("oModal",oModal);
        return "registerClient";
    }
    @RequestMapping("/regClient")
    public String regClient(@ModelAttribute Client client, @ModelAttribute Ubicacio ubicacio,@ModelAttribute PasswordChecker passwordChecker ,  Model model){
        Ubicacio ubiMod = us.saveUbicacio(ubicacio);
        List<Ubicacio> ubis = us.getUbicacions();
        Client clientreturned = null;
        int id = 0;

        if(!ubis.isEmpty()){
            for(Ubicacio u:ubis){
                if(u.getCarrer().equalsIgnoreCase(ubicacio.getCarrer()) &&
                        u.getCiutat().equalsIgnoreCase(
                                ubicacio.getCiutat()) &&
                            u.getPais().equalsIgnoreCase(
                                    ubicacio.getPais())){
                 /*   System.out.println("keke1");
                    System.out.println(passwordChecker.getPass1() + "- 1");
                    System.out.println();
                    System.out.println(passwordChecker.getPass2()+"- 2");*/
                    if(String.valueOf(passwordChecker.getPass1()).equals(String.valueOf(passwordChecker.getPass2()))){
                        client.setPassword(passwordChecker.getPass1());

                        clientreturned = cs.saveClientByUbi(client, u.getId());
                    }

                }
            }
        }



        System.out.println(clientreturned);

        return "redirect:/getClients";
    }
    @RequestMapping("/regArt")
    public String regArt(@ModelAttribute Artista artista, Model model){
        List<Artista> artistesRegistrats = as.getArtistes();
        boolean exists=false;
        Artista artistaRegistrat;
        for(Artista a:artistesRegistrats){
            if(a.getNom().equalsIgnoreCase(artista.getNom())){
                exists=true;
                break;
            }
        }

        if(!exists){
            artistaRegistrat=as.saveArtista(artista);

        }
        return "redirect:/addArtista";
    }

    @RequestMapping("/getClients")
    public String llistarClients(Model model){
        List<Ubicacio> llistaUbis = null;
        List<Client> llistaClients=null;
        try{

            llistaUbis = us.getUbicacions();
        } catch (Exception e) {
            System.out.println("ERROR demanant la llista de tots els Ubicacions a llistarCLients");
        }
        try{

           llistaClients = cs.getClients();
        } catch (Exception e) {
            System.out.println("ERROR demanant la llista de tots els clients");
        }
        model.addAttribute("llistaClients",llistaClients);
        model.addAttribute("llistaUbis",llistaUbis);
        for(Ubicacio u:llistaUbis){
            System.out.println(u.getId());
        }
        return "llistaClients";
    }
}
