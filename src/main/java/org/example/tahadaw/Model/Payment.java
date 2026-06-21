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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(columnDefinition = "bigint not null")
    private Long amountMinor;

    @Column(columnDefinition = "varchar(3) not null")
    private String currency;

        @Column(columnDefinition = "varchar(20) not null")
    private String paymentType;

        @Column(columnDefinition = "varchar(20) not null")
    private String status;

    @Column(columnDefinition = "varchar(50)")
    private String provider;

    @Column(columnDefinition = "varchar(100)", unique = true)
    private String transactionId;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private PremiumAccess premiumAccess;
}
