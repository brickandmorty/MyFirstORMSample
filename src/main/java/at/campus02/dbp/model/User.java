package at.campus02.dbp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user") // Tabellenname in der DB
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String alias;

    public User() {
        // Hibernate braucht einen No-Args-Konstruktor
    }

    public User(String firstname, String alias) {
        this.firstname = firstname;
        this.alias = alias;
    }

    // Getter und Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    @Override
    public String toString() {
        return "User{id=" + id + ", firstname='" + firstname + "', alias='" + alias + "'}";
    }
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteBook> favoriteBooks = new ArrayList<>();

}