package com.example.fastcampusmysql.domain.member.repository;

import static java.util.Collections.emptyList;

import com.example.fastcampusmysql.domain.member.entity.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
	public static final RowMapper<Member> rowMapper = (rs, rowNum) -> Member.builder()
			.id(rs.getLong("id"))
			.nickname(rs.getString("nickname"))
			.email(rs.getString("email"))
			.birthday(rs.getObject("birthday", LocalDate.class))
			.createdAt(rs.getObject("createdAt", LocalDateTime.class))
			.build();

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public Optional<Member> findById(final Long id) {
		final var sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);
		final var params = new MapSqlParameterSource()
				.addValue("id", id);
		final var member = namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);
		return Optional.ofNullable(member);
	}

	public List<Member> findAllByIdIn(final List<Long> ids) {
		if (ids.isEmpty()) {
			return emptyList();
		}
		final var sql = String.format("SELECT * FROM %s WHERE id in (:ids)", TABLE);
		final var params = new MapSqlParameterSource().addValue("ids", ids);
		return namedParameterJdbcTemplate.query(sql, params, rowMapper);
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
				.withTableName(TABLE)
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
		final var sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", TABLE);
		final var params = new BeanPropertySqlParameterSource(member);
		namedParameterJdbcTemplate.update(sql, params);
		return member;
	}

}
