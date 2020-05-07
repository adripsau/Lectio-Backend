package es.ulpgc.LectioBackend.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {

    @Size(min = 3)
    @Column(name = "title", nullable = false)
    private String title;

    @Size(min = 3)
    @Column(name = "author", nullable = false)
    private String author;

    @Size(min = 3)
    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Size(min = 3)
    @Column(name = "number_pages", nullable = false)
    private Integer number_pages;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    public Book() {
    }

    public Book(String title, String author, String publisher, Integer number_pages, String isbn, Genre genre) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.number_pages = number_pages;
        this.isbn = isbn;
        this.genre = genre;
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

    public Integer getNumber_pages() {
        return number_pages;
    }

    public void setNumber_pages(Integer number_pages) {
        this.number_pages = number_pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void updateAll(Book book) {
        setTitle(book.getTitle());
        setAuthor(book.getAuthor());
        setPublisher(book.getPublisher());
        setNumber_pages(book.getNumber_pages());
        setIsbn(book.getIsbn());
        setGenre(book.getGenre());
    }
}
