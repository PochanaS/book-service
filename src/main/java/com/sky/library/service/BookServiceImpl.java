package com.sky.library.service;

import com.sky.library.exception.BookNotFoundException;
import com.sky.library.model.Book;
import com.sky.library.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOK_PREFIX = "BOOK-";

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book retrieveBook(String bookReference) throws BookNotFoundException {
        validateBookReference(bookReference);
        Book book = findByBookReference(bookReference);
        return book;
    }

    @Override
    public String getBookSummary(String bookReference) throws BookNotFoundException {
        validateBookReference(bookReference);

        Book book = findByBookReference(bookReference);
        String review = book.getReview();
        String words[] = review.split(" ");

        if(isEmpty(review) || words.length <= 9) {
            return buildBookSummary(book, false);
        }

        return buildBookSummary(book, true);
    }

    private void validateBookReference(String bookReference) throws BookNotFoundException {
        if(isEmpty(bookReference) || !bookReference.startsWith(BOOK_PREFIX)) {
            throw new BookNotFoundException("The book reference must begin with "+BOOK_PREFIX);
        }
    }

    private Book findByBookReference(String bookReference) throws BookNotFoundException {
        return bookRepository.retrieveBook(bookReference).orElseThrow(BookNotFoundException::new);
    }

    private String buildBookSummary(Book book, boolean addEllipsis) {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append(book.getReference()).append("]");
        builder.append(" ").append(book.getTitle()).append(" - ");

        if(addEllipsis) {
            builder.append(String.join(" ",
                    book.getReview().split(" ", 9)));
            builder.append("...");
        } else {
            builder.append(book.getReview());
            builder.append(".");
        }
        return builder.toString();
    }
}
