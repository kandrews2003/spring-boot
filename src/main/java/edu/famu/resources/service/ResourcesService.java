package edu.famu.resources.service;

import edu.famu.resources.dto.ResourceDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResourcesService {

    private final InMemoryResourceStore store;

    public ResourcesService(InMemoryResourceStore store) {
        this.store = store;
    }

    public List<ResourceDTO> getResources(Optional<String> category, Optional<String> q) {
        System.out.println("[INFO] Fetching resources with filters: category=" + category.orElse("none") + ", q=" + q.orElse("none"));
        return store.findByFilters(category, q);
    }

    public Optional<ResourceDTO> getResourceById(String id) {
        System.out.println("[INFO] Fetching resource by ID: " + id);
        System.out.println("[DEBUG] Returning " + store.findAll().size() + " resources.");
        return store.findById(id);

    }

    public ResourceDTO createResource(ResourceDTO dto) {
        System.out.println("[INFO] Creating new resource: " + dto.name());
        return store.save(dto);
    }

    public Optional<ResourceDTO> updateResource(String id, ResourceDTO dto) {
        System.out.println("[INFO] Updating resource ID: " + id);
        return store.update(id, dto);
    }

    public boolean deleteResource(String id) {
        System.out.println("[INFO] Deleting resource ID: " + id);
        return store.delete(id);
    }


}}