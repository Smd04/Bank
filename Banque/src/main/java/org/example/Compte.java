package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class Compte {
    private static int counter = 0;
    private int acnbr;
    private String date_cr;
    private String date_up;
    private String devise;
    private Banque banque;
    private List<Transaction> transactions;
    private Client client;
    private double balance;

    public Compte(Client client, String date_cr, String date_up, String devise, double balance) {
        this.client = client;
        this.acnbr = ++counter;
        this.date_cr = date_cr;
        this.date_up = date_up;
        this.devise = devise;
        this.transactions = new ArrayList<>();
        this.balance = balance;
    }

    public int getNumclient() {
        return client.getNumclient();
    }

    public String getDevise() {
        return devise;
    }

    public double getBalance() {
        return balance;
    }

    public int getAccountNumber() {
        return acnbr;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addTransaction("withdrawal", amount);
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("deposit", amount);
        }
    }

    private void addTransaction(String type, double amount) {
        // Assuming `Transaction` has a constructor that takes a type and amount for simplicity
        Transaction newTransaction = new Transaction(type, amount);
        transactions.add(newTransaction);
    }

    // Convertir un objet Compte en JSON
    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    // Convertir un JSON en objet Compte
    public static Compte fromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Compte.class);
    }
}
