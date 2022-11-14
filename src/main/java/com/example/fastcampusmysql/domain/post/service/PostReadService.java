package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReadService {

	private final PostRepository postRepository;

	public List<DailyPostCount> getDailyPostCount(final DailyPostCountRequest request) {
		/**
		 * 반환 값 -> 리스트 [작성일자, 작성회원, 작성 게시물 갯수]

		 select *
		 from Post
		 where memberId = :memberId
		 and createdDate between :startDate and :endDate
		 group by createdDate, memberId
		 */
		return postRepository.groupByCreatedDate(request);
	}

	public Page<Post> getPosts(final Long memberId, final Pageable pageable) {
		return postRepository.findAllByMemberId(memberId, pageable);
	}

	public PageCursor<Post> getPosts(final Long memberId, final CursorRequest cursorRequest) {
		final var posts = findAllBy(memberId, cursorRequest);
		final var nextKey = posts.stream()
				.mapToLong(Post::getId)
				.min()
				.orElse(CursorRequest.NONE_KEY);
		return new PageCursor<>(cursorRequest.next(nextKey), posts);
	}

	private List<Post> findAllBy(final Long memberId, final CursorRequest cursorRequest) {
		if (cursorRequest.hasKey()) {
			return postRepository.findAllByLessThanIdAndMemberIdOrderByIdDesc(
					cursorRequest.key(), memberId, cursorRequest.size());
		}
		return postRepository.findAllByMemberIdOrderByIdDesc(memberId, cursorRequest.size());
	}

}
