package org.example.tahadaw.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Recipient recipient;

        @Column(columnDefinition = "varchar(30) not null")
    private String occasionType;

    @Column(columnDefinition = "date")
    private LocalDate occasionDate;

    @Column(columnDefinition = "bigint not null")
    private Long budgetMinor;

    @Column(columnDefinition = "varchar(3) not null")
    private String currency;

        @Column(columnDefinition = "varchar(20)")
    private String preferredGiftStyle;

    @Column(columnDefinition = "varchar(10)")
    private String language;

        @Column(columnDefinition = "varchar(40) not null")
    private String status;

    @Column(columnDefinition = "text")
    private String recommendationSummary;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "datetime not null")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "selected_gift_idea_id")
    private GiftIdeaRecommendation selectedGiftIdea;

    @OneToMany(mappedBy = "giftPlan", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<RequiredQuestionAnswer> requiredQuestionAnswers;

    @OneToMany(mappedBy = "giftPlan", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<AiGeneratedQuestion> aiGeneratedQuestions;

    @OneToMany(mappedBy = "giftPlan", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<GiftIdeaRecommendation> giftIdeaRecommendations;

    @OneToMany(mappedBy = "giftPlan", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<GiftMessage> giftMessages;

    @OneToOne(mappedBy = "giftPlan", cascade = CascadeType.ALL)
    @JsonIgnore
    private SurprisePlan surprisePlan;

    @OneToOne(mappedBy = "giftPlan", cascade = CascadeType.ALL)
    @JsonIgnore
    private GiftCard giftCard;

    @OneToMany(mappedBy = "giftPlan", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Reminder> reminders;
}
