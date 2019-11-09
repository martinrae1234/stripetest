package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.ReasonForNonFeeding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReasonForNonFeeding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReasonForNonFeedingRepository extends JpaRepository<ReasonForNonFeeding, Long> {

}
