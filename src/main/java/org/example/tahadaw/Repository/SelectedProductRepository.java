package org.example.tahadaw.Repository;

import org.example.tahadaw.Model.GiftIdeaRecommendation;
import org.example.tahadaw.Model.SelectedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SelectedProductRepository extends JpaRepository<SelectedProduct, Long> {

    SelectedProduct findSelectedProductByGiftIdeaRecommendationAndIsSelectedTrue(GiftIdeaRecommendation giftIdeaRecommendation);

    List<SelectedProduct> findSelectedProductByGiftIdeaRecommendation(GiftIdeaRecommendation giftIdeaRecommendation);

}
