package com.pagewise.strategy;

import com.pagewise.model.Book;
import java.util.List;

public class ExactSearchStrategy implements SearchStrategy {
	@Override
	public List<Book> search(List<Book> books, String query) {
		return books.stream()
				.filter(book -> book.getTitle().equalsIgnoreCase(query) ||
						book.getAuthor().equalsIgnoreCase(query) ||
						book.getGenre().equalsIgnoreCase(query))
                .collect(java.util.stream.Collectors.toList());
	}
}


