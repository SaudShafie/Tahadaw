package org.example.tahadaw.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GiftHistoryDTOOut {

    private Long id;
    private Long userId;
    private Long recipientId;
    private Long giftIdeaRecommendationId;
    private String giftName;
    private String occasionType;
    private LocalDate giftDate;
    private Double priceMinor;
    private Boolean wasGifted;
    private Integer userRating;
    private String notes;
    private LocalDateTime createdAt;
}
