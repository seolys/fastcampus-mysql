package com.example.fastcampusmysql.util;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class PageHelper {

	public static String orderBy(final Sort sort) {
		if (sort.isEmpty()) {
			return "id DESC";
		}

		final List<Order> orders = sort.toList();
		return orders.stream()
				.map(order -> order.getProperty() + " " + order.getDirection())
				.collect(Collectors.joining(", "));
	}

}
