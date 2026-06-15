package org.example.tahadaw.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tahadaw.DTO.IN.QrCodeGenerateDTOIn;
import org.example.tahadaw.DTO.OUT.QrCodeDTOOut;
import org.example.tahadaw.Service.QrCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/qr-code")
@RequiredArgsConstructor
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @PostMapping("/generate")
    public ResponseEntity<QrCodeDTOOut> generate(@Valid @RequestBody QrCodeGenerateDTOIn request) {
        return ResponseEntity.ok(qrCodeService.generateQrCodeResponse(request.getUrl()));
    }
}
