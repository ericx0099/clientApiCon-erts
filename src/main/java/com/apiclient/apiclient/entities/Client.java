package com.apiclient.apiclient.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


public class Client extends Persona {


    List<Compra> ticketsComprats = new ArrayList<Compra>();

    public Client() {


        super();
    }

    public void addCompra(Compra c) {
        this.getTicketsComprats().add(c);
    }

    public List<Compra> getTicketsComprats() {
        return ticketsComprats;
    }

    public void setTicketsComprats(List<Compra> ticketsComprats) {
        this.ticketsComprats = ticketsComprats;
    }
}
