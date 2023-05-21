package com.manage.team.domain.usecase.team;

import com.manage.team.domain.error.BadInputException;
import com.manage.team.domain.error.ConflictBusinessException;
import com.manage.team.domain.error.InternalException;
import com.manage.team.domain.model.Team;
import com.manage.team.domain.usecase.team.port.TeamUseCase;
import com.manage.team.domain.usecase.team.port.TeamPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static java.util.Objects.isNull;

public class TeamUseCaseHandler implements TeamUseCase {

    static Logger log = LoggerFactory.getLogger(TeamUseCaseHandler.class);

    private final TeamPort teamPort;

    public TeamUseCaseHandler(TeamPort teamPort) {
        this.teamPort = teamPort;
    }

    public Team apply(TeamCommand command) {
        checkCommand(command);
        checkTeamExists(command.getTeam().getAcronym());
        return saveTeam(command.getTeam());
    }

    public Page<Team> getAllTeams(Pageable pageable) {
        return teamPort.getAllTeams(pageable);
    }


    private void checkCommand(TeamCommand command) {
        if (isNull(command) || isNull(command.getTeam().getAcronym()))
            throw new BadInputException("Bad input");

    }

    private void checkTeamExists(String acronym) {
        boolean teamExists = teamPort.teamExists(acronym);
        if (teamExists)
            throw new ConflictBusinessException("Team with same acronym exists");
    }


    private Team saveTeam(Team team) {
        try {
            return teamPort.save(team);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalException("Failed to save team");
        }
    }
}

