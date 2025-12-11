package com.example.demo.controller;

import com.example.demo.model.MappingSchema;
import com.example.demo.service.MappingSchemaService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schemas")
@RequiredArgsConstructor
public class MappingSchemaController {

    private final MappingSchemaService service;

    @GetMapping
    public List<MappingSchema> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public MappingSchema getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public MappingSchema create(@RequestBody MappingSchema schema) {
        return service.create(schema);
    }

    @PutMapping("/{id}")
    public MappingSchema update(@PathVariable Long id, @RequestBody MappingSchema schema) {
        return service.update(id, schema);
    }

    @PatchMapping("/{id}")
    public MappingSchema partialUpdate(@PathVariable Long id, @RequestBody MappingSchema patch) {
        return service.partialUpdate(id, patch);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
