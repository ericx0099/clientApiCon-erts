package com.apiclient.apiclient.service;

import com.apiclient.apiclient.entities.Client;
import com.apiclient.apiclient.entities.Event;
import com.apiclient.apiclient.entities.Ubicacio;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class EventsService {
    final String uri = "http://Backendapi-env.eba-bwxmtzsw.us-east-1.elasticbeanstalk.com/";
    public List<Event> getEvents(){
        String uriC = uri+"getEvents";
        RestTemplate rt = new RestTemplate();
        ResponseEntity<List<Event>> rateResponse =
                rt.exchange(uriC,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Event>>() {
                        });
        List<Event> llista = rateResponse.getBody();
        return llista;
    }

    public Event saveEvent(Event event){
        String urlAdd = uri+"addEvent";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Event> httpEntity= new HttpEntity<>(event,headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Event> responseEntity = rt.postForEntity(urlAdd,httpEntity,Event.class);
        Event eventMod = responseEntity.getBody();
        return eventMod;
    }
    public Event saveEventByIds(Event event,int idA,int idU){
        String urlAdd = uri+"createEvent/"+idA+"/"+idU;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Event> httpEntity= new HttpEntity<>(event,headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Event> responseEntity = rt.postForEntity(urlAdd,httpEntity,Event.class);
        Event eventMod = responseEntity.getBody();
        return eventMod;
    }
}
