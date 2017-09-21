package com.stratio.scddatanps.repository;

import com.stratio.scddatanps.domain.TouchpointNps;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Spring Data JPA repository for the TouchpointNps entity.
 */
public interface TouchpointNpsRepository extends JpaRepository<TouchpointNps, String> {

    List<TouchpointNps> findByClientIdOrderByControlDateDesc(Long clientId);
    List<TouchpointNps> findByClientId(Long clientId);
    int countByClientId(Long clientId);
    List<TouchpointNps> findByClientIdAndStatusDateAfterOrderByControlDateDesc(Long clientid, Date loadDate);
}
