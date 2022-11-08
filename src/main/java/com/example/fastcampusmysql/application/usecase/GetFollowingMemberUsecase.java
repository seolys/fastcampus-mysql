package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GetFollowingMemberUsecase {

	private final MemberReadService memberReadService;
	private final FollowReadService followReadService;

	public List<MemberDto> execute(final Long memberId) {
		/**
		 * 1. fromMemberId = memberId -> Follow list
		 * 2. 1번을 순회하면서 회원정보를 찾으면 된다.
		 */
		final var followings = followReadService.getFollowings(memberId);
		final var followingIds = followings.stream().map(Follow::getToMemberId).collect(Collectors.toList());
		return memberReadService.getMembers(followingIds);
	}

}
