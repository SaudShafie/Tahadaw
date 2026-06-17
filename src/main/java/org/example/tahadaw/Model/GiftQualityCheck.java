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
public class GiftQualityCheck {

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

    @Column(columnDefinition = "varchar(200) not null")
    private String giftName;

    @Column(columnDefinition = "text")
    private String giftDescription;

    @Column(columnDefinition = "bigint")
    private Long priceMinor;

        @Column(columnDefinition = "varchar(30)")
    private String occasionType;

        @Column(columnDefinition = "varchar(20)")
    private String suitability;


    @Column(columnDefinition = "text")
    private String strengths;

    @Column(columnDefinition = "text")
    private String weaknesses;

    @Column(columnDefinition = "text")
    private String aiAdvice;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
