package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private static final String TABLE = "member";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public Optional<Member> findById(final Long id) {
		final var sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);
		final var params = new MapSqlParameterSource()
				.addValue("id", id);
		final RowMapper<Member> rowMapper = (rs, rowNum) -> Member.builder()
				.id(rs.getLong("id"))
				.nickname(rs.getString("nickname"))
				.email(rs.getString("email"))
				.birthday(rs.getObject("birthday", LocalDate.class))
				.createdAt(rs.getObject("createdAt", LocalDateTime.class))
				.build();
		final var member = namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);
		return Optional.ofNullable(member);
	}

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
