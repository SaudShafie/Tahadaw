package org.example.tahadaw.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QrCodeDTOOut {

    private String url;
    private String contentType;
    private String base64Png;
}
