package com.manage.team.infrastructure.player;


import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(indexes = {
        @Index(name = "id_player", columnList = "playerId")
})
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playerId;
    @Column(nullable = false)
    private String name;
    private String position;
}
