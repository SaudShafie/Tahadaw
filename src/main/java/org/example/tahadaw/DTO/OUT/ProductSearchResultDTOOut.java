package org.example.tahadaw.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductSearchResultDTOOut {

    private String title;
    private Long priceMinor;
    private String currency;
    private String priceLabel;
    private String imageUrl;
    private String productUrl;
    private String sourceName;
    private Double rating;
}
