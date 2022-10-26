package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public Member save(final Member member) {
		/**
		 * member id를 보고 갱신 또는 삽입을 정함
		 * 반환값은 id를 담아서 반환한다.
		 */
		if (Objects.isNull(member.getId())) {
			return insert(member);
		}
		return update(member);
	}

	private Member insert(final Member member) {
		final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
				.withTableName("member")
				.usingGeneratedKeyColumns("id");
		final SqlParameterSource params = new BeanPropertySqlParameterSource(member);
		final var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
		return Member.builder()
				.id(id)
				.email(member.getEmail())
				.nickname(member.getNickname())
				.birthday(member.getBirthday())
				.createdAt(member.getCreatedAt())
				.build();
	}

	private Member update(final Member member) {
		// TODO
		return member;
	}

}
