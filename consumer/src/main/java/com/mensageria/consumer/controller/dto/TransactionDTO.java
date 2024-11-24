package com.mensageria.consumer.controller.dto;

import lombok.Data;

@Data
public class TransactionDTO {
    private String id;
    private String status;

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
