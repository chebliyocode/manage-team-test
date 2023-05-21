package com.manage.team.domain.model;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Data
@NoArgsConstructor
@Builder
public class Player {

    private String playerId;
    private String name;
    private String position;
}
