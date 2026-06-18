package org.example.tahadaw.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GoogleShoppingResponse {

    @JsonProperty("shopping_results")
    private List<ShoppingResult> shoppingResults;

}
