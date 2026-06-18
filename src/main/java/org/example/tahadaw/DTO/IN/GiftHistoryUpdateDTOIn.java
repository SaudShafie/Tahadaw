package org.example.tahadaw.DTO.IN;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftHistoryUpdateDTOIn {

    private String giftName;
    private String occasionType;
    private LocalDate giftDate;
    private Double priceMinor;
    private Boolean wasGifted;
    private Integer userRating;
    private String notes;
}
