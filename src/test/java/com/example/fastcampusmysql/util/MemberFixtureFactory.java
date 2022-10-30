package com.example.fastcampusmysql.util;

import com.example.fastcampusmysql.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class MemberFixtureFactory {

	public static Member create() {
		final var param = new EasyRandomParameters();
		return new EasyRandom(param).nextObject(Member.class);
	}

	public static Member create(final long seed) {
		final var param = new EasyRandomParameters().seed(seed);
		return new EasyRandom(param).nextObject(Member.class);
	}

}
