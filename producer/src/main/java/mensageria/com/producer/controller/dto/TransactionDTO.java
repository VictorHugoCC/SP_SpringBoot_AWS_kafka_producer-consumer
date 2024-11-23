package mensageria.com.producer.controller.dto;


import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private String id;
    private String status;
}
