package com.example.fastcampusmysql.domain.follow.repository;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import java.sql.ResultSet;
import java.util.List;
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
public class FollowRepository {

	private final String TABLE = "follow";
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final RowMapper<Follow> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> Follow.builder()
			.id(resultSet.getLong("id"))
			.fromMemberId(resultSet.getLong("fromMemberId"))
			.toMemberId(resultSet.getLong("toMemberId"))
			.createdAt(resultSet.getObject("createdAt", java.time.LocalDateTime.class))
			.build();

	public List<Follow> findAllByFromMemberId(final Long fromMemberId) {
		final var sql = String.format("SELECT * FROM %s WHERE fromMemberId = :fromMemberId", TABLE);
		final var params = new MapSqlParameterSource().addValue("fromMemberId", fromMemberId);
		return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
	}

	public Follow save(final Follow follow) {
		if (follow.getId() == null) {
			return insert(follow);
		}
		throw new UnsupportedOperationException("Follow는 갱신을 지원하지 않습니다.");
	}

	private Follow insert(final Follow follow) {
		final SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
				.withTableName(TABLE)
				.usingGeneratedKeyColumns("id");

		final SqlParameterSource params = new BeanPropertySqlParameterSource(follow);
		final var id = jdbcInsert.executeAndReturnKey(params).longValue();

		return Follow.builder()
				.id(id)
				.fromMemberId(follow.getFromMemberId())
				.toMemberId(follow.getToMemberId())
				.createdAt(follow.getCreatedAt())
				.build();
	}

}
