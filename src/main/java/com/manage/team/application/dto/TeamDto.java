package com.manage.team.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private int id;
    private String name;
    private String acronym;
    private List<PlayerDto> players = List.of();
    private BigDecimal budget;
}
