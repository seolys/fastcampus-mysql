package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReadService {

	private final MemberRepository memberRepository;

	public MemberDto getMember(final Long id) {
		final var member = memberRepository.findById(id).orElseThrow();
		return MemberDto.from(member);
	}

}
