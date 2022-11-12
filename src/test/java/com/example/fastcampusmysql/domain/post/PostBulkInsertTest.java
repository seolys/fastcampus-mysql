package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@SpringBootTest
public class PostBulkInsertTest {

	@Autowired private PostRepository postRepository;

	@Test
	public void bulkInsert() {
		final var easyRandom = PostFixtureFactory.get(
				3L,
				LocalDate.of(2022, 1, 1),
				LocalDate.of(2022, 2, 1)
		);

		final var easyRandomStopWatch = new StopWatch();
		easyRandomStopWatch.start();

		final var posts = IntStream.range(0, 10_000 * 100)
				.parallel()
				.mapToObj(i -> easyRandom.nextObject(Post.class))
				.collect(Collectors.toList());
		easyRandomStopWatch.stop();
		System.out.println("객체 생성 시간 = " + easyRandomStopWatch.getTotalTimeSeconds());

		final var queryStopWatch = new StopWatch();
		queryStopWatch.start();
		postRepository.bulkInsert(posts);
		queryStopWatch.stop();
		System.out.println("DB INSERT 시간 = " + queryStopWatch.getTotalTimeSeconds());
	}

}

