package com.pagewise.strategy;

import com.pagewise.model.Book;
import java.util.List;

public interface SearchStrategy {
    List<Book> search(List<Book> books, String query);
}
