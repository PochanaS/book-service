package com.sky.library.repo;

import com.sky.library.model.Book;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BookRepositoryStub implements BookRepository {

    private static final Map<String, Book> BOOKS_MAP = new HashMap<>();

    public BookRepositoryStub() {
        if(BOOKS_MAP.size() == 0) {
            loadBooks().forEach(book -> BOOKS_MAP.put(book.getReference(), book));
        }
    }

    @Override
    public Optional<Book> retrieveBook(String reference) {
        return Optional.ofNullable(BOOKS_MAP.get(reference));
    }


    private List<Book> loadBooks() {
        List<Book> booksList = new ArrayList<>();
        booksList.add(
                new Book("BOOK-GRUFF472", "The Gruffalo",
                        "A mouse taking a walk in the woods")
        );

        booksList.add(
                new Book("BOOK-POOH222", "Winnie The Pooh",
                        "In this first volume, we meet all\n" +
                                "the friends and their roles")
        );

        booksList.add(
                new Book("BOOK-WILL987", "The Wind In The Willows",
                        "With the arrival of spring\n" +
                                "and fine weather atmosphere is outside")
        );

        return booksList;
    }

    public static void main(String[] args) {
        String summary = "With the arrival of spring and fine weather atmosphere is outside";
        System.out.println(Arrays.asList(summary.split(" ")).toString());
        String[] review = Arrays.copyOf(summary.split(" "), 9);
        System.out.println(String.join(" ", review));

        String summary2 = "In this first volume, we meet all the friends and their roles";
        //System.out.println(Arrays.asList(summary2.split(" ")).toString());
    }
}
