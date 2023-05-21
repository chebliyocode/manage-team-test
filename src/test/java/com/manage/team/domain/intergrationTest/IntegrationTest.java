package com.manage.team.domain.intergrationTest;

import com.manage.team.domain.error.ConflictBusinessException;
import com.manage.team.domain.model.Player;
import com.manage.team.domain.model.Team;
import com.manage.team.domain.usecase.player.PlayerCommand;
import com.manage.team.domain.usecase.player.PlayerUseCaseHandler;
import com.manage.team.domain.usecase.team.TeamCommand;
import com.manage.team.domain.usecase.team.TeamUseCaseHandler;
import com.manage.team.infrastructure.Mapper;
import com.manage.team.infrastructure.player.PlayerRepository;
import com.manage.team.infrastructure.team.TeamEntity;
import com.manage.team.infrastructure.team.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class IntegrationTest {

    @Autowired
    private PlayerUseCaseHandler playerUseCaseHandler;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamUseCaseHandler teamUseCaseHandler;

    @Autowired
    private TeamRepository teamRepository;



    @Test
    void integrationTest() {

        // Create a team and save it using the use case handler
        Player player1 = new Player("TEAM123","Player 1", "Attacker");
        Player player2 = new Player("TEAM134","Player 2", "Midfielder");
        Team team = new Team(1, "Team 1", "TEAM1", List.of(player1, player2), new BigDecimal(20000));
        TeamCommand createCommand = new TeamCommand(team);
        Team createdTeam = teamUseCaseHandler.apply(createCommand);

        assertEquals(1, createdTeam.getId());
        assertEquals("Team 1", createdTeam.getName());
        assertEquals("TEAM1", createdTeam.getAcronym());
        assertEquals(2, createdTeam.getPlayers().size());
        assertEquals(new BigDecimal(20000), createdTeam.getBudget());

        // Retrieve the team from the repository and compare the retrieved team with the created team
        TeamEntity retrievedTeam = teamRepository.findTeamEntityByAcronym(createdTeam.getAcronym()).orElse(null);
        Team team1 = Mapper.toDomain(retrievedTeam);
        assertNotNull(team1);
        assertEquals(createdTeam.getId(), team1.getId());
        assertEquals(createdTeam.getName(), team1.getName());
        assertEquals(createdTeam.getAcronym(), team1.getAcronym());
        assertEquals(createdTeam.getPlayers().size(), team1.getPlayers().size());

        // Create Team with the same Acronym
        TeamCommand existTeamCommand = new TeamCommand(team1);
        assertThrows(ConflictBusinessException.class, () -> teamUseCaseHandler.apply(existTeamCommand));
    }
}

