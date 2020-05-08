package es.ulpgc.LectioBackend.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3)
    @Column(name = "title", nullable = false)
    private String title;

    @Size(min = 3)
    @Column(name = "author", nullable = false)
    private String author;

    @Size(min = 3)
    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Size(min = 1)
    @Column(name = "pages", nullable = false)
    private String pages;

    @Column(name = "isbn", unique=true, nullable = false)
    private String isbn;

    @Column(name = "genres", nullable = false)
    private String genres;

    @Column(name = "synopsis")
    private String synopsis;

    public Book() {
    }

    public Book(String title, String author, String publisher, String pages, String isbn, String[] genres, String synopsis) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.pages = pages;
        this.isbn = isbn;
        this.genres = parseToString(genres);
        this.synopsis = synopsis;
    }

    private String parseToString(String[] list) {
        return String.join(",", list);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String[] getGenres() {
        return genres.split(",");
    }

    public void setGenres(String[] genres) {
        this.genres = parseToString(genres);
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void updateAll(Book book) {
        setTitle(book.getTitle());
        setAuthor(book.getAuthor());
        setPublisher(book.getPublisher());
        setPages(book.getPages());
        setIsbn(book.getIsbn());
        setGenres(book.getGenres());
        setSynopsis(book.getSynopsis());
    }
}
