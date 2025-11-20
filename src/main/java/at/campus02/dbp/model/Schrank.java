package at.campus02.dbp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Schrank")
public class Schrank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "Standort", nullable = false, length = 50)
    private Standort standort;

    @Column(name = "MaxAnzahlAnKleidungsstuecken", nullable = false)
    private int maxAnzahlAnKleidungsstuecken;

    // Standard-Konstruktor (wichtig für JPA)
    public Schrank() {
    }

    public Schrank(Standort standort, int maxAnzahlAnKleidungsstuecken) {
        this.standort = standort;
        this.maxAnzahlAnKleidungsstuecken = maxAnzahlAnKleidungsstuecken;
    }

    // Getter/Setter

    public Long getId() {
        return id;
    }

    public Standort getStandort() {
        return standort;
    }

    public void setStandort(Standort standort) {
        this.standort = standort;
    }

    public int getMaxAnzahlAnKleidungsstuecken() {
        return maxAnzahlAnKleidungsstuecken;
    }

    public void setMaxAnzahlAnKleidungsstuecken(int maxAnzahlAnKleidungsstuecken) {
        this.maxAnzahlAnKleidungsstuecken = maxAnzahlAnKleidungsstuecken;
    }

    @Override
    public String toString() {
        return "Schrank{" +
                "id=" + id +
                ", standort=" + standort +
                ", maxAnzahlAnKleidungsstuecken=" + maxAnzahlAnKleidungsstuecken +
                '}';
    }

    // Optional: equals/hashCode, falls du Collections oder Caching nutzt
}