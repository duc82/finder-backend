package com.duc82.finderapi.properties;

import com.duc82.finderapi.properties.entities.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropertyRepository extends JpaRepository<Property,UUID> {
    Page<Property> findAllByDeletedAtIsNull(Specification<Property> spec, Pageable pageable);
}
