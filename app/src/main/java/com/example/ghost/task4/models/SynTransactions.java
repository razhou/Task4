package com.example.ghost.task4.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ghost on 29/03/2016.
 */
public class SynTransactions {


    public List<SynTransactionItem> transactions;

    public List<SynTransactionItem> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<SynTransactionItem> transactions) {
        this.transactions = transactions;
    }

    public SynTransactions(List<SynTransactionItem> transactions) {
        this.transactions = transactions;
    }

    public class SynTransactionItem {
        private String status;
        private String message;

        public String getStatus()
        {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
