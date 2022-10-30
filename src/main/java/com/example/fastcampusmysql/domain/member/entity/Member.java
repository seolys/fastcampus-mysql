package com.example.fastcampusmysql.domain.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class Member {

	final private Long id;

	private String nickname;

	final private String email;

	final private LocalDate birthday;

	final private LocalDateTime createdAt;

	final private static Long NAME_MAX_LENGTH = 10L;

	@Builder(toBuilder = true)
	public Member(
			final Long id,
			final String nickname,
			final String email,
			final LocalDate birthday,
			final LocalDateTime createdAt
	) {
		validateNickname(nickname);
		this.id = id;
		this.nickname = Objects.requireNonNull(nickname);
		this.email = Objects.requireNonNull(email);
		this.birthday = Objects.requireNonNull(birthday);
		this.createdAt = Objects.requireNonNullElse(createdAt, LocalDateTime.now());
	}

	public void changeNickname(final String nickname) {
		Objects.requireNonNull(nickname);
		validateNickname(nickname);
		this.nickname = nickname;
	}

	private void validateNickname(final String nickname) {
		Assert.isTrue(nickname.length() <= NAME_MAX_LENGTH, "닉네임은 10자를 넘길 수 없습니다.");
	}
}
