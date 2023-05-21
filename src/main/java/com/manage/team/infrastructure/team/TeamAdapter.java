package com.manage.team.infrastructure.team;

import com.manage.team.domain.model.Team;
import com.manage.team.domain.usecase.team.port.TeamPort;
import com.manage.team.infrastructure.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**

 The TeamAdapter class is responsible for providing the implementation of the TeamPort interface

 and acts as an adapter between the domain layer and the infrastructure layer for team-related operations.

 It interacts with the TeamRepository to perform CRUD operations on teams.
 */
@Component
public class TeamAdapter implements TeamPort {

    private final TeamRepository repository;

    /**

     Constructs a new TeamAdapter with the specified TeamRepository.
     @param repository the TeamRepository to be used for data access
     */
    public TeamAdapter(TeamRepository repository) {
        this.repository = repository;
    }
    /**

     Checks if a team with the given acronym exists.
     @param acronym the acronym of the team to check
     @return true if a team with the given acronym exists, false otherwise
     */
    @Override
    public boolean teamExists(String acronym) {
        return repository.existsByAcronym(acronym);
    }
    /**

     Saves a team by converting it to a TeamEntity and persisting it using the TeamRepository.
     @param team the team to be saved
     @return the saved team as a domain object
     */
    @Override
    public Team save(Team team) {
        TeamEntity entity = Mapper.toEntity(team);
        repository.save(entity);
        return Mapper.toDomain(entity);
    }
    /**

     Retrieves all teams using the specified pagination information.

     @param pageable the pagination information

     @return a Page object containing the teams
     */
    @Override
    public Page<Team> getAllTeams(Pageable pageable) {
        Page<TeamEntity> teamEntitiesPage = repository.findAll(pageable);

        List<Team> teams = teamEntitiesPage.getContent().stream()
                .map(Mapper::toDomain)
                .toList();

        return new PageImpl<>(teams, pageable, teamEntitiesPage.getTotalElements());
    }
}