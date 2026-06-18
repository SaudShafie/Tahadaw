package org.example.tahadaw.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ShoppingResult {
    private int position;

    @JsonProperty("product_id")
    private String productId;

    private String title;

    @JsonProperty("product_link")
    private String productLink;

    private String seller;
    private String price;

    @JsonProperty("extracted_price")
    private Double extractedPrice;

    private Double rating;
    private Integer reviews;
    private String thumbnail;


}
