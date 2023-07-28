package fr.diginamic.gestit_back.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class ReservationVehiculeService extends AbstractBaseEntity {

    @ManyToOne
    private Utilisateur collaborateur;

    @ManyToOne
    private VehiculeService vehiculeService;

    private LocalDateTime dateHeureRetour;
    private LocalDateTime dateHeureDepart;

    public ReservationVehiculeService() {

    }
}
