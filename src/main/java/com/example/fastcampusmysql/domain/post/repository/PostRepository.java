package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepository {

	private static final String TABLE = "Post";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final RowMapper<DailyPostCount> DAILY_POST_COUNT_MAPPER = (ResultSet resultSet, int rowNum) -> new DailyPostCount(
			resultSet.getLong("memberId"),
			resultSet.getObject("createdDate", LocalDate.class),
			resultSet.getLong("postCount"));

	public List<DailyPostCount> groupByCreatedDate(final DailyPostCountRequest request) {
		final var sql = String.format("""
				 SELECT memberId, createdDate, COUNT(id) as postCount 
				 FROM %s
				 WHERE memberId = :memberId
				 AND createdDate between :firstDate AND :lastDate
				 GROUP BY memberId, createdDate 
				""", TABLE);
		final var params = new BeanPropertySqlParameterSource(request);
		return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_MAPPER);
	}

	public Post save(final Post post) {
		if (post.getId() == null) {
			return insert(post);
		}
		throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");
	}

	public void bulkInsert(final List<Post> posts) {
		final var sql = String.format("""
				INSERT INTO %s (memberId, contents, createdDate, createdAt)
				VALUES (:memberId, :contents, :createdDate, :createdAt)
				""", TABLE);

		final SqlParameterSource[] params = posts.stream()
				.map(BeanPropertySqlParameterSource::new)
				.toArray(SqlParameterSource[]::new);
		namedParameterJdbcTemplate.batchUpdate(sql, params);
	}

	private Post insert(final Post post) {
		final SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
				.withTableName(TABLE)
				.usingGeneratedKeyColumns("id");

		final SqlParameterSource params = new BeanPropertySqlParameterSource(post);
		final var id = jdbcInsert.executeAndReturnKey(params).longValue();

		return Post.builder()
				.id(id)
				.memberId(post.getMemberId())
				.contents(post.getContents())
				.createdDate(post.getCreatedDate())
				.createdAt(post.getCreatedAt())
				.build();
	}
}
