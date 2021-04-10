package com.bachelors.nss.database.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SOURCE")
public class Source implements Serializable {

    @Id
    @Column(name = "S_ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "S_NAME", nullable = false, unique = true)
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "sources", cascade = CascadeType.REMOVE)
    private Set<Client> clients = new HashSet<>();
}
