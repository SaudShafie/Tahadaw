package org.example.tahadaw.Controller;

import lombok.RequiredArgsConstructor;
import org.example.tahadaw.Api.ApiResponse;
import org.example.tahadaw.DTO.OUT.SelectedProductDTOOut;
import org.example.tahadaw.Service.ProductSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/selected-products")
@RequiredArgsConstructor
public class SelectedProductController {

    private final ProductSearchService productSearchService;

    @PostMapping("/select-product/{userId}/{productId}")
    public ResponseEntity<?> selectProduct(@PathVariable Long userId,
                                           @PathVariable Long productId) {
        productSearchService.selectProduct(userId,productId);
        return ResponseEntity.status(200).body(new ApiResponse("Product selected successfully."));
    }

    @GetMapping("/get-selected-product/{giftPlanId}")
    public ResponseEntity<SelectedProductDTOOut> getSelectedProduct(@PathVariable Long giftPlanId) {
        return ResponseEntity.status(200).body(productSearchService.getSelectedProduct(giftPlanId));
    }
}
