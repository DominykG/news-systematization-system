package com.bachelors.nss.database.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RSS_FEED")
public class RssFeed {

    @Id
    @Column(name = "R_ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "R_URL", nullable = false, unique = true)
    private String url;

    @Column(name = "R_SOURCE", nullable = false, unique = true)
    private String source;

    @JsonBackReference
    @ManyToMany(mappedBy = "rssFeeds", cascade = CascadeType.REMOVE)
    private Set<Client> clients = new HashSet<>();

}
