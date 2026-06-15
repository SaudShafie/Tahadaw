package org.example.tahadaw.Repository;

import org.example.tahadaw.Model.GiftIdeaRecommendation;
import org.example.tahadaw.Model.GiftPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GiftIdeaRecommendationRepository extends JpaRepository<GiftIdeaRecommendation, Long> {

    Optional<GiftIdeaRecommendation> findByGiftPlanAndIsSelectedTrue(GiftPlan giftPlan);
}
