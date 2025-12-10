package edu.famu.resources.service;

import edu.famu.resources.dto.ResourceDTO;
import edu.famu.resources.store.InMemoryResourceStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourcesService {

    private static final Logger log = LoggerFactory.getLogger(ResourcesService.class);

    private final InMemoryResourceStore store;

    public ResourcesService(InMemoryResourceStore store) {
        this.store = store;
    }

    public List<ResourceDTO> getResources(Optional<String> category, Optional<String> q) {
        String categoryValue = category.orElse("none");
        String qValue = q.orElse("none");

        log.info("Fetching resources with filters: category={}, q={}", categoryValue, qValue);

        List<ResourceDTO> result;
        if (category.isEmpty() && q.isEmpty()) {
            result = store.findAll();
            log.debug("Returning {} resources (no filters applied)", result.size());
        } else {
            result = store.findByFilters(category, q);
            log.debug("Returning {} resources after applying filters", result.size());
        }

        return result;
    }

    public Optional<ResourceDTO> getResourceById(String id) {
        log.info("Fetching resource by id={}", id);

        Optional<ResourceDTO> resource = store.findById(id);

        if (resource.isEmpty()) {
            log.info("Resource with id={} not found", id);
        } else {
            log.debug("Resource with id={} found: {}", id, resource.get().name());
        }

        return resource;
    }
}
