package com.manage.team.domain.usecase.player;

import com.manage.team.domain.model.Player;

public class PlayerCommand {

    private final Player player;

    public PlayerCommand(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
