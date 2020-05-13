package es.ulpgc.LectioBackend.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookListId implements Serializable {

    @Column(name = "list_id")
    private long list_id;

    @Column(name = "book_id")
    private long book_id;

    public BookListId() {
    }

    public BookListId(long list_id, long book_id) {
        this.list_id = list_id;
        this.book_id = book_id;
    }

    public long getList_id() {
        return list_id;
    }

    public long getBook_id() {
        return book_id;
    }

    public void setList_id(long list_id) {
        this.list_id = list_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookListId that = (BookListId) o;
        return list_id == that.list_id &&
                book_id == that.book_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(list_id, book_id);
    }
}
