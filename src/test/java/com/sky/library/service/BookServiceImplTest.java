package com.sky.library.service;

import com.sky.library.exception.BookNotFoundException;
import com.sky.library.model.Book;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceImplTest {
    private static final String invalidMsg = "The book reference must begin with BOOK-";

    @Autowired
    private BookService bookService;

    @Test
    public void retrieveBookInvalidFormatTest() throws BookNotFoundException {
       BookNotFoundException bookNotFoundException = Assertions.assertThrows(
               BookNotFoundException.class, () -> {
                   bookService.retrieveBook("INVALID-TEXT");
                });
        Assertions.assertEquals(invalidMsg, bookNotFoundException.getMessage());
    }

    @Test(expected = BookNotFoundException.class)
    public void retrieveUnknownBookTest() throws BookNotFoundException {
        bookService.retrieveBook("BOOK-999");
    }

    @Test()
    public void retrieveBookValidTest() throws BookNotFoundException {
        Book book = bookService.retrieveBook("BOOK-GRUFF472");
        Assertions.assertEquals("BOOK-GRUFF472", book.getReference());
        Assertions.assertEquals("The Gruffalo", book.getTitle());
    }


    @Test
    public void retrieveSummaryInvalidFormatTest() throws BookNotFoundException {
        BookNotFoundException bookNotFoundException = Assertions.assertThrows(
                BookNotFoundException.class, () -> {
                    bookService.getBookSummary("INVALID-TEXT");
                });
        Assertions.assertEquals(invalidMsg, bookNotFoundException.getMessage());
    }

    @Test(expected = BookNotFoundException.class)
    public void retrieveSummaryUnknownBookTest() throws BookNotFoundException {
        bookService.getBookSummary("BOOK-999");
    }

    @Test
    public void retriveBookSummaryTest() throws BookNotFoundException {
        String summary = bookService.getBookSummary("BOOK-GRUFF472");
        String expected = "[BOOK-GRUFF472] The Gruffalo - A mouse taking a walk in the woods.";
        Assertions.assertEquals(expected, summary);

        summary = bookService.getBookSummary("BOOK-POOH222");
        expected = "[BOOK-POOH222] Winnie The Pooh - In this first volume, we meet all\n" +
                "the friends and their roles...";
        Assertions.assertEquals(expected, summary);

        summary = bookService.getBookSummary("BOOK-WILL987");
        expected = "[BOOK-WILL987] The Wind In The Willows - With the arrival of spring\n" +
                "and fine weather atmosphere is outside...";
        Assertions.assertEquals(expected, summary);
    }

}
