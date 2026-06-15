package org.example.tahadaw.DTO.IN;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeGenerateDTOIn {

    @NotBlank
    private String url;
}
