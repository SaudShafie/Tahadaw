package org.example.tahadaw.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PremiumStatusDTOOut {

    private boolean premium;
    private LocalDateTime activatedAt;
}
