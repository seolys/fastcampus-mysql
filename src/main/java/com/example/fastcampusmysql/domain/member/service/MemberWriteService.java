package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

	private final MemberRepository memberRepository;
	private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

	public MemberDto register(final RegisterMemberCommand command) {
		/**
		 * 목표 - 회원정보(이메일, 닉네임, 생년월일)를 등록한다.
		 *     - 닉네임은 10자를 넘길 수 없다.
		 * 파라미터 - memberRegisterCommand
		 *
		 * var member = Member.of(memberRegisterCommand)
		 * memberRepository.save(member)
		 */
		final var member = Member.builder()
				.email(command.email())
				.nickname(command.nickname())
				.birthday(command.birthday())
				.build();
		final var savedMember = memberRepository.save(member);
		saveMemberNicknameHistory(savedMember.getId(), savedMember.getNickname());
		return MemberDto.from(savedMember);
	}

	public void changeNickname(final Long memberId, final String nickname) {
		final var member = memberRepository.findById(memberId).orElseThrow();
		member.changeNickname(nickname);
		memberRepository.save(member);

		saveMemberNicknameHistory(memberId, nickname);
	}

	private void saveMemberNicknameHistory(final Long memberId, final String nickname) {
		final var memberNicknameHistory = MemberNicknameHistory.builder()
				.memberId(memberId)
				.nickname(nickname)
				.build();
		memberNicknameHistoryRepository.save(memberNicknameHistory);
	}


}
