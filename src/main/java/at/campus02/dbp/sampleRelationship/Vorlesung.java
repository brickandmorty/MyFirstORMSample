package at.campus02.dbp.sampleRelationship;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Vorlesung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titel;

    @OneToMany(
            mappedBy = "vorlesung",          // Feldname in Student
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch =  FetchType.EAGER
    )
    private List<Student> studierende = new ArrayList<>();

    // === Helper-Methoden für bidirektionale Beziehung ===
    public void addStudent(Student student) {
        studierende.add(student);
        student.setVorlesung(this);
    }

    public void removeStudent(Student student) {
        studierende.remove(student);
        student.setVorlesung(null);
    }

    // === Getter / Setter ===
    public Long getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public List<Student> getStudierende() {
        return studierende;
    }

    public void setStudierende(List<Student> studierende) {
        this.studierende = studierende;
    }
}