package es.ulpgc.LectioBackend.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "booklist")
public class BookList implements Serializable {


    @EmbeddedId
    private BookListId bookListId;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_state", nullable = false)
    private BookState book_state;

    public BookList() {
    }

    public BookList(BookListId bookListId, BookState book_state) {
        this.bookListId = bookListId;
        this.book_state = book_state;
    }

    public BookListId getBookListId() {
        return bookListId;
    }

    public void setBookListId(BookListId bookListId) {
        this.bookListId = bookListId;
    }

    public BookState getBook_state() {
        return book_state;
    }

    public void setBook_state(BookState book_state) {
        this.book_state = book_state;
    }
}
