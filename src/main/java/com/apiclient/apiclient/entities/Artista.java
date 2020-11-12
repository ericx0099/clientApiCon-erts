package com.apiclient.apiclient.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Artista {

    private int id;
    private String nom;
    private String membres;
    private String urlPhoto;
    List<Event> events = new ArrayList<Event>();

    public Artista() {
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getMembres() {
        return membres;
    }

    public void setMembres(String membres) {
        this.membres = membres;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getParticipants() {
        return membres;
    }

    public void setParticipants(String participants) {
        this.membres = participants;
    }
}
