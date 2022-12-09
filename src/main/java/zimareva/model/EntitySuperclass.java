package zimareva.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Logger;

@MappedSuperclass
public abstract class EntitySuperclass {
    private static Logger logger = Logger.getLogger(EntitySuperclass.class.getName());

    @Id
/*    @SequenceGenerator(name = "id_seq", sequenceName = "id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(insertable = false, updatable = false)
    private LocalDateTime created;
    @Column(insertable = false)
    private LocalDateTime modified;
    @Column(insertable = false)
    private boolean isDeleted;

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

    @PrePersist
    public void prePersist() {
        logger.info("Pre persist. Attempt to add new entity");
        this.created = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        this.modified = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        this.isDeleted = false;
    }

    @PreUpdate
    public void setLastUpdate() {
        logger.info("Pre update. Attempt to update entity");
        this.modified = LocalDateTime.of(LocalDate.now(), LocalTime.now());
    }
}
