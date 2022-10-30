package com.example.fastcampusmysql.domain.member.entity;

import static org.assertj.core.api.Assertions.*;

import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

	@Test
	@DisplayName("회원은 닉네임을 변경할 수 있다.")
	void changeNickname_success() {
		final var member = MemberFixtureFactory.create();
		final var expected = "seol";

		member.changeNickname(expected);

		assertThat(member.getNickname()).isEqualTo(expected);
	}

	@Test
	@DisplayName("회원의 닉네임은 10자를 초과할 수 없다.")
	void changeNickname_maxLength_fail() {
		final var member = MemberFixtureFactory.create();
		final var expected = "1234567890a";

		assertThatThrownBy(() -> member.changeNickname(expected))
				.isInstanceOf(IllegalArgumentException.class);
	}


}