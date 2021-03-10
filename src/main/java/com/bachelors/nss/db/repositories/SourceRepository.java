package com.bachelors.nss.db.repositories;

import com.bachelors.nss.db.models.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<Source, String> {
    Source findByName(String name);
}
