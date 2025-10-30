package com.pagewise.strategy;

import com.pagewise.model.Book;
import java.util.List;

public class KeywordSearchStrategy implements SearchStrategy {
	@Override
	public List<Book> search(List<Book> books, String query) {
		String[] keywords = query.toLowerCase().split("\\s+");
		return books.stream()
				.filter(book -> {
					String searchableText = (book.getTitle() + " " +
							book.getAuthor() + " " +
							book.getGenre()).toLowerCase();
					for (String keyword : keywords) {
						if (!searchableText.contains(keyword)) {
							return false;
						}
					}
					return true;
				})
				.collect(java.util.stream.Collectors.toList());
	}
}


