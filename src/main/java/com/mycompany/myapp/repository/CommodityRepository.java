package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.Commodity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Commodity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommodityRepository extends JpaRepository<Commodity, Long> {

}
