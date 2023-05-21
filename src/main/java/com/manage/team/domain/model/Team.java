package com.manage.team.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Team {
    private int id;
    private String name;
    private String acronym;
    private List<Player> players;
    private BigDecimal budget;
}
