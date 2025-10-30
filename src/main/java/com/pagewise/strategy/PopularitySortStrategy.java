package com.pagewise.strategy;

import com.pagewise.model.Book;
import java.util.Comparator;
import java.util.List;

public class PopularitySortStrategy implements SortStrategy {
	@Override
	public List<Book> sort(List<Book> books) {
		return books.stream()
				.sorted(Comparator.comparing(Book::getAvailableCopies).reversed()
						.thenComparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER))
				.collect(java.util.stream.Collectors.toList());
	}
}


