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
public class GiftMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gift_plan_id", nullable = false)
    @JsonIgnore
    private GiftPlan giftPlan;

    @Column(columnDefinition = "varchar(50)")
    private String tone;

    @Column(columnDefinition = "varchar(10)")
    private String language;

    @Column(columnDefinition = "text not null")
    private String messageText;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "giftMessage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private GiftCard giftCard;
}
