package org.example.tahadaw.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftHistory {

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

    @OneToOne
    @JoinColumn(name = "gift_idea_recommendation_id", unique = true)
    private GiftIdeaRecommendation giftIdeaRecommendation;

    @Column(columnDefinition = "varchar(200) not null")
    private String giftName;

        @Column(columnDefinition = "varchar(30)")
    private String occasionType;

    @Column(columnDefinition = "date")
    private LocalDate giftDate;

    @Column(columnDefinition = "bigint")
    private Double priceMinor;

    @Column(columnDefinition = "boolean")
    private Boolean wasGifted;

    @Column(columnDefinition = "int")
    private Integer userRating;

    @Column(columnDefinition = "text")
    private String notes;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
