package edu.famu.resources.controller;

import edu.famu.resources.dto.ResourceDTO;
import edu.famu.resources.service.ResourcesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resources")
public class ResourcesController {

    private final ResourcesService service;

    public ResourcesController(ResourcesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ResourceDTO>> listResources(
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> q) {
        return ResponseEntity.ok(service.getResources(category, q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDTO> getResourceById(@PathVariable String id) {
        return service.getResourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- CREATE ---
    @PostMapping
    public ResourceDTO createResource(@RequestBody ResourceDTO dto) {
        return service.createResource(dto);
    }

    // --- UPDATE ---
    @PutMapping("/{id}")
    public Optional<ResourceDTO> updateResource(@PathVariable String id, @RequestBody ResourceDTO dto) {
        return service.updateResource(id, dto);
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public boolean deleteResource(@PathVariable String id) {
        return service.deleteResource(id);
    }


}