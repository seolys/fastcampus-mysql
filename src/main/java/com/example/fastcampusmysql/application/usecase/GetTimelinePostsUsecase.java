package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.TimelineReadService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTimelinePostsUsecase {

	private final FollowReadService followReadService;
	private final PostReadService postReadService;
	private final TimelineReadService timelineReadService;


	public PageCursor<Post> execute(final Long memberId, final CursorRequest cursorRequest) {
		/**
		 * 1. memberId로 follow 조회
		 * 2. 1번의 결과로 게시물 조회
		 */
		final var followings = followReadService.getFollowings(memberId);
		final var followingMemberIds = followings.stream()
				.map(Follow::getToMemberId)
				.toList();
		return postReadService.getPosts(followingMemberIds, cursorRequest);
	}

	public PageCursor<Post> executeByTimeline(final Long memberId, final CursorRequest cursorRequest) {
		/**
		 * 1. Timeline 조회
		 * 2. 1번의 결과로 게시물 조회
		 */
		final var pagedTimelines = timelineReadService.getTimelines(memberId, cursorRequest);
		final var postIds = pagedTimelines.body().stream()
				.map(Timeline::getPostId)
				.toList();
		final var posts = postReadService.getPosts(postIds);
		return new PageCursor<>(pagedTimelines.nextCursorReqeust(), posts);
	}

}
