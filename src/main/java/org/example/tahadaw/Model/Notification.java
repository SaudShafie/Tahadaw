package org.example.tahadaw.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tahadaw.Model.enums.NotificationStatus;
import org.example.tahadaw.Model.enums.NotificationType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(columnDefinition = "varchar(200) not null")
    private String title;

    @Column(columnDefinition = "text not null")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(40) not null")
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) not null")
    private NotificationStatus status;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
