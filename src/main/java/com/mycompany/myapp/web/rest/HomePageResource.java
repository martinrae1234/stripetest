package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.HomePage;
import com.mycompany.myapp.repository.HomePageRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.HomePage}.
 */
@RestController
@RequestMapping("/api")
public class HomePageResource {

    private final Logger log = LoggerFactory.getLogger(HomePageResource.class);

    private static final String ENTITY_NAME = "homePage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HomePageRepository homePageRepository;

    public HomePageResource(HomePageRepository homePageRepository) {
        this.homePageRepository = homePageRepository;
    }

    /**
     * {@code POST  /home-pages} : Create a new homePage.
     *
     * @param homePage the homePage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new homePage, or with status {@code 400 (Bad Request)} if the homePage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/home-pages")
    public ResponseEntity<HomePage> createHomePage(@RequestBody HomePage homePage) throws URISyntaxException {
        log.debug("REST request to save HomePage : {}", homePage);
        if (homePage.getId() != null) {
            throw new BadRequestAlertException("A new homePage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HomePage result = homePageRepository.save(homePage);
        return ResponseEntity.created(new URI("/api/home-pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /home-pages} : Updates an existing homePage.
     *
     * @param homePage the homePage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated homePage,
     * or with status {@code 400 (Bad Request)} if the homePage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the homePage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/home-pages")
    public ResponseEntity<HomePage> updateHomePage(@RequestBody HomePage homePage) throws URISyntaxException {
        log.debug("REST request to update HomePage : {}", homePage);
        if (homePage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HomePage result = homePageRepository.save(homePage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, homePage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /home-pages} : get all the homePages.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of homePages in body.
     */
    @GetMapping("/home-pages")
    public List<HomePage> getAllHomePages() {
        log.debug("REST request to get all HomePages");
        return homePageRepository.findAll();
    }

    /**
     * {@code GET  /home-pages/:id} : get the "id" homePage.
     *
     * @param id the id of the homePage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the homePage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/home-pages/{id}")
    public ResponseEntity<HomePage> getHomePage(@PathVariable Long id) {
        log.debug("REST request to get HomePage : {}", id);
        Optional<HomePage> homePage = homePageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(homePage);
    }

    /**
     * {@code DELETE  /home-pages/:id} : delete the "id" homePage.
     *
     * @param id the id of the homePage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/home-pages/{id}")
    public ResponseEntity<Void> deleteHomePage(@PathVariable Long id) {
        log.debug("REST request to delete HomePage : {}", id);
        homePageRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
