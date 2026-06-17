package org.example.tahadaw.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurprisePlanDTOOut {

    private Long id;
    private Long giftPlanId;
    private String planTitle;
    private String steps;
    private String requiredItems;
    private String timingSuggestion;
    private String backupPlan;
    private String aiExplanation;
    private LocalDateTime createdAt;
}
