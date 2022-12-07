package zimareva.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@MappedSuperclass
public abstract class EntitySuperclass {
    @Id
/*    @SequenceGenerator(name = "id_seq", sequenceName = "id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created = LocalDateTime.of(LocalDate.now(), LocalTime.now());
    private LocalDateTime modified = LocalDateTime.of(LocalDate.now(), LocalTime.now());
    private boolean isDeleted = false;

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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @PreUpdate
    public void setLastUpdate() {
        this.modified = LocalDateTime.of(LocalDate.now(), LocalTime.now());
    }
}
