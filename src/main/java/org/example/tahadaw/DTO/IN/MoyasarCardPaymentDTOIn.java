package org.example.tahadaw.DTO.IN;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoyasarCardPaymentDTOIn {

    @NotNull
    private Long userId;

    @NotBlank
    private String name;

    @NotBlank
    private String number;

    @NotBlank
    private String cvc;

    @NotBlank
    private String month;

    @NotBlank
    private String year;

    private String callbackUrl;
}
