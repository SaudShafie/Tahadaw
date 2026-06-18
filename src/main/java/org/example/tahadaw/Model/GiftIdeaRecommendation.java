package org.example.tahadaw.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftIdeaRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gift_plan_id", nullable = false)
    @JsonIgnore
    private GiftPlan giftPlan;

    @Column(columnDefinition = "varchar(200) not null")
    private String productName;

    @Column(columnDefinition = "varchar(100)")
    private String category;

        @Column(columnDefinition = "varchar(20)")
    private String priceBand;

    @Column(columnDefinition = "text")
    private String reason;

        @Column(columnDefinition = "varchar(255)")
    private String emotionalFit;

        @Column(columnDefinition = "varchar(255)")
    private String practicalFit;


    @Column(columnDefinition = "text")
    private String aiExplanation;


    @Column(columnDefinition = "boolean not null")
    private Boolean isSelected;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "giftIdeaRecommendation", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<SelectedProduct> selectedProducts;

    @OneToOne(mappedBy = "giftIdeaRecommendation", cascade = CascadeType.ALL)
    @JsonIgnore
    private GiftHistory giftHistory;
}
