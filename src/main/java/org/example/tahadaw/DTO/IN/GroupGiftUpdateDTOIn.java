package org.example.tahadaw.DTO.IN;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupGiftUpdateDTOIn {

    private String title;
    private String description;
    private String responsiblePersonName;
    private String responsiblePersonEmail;
    private LocalDate giftGivingDate;
    private LocalDateTime votingDeadline;
    private String status;
}
