package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.service.FollowWriteService;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFollowMemberUsecase {

	private final MemberReadService memberReadService;
	private final FollowWriteService followWriteService;

	public void execute(final Long fromMemberId, final Long toMemberId) {
		/*
		 * 1. 입력받은 memberId로 회원조회
		 * 2. FollowWriteService.create()
		 * */
		final var fromMember = memberReadService.getMember(fromMemberId);
		final var toMember = memberReadService.getMember(toMemberId);

		followWriteService.create(fromMember, toMember);
	}

}
