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
public class AiGeneratedQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gift_plan_id", nullable = false)
    @JsonIgnore
    private GiftPlan giftPlan;

    @Column(columnDefinition = "text not null")
    private String questionText;

    @Column(columnDefinition = "text")
    private String reasonForQuestion;

    @Column(columnDefinition = "int not null")
    private Integer displayOrder;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "aiGeneratedQuestion", cascade = CascadeType.ALL)
    @JsonIgnore
    private AiQuestionAnswer answer;
}
