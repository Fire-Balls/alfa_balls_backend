//package org.fireballs.alfaballs.app.service;
//
//import org.fireballs.alfaballs.app.repository.FieldDefinitionRepository;
//import org.fireballs.alfaballs.domain.FieldDefinition;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class FieldDefinitionServiceIntegrationTest {
//
//    @Autowired
//    private FieldDefinitionService fieldDefinitionService;
//
//    @Autowired
//    private FieldDefinitionRepository fieldDefinitionRepository;
//
//    @Test
//    void saveFieldDefinition_shouldPersistSuccessfully() {
//        FieldDefinition fieldDefinition = new FieldDefinition(null, "Priority", "string");
//
//        FieldDefinition saved = fieldDefinitionService.saveFieldDefinition(fieldDefinition);
//
//        assertNotNull(saved.getId());
//        assertEquals("Priority", saved.getName());
//        assertEquals("string", saved.getDataType());
//    }
//
//    @Test
//    void getFieldDefinitionById_shouldReturnCorrectEntity() {
//        FieldDefinition fieldDefinition = new FieldDefinition(null, "Severity", "int");
//
//        FieldDefinition saved = fieldDefinitionService.saveFieldDefinition(fieldDefinition);
//
//        FieldDefinition found = fieldDefinitionService.getFieldDefinitionById(saved.getId());
//
//        assertEquals(saved.getId(), found.getId());
//        assertEquals("Severity", found.getName());
//    }
//
//    @Test
//    void deleteFieldDefinition_shouldRemoveEntity() {
//        FieldDefinition fieldDefinition = new FieldDefinition(null, "Component", "string");
//
//        FieldDefinition saved = fieldDefinitionService.saveFieldDefinition(fieldDefinition);
//
//        fieldDefinitionService.deleteFieldDefinition(saved.getId());
//
//        assertFalse(fieldDefinitionRepository.findById(saved.getId()).isPresent());
//    }
//
//    @Test
//    void getAllFieldDefinitions_shouldReturnAllSaved() {
//        fieldDefinitionService.saveFieldDefinition(new FieldDefinition(null, "Module", "string"));
//        fieldDefinitionService.saveFieldDefinition(new FieldDefinition(null, "Category", "enum"));
//
//        List<FieldDefinition> all = fieldDefinitionService.getAllFieldDefinitions();
//
//        assertTrue(all.size() >= 2);
//        assertTrue(all.stream().anyMatch(f -> f.getName().equals("Module")));
//    }
//
//    @Test
//    void saveFieldDefinition_shouldThrowException_whenNull() {
//        assertThrows(IllegalArgumentException.class, () -> fieldDefinitionService.saveFieldDefinition(null));
//    }
//}
