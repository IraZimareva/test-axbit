package zimareva.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book extends EntitySuperclass{

    //todo: уникальный идентификатор. Может, сделать вместо id? но лучше не надо, запутаюсь
    //todo: сделать уникальным
    private String isbn;
    @ManyToOne
    @JoinColumn(name="genre_id", nullable=false)
    private Genre genre;

    public Book() {
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

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", genre=" + genre +
                '}';
    }
}
