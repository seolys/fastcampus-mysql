package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReadService {

	private final MemberRepository memberRepository;
	private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

	public MemberDto getMember(final Long id) {
		final var member = memberRepository.findById(id).orElseThrow();
		return MemberDto.from(member);
	}

	public List<MemberNicknameHistoryDto> getNicknameHistories(final Long memberId) {
		return memberNicknameHistoryRepository.findAllByMemberId(memberId)
				.stream()
				.map(MemberNicknameHistoryDto::from)
				.collect(Collectors.toList());
	}

}
