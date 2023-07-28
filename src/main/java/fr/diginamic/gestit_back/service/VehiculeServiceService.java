package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.entites.VehiculeService;
import fr.diginamic.gestit_back.repository.VehiculeServiceRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class VehiculeServiceService {
    private VehiculeServiceRepository vehiculeServiceRepository;

    public VehiculeService trouverParId(Integer id){
        return this.vehiculeServiceRepository.findById(id).orElseThrow();
    }
}
