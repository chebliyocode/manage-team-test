package com.manage.team.application;


import com.manage.team.application.dto.PlayerDto;
import com.manage.team.application.dto.TeamDto;
import com.manage.team.domain.model.Player;
import com.manage.team.domain.model.Team;
import com.manage.team.domain.usecase.player.PlayerCommand;
import com.manage.team.domain.usecase.team.TeamCommand;

/**

 The Mapper interface provides static methods to map between DTOs (Data Transfer Objects) and domain entities or commands.
 */
public interface Mapper {

    /**

     Converts a PlayerDto to a PlayerCommand.
     @param dto the PlayerDto to be converted
     @return a PlayerCommand instance
     */
    static PlayerCommand toCommand(PlayerDto dto) {
        Player player = new Player();
        player.setPlayerId(dto.getPlayerId());
        player.setName(dto.getName());
        player.setPosition(dto.getPosition());
        return new PlayerCommand(player);
    }
    /**

     Converts a TeamDto to a TeamCommand.
     @param dto the TeamDto to be converted
     @return a TeamCommand instance
     */
    static TeamCommand toCommand(TeamDto dto) {
        Team team = new Team();
        team.setId(dto.getId());
        team.setAcronym(dto.getAcronym());
        team.setName(dto.getName());
        team.setPlayers(dto.getPlayers().stream().map(Mapper::map).toList());
        team.setBudget(dto.getBudget());
        return new TeamCommand(team);
    }
    /**

     Maps a Player entity to a PlayerDto.
     @param entity the Player entity to be mapped
     @return a PlayerDto instance
     */
    static PlayerDto map(Player entity) {
        PlayerDto dto = new PlayerDto();
        dto.setPlayerId(entity.getPlayerId());
        dto.setName(entity.getName());
        dto.setPosition(entity.getPosition());
        return dto;
    }
    /**

     Maps a PlayerDto to a Player entity.
     @param dto the PlayerDto to be mapped
     @return a Player entity instance
     */
    static Player map(PlayerDto dto) {
        Player player = new Player();
        player.setPlayerId(dto.getPlayerId());
        player.setName(dto.getName());
        player.setPosition(dto.getPosition());
        return player;
    }
    /**

     Maps a Team entity to a TeamDto.
     @param entity the Team entity to be mapped
     @return a TeamDto instance
     */
    static TeamDto map(Team entity) {
        TeamDto dto = new TeamDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAcronym(entity.getAcronym());
        dto.setBudget(entity.getBudget());
        dto.setPlayers(entity.getPlayers().stream().map(Mapper::map).toList());
        return dto;
    }
}