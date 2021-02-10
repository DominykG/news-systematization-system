package com.bachelors.nss.database.repositories;

import com.bachelors.nss.database.models.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<NewsArticle, Long> {

}
