package com.my.TimeWork.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "PRACOWNICY")
public class Pracownik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    // Konstruktor bezparametrowy (dla Springa)
    public Pracownik() {}

    // Konstruktor z parametrami
    public Pracownik(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Gettery i settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Pracownik)) {
            return false;
        }

        Pracownik other = (Pracownik) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

