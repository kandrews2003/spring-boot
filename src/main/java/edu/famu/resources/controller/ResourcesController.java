package edu.famu.resources.controller;

import edu.famu.resources.dto.ResourceDTO;
import edu.famu.resources.service.ResourcesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resources")
public class ResourcesController {

    private static final Logger log = LoggerFactory.getLogger(ResourcesController.class);

    private final ResourcesService service;

    public ResourcesController(ResourcesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ResourceDTO>> listResources(
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> q
    ) {
        log.info("Handling GET /api/resources with category={} q={}",
                category.orElse("none"),
                q.orElse("none"));

        List<ResourceDTO> resources = service.getResources(category, q);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDTO> getResourceById(@PathVariable String id) {
        log.info("Handling GET /api/resources/{}", id);

        return service.getResourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
