package org.example.tahadaw.Service;

import lombok.RequiredArgsConstructor;
import org.example.tahadaw.AI.AiJsonParser;
import org.example.tahadaw.AI.AiService;
import org.example.tahadaw.Api.ApiException;
import org.example.tahadaw.DTO.OUT.AiQuestionAnswerDTOOut;
import org.example.tahadaw.Model.*;
import org.example.tahadaw.Repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GiftRecommendationService {

    private final GiftIdeaRecommendationRepository giftIdeaRecommendationRepository;
    private final GiftPlanRepository giftPlanRepository;
    private final AiGeneratedQuestionRepository aiGeneratedQuestionRepository;
    private final AiQuestionAnswerRepository aiQuestionAnswerRepository;
    private final RequiredQuestionAnswerRepository requiredQuestionAnswerRepository;
    private final AiService aiService;
    private final AiQuestionService aiQuestionService;
    private final ModelMapper modelMapper;

    @Transactional
    public void selectRecommendation(Long userId, Long recommendationId) {
        GiftIdeaRecommendation recommendation = giftIdeaRecommendationRepository.findGiftIdeaRecommendationById(recommendationId)
                .orElseThrow(() -> new ApiException("Gift idea recommendation not found."));

        GiftPlan giftPlan = recommendation.getGiftPlan();
        if (!giftPlan.getUser().getId().equals(userId)) {
            throw new ApiException("Gift plan is not yours");
        }
        Optional<GiftIdeaRecommendation> selectedIdea = giftIdeaRecommendationRepository
                .findByGiftPlanAndIsSelectedTrue(giftPlan);

        if (selectedIdea.isPresent()) {
            throw new ApiException("You already selected a gift idea.");
        }

        for (GiftIdeaRecommendation idea : giftIdeaRecommendationRepository.findByGiftPlan(giftPlan)) {
            idea.setIsSelected(idea.getId().equals(recommendationId));
            giftIdeaRecommendationRepository.save(idea);
        }

        giftPlan.setSelectedGiftIdea(recommendation);
        if (giftPlan.getStatus() == "RECOMMENDATIONS_GENERATED") {
            giftPlan.setStatus("GIFT_IDEA_SELECTED");
        }
        giftPlan.setUpdatedAt(LocalDateTime.now());
        giftPlanRepository.save(giftPlan);
    }

    public List<GiftIdeaRecommendation> generateGiftRecommendation(Long userId, Long giftPlanId) {
        GiftPlan giftPlan = requireOwnedGiftPlan(userId, giftPlanId);


        String prompt = buildPrompt(giftPlan);

        String response= aiService.ask(prompt);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(response);
        JsonNode rootNode = mapper.readTree(response); // or however you got your JsonNode

        JsonNode recommendationsNode = rootNode.get("recommendations");

        List<GiftIdeaRecommendation> recommendations = mapper.convertValue(
                recommendationsNode,
                new TypeReference<List<GiftIdeaRecommendation>>() {}
        );

        if (recommendationsNode == null || !recommendationsNode.isArray() || recommendationsNode.isEmpty()) {
            throw new ApiException("AI did not return any gift Recommendation.");
        }

        LocalDateTime now = LocalDateTime.now();

        List<GiftIdeaRecommendation> aiGeneratedRecommendation = new ArrayList<>();
        for (GiftIdeaRecommendation giftRecommendation : recommendations) {

            giftRecommendation.setIsSelected(false);
            giftRecommendation.setGiftPlan(giftPlan);
            giftRecommendation.setCreatedAt(now);
            giftIdeaRecommendationRepository.save(giftRecommendation);

        }

//        giftPlan.setStatus("AI_QUESTIONS_GENERATED");
        giftPlan.setUpdatedAt(now);

        giftPlanRepository.save(giftPlan);

        return recommendations;
    }

    public String buildPrompt(GiftPlan giftPlan) {
        //bring the recipient and required answers
        Recipient recipient = giftPlan.getRecipient();
        List<RequiredQuestionAnswer> requiredAnswers =
                requiredQuestionAnswerRepository.findRequiredQuestionAnswerByGiftPlan(giftPlan);

        List<AiQuestionAnswerDTOOut> aiQuestionAndAnswer=aiQuestionService.listAnswers(giftPlan.getUser().getId(), giftPlan.getId());

        StringBuilder context = new StringBuilder();

        context.append("Recipient name: ").append(recipient.getName()).append('\n');
        if (recipient.getRelationship() != null) {
            context.append("Relationship: ").append(recipient.getRelationship()).append('\n');
        }
        if (recipient.getAge() != null) {
            context.append("Age: ").append(recipient.getAge()).append('\n');
        }
        if (recipient.getGender() != null) {
            context.append("Gender: ").append(recipient.getGender()).append('\n');
        }
        if (recipient.getInterests() != null) {
            context.append("Interests: ").append(recipient.getInterests()).append('\n');
        }
        if (recipient.getHobbies() != null) {
            context.append("Hobbies: ").append(recipient.getHobbies()).append('\n');
        }
        if (recipient.getDislikes() != null) {
            context.append("Dislikes: ").append(recipient.getDislikes()).append('\n');
        }
        if (recipient.getPersonalityStyle() != null) {
            context.append("Personality: ").append(recipient.getPersonalityStyle()).append('\n');
        }
        context.append("Occasion: ").append(giftPlan.getOccasionType()).append('\n');
        if (giftPlan.getOccasionDate() != null) {
            context.append("Occasion date: ").append(giftPlan.getOccasionDate()).append('\n');
        }
        context.append("Budget minor units: ").append(giftPlan.getBudgetMinor()).append('\n');
        context.append("Currency: ").append(giftPlan.getCurrency()).append('\n');
        if (giftPlan.getPreferredGiftStyle() != null) {
            context.append("Preferred gift style: ").append(giftPlan.getPreferredGiftStyle()).append('\n');
        }

        if (!requiredAnswers.isEmpty()) {
            context.append("\nRequired question answers:\n");
            for (RequiredQuestionAnswer answer : requiredAnswers) {
                context.append("- ")
                        .append(answer.getRequiredQuestion().getQuestionText())
                        .append(": ")
                        .append(answer.getAnswerText())
                        .append('\n');
            }
        }
        if (!aiQuestionAndAnswer.isEmpty()) {
            context.append("\n helping question answers:\n");
            for (AiQuestionAnswerDTOOut QandAnswer : aiQuestionAndAnswer) {
                context.append("- ")
                        .append(QandAnswer.getQuestionText())
                        .append(": ")
                        .append(QandAnswer.getAnswerText())
                        .append('\n');
            }
        }

        return """
                 suggest gifts based on the context below.
                Return JSON only in this exact shape:
                {
                  "recommendations": [
                       {
                         "productName": "string",
                         "category": "string",
                         "priceBand": "string",
                         "reason": "string",
                         "emotionalFit": "string",
                         "practicalFit": "string",
                         "aiExplanation": "string",
                
                       }
                                  ]
                 }

                Rules:
                - Generate 3 to 5 suggested gifts
                - gifts must be relevant to the recipient and occasion
                - the response should be in Arabic language

                Context:
                %s
                """.formatted(context);
    }

    private GiftPlan requireOwnedGiftPlan(Long userId, Long giftPlanId) {
        GiftPlan giftPlan = giftPlanRepository.findGiftPlanById(giftPlanId)
                .orElseThrow(() -> new ApiException("Gift plan not found."));
        if (!giftPlan.getUser().getId().equals(userId)) {
            throw new ApiException("Gift plan not found.");
        }
        if (!giftPlan.getRecipient().getUser().getId().equals(userId)) {
            throw new ApiException("Recipient must belong to the gift plan owner.");
        }
        return giftPlan;
    }





}
