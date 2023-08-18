package fr.diginamic.gestit_back.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
public class Adresse extends AbstractBaseEntity {

    private Integer numero;

    private String voie;

    @ManyToOne
    private Commune commune;

    public Adresse() {
    }

    public Adresse(Integer numero, String voie) {
        this.numero = numero;
        this.voie = voie;
    }
}
