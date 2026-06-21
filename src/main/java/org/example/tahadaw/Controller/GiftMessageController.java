package org.example.tahadaw.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tahadaw.DTO.IN.GiftMessageCreateDTOIn;
import org.example.tahadaw.DTO.IN.GiftMessageFromPlanDTOIn;
import org.example.tahadaw.DTO.IN.GiftMessageGenerateDTOIn;
import org.example.tahadaw.DTO.IN.GiftMessageUpdateDTOIn;
import org.example.tahadaw.DTO.OUT.GiftMessageDTOOut;
import org.example.tahadaw.Model.User;
import org.example.tahadaw.Service.GiftMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gift-messages")
@RequiredArgsConstructor
public class GiftMessageController {

    private final GiftMessageService giftMessageService;

    // AI writes the message from the context the user supplies (no gift plan).
    @PostMapping("/generate")
    public ResponseEntity<GiftMessageDTOOut> generate(@AuthenticationPrincipal User user,
                                                      @Valid @RequestBody GiftMessageGenerateDTOIn request) {
        return ResponseEntity.ok(giftMessageService.generate(user.getId(), request));
    }

    // AI writes the message using details pulled from an existing gift plan.
    // Body is optional and only carries tone/language/dialect overrides.
    @PostMapping("/generate-from-plan/{giftPlanId}")
    public ResponseEntity<GiftMessageDTOOut> generateFromPlan(@AuthenticationPrincipal User user,
                                                              @PathVariable Long giftPlanId,
                                                              @RequestBody(required = false) GiftMessageFromPlanDTOIn request) {
        return ResponseEntity.ok(giftMessageService.generateFromPlan(user.getId(), giftPlanId, request));
    }

    // User writes their own message. Body carries only the text.
    @PostMapping("/manual")
    public ResponseEntity<GiftMessageDTOOut> createManual(@AuthenticationPrincipal User user,
                                                          @Valid @RequestBody GiftMessageCreateDTOIn request) {
        return ResponseEntity.ok(giftMessageService.createManual(user.getId(), request));
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<GiftMessageDTOOut> update(@AuthenticationPrincipal User user,
                                                    @PathVariable Long messageId,
                                                    @Valid @RequestBody GiftMessageUpdateDTOIn request) {
        return ResponseEntity.ok(giftMessageService.update(user.getId(), messageId, request));
    }

    @GetMapping("/my")
    public ResponseEntity<List<GiftMessageDTOOut>> listMine(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(giftMessageService.listMine(user.getId()));
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<GiftMessageDTOOut> getOne(@AuthenticationPrincipal User user,
                                                    @PathVariable Long messageId) {
        return ResponseEntity.ok(giftMessageService.getOne(user.getId(), messageId));
    }
}
