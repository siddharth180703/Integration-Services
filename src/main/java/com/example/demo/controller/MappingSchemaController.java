package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.MappingSchemaDTO;
import com.example.demo.service.MappingSchemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mapping-schemas")
@RequiredArgsConstructor
@Slf4j
public class MappingSchemaController {

    private final MappingSchemaService service;

    /* ---------- CREATE ---------- */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<MappingSchemaDTO> create(
            @Valid @RequestBody MappingSchemaDTO dto) {

        log.info("API call: create mapping");
        return new ApiResponse<>("SUCCESS", "Mapping created", service.create(dto));
    }

    /* ---------- READ ALL ---------- */
    @GetMapping
    public ApiResponse<Page<MappingSchemaDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return new ApiResponse<>(
                "SUCCESS",
                "Mappings fetched",
                service.getAll(PageRequest.of(page, size, Sort.by(sortBy)))
        );
    }

    /* ---------- READ BY ID ---------- */
    @GetMapping("/{id}")
    public ApiResponse<MappingSchemaDTO> getById(@PathVariable Long id) {
        return new ApiResponse<>("SUCCESS", "Mapping fetched", service.getById(id));
    }

    /* ---------- UPDATE (PUT) ---------- */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<MappingSchemaDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody MappingSchemaDTO dto) {

        return new ApiResponse<>("SUCCESS", "Mapping updated", service.update(id, dto));
    }

    /* ---------- PARTIAL UPDATE (PATCH) ---------- */
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ApiResponse<MappingSchemaDTO> patch(
            @PathVariable Long id,
            @RequestBody MappingSchemaDTO dto) {

        return new ApiResponse<>("SUCCESS", "Mapping patched", service.partialUpdate(id, dto));
    }

    /* ---------- DELETE ---------- */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ApiResponse<>("SUCCESS", "Mapping deleted", null);
    }
}

