package com.pagewise.strategy;

import com.pagewise.model.Book;
import java.util.List;

public class MultiCriteriaSortStrategy implements SortStrategy {
	private final List<SortStrategy> strategies;

	public MultiCriteriaSortStrategy(List<SortStrategy> strategies) {
		this.strategies = strategies;
	}

	@Override
	public List<Book> sort(List<Book> books) {
		List<Book> result = books;
		for (SortStrategy strategy : strategies) {
			result = strategy.sort(result);
		}
		return result;
	}
}


