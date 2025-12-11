package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "mapping_schemas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MappingSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mappingKey;

    private String protocolIn;
    private String protocolOut;

    private String formatIn;
    private String formatOut;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> requestMapping= new HashMap<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> responseMapping= new HashMap<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> staticFields= new HashMap<>();

    @OneToMany(mappedBy = "mappingSchema", cascade = CascadeType.ALL)
    private java.util.List<ServiceSchema> services;
}
