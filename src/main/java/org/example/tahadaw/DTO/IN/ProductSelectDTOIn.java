package org.example.tahadaw.DTO.IN;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSelectDTOIn {

    @NotBlank
    private String title;

    private Long priceMinor;

    private String currency;

    private String imageUrl;

    @NotBlank
    private String productUrl;

    private String sourceName;

    private Double rating;
}
