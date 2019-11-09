package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Commodity;
import com.mycompany.myapp.repository.CommodityRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Commodity}.
 */
@RestController
@RequestMapping("/api")
public class CommodityResource {

    private final Logger log = LoggerFactory.getLogger(CommodityResource.class);

    private static final String ENTITY_NAME = "commodity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommodityRepository commodityRepository;

    public CommodityResource(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
    }

    /**
     * {@code POST  /commodities} : Create a new commodity.
     *
     * @param commodity the commodity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commodity, or with status {@code 400 (Bad Request)} if the commodity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commodities")
    public ResponseEntity<Commodity> createCommodity(@RequestBody Commodity commodity) throws URISyntaxException {
        log.debug("REST request to save Commodity : {}", commodity);
        if (commodity.getId() != null) {
            throw new BadRequestAlertException("A new commodity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Commodity result = commodityRepository.save(commodity);
        return ResponseEntity.created(new URI("/api/commodities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commodities} : Updates an existing commodity.
     *
     * @param commodity the commodity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commodity,
     * or with status {@code 400 (Bad Request)} if the commodity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commodity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commodities")
    public ResponseEntity<Commodity> updateCommodity(@RequestBody Commodity commodity) throws URISyntaxException {
        log.debug("REST request to update Commodity : {}", commodity);
        if (commodity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Commodity result = commodityRepository.save(commodity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commodity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commodities} : get all the commodities.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commodities in body.
     */
    @GetMapping("/commodities")
    public List<Commodity> getAllCommodities() {
        log.debug("REST request to get all Commodities");
        return commodityRepository.findAll();
    }

    /**
     * {@code GET  /commodities/:id} : get the "id" commodity.
     *
     * @param id the id of the commodity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commodity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commodities/{id}")
    public ResponseEntity<Commodity> getCommodity(@PathVariable Long id) {
        log.debug("REST request to get Commodity : {}", id);
        Optional<Commodity> commodity = commodityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commodity);
    }

    /**
     * {@code DELETE  /commodities/:id} : delete the "id" commodity.
     *
     * @param id the id of the commodity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commodities/{id}")
    public ResponseEntity<Void> deleteCommodity(@PathVariable Long id) {
        log.debug("REST request to delete Commodity : {}", id);
        commodityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
