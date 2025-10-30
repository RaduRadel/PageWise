package com.pagewise.strategy;

import com.pagewise.model.Book;
import java.util.List;

public class FuzzySearchStrategy implements SearchStrategy {
	private static final double SIMILARITY_THRESHOLD = 0.6;

	@Override
	public List<Book> search(List<Book> books, String query) {
		String lowerQuery = query.toLowerCase();
		return books.stream()
				.filter(book -> {
					double titleSimilarity = calculateSimilarity(book.getTitle().toLowerCase(), lowerQuery);
					double authorSimilarity = calculateSimilarity(book.getAuthor().toLowerCase(), lowerQuery);
					double genreSimilarity = calculateSimilarity(book.getGenre().toLowerCase(), lowerQuery);
					return titleSimilarity >= SIMILARITY_THRESHOLD ||
						authorSimilarity >= SIMILARITY_THRESHOLD ||
						genreSimilarity >= SIMILARITY_THRESHOLD;
				})
				.collect(java.util.stream.Collectors.toList());
	}

	private double calculateSimilarity(String s1, String s2) {
		if (s1.equals(s2)) return 1.0;
		if (s1.length() == 0 || s2.length() == 0) return 0.0;
		int maxLength = Math.max(s1.length(), s2.length());
		int distance = levenshteinDistance(s1, s2);
		return 1.0 - (double) distance / maxLength;
	}

	private int levenshteinDistance(String s1, String s2) {
		int[][] dp = new int[s1.length() + 1][s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else {
					dp[i][j] = Math.min(Math.min(
						dp[i - 1][j] + 1,
						dp[i][j - 1] + 1),
						dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1)
					);
				}
			}
		}
		return dp[s1.length()][s2.length()];
	}
}


