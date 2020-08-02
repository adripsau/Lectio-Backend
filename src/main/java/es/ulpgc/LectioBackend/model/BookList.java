package es.ulpgc.LectioBackend.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "booklists")
public class BookList implements Serializable {

    @EmbeddedId
    private BookListId bookListId;

    @Column(name = "progress")
    private long progress;

    public BookList() {
    }

    public BookList(BookListId bookListId, String progress) {
        this.bookListId = bookListId;
        this.progress = Long.parseLong(progress);
    }

    public BookListId getBookListId() {
        return bookListId;
    }

    public void setBookListId(BookListId bookListId) {
        this.bookListId = bookListId;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = Long.parseLong(progress);
    }
}
