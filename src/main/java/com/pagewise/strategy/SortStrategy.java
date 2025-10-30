package com.pagewise.strategy;

import com.pagewise.model.Book;
import java.util.List;

public interface SortStrategy {
    List<Book> sort(List<Book> books);
}
