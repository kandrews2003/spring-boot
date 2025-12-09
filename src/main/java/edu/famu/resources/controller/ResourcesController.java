package edu.famu.resources.controller;

import edu.famu.resources.dto.ResourceDTO;
import edu.famu.resources.service.ResourcesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")

public class ResourcesController {

    private final ResourcesService resourcesService;

    public ResourcesController(ResourcesService resourcesService) {
        this.resourcesService = resourcesService;
    }

    @GetMapping
    public ResponseEntity<List<ResourceDTO>> getAllTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer minPriority) {

        List<ResourceDTO> tasks = resourcesService.getAllTasks(status, minPriority);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDTO> getTaskById(@PathVariable Long id) {
        ResourceDTO task = resourcesService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<ResourceDTO> createTask(@RequestBody ResourceDTO resourceDTO) {
        ResourceDTO createdTask = resourcesService.createTask(resourceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceDTO> updateTask(
            @PathVariable Long id,
            @RequestBody ResourceDTO resourceDTO) {

        ResourceDTO updatedTask = resourcesService.updateTask(id, resourceDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ResourceDTO> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam String newStatus) {

        ResourceDTO updatedTask = resourcesService.updateTaskStatus(id, newStatus);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        resourcesService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
