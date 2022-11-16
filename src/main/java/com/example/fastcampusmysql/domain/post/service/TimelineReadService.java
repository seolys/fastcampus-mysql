package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.repository.TimelineRepository;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimelineReadService {

	private final TimelineRepository timelineRepository;

	public PageCursor<Timeline> getTimelines(final Long memberId, final CursorRequest cursorRequest) {
		final var timelines = findAllBy(memberId, cursorRequest);
		final var nextKey = timelines.stream()
				.mapToLong(Timeline::getId)
				.min()
				.orElse(CursorRequest.NONE_KEY);
		return new PageCursor<>(cursorRequest.next(nextKey), timelines);
	}

	private List<Timeline> findAllBy(final Long memberId, final CursorRequest cursorRequest) {
		if (cursorRequest.hasKey()) {
			return timelineRepository.findAllByLessThanIdAndMemberIdOrderByIdDesc(
					cursorRequest.key(), memberId, cursorRequest.size());
		}
		return timelineRepository.findAllByMemberIdOrderByIdDesc(memberId, cursorRequest.size());
	}

	private static long getNextKey(final List<Timeline> timelines) {
		return timelines.stream()
				.mapToLong(Timeline::getId)
				.min()
				.orElse(CursorRequest.NONE_KEY);
	}

}
