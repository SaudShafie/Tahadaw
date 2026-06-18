package org.example.tahadaw.DTO.IN;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCardSendEmailDTOIn {

    @Email
    private String email;
}
