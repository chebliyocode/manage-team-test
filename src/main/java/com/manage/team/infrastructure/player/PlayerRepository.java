package com.manage.team.infrastructure.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**

 The PlayerRepository interface provides the contract for performing CRUD operations

 on PlayerEntity objects in the database.
 */
@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    /**

     Checks if a player with the given playerId exists in the repository.
     @param playerId the playerId of the player
     @return true if the player exists, false otherwise
     */
    boolean existsByPlayerId(String playerId);
}