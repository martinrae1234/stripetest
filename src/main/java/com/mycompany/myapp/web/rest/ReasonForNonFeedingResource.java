package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ReasonForNonFeeding;
import com.mycompany.myapp.repository.ReasonForNonFeedingRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ReasonForNonFeeding}.
 */
@RestController
@RequestMapping("/api")
public class ReasonForNonFeedingResource {

    private final Logger log = LoggerFactory.getLogger(ReasonForNonFeedingResource.class);

    private static final String ENTITY_NAME = "reasonForNonFeeding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReasonForNonFeedingRepository reasonForNonFeedingRepository;

    public ReasonForNonFeedingResource(ReasonForNonFeedingRepository reasonForNonFeedingRepository) {
        this.reasonForNonFeedingRepository = reasonForNonFeedingRepository;
    }

    /**
     * {@code POST  /reason-for-non-feedings} : Create a new reasonForNonFeeding.
     *
     * @param reasonForNonFeeding the reasonForNonFeeding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reasonForNonFeeding, or with status {@code 400 (Bad Request)} if the reasonForNonFeeding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reason-for-non-feedings")
    public ResponseEntity<ReasonForNonFeeding> createReasonForNonFeeding(@RequestBody ReasonForNonFeeding reasonForNonFeeding) throws URISyntaxException {
        log.debug("REST request to save ReasonForNonFeeding : {}", reasonForNonFeeding);
        if (reasonForNonFeeding.getId() != null) {
            throw new BadRequestAlertException("A new reasonForNonFeeding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReasonForNonFeeding result = reasonForNonFeedingRepository.save(reasonForNonFeeding);
        return ResponseEntity.created(new URI("/api/reason-for-non-feedings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reason-for-non-feedings} : Updates an existing reasonForNonFeeding.
     *
     * @param reasonForNonFeeding the reasonForNonFeeding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reasonForNonFeeding,
     * or with status {@code 400 (Bad Request)} if the reasonForNonFeeding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reasonForNonFeeding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reason-for-non-feedings")
    public ResponseEntity<ReasonForNonFeeding> updateReasonForNonFeeding(@RequestBody ReasonForNonFeeding reasonForNonFeeding) throws URISyntaxException {
        log.debug("REST request to update ReasonForNonFeeding : {}", reasonForNonFeeding);
        if (reasonForNonFeeding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReasonForNonFeeding result = reasonForNonFeedingRepository.save(reasonForNonFeeding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reasonForNonFeeding.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reason-for-non-feedings} : get all the reasonForNonFeedings.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reasonForNonFeedings in body.
     */
    @GetMapping("/reason-for-non-feedings")
    public List<ReasonForNonFeeding> getAllReasonForNonFeedings() {
        log.debug("REST request to get all ReasonForNonFeedings");
        return reasonForNonFeedingRepository.findAll();
    }

    /**
     * {@code GET  /reason-for-non-feedings/:id} : get the "id" reasonForNonFeeding.
     *
     * @param id the id of the reasonForNonFeeding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reasonForNonFeeding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reason-for-non-feedings/{id}")
    public ResponseEntity<ReasonForNonFeeding> getReasonForNonFeeding(@PathVariable Long id) {
        log.debug("REST request to get ReasonForNonFeeding : {}", id);
        Optional<ReasonForNonFeeding> reasonForNonFeeding = reasonForNonFeedingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reasonForNonFeeding);
    }

    /**
     * {@code DELETE  /reason-for-non-feedings/:id} : delete the "id" reasonForNonFeeding.
     *
     * @param id the id of the reasonForNonFeeding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reason-for-non-feedings/{id}")
    public ResponseEntity<Void> deleteReasonForNonFeeding(@PathVariable Long id) {
        log.debug("REST request to delete ReasonForNonFeeding : {}", id);
        reasonForNonFeedingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
