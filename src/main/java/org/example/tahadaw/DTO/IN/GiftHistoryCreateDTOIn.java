package org.example.tahadaw.DTO.IN;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftHistoryCreateDTOIn {

    @NotNull
    private Long recipientId;

    private Long giftIdeaRecommendationId;

    @NotBlank
    private String giftName;

    private String occasionType;
    private LocalDate giftDate;
    private Long priceMinor;
    private Boolean wasGifted;
    private Integer userRating;
    private String notes;
}
