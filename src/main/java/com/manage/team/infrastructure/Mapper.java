package com.manage.team.infrastructure;

import com.manage.team.domain.model.Player;
import com.manage.team.domain.model.Team;
import com.manage.team.infrastructure.player.PlayerEntity;
import com.manage.team.infrastructure.team.TeamEntity;

/**

 The Mapper class provides static methods for converting between domain objects and entity objects.
 */
public interface Mapper {

    /**

     Converts a Team object to a TeamEntity object.
     @param team the Team object to convert
     @return the converted TeamEntity object
     */
    static TeamEntity toEntity(Team team) {
        return TeamEntity.builder()
                .id(team.getId())
                .name(team.getName())
                .acronym(team.getAcronym())
                .budget(team.getBudget())
                .players(team.getPlayers().stream().map(Mapper::map).toList())
                .build();
    }
    /**

     Converts a Player object to a PlayerEntity object.
     @param player the Player object to convert
     @return the converted PlayerEntity object
     */
    static PlayerEntity toEntity(Player player) {
        return PlayerEntity.builder()
                .playerId(player.getPlayerId())
                .name(player.getName())
                .position(player.getPosition())
                .build();
    }
    /**

     Converts a TeamEntity object to a Team object.
     @param teamEntity the TeamEntity object to convert
     @return the converted Team object
     */
    static Team toDomain(TeamEntity teamEntity) {
        return Team.builder()
                .id(teamEntity.getId())
                .name(teamEntity.getName())
                .acronym(teamEntity.getAcronym())
                .budget(teamEntity.getBudget())
                .players(teamEntity.getPlayers().stream().map(Mapper::map).toList())
                .build();
    }
    /**

     Converts a PlayerEntity object to a Player object.
     @param playerEntity the PlayerEntity object to convert
     @return the converted Player object
     */
    static Player toDomain(PlayerEntity playerEntity) {
        return Player.builder()
                .playerId(playerEntity.getPlayerId())
                .name(playerEntity.getName())
                .position(playerEntity.getPosition())
                .build();
    }
    /**

     Maps a Player object to a PlayerEntity object.
     @param player the Player object to map
     @return the mapped PlayerEntity object
     */
    static PlayerEntity map(Player player) {
        return PlayerEntity.builder()
                .playerId(player.getPlayerId())
                .name(player.getName())
                .position(player.getPosition())
                .build();
    }
    /**

     Maps a PlayerEntity object to a Player object.
     @param player the PlayerEntity object to map
     @return the mapped Player object
     */
    static Player map(PlayerEntity player) {
        return Player.builder()
                .playerId(player.getPlayerId())
                .name(player.getName())
                .position(player.getPosition())
                .build();
    }
}
