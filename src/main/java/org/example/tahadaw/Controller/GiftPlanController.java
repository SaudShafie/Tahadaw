package org.example.tahadaw.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tahadaw.Api.ApiResponse;
import org.example.tahadaw.DTO.IN.GiftMessageCreateDTOIn;
import org.example.tahadaw.DTO.IN.GiftMessageGenerateDTOIn;
import org.example.tahadaw.DTO.IN.GiftPlanDTOIn;
import org.example.tahadaw.DTO.IN.ProductSelectDTOIn;
import org.example.tahadaw.DTO.IN.AiQuestionAnswersSubmitDTOIn;
import org.example.tahadaw.DTO.IN.GiftCardCreateDTOIn;
import org.example.tahadaw.DTO.IN.RequiredQuestionAnswersSubmitDTOIn;
import org.example.tahadaw.DTO.IN.SurprisePlanGenerateDTOIn;
import org.example.tahadaw.DTO.OUT.GiftCardDTOOut;
import org.example.tahadaw.DTO.OUT.AiGeneratedQuestionDTOOut;
import org.example.tahadaw.DTO.OUT.AiQuestionAnswerDTOOut;
import org.example.tahadaw.DTO.OUT.GiftHistoryDTOOut;
import org.example.tahadaw.DTO.OUT.GiftMessageDTOOut;
import org.example.tahadaw.DTO.OUT.RequiredQuestionAnswerDTOOut;
import org.example.tahadaw.DTO.OUT.RequiredQuestionDTOOut;
import org.example.tahadaw.DTO.OUT.SelectedProductDTOOut;
import org.example.tahadaw.DTO.OUT.SurprisePlanDTOOut;
import org.example.tahadaw.Model.GiftPlan;
import org.example.tahadaw.Service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gift-plans")
@RequiredArgsConstructor
public class GiftPlanController {

    private final GiftPlanService giftPlanService;
    private final RequiredQuestionService requiredQuestionService;
    private final RequiredQuestionAnswerService requiredQuestionAnswerService;
    private final AiQuestionService aiQuestionService;
    private final ProductSearchService productSearchService;
    private final GiftMessageService giftMessageService;
    private final GiftHistoryService giftHistoryService;
    private final SurprisePlanService surprisePlanService;
    private final GiftRecommendationService giftRecommendationService;
    private final GiftCardService giftCardService;

    // ===== Gift plan CRUD =====
    // NOTE: both endpoint styles kept after the Shahad merge; API design to be unified later.

    // Saud style
    @PostMapping
    public ResponseEntity<GiftPlan> create(@RequestParam Long userId,
                                           @RequestParam Long recipientId,
                                           @RequestBody @Valid GiftPlanDTOIn request) {
        return ResponseEntity.ok(giftPlanService.createGiftPlan(userId, recipientId, request));
    }

    // Shahad style
    @PostMapping("/create/{userId}/{recipientId}")
    public ResponseEntity<?> createByPath(@PathVariable Long userId,
                                          @PathVariable Long recipientId,
                                          @RequestBody @Valid GiftPlanDTOIn request) {
        giftPlanService.createGiftPlan(userId, recipientId, request);
        return ResponseEntity.status(200).body(new ApiResponse("Gift plan created successfully."));
    }

    // Saud style
    @GetMapping
    public ResponseEntity<List<GiftPlan>> listMine(@RequestParam Long userId) {
        return ResponseEntity.ok(giftPlanService.listByUser(userId));
    }

    // Shahad style
    @GetMapping("/get-my-plans/{userId}")
    public ResponseEntity<List<GiftPlan>> listMineByPath(@PathVariable Long userId) {
        return ResponseEntity.ok(giftPlanService.listByUser(userId));
    }

    @GetMapping("/{giftPlanId}")
    public ResponseEntity<GiftPlan> getOne(@RequestParam Long userId,
                                           @PathVariable Long giftPlanId) {
        return ResponseEntity.ok(giftPlanService.getGiftPlanById(userId, giftPlanId));
    }

    @PutMapping("/{giftPlanId}")
    public ResponseEntity<GiftPlan> update(@RequestParam Long userId,
                                           @PathVariable Long giftPlanId,
                                           @RequestBody @Valid GiftPlanDTOIn request) {
        return ResponseEntity.ok(giftPlanService.updateGiftPlan(userId, giftPlanId, request));
    }

