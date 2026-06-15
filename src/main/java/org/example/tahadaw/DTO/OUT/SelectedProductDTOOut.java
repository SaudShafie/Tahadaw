package org.example.tahadaw.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SelectedProductDTOOut {

    private Long id;
    private String title;
    private Long priceMinor;
    private String currency;
    private String imageUrl;
    private String productUrl;
    private String sourceName;
    private Double rating;
    private LocalDateTime createdAt;
}
