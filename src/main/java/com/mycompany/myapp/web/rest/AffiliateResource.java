package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Affiliate;
import com.mycompany.myapp.repository.AffiliateRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Affiliate}.
 */
@RestController
@RequestMapping("/api")
public class AffiliateResource {

    private final Logger log = LoggerFactory.getLogger(AffiliateResource.class);

    private static final String ENTITY_NAME = "affiliate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AffiliateRepository affiliateRepository;

    public AffiliateResource(AffiliateRepository affiliateRepository) {
        this.affiliateRepository = affiliateRepository;
    }

    /**
     * {@code POST  /affiliates} : Create a new affiliate.
     *
     * @param affiliate the affiliate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new affiliate, or with status {@code 400 (Bad Request)} if the affiliate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/affiliates")
    public ResponseEntity<Affiliate> createAffiliate(@Valid @RequestBody Affiliate affiliate) throws URISyntaxException {
        log.debug("REST request to save Affiliate : {}", affiliate);
        if (affiliate.getId() != null) {
            throw new BadRequestAlertException("A new affiliate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Affiliate result = affiliateRepository.save(affiliate);
        return ResponseEntity.created(new URI("/api/affiliates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /affiliates} : Updates an existing affiliate.
     *
     * @param affiliate the affiliate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affiliate,
     * or with status {@code 400 (Bad Request)} if the affiliate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the affiliate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/affiliates")
    public ResponseEntity<Affiliate> updateAffiliate(@Valid @RequestBody Affiliate affiliate) throws URISyntaxException {
        log.debug("REST request to update Affiliate : {}", affiliate);
        if (affiliate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Affiliate result = affiliateRepository.save(affiliate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affiliate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /affiliates} : get all the affiliates.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of affiliates in body.
     */
    @GetMapping("/affiliates")
    public List<Affiliate> getAllAffiliates() {
        log.debug("REST request to get all Affiliates");
        return affiliateRepository.findAll();
    }

    /**
     * {@code GET  /affiliates/:id} : get the "id" affiliate.
     *
     * @param id the id of the affiliate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the affiliate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/affiliates/{id}")
    public ResponseEntity<Affiliate> getAffiliate(@PathVariable Long id) {
        log.debug("REST request to get Affiliate : {}", id);
        Optional<Affiliate> affiliate = affiliateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(affiliate);
    }

    /**
     * {@code DELETE  /affiliates/:id} : delete the "id" affiliate.
     *
     * @param id the id of the affiliate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/affiliates/{id}")
    public ResponseEntity<Void> deleteAffiliate(@PathVariable Long id) {
        log.debug("REST request to delete Affiliate : {}", id);
        affiliateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
