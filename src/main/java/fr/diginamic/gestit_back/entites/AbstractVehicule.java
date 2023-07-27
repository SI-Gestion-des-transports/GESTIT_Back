package fr.diginamic.gestit_back.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractVehicule extends AbstractBaseEntity {
    private String immatriculation;

    private Integer nombreDePlaceDisponibles;

    @ManyToOne
    private Marque marque;

    @ManyToOne
    private Modele modele;

}
