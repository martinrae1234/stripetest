package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FurtherInfo;
import com.mycompany.myapp.repository.FurtherInfoRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.FurtherInfo}.
 */
@RestController
@RequestMapping("/api")
public class FurtherInfoResource {

    private final Logger log = LoggerFactory.getLogger(FurtherInfoResource.class);

    private static final String ENTITY_NAME = "furtherInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FurtherInfoRepository furtherInfoRepository;

    public FurtherInfoResource(FurtherInfoRepository furtherInfoRepository) {
        this.furtherInfoRepository = furtherInfoRepository;
    }

    /**
     * {@code POST  /further-infos} : Create a new furtherInfo.
     *
     * @param furtherInfo the furtherInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new furtherInfo, or with status {@code 400 (Bad Request)} if the furtherInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/further-infos")
    public ResponseEntity<FurtherInfo> createFurtherInfo(@Valid @RequestBody FurtherInfo furtherInfo) throws URISyntaxException {
        log.debug("REST request to save FurtherInfo : {}", furtherInfo);
        if (furtherInfo.getId() != null) {
            throw new BadRequestAlertException("A new furtherInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FurtherInfo result = furtherInfoRepository.save(furtherInfo);
        return ResponseEntity.created(new URI("/api/further-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /further-infos} : Updates an existing furtherInfo.
     *
     * @param furtherInfo the furtherInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated furtherInfo,
     * or with status {@code 400 (Bad Request)} if the furtherInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the furtherInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/further-infos")
    public ResponseEntity<FurtherInfo> updateFurtherInfo(@Valid @RequestBody FurtherInfo furtherInfo) throws URISyntaxException {
        log.debug("REST request to update FurtherInfo : {}", furtherInfo);
        if (furtherInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FurtherInfo result = furtherInfoRepository.save(furtherInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, furtherInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /further-infos} : get all the furtherInfos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of furtherInfos in body.
     */
    @GetMapping("/further-infos")
    public List<FurtherInfo> getAllFurtherInfos() {
        log.debug("REST request to get all FurtherInfos");
        return furtherInfoRepository.findAll();
    }

    /**
     * {@code GET  /further-infos/:id} : get the "id" furtherInfo.
     *
     * @param id the id of the furtherInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the furtherInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/further-infos/{id}")
    public ResponseEntity<FurtherInfo> getFurtherInfo(@PathVariable Long id) {
        log.debug("REST request to get FurtherInfo : {}", id);
        Optional<FurtherInfo> furtherInfo = furtherInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(furtherInfo);
    }

    /**
     * {@code DELETE  /further-infos/:id} : delete the "id" furtherInfo.
     *
     * @param id the id of the furtherInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/further-infos/{id}")
    public ResponseEntity<Void> deleteFurtherInfo(@PathVariable Long id) {
        log.debug("REST request to delete FurtherInfo : {}", id);
        furtherInfoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
