package es.ulpgc.LectioBackend.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "booklist")
public class BookList implements Serializable {

    @EmbeddedId
    private BookListId bookListId;

    public BookList() {
    }

    public BookList(BookListId bookListId) {
        this.bookListId = bookListId;
    }

    public BookListId getBookListId() {
        return bookListId;
    }

    public void setBookListId(BookListId bookListId) {
        this.bookListId = bookListId;
    }
}
