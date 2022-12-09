package zimareva.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import zimareva.exception.AuthorWithBooksExistsException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Entity
@Table(name = "author")
@SQLDelete(sql = "UPDATE author SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class Author extends EntitySuperclass {
    private static Logger logger = Logger.getLogger(EntitySuperclass.class.getName());

    private String lastname;
    private String firstname;
    private String middlename;
    private LocalDate dateOfBirth;
    @OneToMany(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "author_id", nullable = false)
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    @PreRemove
    public void preRemove() {
        logger.info("Pre remove. Attempt to remove author");
        if (this.getBooks().isEmpty() || this.getBooks() == null) {
            logger.info("Remove author without books");
        } else {
            logger.info("Author contains books! Dont remove!");
            throw new AuthorWithBooksExistsException(
                    this.getLastname() + this.getFirstname(),
                    this.getId(),
                    this.getBooks().size());
        }
    }

    @Override
    public String toString() {
        return "Author{" +
                "lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", books=" + books +
                '}';
    }
}
