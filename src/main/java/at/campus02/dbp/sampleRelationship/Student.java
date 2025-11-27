package at.campus02.dbp.sampleRelationship;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vorname;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "vorlesung_id")   // Fremdschlüsselspalte in STUDENT-Tabelle
    private Vorlesung vorlesung;

    // === Getter / Setter ===
    public Long getId() {
        return id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public Vorlesung getVorlesung() {
        return vorlesung;
    }

    public void setVorlesung(Vorlesung vorlesung) {
        this.vorlesung = vorlesung;
    }
}
