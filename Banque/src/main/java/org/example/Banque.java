package org.example;

import java.util.ArrayList;
import java.util.List;

public class Banque {
    private String bankName;
    private String id;
    private String pays;
    private List<Compte> compte;

    public Banque(String bankName,String id, String pays){
        this.bankName = bankName;
        this.id= id;
        this.pays= pays;
        this.compte = new ArrayList<>();
    }

    public Banque(){

    }

    public String getBankName() {
        return bankName;
    }

    public String getPays(){
        return pays;
    }

    public String getId(){
        return id;
    }
}
