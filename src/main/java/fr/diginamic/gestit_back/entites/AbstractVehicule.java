package fr.diginamic.gestit_back.entites;

import jakarta.persistence.*;
import lombok.Data;


@Data

@MappedSuperclass
public abstract class AbstractVehicule extends AbstractBaseEntity{
    private String immatriculation;

    //test
    private Integer nombreDePlaceDisponibles;

    @ManyToOne
    private Modele modele;
}
