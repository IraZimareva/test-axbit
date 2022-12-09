package zimareva.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "genre")
@SQLDelete(sql = "UPDATE genre SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class Genre extends EntitySuperclass{
    private String name;
    @OneToMany(mappedBy="genre")
    @JsonIgnoreProperties("genre")
    private List<Book> books;

    public Genre() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void deleteBook(Book book){
        books.remove(book);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}
