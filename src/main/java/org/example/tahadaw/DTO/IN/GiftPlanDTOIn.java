package org.example.tahadaw.DTO.IN;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftPlanDTOIn {

    @NotNull
    private String occasionType;

    @NotNull
    @FutureOrPresent
    private LocalDate occasionDate;

    //requeired Q
    @Positive
    private Long budget;
    //set in service
    @NotEmpty(message = "Currency cannot be empty")
    @Size(min = 3, max = 3)
    private String currency;

    //requiredQ
    private String preferredGiftStyle;

    @Size(max = 10)
    private String language;
}
