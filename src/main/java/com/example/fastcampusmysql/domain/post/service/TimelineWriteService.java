package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.repository.TimelineRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimelineWriteService {

	private final TimelineRepository timelineRepository;

	public void deliveryToTimeline(final Long postId, final List<Long> toMemberIds) {
		final var timeLines = toMemberIds.stream()
				.map(memberId -> toTimeline(postId, memberId))
				.toList();
		timelineRepository.bulkInsert(timeLines);
	}

	private static Timeline toTimeline(final Long postId, final Long memberId) {
		return Timeline.builder()
				.memberId(memberId)
				.postId(postId)
				.build();
	}

}
