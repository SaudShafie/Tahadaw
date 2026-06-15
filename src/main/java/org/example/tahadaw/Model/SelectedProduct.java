package org.example.tahadaw.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "gift_idea_recommendation_id", nullable = false, unique = true)
    @JsonIgnore
    private GiftIdeaRecommendation giftIdeaRecommendation;

    @Column(columnDefinition = "varchar(300) not null")
    private String title;

    @Column(columnDefinition = "bigint")
    private Long priceMinor;

    @Column(columnDefinition = "varchar(3)")
    private String currency;

    @Column(columnDefinition = "varchar(2048)")
    private String imageUrl;

    @Column(columnDefinition = "varchar(2048)")
    private String productUrl;

    @Column(columnDefinition = "varchar(100)")
    private String sourceName;

    @Column(columnDefinition = "double")
    private Double rating;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
