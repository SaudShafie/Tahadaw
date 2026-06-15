package org.example.tahadaw.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.tahadaw.Model.enums.PaymentStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PaymentDTOOut {

    private Long id;
    private Long amountMinor;
    private String currency;
    private PaymentStatus status;
    private String provider;
    private String transactionId;
    private String moyasarStatus;
    private LocalDateTime createdAt;
}
