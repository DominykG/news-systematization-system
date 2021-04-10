package com.bachelors.nss.database.repositories;

import com.bachelors.nss.database.models.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<Source, String> {
    Source findByName(String name);
}
