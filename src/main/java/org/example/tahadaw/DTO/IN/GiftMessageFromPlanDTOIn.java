package org.example.tahadaw.DTO.IN;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftMessageFromPlanDTOIn {

    // All optional. Everything else (recipient, occasion, gift) is pulled from the gift plan.
    // How the user wants it to sound, e.g. "warm and proud". Defaults to "warm".
    private String tone;

    // Overrides the plan's language. Defaults to the plan language, then "en".
    private String language;

    private String dialect;
}
