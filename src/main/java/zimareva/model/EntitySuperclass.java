package zimareva.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@MappedSuperclass
public abstract class EntitySuperclass {
    @Id
    @SequenceGenerator(name = "id_seq", sequenceName = "id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    private Long id;
    //todo: почему-то не проставляются эти поля автоматически в бд :(
    private LocalDateTime created = LocalDateTime.of(LocalDate.now(), LocalTime.now());;
    private LocalDateTime modified = LocalDateTime.of(LocalDate.now(), LocalTime.now());;

    public EntitySuperclass() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    @PreUpdate
    public void setLastUpdate() {
        this.modified = LocalDateTime.of(LocalDate.now(), LocalTime.now());
    }

    @Override
    public String toString() {
        return "EntitySuperclass{" +
                "id=" + id +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
