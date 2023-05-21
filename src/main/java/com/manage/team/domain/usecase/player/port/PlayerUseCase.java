package com.manage.team.domain.usecase.player.port;

import com.manage.team.domain.model.Player;
import com.manage.team.domain.usecase.player.PlayerCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlayerUseCase {

    Player apply(PlayerCommand command);
    Page<Player> getAllPlayers(Pageable pageable);

}
