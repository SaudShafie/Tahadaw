package org.example.tahadaw.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tahadaw.DTO.IN.ProductSelectDTOIn;
import org.example.tahadaw.DTO.OUT.ProductSearchResultDTOOut;
import org.example.tahadaw.DTO.OUT.SelectedProductDTOOut;
import org.example.tahadaw.Service.ProductSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gift-plans")
@RequiredArgsConstructor
public class GiftPlanProductController {

    private final ProductSearchService productSearchService;

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
}
