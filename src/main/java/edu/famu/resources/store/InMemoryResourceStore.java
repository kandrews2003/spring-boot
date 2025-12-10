package edu.famu.resources.store;

import edu.famu.resources.dto.ResourceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryResourceStore {

    private static final Logger log = LoggerFactory.getLogger(InMemoryResourceStore.class);

    private final Map<String, ResourceDTO> byId = new HashMap<>();
    private final List<ResourceDTO> all = new ArrayList<>();

    public InMemoryResourceStore() {
        // Seed data
        add(new ResourceDTO("1", "Math Lab", "Tutoring", "Coleman Library",
                "https://famu.edu/tutoring", List.of("math", "study", "help")));
        add(new ResourceDTO("2", "CS Advising Office", "Advising", "Building B, Room 202",
                "https://famu.edu/advising", List.of("computer science", "advisor")));
        add(new ResourceDTO("3", "Engineering Lab", "Lab", "Tech Hall 305",
                "https://famu.edu/lab", List.of("engineering", "hardware")));
        add(new ResourceDTO("4", "Writing Center", "Tutoring", "Library 2nd Floor",
                "https://famu.edu/writing", List.of("english", "essays", "writing")));
        add(new ResourceDTO("5", "Career Services", "Advising", "Student Center",
                "https://famu.edu/career", List.of("jobs", "resume", "internships")));

        // Rubric: INFO at startup with seed count
        log.info("Seeded {} campus resources into InMemoryResourceStore", all.size());
    }

    private void add(ResourceDTO r) {
        byId.put(r.id(), r);
        all.add(r);
    }

    public List<ResourceDTO> findAll() {
        return List.copyOf(all); // unmodifiable copy
    }

    public Optional<ResourceDTO> findById(String id) {
        return Optional.ofNullable(byId.get(id));
    }

    public List<ResourceDTO> findByFilters(Optional<String> category, Optional<String> q) {
        return all.stream()
                .filter(r -> category
                        .map(c -> r.category() != null && r.category().equalsIgnoreCase(c))
                        .orElse(true)
                )
                .filter(r -> q
                        .map(query -> matchesQuery(r, query))
                        .orElse(true)
                )
                .collect(Collectors.toList());
    }

    private boolean matchesQuery(ResourceDTO r, String query) {
        String qLower = query.toLowerCase();
        boolean inName = r.name() != null && r.name().toLowerCase().contains(qLower);
        boolean inTags = r.tags() != null &&
                r.tags().stream()
                        .filter(Objects::nonNull)
                        .map(String::toLowerCase)
                        .anyMatch(tag -> tag.contains(qLower));

        return inName || inTags;
    }
}
