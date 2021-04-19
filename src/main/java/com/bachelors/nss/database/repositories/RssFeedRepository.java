package com.bachelors.nss.database.repositories;

import com.bachelors.nss.database.models.RssFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RssFeedRepository extends JpaRepository<RssFeed, String> {
}
