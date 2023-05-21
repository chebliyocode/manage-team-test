package com.manage.team.domain.usecase.team.port;

import com.manage.team.domain.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamPort {

    boolean teamExists(String acronym);

    Team save(Team team);

    Page<Team> getAllTeams(Pageable pageable);

}

