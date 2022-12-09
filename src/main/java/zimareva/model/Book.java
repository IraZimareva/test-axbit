package zimareva.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import zimareva.model.dto.BookDTO;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "book")
@SQLDelete(sql = "UPDATE book SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class Book extends EntitySuperclass{
    private String isbn;
    @ManyToOne
    @JoinColumn(name="genre_id", nullable=false)
    @JsonIgnoreProperties("books")
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

    public static Book from(BookDTO bookDto, Genre genre){
        Book book = new Book();
        book.setIsbn(bookDto.getIsbn());
        book.setGenre(genre);
        return book;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", genre=" + genre +
                '}';
    }
}
