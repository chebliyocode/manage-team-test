package com.manage.team.domain.usecase.team;

import com.manage.team.domain.model.Team;

public class TeamCommand {

    private final Team team;

    public TeamCommand(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}
