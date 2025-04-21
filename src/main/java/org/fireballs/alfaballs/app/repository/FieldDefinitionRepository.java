package org.fireballs.alfaballs.app.repository;

import org.fireballs.alfaballs.domain.FieldDefinition;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDefinitionRepository extends JpaRepository<FieldDefinition, Long> {
}
