package com.apiclient.apiclient.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

//import jdk.jfr.Timestamp;

public class Event {

    private int idEvent;

    private int idArtista;
    private float preu;

    /*@JsonSerialize(using = LocalDateTimeSerializer.class)*/
    @JsonFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    private LocalDateTime dataInici;


    private Map<Integer, Integer> puntuacions = new HashMap<Integer, Integer>();
    private Duration duracio;

    private int idUbicacio;

    public Event() {
    }

    public Map<Integer, Integer> getPuntuacions() {
        return puntuacions;
    }


    public void putPuntuacions(int idClient, int puntuacio) {
        if (!this.puntuacions.containsKey(idClient)) {
            this.puntuacions.put(idClient, puntuacio);
        } else {
            this.puntuacions.replace(idClient, puntuacio);
        }
    }

    public void setPuntuacions(Map<Integer, Integer> puntuacions) {
        this.puntuacions = puntuacions;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }

    public LocalDateTime getDataInici() {
        return dataInici;
    }

    public void setDataInici(LocalDateTime dataInici) {
        this.dataInici = dataInici;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }


    public Duration getDuracio() {
        return duracio;
    }

    public void setDuracio(Duration duracio) {
        this.duracio = duracio;
    }

    public int getIdUbicacio() {
        return idUbicacio;
    }

    public void setIdUbicacio(int idUbicacio) {
        this.idUbicacio = idUbicacio;
    }
}
