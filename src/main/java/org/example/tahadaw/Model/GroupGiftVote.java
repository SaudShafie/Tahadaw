package org.example.tahadaw.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_group_gift_vote_invite", columnNames = "invite_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupGiftVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_gift_id", nullable = false)
    @JsonIgnore
    private GroupGift groupGift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_gift_option_id", nullable = false)
    private GroupGiftOption groupGiftOption;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invite_id", nullable = false, unique = true)
    @JsonIgnore
    private GroupGiftInvite invite;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
