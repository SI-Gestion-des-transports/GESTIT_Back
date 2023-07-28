package fr.diginamic.gestit_back.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
public class Marque extends AbstractBaseEntity {

    private String nom;

    @OneToMany(mappedBy = "marque")
    private Set<Modele> modeles;

    public Marque() {

    }
}
