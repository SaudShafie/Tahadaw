package org.example.tahadaw.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tahadaw.DTO.IN.MoyasarCardPaymentDTOIn;
import org.example.tahadaw.DTO.IN.MoyasarWebhookDTOIn;
import org.example.tahadaw.DTO.OUT.PaymentDTOOut;
import org.example.tahadaw.DTO.OUT.PremiumStatusDTOOut;
import org.example.tahadaw.Model.User;
import org.example.tahadaw.Service.PaymentService;
import org.example.tahadaw.Service.PremiumService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PremiumService premiumService;

    @PostMapping("/payments/premium")
    public ResponseEntity<PaymentDTOOut> processPremiumPayment(@AuthenticationPrincipal User user,
                                                               @Valid @RequestBody MoyasarCardPaymentDTOIn request) {
        return ResponseEntity.ok(paymentService.processPremiumPayment(user.getId(), request));
    }

    @GetMapping("/payments/my")
    public ResponseEntity<List<PaymentDTOOut>> getMyPayments(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(paymentService.getMyPayments(user.getId()));
    }

    @GetMapping("/premium/status")
    public ResponseEntity<PremiumStatusDTOOut> getPremiumStatus(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(premiumService.getPremiumStatus(user.getId()));
    }

    @PostMapping("/payments/webhook/moyasar")
    public ResponseEntity<PaymentDTOOut> moyasarWebhook(@Valid @RequestBody MoyasarWebhookDTOIn webhook) {
        return ResponseEntity.ok(paymentService.syncPaymentFromWebhook(webhook));
    }

    @GetMapping("/payments/moyasar-status/{id}")
    public ResponseEntity<PaymentDTOOut> getMoyasarPaymentStatus(@PathVariable String id) {
        return ResponseEntity.ok(paymentService.refreshMoyasarPaymentStatus(id));
    }
}
