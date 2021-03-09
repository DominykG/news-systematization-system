package com.bachelors.nss.db.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLIENT")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "C_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "C_NAME", nullable = false)
    private String name;

    @Column(name = "C_DATE_FROM")
    private LocalDateTime dateFrom;

    @Column(name = "C_KAFKA_TOPIC", nullable = false, unique = true)
    private String assignedKafkaTopic;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "CLIENT_SOURCE",
            joinColumns = { @JoinColumn(name = "c_id") },
            inverseJoinColumns = { @JoinColumn(name = "s_id") }
    )
    private Set<Source> sources;
}
