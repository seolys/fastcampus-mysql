package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

	private final PostWriteService postWriteService;
	private final PostReadService postReadService;

	@PostMapping
	public Long create(final PostCommand command) {
		return postWriteService.create(command);
	}

	@GetMapping("/daily-post-counts")
	public List<DailyPostCount> getDailyPostCounts(final DailyPostCountRequest request) {
		return postReadService.getDailyPostCount(request);
	}

	/**
	 * 회원별 Post조회
	 * @param memberId
	 * @param pageable <code>page=0&size=10&sort=createdDate,desc</code>
	 * @return
	 */
	@GetMapping("/members/{memberId}")
	public Page<Post> getPosts(
			@PathVariable("memberId") final Long memberId,
//			@RequestParam("page") final int page,
//			@RequestParam("size") final int size
			final Pageable pageable
	) {
//		return postReadService.getPosts(memberId, PageRequest.of(page, size));
		return postReadService.getPosts(memberId, pageable);
	}
}
