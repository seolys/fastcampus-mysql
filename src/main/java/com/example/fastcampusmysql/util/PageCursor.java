package com.example.fastcampusmysql.util;

import java.util.List;

public record PageCursor<T>(
		CursorRequest nextCursorReqeust,
		List<T> body
) {

}
