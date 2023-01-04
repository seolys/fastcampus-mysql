package com.example.fastcampusmysql.domain.post.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Post {

    private final Long id;

    private final Long memberId;

    private final String contents;

    private final LocalDate createdDate;

    private Long likeCount;

    private final LocalDateTime createdAt;

    @Builder
    public Post(final Long id, final Long memberId, final String contents, final LocalDate createdDate, final Long likeCount, final LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(contents);
        this.createdDate = Objects.isNull(createdDate) ? LocalDate.now() : createdDate;
        this.likeCount = Objects.isNull(likeCount) ? 0 : likeCount;
        this.createdAt = Objects.isNull(createdAt) ? LocalDateTime.now() : createdAt;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

}
