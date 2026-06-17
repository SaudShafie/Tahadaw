package org.example.tahadaw.DTO.IN;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCardCreateDTOIn {

    @NotNull
    private Long giftPlanId;

    private Long giftMessageId;

    @NotBlank
    private String recipientName;

    @NotBlank
    private String senderName;

    private String cardSize;
    private String linkType;
    private String linkUrl;
    private String sentToEmail;
    private String status;
}
