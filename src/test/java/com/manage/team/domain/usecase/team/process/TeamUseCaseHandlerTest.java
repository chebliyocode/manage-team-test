package com.manage.team.domain.usecase.team.process;

import com.manage.team.domain.error.*;
import com.manage.team.domain.model.Player;
import com.manage.team.domain.model.Team;
import com.manage.team.domain.usecase.player.port.PlayerPort;
import com.manage.team.domain.usecase.team.TeamCommand;
import com.manage.team.domain.usecase.team.TeamUseCaseHandler;
import com.manage.team.domain.usecase.team.port.TeamPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class TeamUseCaseHandlerTest {

    public static final String REF_PLAYER = "TEAM1234";

    @Mock
    PlayerPort playerPort;
    @Mock
    TeamPort teamPort;
    @InjectMocks
    TeamUseCaseHandler handler;
    @Captor
    ArgumentCaptor<Team> teamArgumentCaptor;
    private TeamCommand command;
    private Team savedTeam;


    @Test
    void apply_shouldThrow_whenCommandIsNull() {
        givenNullCommand();
        thenThrowsWhenApplyInvoked(BadInputException.class);
    }

    @Test
    void apply_shouldReturnTeam_whenNoPlayersExists() {
        givenTeamCommandWithoutPlayers();
        givenRepoHasNoMatchingPlayer();
        givenRepoWillSaveTeam();
        whenApplyInvoked();

        thenRepoSavesAndReturnTeamWithId();
    }

    @Test
    void apply_shouldThrow_whenMatchingTeamExists() {
        givenTeamCommandWithPlayers();
        givenRepoHasMatchingPlayer();
        givenRepoHasSameTeam(true);

        thenThrowsWhenApplyInvoked(ConflictBusinessException.class);
    }

    @Test
    void apply_shouldThrow_whenRepoFailedToPersist() {
        givenTeamCommandWithPlayers();
        givenRepoHasMatchingPlayer();
        givenRepoHasSameTeam(false);
        givenRepoWillFailToSaveTeam();

        thenThrowsWhenApplyInvoked(InternalException.class);
    }

    @Test
    void apply_shouldReturnTeam_whenAllSucceeded() {
        givenTeamCommandWithPlayers();
        givenRepoHasMatchingPlayer();
        givenRepoHasSameTeam(false);
        givenRepoWillSaveTeam();

        whenApplyInvoked();

        thenRepoSavesAndReturnTeamWithId();
    }


    private void givenNullCommand() {
        command = null;
    }

    private void givenTeamCommandWithPlayers() {
        Player player = new Player(REF_PLAYER,"Player 1","Attacker");
        Team team = new Team(1, "Team 1","TEAM", List.of(player),new BigDecimal(20000));
        command = new TeamCommand(team);
    }

    private void givenTeamCommandWithoutPlayers() {
        Team team = new Team(1, "Team 1","TEAM", List.of(),new BigDecimal(20000));
        command = new TeamCommand(team);
    }

    private void givenRepoHasNoMatchingPlayer() {
        given(playerPort.isExist(any())).willReturn(false);
    }

    private void givenRepoHasMatchingPlayer() {
        given(playerPort.isExist(any())).willReturn(true);
    }

    private void givenRepoHasSameTeam(boolean exists) {
        given(teamPort.teamExists(any())).willReturn(exists);
    }

    private void givenRepoWillSaveTeam() {
        Player player = new Player(REF_PLAYER,"Player 1","Attacker");
        Team team = new Team(1, "Team 1","TEAM", List.of(player),new BigDecimal(20000));
        given(teamPort.save(teamArgumentCaptor.capture())).willReturn(team);
    }

    private void givenRepoWillFailToSaveTeam() {
        given(teamPort.save(teamArgumentCaptor.capture())).willThrow(RuntimeException.class);
    }

    private void whenApplyInvoked() {
        savedTeam = handler.apply(command);
    }

    private <T extends CustomException> void thenThrowsWhenApplyInvoked(Class<T> exceptionClass) {
        assertThrows(exceptionClass, () -> handler.apply(command));
    }

    private void thenRepoSavesAndReturnTeamWithId() {
        Team teamArg = teamArgumentCaptor.getValue();
        assertNotNull(teamArg.getAcronym());
        assertEquals(command.getTeam().getAcronym(), teamArg.getAcronym());
        assertEquals(command.getTeam().getBudget(), teamArg.getBudget());
    }
}