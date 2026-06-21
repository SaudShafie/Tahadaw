package org.example.tahadaw.Service;

import lombok.RequiredArgsConstructor;
import org.example.tahadaw.Api.ApiException;
import org.example.tahadaw.DTO.IN.ProductSelectDTOIn;
import org.example.tahadaw.DTO.OUT.ProductSearchResultDTOOut;
import org.example.tahadaw.DTO.OUT.SelectedProductDTOOut;
import org.example.tahadaw.Model.GiftIdeaRecommendation;
import org.example.tahadaw.Model.GiftPlan;
import org.example.tahadaw.Model.SelectedProduct;
import org.example.tahadaw.Repository.GiftIdeaRecommendationRepository;
import org.example.tahadaw.Repository.GiftPlanRepository;
import org.example.tahadaw.Repository.SelectedProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private static final JsonMapper JSON = JsonMapper.builder().build();

    private final GiftPlanRepository giftPlanRepository;
    private final GiftIdeaRecommendationRepository giftIdeaRecommendationRepository;
    private final SelectedProductRepository selectedProductRepository;


    @Transactional
    public void selectProduct(Long userId ,Long productId) {
        SelectedProduct selectedProduct=selectedProductRepository.findSelectedProductById(productId);
        if(selectedProduct==null){
            throw new ApiException("Product not found");
        }

        GiftIdeaRecommendation recommendation = giftIdeaRecommendationRepository.findGiftIdeaRecommendationById(selectedProduct.getGiftIdeaRecommendation().getId())
                .orElseThrow(() -> new ApiException("Gift idea recommendation not found."));

        GiftPlan giftPlan = recommendation.getGiftPlan();
        if (!giftPlan.getUser().getId().equals(userId)) {
            throw new ApiException("Gift plan is not yours");
        }
        List<SelectedProduct> selectedIdea = selectedProductRepository
                .findSelectedProductByGiftIdeaRecommendationAndIsSelectedTrue(recommendation);


        if (!selectedIdea.isEmpty()) {
            throw new ApiException("You already selected a product for this gift plan.");
        }

        List<SelectedProduct> productIdeas = selectedProductRepository
                .findSelectedProductByGiftIdeaRecommendation(recommendation);

        if (productIdeas.isEmpty()) {
            throw new ApiException("You have to generate a product recommendation before selecting a product.");
        }


        giftPlan.setSelectedProduct(selectedProduct);
        selectedProduct.setIsSelected(true);
        if (selectedProduct.getCreatedAt() == null) {
            selectedProduct.setCreatedAt(LocalDateTime.now());
        }
        selectedProduct.setGiftPlan(giftPlan);
        selectedProduct.setUser(giftPlan.getUser());
        selectedProduct.setRecipient(giftPlan.getRecipient());
        selectedProductRepository.save(selectedProduct);
        selectedProductRepository.deleteSelectedProductsByGiftIdeaRecommendation(recommendation);
        if (giftPlan.getStatus() == "GIFT_IDEA_SELECTED") {
            giftPlan.setStatus("PRODUCT_SELECTED");
        }
        giftPlan.setUpdatedAt(LocalDateTime.now());
        giftPlanRepository.save(giftPlan);
    }



    public SelectedProductDTOOut getSelectedProduct(Long userId, Long giftPlanId) {
        GiftPlan giftPlan = giftPlanRepository.findGiftPlanById(giftPlanId)
                .orElseThrow(() -> new ApiException("Gift plan not found."));
        if (!giftPlan.getUser().getId().equals(userId)) {
            throw new ApiException("Gift plan not found.");
        }

        SelectedProduct selectedProduct = selectedProductRepository
                .findSelectedProductByGiftPlan(giftPlan);

        if (selectedProduct == null) {
            throw new ApiException("No product selected yet for this gift plan.");
        }

        return toSelectedProductDto(selectedProduct);
    }

    /**
     * Clears the chosen product for a gift plan so the user can search and pick again.
     * Resets the plan status back to GIFT_IDEA_SELECTED.
     */
    @Transactional
    public void clearSelectedProduct(Long userId, Long giftPlanId) {
        GiftPlan giftPlan = giftPlanRepository.findGiftPlanById(giftPlanId)
                .orElseThrow(() -> new ApiException("Gift plan not found."));
        if (!giftPlan.getUser().getId().equals(userId)) {
            throw new ApiException("Gift plan is not yours");
        }

        SelectedProduct selectedProduct = selectedProductRepository
                .findSelectedProductByGiftPlan(giftPlan);
        if (selectedProduct == null) {
            throw new ApiException("No product selected yet for this gift plan.");
        }

        // break the link on both sides before removing the row
        giftPlan.setSelectedProduct(null);
        selectedProduct.setGiftPlan(null);
        selectedProductRepository.delete(selectedProduct);
        selectedProductRepository.flush();

        if ("PRODUCT_SELECTED".equals(giftPlan.getStatus())) {
            giftPlan.setStatus("GIFT_IDEA_SELECTED");
        }
        giftPlan.setUpdatedAt(LocalDateTime.now());
        giftPlanRepository.save(giftPlan);
    }

   
    private ProductSearchResultDTOOut mapShoppingResult(JsonNode item, String defaultCurrency) {
        String title = item.path("title").asString(null);
        String productUrl = item.path("link").asString(null);
        if (title == null || title.isBlank() || productUrl == null || productUrl.isBlank()) {
            return null;
        }

        String priceLabel = item.path("price").asString(null);
        Double extractedPrice = item.path("extracted_price").isNumber()
                ? item.path("extracted_price").asDouble()
                : null;
        Long priceMinor = extractedPrice != null ? Math.round(extractedPrice * 100) : null;

        String imageUrl = item.path("thumbnail").asString(null);
        if (imageUrl == null || imageUrl.isBlank()) {
            imageUrl = item.path("image").asString(null);
        }

        String sourceName = item.path("seller").asString(null);
        Double rating = item.path("rating").isNumber() ? item.path("rating").asDouble() : null;

        return new ProductSearchResultDTOOut(
                title,
                priceMinor,
                defaultCurrency,
                priceLabel,
                imageUrl,
                productUrl,
                sourceName,
                rating
        );
    }

    private SelectedProductDTOOut toSelectedProductDto(SelectedProduct selectedProduct) {
        return new SelectedProductDTOOut(
                selectedProduct.getId(),
                selectedProduct.getProductName(),
                selectedProduct.getPrice(),
                selectedProduct.getCurrency(),
                selectedProduct.getImageUrl(),
                selectedProduct.getProductUrl(),
                selectedProduct.getStoreName(),
                selectedProduct.getRating(),
                selectedProduct.getCreatedAt()
        );
    }
}
