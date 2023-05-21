package com.manage.team.domain.usecase.player.port;

import com.manage.team.domain.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlayerPort {
    boolean isExist(String playerId);
    Player save(Player player);
    Page<Player> getAllPlayers(Pageable pageable);
}
