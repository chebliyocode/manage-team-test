package com.manage.team.application;

import com.manage.team.domain.usecase.team.TeamUseCaseHandler;
import com.manage.team.domain.usecase.team.port.TeamPort;
import com.manage.team.domain.usecase.team.port.TeamUseCase;
import com.manage.team.domain.usecase.player.PlayerUseCaseHandler;
import com.manage.team.domain.usecase.player.port.PlayerPort;
import com.manage.team.domain.usecase.player.port.PlayerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**

 The Config class provides the configuration for the application, including the

 instantiation of use case handlers.
 */
@Configuration
public class Config {

    /**

     Creates a new instance of PlayerUseCaseHandler.
     @param playerPort the PlayerPort instance for player-related operations
     @return a PlayerUseCase instance
     */
    @Bean
    public PlayerUseCase playerCreationUseCase(PlayerPort playerPort) {
        return new PlayerUseCaseHandler(playerPort);
    }
    /**

     Creates a new instance of TeamUseCaseHandler.
     @param teamPort the TeamPort instance for team-related operations
     @return a TeamUseCase instance
     */
    @Bean
    public TeamUseCase teamCreationUseCase(TeamPort teamPort) {
        return new TeamUseCaseHandler(teamPort);
    }
}
