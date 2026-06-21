package org.example.tahadaw.Controller;

import lombok.RequiredArgsConstructor;
import org.example.tahadaw.Api.ApiResponse;
import org.example.tahadaw.DTO.OUT.SelectedProductDTOOut;
import org.example.tahadaw.Model.User;
import org.example.tahadaw.Service.ProductSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/selected-products")
@RequiredArgsConstructor
public class SelectedProductController {

    private final ProductSearchService productSearchService;

    @PostMapping("/select-product/{productId}")
    public ResponseEntity<?> selectProduct(@AuthenticationPrincipal User user,
                                           @PathVariable Long productId) {
        productSearchService.selectProduct(user.getId(),productId);
        return ResponseEntity.status(200).body(new ApiResponse("Product selected successfully."));
    }

    @GetMapping("/get-selected-product/{giftPlanId}")
    public ResponseEntity<SelectedProductDTOOut> getSelectedProduct(@AuthenticationPrincipal User user,
                                                                    @PathVariable Long giftPlanId) {
        return ResponseEntity.status(200).body(productSearchService.getSelectedProduct(user.getId(), giftPlanId));
    }

    @DeleteMapping("/clear-selected-product/{giftPlanId}")
    public ResponseEntity<?> clearSelectedProduct(@AuthenticationPrincipal User user,
                                                  @PathVariable Long giftPlanId) {
        productSearchService.clearSelectedProduct(user.getId(), giftPlanId);
        return ResponseEntity.status(200).body(new ApiResponse("Selected product cleared successfully."));
    }
}
