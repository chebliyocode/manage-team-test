package com.manage.team.application.controller;

import com.manage.team.application.Mapper;
import com.manage.team.application.dto.PageableRequest;
import com.manage.team.application.dto.PlayerDto;
import com.manage.team.application.dto.TeamDto;
import com.manage.team.domain.model.Player;
import com.manage.team.domain.model.Team;
import com.manage.team.domain.usecase.player.PlayerCommand;
import com.manage.team.domain.usecase.player.port.PlayerUseCase;
import com.manage.team.domain.usecase.team.TeamCommand;
import com.manage.team.domain.usecase.team.port.TeamUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**

 The Controller class is responsible for handling HTTP requests and

 returning appropriate responses for managing players and teams.
 */
@RestController
@RequestMapping("/manage")
public class Controller {

    private final PlayerUseCase playerUseCase;
    private final TeamUseCase teamUseCase;

    /**

     Constructs a new Controller instance with the specified dependencies.
     @param playerUseCase the PlayerUseCase instance for player-related operations
     @param teamUseCase the TeamUseCase instance for team-related operations
     */
    public Controller(PlayerUseCase playerUseCase, TeamUseCase teamUseCase) {
        this.playerUseCase = playerUseCase;
        this.teamUseCase = teamUseCase;
    }
    /**

     Creates a new player.
     @param playerDto the PlayerDto object containing the player data
     @return a ResponseEntity containing the created player DTO and HTTP status code
     */
    @PostMapping("/player")
    public ResponseEntity<PlayerDto> createPlayer(@Valid @RequestBody PlayerDto playerDto) {
        PlayerCommand command = Mapper.toCommand(playerDto);
        Player player = playerUseCase.apply(command);
        PlayerDto body = Mapper.map(player);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }
    /**

     Creates a new team.
     @param teamDto the TeamDto object containing the team data
     @return a ResponseEntity containing the created team DTO and HTTP status code
     */
    @PostMapping("/team")
    public ResponseEntity<TeamDto> createTeam(@Valid @RequestBody TeamDto teamDto) {
        TeamCommand command = Mapper.toCommand(teamDto);
        Team team = teamUseCase.apply(command);
        TeamDto body = Mapper.map(team);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }
    /**

     Retrieves all teams.

     @param pageableRequest the PageableRequest object containing pagination and sorting parameters

     @return a ResponseEntity containing the page of teams and HTTP status code
     */
    @GetMapping("/teams")
    public ResponseEntity<Page<Team>> getAllTeams(@RequestBody PageableRequest pageableRequest) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("DESC".equalsIgnoreCase(pageableRequest.getOrder())) {
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(pageableRequest.getPage(), pageableRequest.getSize(), direction, pageableRequest.getSort());

        Page<Team> teams = teamUseCase.getAllTeams(pageable);

        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    /**

     Retrieves all players.

     @param pageableRequest the PageableRequest object containing pagination and sorting parameters

     @return a ResponseEntity containing the page of players and HTTP status code
     */
    @GetMapping("/players")
    public ResponseEntity<Page<Player>> getAllPlayers(@RequestBody PageableRequest pageableRequest) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("DESC".equalsIgnoreCase(pageableRequest.getOrder())) {
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(pageableRequest.getPage(), pageableRequest.getSize(), direction, pageableRequest.getSort());
        Page<Player> players = playerUseCase.getAllPlayers(pageable);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
}





