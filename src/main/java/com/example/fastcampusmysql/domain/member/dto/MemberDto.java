package com.example.fastcampusmysql.domain.member.dto;

import com.example.fastcampusmysql.domain.member.entity.Member;
import java.time.LocalDate;

public record MemberDto(
		Long id,
		String email,
		String nickname,
		LocalDate birthday
) {

	public static MemberDto from(final Member member) {
		return new MemberDto(
				member.getId(),
				member.getEmail(),
				member.getNickname(),
				member.getBirthday()
		);
	}

}
