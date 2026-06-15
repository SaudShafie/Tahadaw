package org.example.tahadaw.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tahadaw.Model.enums.ReminderChannel;
import org.example.tahadaw.Model.enums.ReminderStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Recipient recipient;

    @ManyToOne
    @JoinColumn(name = "gift_plan_id")
    private GiftPlan giftPlan;

    @ManyToOne
    @JoinColumn(name = "group_gift_id")
    private GroupGift groupGift;

    @Column(columnDefinition = "datetime not null")
    private LocalDateTime reminderDate;

    @Column(columnDefinition = "text")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) not null")
    private ReminderChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) not null")
    private ReminderStatus status;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
