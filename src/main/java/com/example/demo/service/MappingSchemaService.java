package com.example.demo.service;

import com.example.demo.model.MappingSchema;
import com.example.demo.repository.MappingSchemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MappingSchemaService {

    private final MappingSchemaRepository repository;

    public List<MappingSchema> getAll() {
        return repository.findAll();
    }

    public MappingSchema getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schema not found"));
    }

    public MappingSchema create(MappingSchema schema) {
        return repository.save(schema);
    }

    public MappingSchema update(Long id, MappingSchema newSchema) {
        newSchema.setId(id);
        return repository.save(newSchema);
    }

    public MappingSchema partialUpdate(Long id, MappingSchema patch) {
        MappingSchema existing = getById(id);

        if (patch.getMappingKey() != null)
            existing.setMappingKey(patch.getMappingKey());

        if (patch.getFormatIn() != null)
            existing.setFormatIn(patch.getFormatIn());

        if (patch.getFormatOut() != null)
            existing.setFormatOut(patch.getFormatOut());

        if (patch.getRequestMapping() != null)
            existing.setRequestMapping(patch.getRequestMapping());

        if (patch.getResponseMapping() != null)
            existing.setResponseMapping(patch.getResponseMapping());

        if (patch.getStaticFields() != null)
            existing.setStaticFields(patch.getStaticFields());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
