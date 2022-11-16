package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.domain.post.service.TimelineWriteService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostUsecase {

	private final PostWriteService postWriteService;
	private final FollowReadService followReadService;
	private final TimelineWriteService timelineWriteService;

	public Long execute(final PostCommand postCommand) {
		final var postId = postWriteService.create(postCommand);

		final var followerMemberIds = followReadService.getFollowers(postCommand.memberId())
				.stream()
				.map(Follow::getFromMemberId)
				.collect(Collectors.toList());

		timelineWriteService.deliveryToTimeline(postId, followerMemberIds);
		return postId;
	}

}
