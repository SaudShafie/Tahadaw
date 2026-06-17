package org.example.tahadaw.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupGiftOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_gift_id", nullable = false)
    @JsonIgnore
    private GroupGift groupGift;

    @Column(columnDefinition = "varchar(200) not null")
    private String giftName;

    @Column(columnDefinition = "text")
    private String description;

        @Column(columnDefinition = "varchar(20)")
    private String priceBand;

    @Column(columnDefinition = "text")
    private String reason;

    @Column(updatable = false, columnDefinition = "datetime not null")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "groupGiftOption", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<GroupGiftVote> votes;
}
