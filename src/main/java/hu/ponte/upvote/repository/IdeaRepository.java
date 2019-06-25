package hu.ponte.upvote.repository;

import hu.ponte.upvote.domain.Account;
import hu.ponte.upvote.domain.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    //for ROLE_USER
    @Query("SELECT i FROM Idea i WHERE i.status = 'STATUS_APPROVED' AND i.account = :myAccount")
    List<Idea> getAllApprovedIdeasByAccount(@Param("myAccount") Account myAccount);

    //for ROLE_USER
    @Query("SELECT i FROM Idea i WHERE i.status = 'STATUS_APPROVED' AND i.account <> :myAccount")
    List<Idea> getAllApprovedIdeasExceptAccount(@Param("myAccount") Account myAccount);

    @Query("SELECT i FROM Idea i WHERE i.status = 'STATUS_APPROVED'")
    List<Idea> getAllApprovedIdeas();

    @Query("SELECT i FROM Idea i WHERE i.status = 'STATUS_WAITING_FOR_ACCEPTANCE'")
    List<Idea> getAllUnApprovedIdeas();
}