    @DeleteMapping("/{giftPlanId}")
    public ResponseEntity<Void> delete(@RequestParam Long userId,
                                       @PathVariable Long giftPlanId) {
        giftPlanService.deleteGiftPlan(userId, giftPlanId);
        return ResponseEntity.noContent().build();
    }

    // ===== Required questions / answers =====

    @GetMapping("/{giftPlanId}/required-questions")
    public ResponseEntity<List<RequiredQuestionDTOOut>> listRequiredQuestions(@RequestParam Long userId,
                                                                              @PathVariable Long giftPlanId) {
        return ResponseEntity.ok(requiredQuestionService.listActiveForGiftPlan(userId, giftPlanId));
    }

    @PostMapping("/{giftPlanId}/required-answers")
    public ResponseEntity<List<RequiredQuestionAnswerDTOOut>> submitRequiredAnswers(
            @RequestParam Long userId,
            @PathVariable Long giftPlanId,
            @RequestBody @Valid RequiredQuestionAnswersSubmitDTOIn request) {
        return ResponseEntity.ok(requiredQuestionAnswerService.submitAnswers(userId, giftPlanId, request));
    }

    @GetMapping("/{giftPlanId}/required-answers")
    public ResponseEntity<List<RequiredQuestionAnswerDTOOut>> listRequiredAnswers(@RequestParam Long userId,
                                                                                  @PathVariable Long giftPlanId) {
        return ResponseEntity.ok(requiredQuestionAnswerService.listByGiftPlan(userId, giftPlanId));
    }


    // ===== Messages =====

    @PostMapping("/{giftPlanId}/messages/generate")
    public ResponseEntity<GiftMessageDTOOut> generateMessage(@RequestParam Long userId,
                                                             @PathVariable Long giftPlanId,
                                                             @Valid @RequestBody GiftMessageGenerateDTOIn request) {
        return ResponseEntity.ok(giftMessageService.generate(userId, giftPlanId, request));
    }

    // User writes their own message (no AI). Returns a giftMessageId usable when creating the gift card.
    @PostMapping("/{giftPlanId}/messages")
    public ResponseEntity<GiftMessageDTOOut> writeMessage(@RequestParam Long userId,
                                                          @PathVariable Long giftPlanId,
                                                          @Valid @RequestBody GiftMessageCreateDTOIn request) {
        return ResponseEntity.ok(giftMessageService.createManual(userId, giftPlanId, request));
    }

    @GetMapping("/{giftPlanId}/messages")
    public ResponseEntity<List<GiftMessageDTOOut>> listMessages(@RequestParam Long userId,
                                                                @PathVariable Long giftPlanId) {
        return ResponseEntity.ok(giftMessageService.listByGiftPlan(userId, giftPlanId));
    }

    // ===== History =====

    @PostMapping("/{giftPlanId}/history")
    public ResponseEntity<GiftHistoryDTOOut> saveHistoryFromPlan(@RequestParam Long userId,
                                                                 @PathVariable Long giftPlanId) {
        return ResponseEntity.ok(giftHistoryService.saveFromPlan(userId, giftPlanId));
    }

    // ===== Surprise plan (Saud) =====

    @PostMapping("/{giftPlanId}/surprise-plan/generate")
    public ResponseEntity<SurprisePlanDTOOut> generateSurprisePlan(
            @RequestParam Long userId,
            @PathVariable Long giftPlanId,
            @RequestBody(required = false) SurprisePlanGenerateDTOIn request) {
        return ResponseEntity.ok(surprisePlanService.generate(userId, giftPlanId, request));
    }

    @GetMapping("/{giftPlanId}/surprise-plan")
    public ResponseEntity<SurprisePlanDTOOut> getSurprisePlan(@RequestParam Long userId,
                                                              @PathVariable Long giftPlanId) {
        return ResponseEntity.ok(surprisePlanService.getByGiftPlan(userId, giftPlanId));
    }

    @PostMapping("/{giftPlanId}/gift-card")
    public ResponseEntity<GiftCardDTOOut> createGiftCard(@RequestParam Long userId,
                                                         @PathVariable Long giftPlanId,
                                                         @Valid @RequestBody GiftCardCreateDTOIn request) {
        request.setGiftPlanId(giftPlanId);
        return ResponseEntity.ok(giftCardService.create(userId, request));
    }
}
