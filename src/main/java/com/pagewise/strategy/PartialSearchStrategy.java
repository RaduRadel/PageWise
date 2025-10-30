package com.pagewise.strategy;

import com.pagewise.model.Book;
import java.util.List;

public class PartialSearchStrategy implements SearchStrategy {
	@Override
	public List<Book> search(List<Book> books, String query) {
		String lowerQuery = query.toLowerCase();
		return books.stream()
				.filter(book -> book.getTitle().toLowerCase().contains(lowerQuery) ||
						book.getAuthor().toLowerCase().contains(lowerQuery) ||
						book.getGenre().toLowerCase().contains(lowerQuery))
				.collect(java.util.stream.Collectors.toList());
	}
}


