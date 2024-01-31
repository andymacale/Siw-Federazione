package it.uniroma3.siw.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Giocatore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
    public String cognome;

    @Column(nullable = false)
    public LocalDate dataNascita;

    @Column(nullable = false)
    public String luogoNascita;

    public String ruolo;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Giocatore giocatore = (Giocatore) o;
        return Objects.equals(nome, giocatore.nome) && Objects.equals(cognome, giocatore.cognome) && Objects.equals(dataNascita, giocatore.dataNascita) && Objects.equals(luogoNascita, giocatore.luogoNascita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cognome, dataNascita, luogoNascita);
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

}
