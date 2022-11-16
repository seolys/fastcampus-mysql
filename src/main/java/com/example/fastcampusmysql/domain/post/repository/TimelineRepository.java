package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.entity.Timeline;
import java.sql.ResultSet;
import java.time.LocalDateTime;
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
public class TimelineRepository {

	private static final String TABLE = "Timeline";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final RowMapper<Timeline> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> Timeline.builder()
			.id(resultSet.getLong("id"))
			.memberId(resultSet.getLong("memberId"))
			.postId(resultSet.getLong("postId"))
			.createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
			.build();

	public Timeline save(final Timeline timeline) {
		if (timeline.getId() == null) {
			return insert(timeline);
		}
		throw new UnsupportedOperationException("Timeline은 갱신을 지원하지 않습니다.");
	}

	public List<Timeline> findAllByMemberIdOrderByIdDesc(final Long memberId, final int size) {
		final var sql = String.format("""
				 SELECT *
				 FROM %s
				 WHERE memberId = :memberId
				 ORDER BY createdAt DESC
				 LIMIT :size
				""", TABLE);
		final var params = new MapSqlParameterSource()
				.addValue("memberId", memberId)
				.addValue("size", size);
		return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
	}

	public List<Timeline> findAllByLessThanIdAndMemberIdOrderByIdDesc(final Long id, final Long memberId, final int size) {
		final var sql = String.format("""
				SELECT *
				FROM %s
				WHERE memberId = :memberIds and id < :id
				ORDER BY id DESC
				LIMIT :size
				""", TABLE);
		final var params = new MapSqlParameterSource()
				.addValue("memberId", memberId)
				.addValue("id", id)
				.addValue("size", size);
		return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
	}

	private Timeline insert(final Timeline timeline) {
		final SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
				.withTableName(TABLE)
				.usingGeneratedKeyColumns("id");

		final SqlParameterSource params = new BeanPropertySqlParameterSource(timeline);
		final var id = jdbcInsert.executeAndReturnKey(params).longValue();

		return Timeline.builder()
				.id(id)
				.memberId(timeline.getMemberId())
				.postId(timeline.getPostId())
				.createdAt(timeline.getCreatedAt())
				.build();
	}

	public void bulkInsert(final List<Timeline> timelines) {
		final var sql = String.format("""
				INSERT INTO %s (memberId, postId, createdAt)
				VALUES (:memberId, :postId, :createdAt)
				""", TABLE);

		final SqlParameterSource[] params = timelines.stream()
				.map(BeanPropertySqlParameterSource::new)
				.toArray(SqlParameterSource[]::new);
		namedParameterJdbcTemplate.batchUpdate(sql, params);
	}

}
