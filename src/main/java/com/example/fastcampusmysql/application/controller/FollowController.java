package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreateFollowMemberUsecase;
import com.example.fastcampusmysql.application.usecase.GetFollowingMemberUsecase;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/follow")
@RestController
@RequiredArgsConstructor
public class FollowController {

	private final CreateFollowMemberUsecase createFollowMemberUsecase;
	private final GetFollowingMemberUsecase getFollowingMemberUsecase;

	@PostMapping("/{fromId}/{toId}")
	public void register(@PathVariable final Long fromId, @PathVariable final Long toId) {
		createFollowMemberUsecase.execute(fromId, toId);
	}

	@GetMapping("/members/{fromId}")
	public List<MemberDto> followingMembers(@PathVariable final Long fromId) {
		return getFollowingMemberUsecase.execute(fromId);
	}

}
