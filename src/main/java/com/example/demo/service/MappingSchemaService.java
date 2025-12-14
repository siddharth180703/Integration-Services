package com.example.demo.service;
import java.util.Map;
import lombok.Data;
import com.example.demo.dto.MappingSchemaDTO;
import com.example.demo.model.MappingSchema;
import com.example.demo.repository.MappingSchemaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
@Slf4j
public class MappingSchemaService {

    private final MappingSchemaRepository repository;

    /* ---------- CREATE ---------- */
    public MappingSchemaDTO create(MappingSchemaDTO dto) {
        log.info("Creating mapping with key {}", dto.getMappingKey());

        MappingSchema entity = toEntity(dto);
        MappingSchema saved = repository.save(entity);

        return toDTO(saved);
    }

    /* ---------- READ ALL (Pagination) ---------- */
    public Page<MappingSchemaDTO> getAll(Pageable pageable) {
        log.info("Fetching mappings page {}", pageable.getPageNumber());
        return repository.findAll(pageable).map(this::toDTO);
    }

    /* ---------- READ BY ID ---------- */
    public MappingSchemaDTO getById(Long id) {
        log.info("Fetching mapping with id {}", id);

        MappingSchema entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mapping not found"));

        return toDTO(entity);
    }

    /* ---------- UPDATE (PUT = full update) ---------- */
    public MappingSchemaDTO update(Long id, MappingSchemaDTO dto) {
        log.info("Updating mapping (PUT) id {}", id);

        MappingSchema entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mapping not found"));

        entity.setMappingKey(dto.getMappingKey());
        entity.setProtocolIn(dto.getProtocolIn());
        entity.setProtocolOut(dto.getProtocolOut());
        entity.setFormatIn(dto.getFormatIn());
        entity.setFormatOut(dto.getFormatOut());
        entity.setRequestMapping(dto.getRequestMapping());
        entity.setResponseMapping(dto.getResponseMapping());
        entity.setStaticFields(dto.getStaticFields());

        return toDTO(repository.save(entity));
    }

    /* ---------- PARTIAL UPDATE (PATCH) ---------- */
    public MappingSchemaDTO partialUpdate(Long id, MappingSchemaDTO dto) {
        log.info("Updating mapping (PATCH) id {}", id);

        MappingSchema entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mapping not found"));

        if (dto.getMappingKey() != null) entity.setMappingKey(dto.getMappingKey());
        if (dto.getProtocolIn() != null) entity.setProtocolIn(dto.getProtocolIn());
        if (dto.getProtocolOut() != null) entity.setProtocolOut(dto.getProtocolOut());
        if (dto.getFormatIn() != null) entity.setFormatIn(dto.getFormatIn());
        if (dto.getFormatOut() != null) entity.setFormatOut(dto.getFormatOut());
        if (dto.getRequestMapping() != null) entity.setRequestMapping(dto.getRequestMapping());
        if (dto.getResponseMapping() != null) entity.setResponseMapping(dto.getResponseMapping());
        if (dto.getStaticFields() != null) entity.setStaticFields(dto.getStaticFields());

        return toDTO(repository.save(entity));
    }

    /* ---------- DELETE ---------- */
    public void delete(Long id) {
        log.warn("Deleting mapping with id {}", id);
        repository.deleteById(id);
    }

    /* ---------- MAPPERS ---------- */
    private MappingSchema toEntity(MappingSchemaDTO dto) {
        MappingSchema entity = new MappingSchema();
        entity.setMappingKey(dto.getMappingKey());
        entity.setProtocolIn(dto.getProtocolIn());
        entity.setProtocolOut(dto.getProtocolOut());
        entity.setFormatIn(dto.getFormatIn());
        entity.setFormatOut(dto.getFormatOut());
        entity.setRequestMapping(dto.getRequestMapping());
        entity.setResponseMapping(dto.getResponseMapping());
        entity.setStaticFields(dto.getStaticFields());
        return entity;
    }

    private MappingSchemaDTO toDTO(MappingSchema entity) {
        MappingSchemaDTO dto = new MappingSchemaDTO();
        dto.setMappingKey(entity.getMappingKey());
        dto.setProtocolIn(entity.getProtocolIn());
        dto.setProtocolOut(entity.getProtocolOut());
        dto.setFormatIn(entity.getFormatIn());
        dto.setFormatOut(entity.getFormatOut());
        dto.setRequestMapping(entity.getRequestMapping());
        dto.setResponseMapping(entity.getResponseMapping());
        dto.setStaticFields(entity.getStaticFields());
        return dto;
    }
}
