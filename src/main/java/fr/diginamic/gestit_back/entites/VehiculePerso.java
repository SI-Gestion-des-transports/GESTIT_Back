package fr.diginamic.gestit_back.entites;

import fr.diginamic.gestit_back.dto.VehiculePersoDto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
public class VehiculePerso extends AbstractVehicule {

    @ManyToOne
    private Utilisateur proprietaire;

    @OneToMany(mappedBy = "vehiculePerso")
    private Set<Covoiturage> covoiturages = new HashSet<>();

    public VehiculePerso() {
    }
    public VehiculePerso(VehiculePersoDto dto){
        this.setImmatriculation(dto.getImmatriculation());

    }
}
