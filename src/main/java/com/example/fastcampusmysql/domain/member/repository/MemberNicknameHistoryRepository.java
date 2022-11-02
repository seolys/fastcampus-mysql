package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.MemberNicknameHistory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
public class MemberNicknameHistoryRepository {

	private static final String TABLE = "MemberNicknameHistory";
	private static final RowMapper<MemberNicknameHistory> rowMapper = (rs, rowNum) -> MemberNicknameHistory.builder()
			.id(rs.getLong("id"))
			.memberId(rs.getLong("memberId"))
			.nickname(rs.getString("nickname"))
			.createdAt(rs.getObject("createdAt", LocalDateTime.class))
			.build();

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List<MemberNicknameHistory> findAllByMemberId(final Long memberId) {
		final var sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLE);
		final var params = new MapSqlParameterSource().addValue("memberId", memberId);
		return namedParameterJdbcTemplate.query(sql, params, rowMapper);
	}

	public MemberNicknameHistory save(final MemberNicknameHistory member) {
		/**
		 * member id를 보고 갱신 또는 삽입을 정함
		 * 반환값은 id를 담아서 반환한다.
		 */
		if (Objects.isNull(member.getId())) {
			return insert(member);
		}
		throw new UnsupportedOperationException();
	}

	private MemberNicknameHistory insert(final MemberNicknameHistory history) {
		final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
				.withTableName(TABLE)
				.usingGeneratedKeyColumns("id");
		final SqlParameterSource params = new BeanPropertySqlParameterSource(history);
		final var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
		return MemberNicknameHistory.builder()
				.id(id)
				.memberId(history.getMemberId())
				.nickname(history.getNickname())
				.createdAt(history.getCreatedAt())
				.build();
	}


}
