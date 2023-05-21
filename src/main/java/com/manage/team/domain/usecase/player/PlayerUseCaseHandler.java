package com.manage.team.domain.usecase.player;

import com.manage.team.domain.model.Player;
import com.manage.team.domain.error.BadInputException;
import com.manage.team.domain.error.ConflictBusinessException;
import com.manage.team.domain.error.InternalException;
import com.manage.team.domain.usecase.player.port.PlayerUseCase;
import com.manage.team.domain.usecase.player.port.PlayerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static java.util.Objects.isNull;


public class PlayerUseCaseHandler implements PlayerUseCase {

    Logger log = LoggerFactory.getLogger(PlayerUseCaseHandler.class);

    private final PlayerPort playerPort;

    public PlayerUseCaseHandler(PlayerPort playerPort) {
        this.playerPort = playerPort;
    }

    public Player apply(PlayerCommand command) {
        checkInput(command);
        checkNoPlayerWithSameIdExists(command);
        return saveNewPlayer(command.getPlayer());
    }

    public Page<Player> getAllPlayers(Pageable pageable) {
        return playerPort.getAllPlayers(pageable);
    }


    private void checkNoPlayerWithSameIdExists(PlayerCommand command) {
        boolean playerExists = playerPort.isExist(command.getPlayer().getPlayerId());
        if(playerExists)
            throw new ConflictBusinessException("Player with same id exists");
    }

    private static void checkInput(PlayerCommand command) {
        if(isNull(command) || isNull(command.getPlayer()))
            throw new BadInputException("Invalid input");
    }


    private Player saveNewPlayer(Player player) {
        try {
            Player newPlayer = this.playerPort.save(player);
            log.info("New player saved {}", newPlayer.getPlayerId());
            return newPlayer;
        } catch (Exception e) {
            log.error("Failed to save player {}", player);
            throw new InternalException("Failed to save player {}");
        }
    }
}
