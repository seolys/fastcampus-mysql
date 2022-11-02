package com.example.fastcampusmysql.domain.member.dto;

import com.example.fastcampusmysql.domain.MemberNicknameHistory;
import java.time.LocalDateTime;

public record MemberNicknameHistoryDto(
		Long id,
		Long memberId,
		String nickname,
		LocalDateTime createdAt
) {

	public static MemberNicknameHistoryDto from(final MemberNicknameHistory memberNicknameHistory) {
		return new MemberNicknameHistoryDto(
				memberNicknameHistory.getId(),
				memberNicknameHistory.getMemberId(),
				memberNicknameHistory.getNickname(),
				memberNicknameHistory.getCreatedAt()
		);
	}

}
