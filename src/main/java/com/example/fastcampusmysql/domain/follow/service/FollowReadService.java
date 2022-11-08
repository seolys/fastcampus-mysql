package com.example.fastcampusmysql.domain.follow.service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowReadService {
	
	private final FollowRepository followRepository;

	public List<Follow> getFollowings(final Long memberId) {
		return followRepository.findAllByFromMemberId(memberId);
	}

}
