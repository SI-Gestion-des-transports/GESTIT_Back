package fr.diginamic.gestit_back.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
public class ReservationVehiculeService extends AbstractBaseEntity {

    @ManyToOne
    private Utilisateur collaborateur;

    @ManyToOne
    private VehiculeService vehiculeService;
    private LocalDateTime dateHeureDepart;
    private LocalDateTime dateHeureRetour;


    public ReservationVehiculeService() {

    }
}
