package com.example.fastcampusmysql.domain.post.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

	private final Long id;

	private final Long memberId;

	private final String contents;

	private final LocalDate createdDate;

	private final LocalDateTime createdAt;

	@Builder
	public Post(final Long id, final Long memberId, final String contents, final LocalDate createdDate, final LocalDateTime createdAt) {
		this.id = id;
		this.memberId = Objects.requireNonNull(memberId);
		this.contents = Objects.requireNonNull(contents);
		this.createdDate = Objects.isNull(createdDate) ? LocalDate.now() : createdDate;
		this.createdAt = Objects.isNull(createdAt) ? LocalDateTime.now() : createdAt;
	}
}
