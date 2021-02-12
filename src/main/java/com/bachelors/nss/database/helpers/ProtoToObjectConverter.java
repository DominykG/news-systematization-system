package com.bachelors.nss.database.helpers;

import com.bachelors.nss.database.models.NewsArticle;
import com.bachelors.nss.protobuf.NewsArticleProto.Article;
import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class ProtoToObjectConverter {

    public static NewsArticle parseProtoToObject(Article protoArticle) {
        return NewsArticle.builder()
                .source(protoArticle.getSourceName())
                .author(protoArticle.getAuthor())
                .title(protoArticle.getTitle())
                .description(protoArticle.getDescription())
                .url(protoArticle.getUrl())
                .urlToImage(protoArticle.getImageUrl())
                .publishedAt(protoTimestampToLocalDateTime(protoArticle.getDatePublish()))
                .content(protoArticle.getContent())
                .build();
    }

    private static LocalDateTime protoTimestampToLocalDateTime(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos())
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
    }

}
