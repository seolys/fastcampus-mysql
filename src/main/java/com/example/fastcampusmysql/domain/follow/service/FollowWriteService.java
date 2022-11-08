package com.example.fastcampusmysql.domain.follow.service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class FollowWriteService {

	private final FollowRepository followRepository;

	public void create(final MemberDto fromMember, final MemberDto toMember) {
		Assert.isTrue(!Objects.equals(fromMember.id(), toMember.id()), "자기 자신을 팔로우할 수 없습니다.");

		final var follow = Follow.builder()
				.fromMemberId(fromMember.id())
				.toMemberId(toMember.id())
				.build();
		followRepository.save(follow);
	}

}
