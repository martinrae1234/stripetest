package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.FurtherInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FurtherInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FurtherInfoRepository extends JpaRepository<FurtherInfo, Long> {

}
