package org.example.tahadaw.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tahadaw.DTO.IN.GiftMessageGenerateDTOIn;
import org.example.tahadaw.DTO.IN.ProductSelectDTOIn;
import org.example.tahadaw.DTO.OUT.GiftHistoryDTOOut;
import org.example.tahadaw.DTO.OUT.GiftMessageDTOOut;
import org.example.tahadaw.DTO.OUT.ProductSearchResultDTOOut;
import org.example.tahadaw.DTO.OUT.SelectedProductDTOOut;
import org.example.tahadaw.Service.GiftHistoryService;
import org.example.tahadaw.Service.GiftMessageService;
import org.example.tahadaw.Service.ProductSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gift-plans")
@RequiredArgsConstructor
public class GiftPlanController {

    private final ProductSearchService productSearchService;
    private final GiftMessageService giftMessageService;
    private final GiftHistoryService giftHistoryService;

    @PostMapping("/{giftPlanId}/products/search")
    public ResponseEntity<List<ProductSearchResultDTOOut>> searchProducts(@PathVariable Long giftPlanId) {
        return ResponseEntity.ok(productSearchService.searchProducts(giftPlanId));
    }

    @PostMapping("/{giftPlanId}/products/select")
    public ResponseEntity<SelectedProductDTOOut> selectProduct(@PathVariable Long giftPlanId,
                                                               @Valid @RequestBody ProductSelectDTOIn request) {
        return ResponseEntity.ok(productSearchService.selectProduct(giftPlanId, request));
    }

    @GetMapping("/{giftPlanId}/selected-product")
    public ResponseEntity<SelectedProductDTOOut> getSelectedProduct(@PathVariable Long giftPlanId) {
        return ResponseEntity.ok(productSearchService.getSelectedProduct(giftPlanId));
    }

    @PostMapping("/{giftPlanId}/messages/generate")
    public ResponseEntity<GiftMessageDTOOut> generateMessage(@RequestParam Long userId,
                                                             @PathVariable Long giftPlanId,
                                                             @Valid @RequestBody GiftMessageGenerateDTOIn request) {
        return ResponseEntity.ok(giftMessageService.generate(userId, giftPlanId, request));
    }

    @GetMapping("/{giftPlanId}/messages")
    public ResponseEntity<List<GiftMessageDTOOut>> listMessages(@RequestParam Long userId,
                                                                @PathVariable Long giftPlanId) {
        return ResponseEntity.ok(giftMessageService.listByGiftPlan(userId, giftPlanId));
    }

    @PostMapping("/{giftPlanId}/history")
    public ResponseEntity<GiftHistoryDTOOut> saveHistoryFromPlan(@RequestParam Long userId,
                                                                 @PathVariable Long giftPlanId) {
        return ResponseEntity.ok(giftHistoryService.saveFromPlan(userId, giftPlanId));
    }
}
