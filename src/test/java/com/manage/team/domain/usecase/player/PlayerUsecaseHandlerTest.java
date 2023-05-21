package com.manage.team.domain.usecase.player;

import com.manage.team.domain.error.BadInputException;
import com.manage.team.domain.error.ConflictBusinessException;
import com.manage.team.domain.error.CustomException;
import com.manage.team.domain.error.InternalException;
import com.manage.team.domain.model.Player;
import com.manage.team.domain.usecase.player.port.PlayerPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
class PlayerUsecaseHandlerTest {

    @Mock
    private PlayerPort repository;

    @InjectMocks
    PlayerUseCaseHandler handler;
    @Captor
    ArgumentCaptor<Player> playerArgumentCaptor;

    PlayerCommand command;
    private Player newPlayer;

    @BeforeEach
    void setUp() {
        command = null;
    }

    @Test
    void apply_shouldThrow_whenNullCommand() {
        givenNullCommand();
        thenThrowsWhenApplyInvoked(BadInputException.class);
    }

    @Test
    void apply_shouldThrow_whenNullPlayerPassed() {
        givenWithNullPlayerCommand();

        thenThrowsWhenApplyInvoked(BadInputException.class);
    }

    @Test
    void apply_shouldThrow_whenPlayerWithSameIDExists() {
        givenCommandWithPlayer();
        givenPlayerWithSameIDExists(true);

        thenThrowsWhenApplyInvoked(ConflictBusinessException.class);
    }

    @Test
    void apply_shouldThrow_whenNoUserWithSameRefExistsAndSavingFails() {
        givenPlayerWithSameIDExists(false);
        givenCommandWithPlayer();
        givenRepoWillFail();

        thenThrowsWhenApplyInvoked(InternalException.class);
    }

    @Test
    void apply_shouldReturnPlayer_whenNoPlayerExistsAndSavingSucceed() {
        givenPlayerWithSameIDExists(false);
        givenRepoWillSave();
        givenCommandWithPlayer();

        whenApplyInvoked();

        thenRepoSavesAndReturnWithId();
    }

    private void givenPlayerWithSameIDExists(boolean exists) {
        given(repository.isExist(any())).willReturn(exists);
    }

    private void givenRepoWillSave() {
        Player player = new Player();
        player.setPlayerId("1L");
        player.setName("");
        player.setPosition("");
        given(repository.save(playerArgumentCaptor.capture())).willReturn(player);
    }

    private void givenRepoWillFail() {
        Player player = new Player();
        player.setPlayerId("1L");
        given(repository.save(playerArgumentCaptor.capture())).willThrow(RuntimeException.class);
    }


    private void givenNullCommand() {
        command = null;
    }

    private void givenWithNullPlayerCommand() {
        command = new PlayerCommand(null);
    }

    private void givenCommandWithPlayer() {
        Player player = new Player();
        command = new PlayerCommand(player);
    }

    private void whenApplyInvoked() {
        newPlayer = handler.apply(command);
    }

    private <T extends CustomException> void thenThrowsWhenApplyInvoked(Class<T> exceptionClass) {
        assertThrows(exceptionClass, () -> handler.apply(command));
    }

    private void thenRepoSavesAndReturnWithId() {
        Player playerArg = playerArgumentCaptor.getValue();
        assertEquals(command.getPlayer().getPlayerId(), playerArg.getPlayerId());
        assertNotNull(newPlayer.getPlayerId());
    }

}