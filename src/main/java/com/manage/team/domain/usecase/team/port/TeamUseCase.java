package com.manage.team.domain.usecase.team.port;

import com.manage.team.domain.model.Team;
import com.manage.team.domain.usecase.team.TeamCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamUseCase {

    Team apply(TeamCommand command);

    Page<Team> getAllTeams(Pageable pageable);

}
