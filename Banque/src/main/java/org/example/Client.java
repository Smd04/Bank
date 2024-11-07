package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static int counter = 0;
    private int numclient;
    private String nom;
    private String prenom;
    private String adresse;
    private String phone;
    private String email;
    private List<Compte> compte;

    public Client( String nom, String prenom, String adresse, String phone, String email) {
        this.numclient = ++counter;
        this.numclient = numclient;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.phone = phone;
        this.email = email;
        this.compte= new ArrayList<>();
    }

    public int getNumclient() {
        return numclient;
    }

    public String getNom(){
        return nom;
    }

    // Convertir un objet Client en JSON
    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    // Convertir un JSON en objet Client
    public static Client fromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Client.class);
    }
}
