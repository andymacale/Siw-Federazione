package it.uniroma3.siw.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nome", "presidente_id"}))
public class Squadra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String nome;

    public String annoFondazione;

    public String sede;

    @OneToOne
    public Presidente presidente;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Squadra other = (Squadra) o;
        return Objects.equals(nome, other.nome) && Objects.equals(presidente, other.presidente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, presidente);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAnnoFondazione() {
        return annoFondazione;
    }

    public void setAnnoFondazione(String annoFondazione) {
        this.annoFondazione = annoFondazione;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public Presidente getPresidente() {
        return presidente;
    }

    public void setPresidente(Presidente presidente) {
        this.presidente = presidente;
    }
}
