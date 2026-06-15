package org.example.tahadaw.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.example.tahadaw.Api.ApiException;
import org.example.tahadaw.DTO.OUT.QrCodeDTOOut;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

@Service
public class QrCodeService {

    private static final int DEFAULT_SIZE = 512;

    public byte[] generateQrCodePng(String url) {
        return generateQrCodePng(url, DEFAULT_SIZE, DEFAULT_SIZE);
    }

    public byte[] generateQrCodePng(String url, int width, int height) {
        validateUrl(url);

        try {
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(url, BarcodeFormat.QR_CODE, width, height, hints);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);
            return outputStream.toByteArray();
        } catch (Exception ex) {
            throw new ApiException("Failed to generate QR code: " + ex.getMessage());
        }
    }

    public QrCodeDTOOut generateQrCodeResponse(String url) {
        byte[] pngBytes = generateQrCodePng(url);
        return new QrCodeDTOOut(
                url,
                "image/png",
                Base64.getEncoder().encodeToString(pngBytes)
        );
    }

    private void validateUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new ApiException("QR link URL is required.");
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new ApiException("QR link must be a safe http(s) URL.");
        }
    }
}
