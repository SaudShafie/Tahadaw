package org.example.tahadaw.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tahadaw.Model.enums.PaymentStatus;
import org.example.tahadaw.Model.enums.PaymentType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(columnDefinition = "bigint not null")
    private Long amountMinor;

    @Column(columnDefinition = "varchar(3) not null")
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) not null")
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) not null")
    private PaymentStatus status;

    @Column(columnDefinition = "varchar(50)")
    private String provider;

    @Column(columnDefinition = "varchar(100)")
    private String transactionId;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    @JsonIgnore
    private PremiumAccess premiumAccess;
}
