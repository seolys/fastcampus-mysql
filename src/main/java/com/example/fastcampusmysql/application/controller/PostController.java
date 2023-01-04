package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreatePostUsecase;
import com.example.fastcampusmysql.application.usecase.GetTimelinePostsUsecase;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    private final GetTimelinePostsUsecase getTimelinePostsUsecase;
    private final CreatePostUsecase createPostUsecase;

    private final boolean isPull = false;

    @PostMapping
    public Long create(final PostCommand command) {
        if (isPull) {
            return postWriteService.create(command); // pull
        }
        return createPostUsecase.execute(command); // push
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(final DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    /**
     * 회원별 Post조회
     *
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

    /**
     * 회원별 Post조회
     */
    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<Post> getPostsByCursor(
            @PathVariable("memberId") final Long memberId,
            final CursorRequest cursorRequest
    ) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/member/{memberId}/timeline")
    public PageCursor<Post> getTimelinePosts(
            @PathVariable("memberId") final Long memberId,
            final CursorRequest cursorRequest
    ) {
        if (isPull) {
            return getTimelinePostsUsecase.execute(memberId, cursorRequest); // pull
        }
        return getTimelinePostsUsecase.executeByTimeline(memberId, cursorRequest); // push
    }

    @PostMapping("/{postId}/like")
    public void likePost(@PathVariable final Long postId) {
        postWriteService.likePost(postId);
    }

}
