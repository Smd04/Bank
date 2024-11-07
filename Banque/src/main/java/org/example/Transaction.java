package org.example;

import java.time.LocalDateTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Transaction extends Banque {
    private String type;
    private LocalDateTime timestamp;
    private String reference;
    private String pays_re;
    private String bank_re;
    private Compte compte;
    private String id;
    private double amount;

    // Existing constructor for inter-bank transactions
    public Transaction(String reference, String pays_re, String bank_re, String id, double amount) {
        this.timestamp = LocalDateTime.now();
        this.type = transactionType();
        this.reference = reference;
        this.pays_re = pays_re;
        this.bank_re = bank_re;
        this.id = id;
        this.amount= amount;
    }

    // New constructor for basic transactions like deposit and withdrawal
    public Transaction(String type, double amount) {
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
    }

    // Getter for amount
    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    private String transactionType() {
        if (!getId().equals(this.id) && !getPays().equals(pays_re) && !getBankName().equals(bank_re)) {
            return "VIRCHAC";
        } else if (getPays().equals(this.pays_re)) {
            if (getBankName().equals(this.bank_re)) {
                return "VIRINT";
            } else return "VIREST";
        } else return "VIRMULTA";
    }

    // Convertir un objet Transaction en JSON
    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    // Convertir un JSON en objet Transaction
    public static Transaction fromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Transaction.class);
    }
}
