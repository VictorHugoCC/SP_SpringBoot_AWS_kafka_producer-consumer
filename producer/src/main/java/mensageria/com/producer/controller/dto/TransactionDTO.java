package mensageria.com.producer.controller.dto;

import jakarta.validation.constraints.NotBlank;



public class TransactionDTO {
    @NotBlank(message = "O campo 'id' não pode ser nulo ou vazio.")
    private String id;

    @NotBlank(message = "O campo 'status' não pode ser nulo ou vazio.")
    private String status;

    // Getters e setters
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
