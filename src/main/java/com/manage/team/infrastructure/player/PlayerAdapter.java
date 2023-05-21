package com.manage.team.infrastructure.player;

import com.manage.team.domain.model.Player;
import com.manage.team.domain.usecase.player.port.PlayerPort;
import com.manage.team.infrastructure.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**

 The PlayerAdapter class is responsible for providing an implementation of the PlayerPort interface

 and interacting with the PlayerRepository to perform CRUD operations on Player entities.
 */
@Component
public class PlayerAdapter implements PlayerPort {

    private final PlayerRepository repository;

    /**

     Constructs a new PlayerAdapter with the specified PlayerRepository.
     @param repository the PlayerRepository to be used for data access
     */
    public PlayerAdapter(PlayerRepository repository) {
        this.repository = repository;
    }
    /**

     Checks if a player with the given playerId exists in the repository.
     @param playerId the playerId of the player
     @return true if the player exists, false otherwise
     */
    @Override
    public boolean isExist(String playerId) {
        return repository.existsByPlayerId(playerId);
    }
    /**

     Saves a player in the repository.
     @param player the player to be saved
     @return the saved player
     */
    @Override
    public Player save(Player player) {
        PlayerEntity entity = Mapper.toEntity(player);
        entity = repository.save(entity);
        return Mapper.toDomain(entity);
    }
    /**

     Retrieves all players from the repository.
     @param pageable the pagination information
     @return a Page containing the list of players
     */
    @Override
    public Page<Player> getAllPlayers(Pageable pageable) {
        Page<PlayerEntity> playerEntityPage = repository.findAll(pageable);
        List<Player> players = playerEntityPage.getContent().stream()
                .map(Mapper::toDomain)
                .toList();
        return new PageImpl<>(players, pageable, playerEntityPage.getTotalElements());
    }
}




