package es.ulpgc.LectioBackend.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookListId implements Serializable {

    @Column(name = "list_id")
    private int list_id;

    @Column(name = "book_id")
    private int book_id;

    public BookListId() {
    }

    public BookListId(int list_id, int book_id) {
        this.list_id = list_id;
        this.book_id = book_id;
    }

    public int getList_id() {
        return list_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public void setBook_id(int book_id) {
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
