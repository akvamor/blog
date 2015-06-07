package ua.org.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.org.project.domain.impl.Entry;

import javax.persistence.*;

/**
 * Created by Dmitry Petrov on 28.06.14.
 */
@Entity
@Table(name = "ENTRY_IMPRESSION")
public class Impression {

    private Long id;
    private Entry entry;
    private Long quantity = 0l;
    private int version;


    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTRY_ID")
    public Entry getEntry() {
        return entry;
    }
    public void setEntry(Entry entry) {
        this.entry = entry;
    }


    @Column(name = "QUANTITY")
    public Long getQuantity() {
        return quantity;
    }
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }


    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
}
