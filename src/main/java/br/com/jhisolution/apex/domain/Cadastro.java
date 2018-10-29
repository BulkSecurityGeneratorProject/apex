package br.com.jhisolution.apex.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Cadastro.
 */
@Entity
@Table(name = "cadastro")
public class Cadastro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 250)
    @Column(name = "nome", length = 250, nullable = false)
    private String nome;

    @NotNull
    @Size(min = 3, max = 150)
    @Column(name = "email", length = 150, nullable = false)
    private String email;

    
    @Lob
    @Column(name = "gif", nullable = false)
    private byte[] gif;

    @Column(name = "gif_content_type", nullable = false)
    private String gifContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Cadastro nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public Cadastro email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getGif() {
        return gif;
    }

    public Cadastro gif(byte[] gif) {
        this.gif = gif;
        return this;
    }

    public void setGif(byte[] gif) {
        this.gif = gif;
    }

    public String getGifContentType() {
        return gifContentType;
    }

    public Cadastro gifContentType(String gifContentType) {
        this.gifContentType = gifContentType;
        return this;
    }

    public void setGifContentType(String gifContentType) {
        this.gifContentType = gifContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cadastro cadastro = (Cadastro) o;
        if (cadastro.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cadastro.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cadastro{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", gif='" + getGif() + "'" +
            ", gifContentType='" + getGifContentType() + "'" +
            "}";
    }
}
