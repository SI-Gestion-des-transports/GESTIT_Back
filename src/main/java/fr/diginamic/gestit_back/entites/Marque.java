package fr.diginamic.gestit_back.entites;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
public class Marque extends AbstractBaseEntity {

    private String nom;

    public Marque() {

    }
}
