package edu.famu.resources.store;

import edu.famu.resources.dto.ResourceDTO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryResourceStore {

    private final Map<String, ResourceDTO> byId = new HashMap<>();
    private final List<ResourceDTO> all = new ArrayList<>();

    public InMemoryResourceStore() {
        // Seed data
        add(new ResourceDTO("1", "Math Tutoring Center", "Tutoring", "Building A, Room 101",
                "https://famu.edu/tutoring", List.of("math", "study", "help")));
        add(new ResourceDTO("2", "CS Advising Office", "Advising", "Building B, Room 202",
                "https://famu.edu/advising", List.of("computer science", "advisor")));
        add(new ResourceDTO("3", "Engineering Lab", "Lab", "Tech Hall 305",
                "https://famu.edu/lab", List.of("engineering", "hardware")));
        add(new ResourceDTO("4", "Writing Center", "Tutoring", "Library 2nd Floor",
                "https://famu.edu/writing", List.of("english", "essays", "writing")));
        add(new ResourceDTO("5", "Career Services", "Advising", "Student Center",
                "https://famu.edu/career", List.of("jobs", "resume", "internships")));

        System.out.println("[INFO] Seeded " + all.size() + " campus resources.");
    }

    private void add(ResourceDTO r) {
        byId.put(r.id(), r);
        all.add(r);
    }

    public List<ResourceDTO> findAll() {
        return List.copyOf(all);
    }

    public Optional<ResourceDTO> findById(String id) {
        return Optional.ofNullable(byId.get(id));
    }

    public List<ResourceDTO> findByFilters(Optional<String> category, Optional<String> q) {
        return all.stream()
                .filter(r -> category.map(c -> r.category().equalsIgnoreCase(c)).orElse(true))
                .filter(r -> q.map(query -> matchesQuery(r, query)).orElse(true))
                .collect(Collectors.toList());
    }

    private boolean matchesQuery(ResourceDTO r, String query) {
        String qLower = query.toLowerCase();
        return r.name().toLowerCase().contains(qLower)
                || r.tags().stream().anyMatch(tag -> tag.toLowerCase().contains(qLower));
    }


    public ResourceDTO save(ResourceDTO dto) {
        String newId = String.valueOf(all.size() + 1);
        ResourceDTO newResource = new ResourceDTO(
                newId,
                dto.name(),
                dto.category(),
                dto.location(),
                dto.url(),
                dto.tags()
        );
        byId.put(newId, newResource);
        all.add(newResource);
        System.out.println("[INFO] Added new resource: " + newResource.name());
        return newResource;
    }


    public Optional<ResourceDTO> update(String id, ResourceDTO dto) {
        Optional<ResourceDTO> existing = findById(id);
        if (existing.isEmpty()) return Optional.empty();

        ResourceDTO updated = new ResourceDTO(
                id,
                dto.name(),
                dto.category(),
                dto.location(),
                dto.url(),
                dto.tags()
        );


        byId.put(id, updated);
        all.removeIf(r -> r.id().equals(id));
        all.add(updated);

        System.out.println("[INFO] Updated resource: " + updated.name());
        return Optional.of(updated);
    }


    public boolean delete(String id) {
        ResourceDTO removed = byId.remove(id);
        if (removed != null) {
            all.removeIf(r -> r.id().equals(id));
            System.out.println("[INFO] Deleted resource with ID: " + id);
            return true;
        }
        return false;
    }

}