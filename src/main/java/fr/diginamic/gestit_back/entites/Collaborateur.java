package fr.diginamic.gestit_back.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
public class Collaborateur extends AbstractUtilisateur {

    @OneToMany(mappedBy = "collaborateur")
    private Set<ReservationVehiculeService> reservationVehiculeServices;

    @ManyToMany
    @JoinTable(name="covoiturages_collaborateur",
        joinColumns = @JoinColumn(name="collaborateur_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "covoiturage_id", referencedColumnName = "id"))
    private Set<Covoiturage> covoiturages;


    public Collaborateur() {

    }
}
