package com.manage.team.infrastructure.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**

 The TeamRepository interface provides the contract for performing CRUD operations

 on TeamEntity objects in the underlying database.
 */
@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

     /**

      Checks if a team with the specified acronym exists.
      @param acronym the acronym of the team
      @return true if a team with the specified acronym exists, false otherwise
      */
     boolean existsByAcronym(String acronym);
     /**

      Retrieves the TeamEntity object with the specified acronym.
      @param acronym the acronym of the team
      @return an Optional containing the TeamEntity object if found, or an empty Optional if not found
      */
     Optional<TeamEntity> findTeamEntityByAcronym(String acronym);
}
