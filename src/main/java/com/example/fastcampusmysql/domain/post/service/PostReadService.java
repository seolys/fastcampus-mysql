package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

}
