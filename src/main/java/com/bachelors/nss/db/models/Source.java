package com.bachelors.nss.db.models;

import lombok.Getter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SOURCE")
public class Source implements Serializable {

    @Id
    @Column(name = "S_ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "S_NAME", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "sources")
    private Set<Client> clients;
}
