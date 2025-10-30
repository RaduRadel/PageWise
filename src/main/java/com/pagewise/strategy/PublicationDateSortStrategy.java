package com.pagewise.strategy;

import com.pagewise.model.Book;
import java.util.Comparator;
import java.util.List;

public class PublicationDateSortStrategy implements SortStrategy {
	@Override
	public List<Book> sort(List<Book> books) {
		return books.stream()
				.sorted(Comparator.comparing(Book::getPublicationDate).reversed())
				.collect(java.util.stream.Collectors.toList());
	}
}


