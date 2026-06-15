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
public class SurprisePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "gift_plan_id", nullable = false, unique = true)
    @JsonIgnore
    private GiftPlan giftPlan;

    @Column(columnDefinition = "varchar(200) not null")
    private String planTitle;

    @Column(columnDefinition = "text not null")
    private String steps;

    @Column(columnDefinition = "text")
    private String requiredItems;

    @Column(columnDefinition = "text")
    private String timingSuggestion;

    @Column(columnDefinition = "text")
    private String backupPlan;

    @Column(columnDefinition = "text")
    private String aiExplanation;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
