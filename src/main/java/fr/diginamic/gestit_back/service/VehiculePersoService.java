package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.VehiculePersoDto;
import fr.diginamic.gestit_back.entites.VehiculePerso;
import fr.diginamic.gestit_back.repository.VehiculePersoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class VehiculePersoService {
    private VehiculePersoRepository vehiculePersoRepository;
    public ResponseEntity<?> createVehiculePerso(VehiculePersoDto dto){

        return null;
    }
}
