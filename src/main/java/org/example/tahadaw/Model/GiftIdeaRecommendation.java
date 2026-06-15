package org.example.tahadaw.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tahadaw.Model.enums.FitLevel;
import org.example.tahadaw.Model.enums.PriceBand;
import org.example.tahadaw.Model.enums.RiskLevel;

import java.time.LocalDateTime;

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
    private String title;

    @Column(columnDefinition = "varchar(100)")
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20)")
    private PriceBand priceBand;

    @Column(columnDefinition = "text")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    private FitLevel emotionalFit;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    private FitLevel practicalFit;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    private RiskLevel riskLevel;

    @Column(columnDefinition = "text")
    private String aiExplanation;

    @Column(columnDefinition = "varchar(200)")
    private String searchKeyword;

    @Column(columnDefinition = "boolean not null")
    private Boolean isSelected;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "giftIdeaRecommendation", cascade = CascadeType.ALL)
    @JsonIgnore
    private SelectedProduct selectedProduct;

    @OneToOne(mappedBy = "giftIdeaRecommendation", cascade = CascadeType.ALL)
    @JsonIgnore
    private GiftHistory giftHistory;
}
