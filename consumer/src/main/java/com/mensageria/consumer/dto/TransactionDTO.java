package com.mensageria.consumer.dto;

public class TransactionDTO {
    private String id;
    private String status;

    public TransactionDTO() {}

    public TransactionDTO(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
