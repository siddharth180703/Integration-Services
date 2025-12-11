package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service_schema")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private String baseUrl;
    private String authType;
    private Integer timeout;
    @ManyToOne
    @JoinColumn(name = "mapping_schema_id")
    private MappingSchema mappingSchema;
}
