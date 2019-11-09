package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Supporter;
import com.mycompany.myapp.repository.SupporterRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Supporter}.
 */
@RestController
@RequestMapping("/api")
public class SupporterResource {

    private final Logger log = LoggerFactory.getLogger(SupporterResource.class);

    private static final String ENTITY_NAME = "supporter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupporterRepository supporterRepository;

    public SupporterResource(SupporterRepository supporterRepository) {
        this.supporterRepository = supporterRepository;
    }

    /**
     * {@code POST  /supporters} : Create a new supporter.
     *
     * @param supporter the supporter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supporter, or with status {@code 400 (Bad Request)} if the supporter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supporters")
    public ResponseEntity<Supporter> createSupporter(@Valid @RequestBody Supporter supporter) throws URISyntaxException {
        log.debug("REST request to save Supporter : {}", supporter);
        if (supporter.getId() != null) {
            throw new BadRequestAlertException("A new supporter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Supporter result = supporterRepository.save(supporter);
        return ResponseEntity.created(new URI("/api/supporters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supporters} : Updates an existing supporter.
     *
     * @param supporter the supporter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supporter,
     * or with status {@code 400 (Bad Request)} if the supporter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supporter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supporters")
    public ResponseEntity<Supporter> updateSupporter(@Valid @RequestBody Supporter supporter) throws URISyntaxException {
        log.debug("REST request to update Supporter : {}", supporter);
        if (supporter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Supporter result = supporterRepository.save(supporter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supporter.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supporters} : get all the supporters.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supporters in body.
     */
    @GetMapping("/supporters")
    public ResponseEntity<List<Supporter>> getAllSupporters(Pageable pageable) {
        log.debug("REST request to get a page of Supporters");
        Page<Supporter> page = supporterRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supporters/:id} : get the "id" supporter.
     *
     * @param id the id of the supporter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supporter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supporters/{id}")
    public ResponseEntity<Supporter> getSupporter(@PathVariable Long id) {
        log.debug("REST request to get Supporter : {}", id);
        Optional<Supporter> supporter = supporterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(supporter);
    }

    /**
     * {@code DELETE  /supporters/:id} : delete the "id" supporter.
     *
     * @param id the id of the supporter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supporters/{id}")
    public ResponseEntity<Void> deleteSupporter(@PathVariable Long id) {
        log.debug("REST request to delete Supporter : {}", id);
        supporterRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
