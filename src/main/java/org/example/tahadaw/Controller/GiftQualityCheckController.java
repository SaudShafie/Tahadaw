package org.example.tahadaw.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tahadaw.Api.ApiResponse;
import org.example.tahadaw.Model.GiftQualityCheck;
import org.example.tahadaw.Service.GiftQualityCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gift-quality-checks")
@RequiredArgsConstructor
public class GiftQualityCheckController {


    private final GiftQualityCheckService giftQualityCheckService;

    @PostMapping("/add/{userId}/{recipientId}")
    public ResponseEntity<?> runGiftQualityCheck(@PathVariable Long userId,
                                                 @PathVariable Long recipientId,
                                                 @RequestBody @Valid GiftQualityCheck giftQualityCheck) {

        giftQualityCheckService.runGiftQualityCheck(userId, recipientId, giftQualityCheck);
        return ResponseEntity.status(200).body(new ApiResponse("Gift quality check completed successfully"));
    }


    @GetMapping("/recipients/{recipientId}/gift-quality-checks")
    public ResponseEntity<?> getGiftQualityChecksByRecipient(@PathVariable Long recipientId,
                                                             @RequestParam Long userId) {

        return ResponseEntity.status(200).body(giftQualityCheckService.getGiftQualityChecksByRecipient(userId, recipientId));
    }

    @GetMapping("/gift-quality-checks/{checkId}")
    public ResponseEntity<?> getGiftQualityCheckById(@PathVariable Long checkId,
                                                     @RequestParam Long userId) {

        return ResponseEntity.status(200).body(giftQualityCheckService.getGiftQualityCheckById(userId, checkId));
    }
}
