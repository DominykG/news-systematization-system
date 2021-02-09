package com.bachelors.nss.database.repositories;

import com.bachelors.nss.database.models.NewsTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<NewsTopic, Long> {

}
