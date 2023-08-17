package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.VehiculeServiceDto;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.entites.VehiculeService;
import fr.diginamic.gestit_back.repository.ModeleRepository;
import fr.diginamic.gestit_back.repository.VehiculeServiceRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class VehiculeServiceService {
    private VehiculeServiceRepository vehiculeServiceRepository;
    private ModeleRepository modeleRepository;

    public VehiculeService trouverParId(Integer id){
        return this.vehiculeServiceRepository.findById(id).orElseThrow();
    }
    public List<VehiculeServiceDto> listVehiculeService(Integer start,Integer size){
        Pageable pages = PageRequest.of(start,size);
        Page<VehiculeService> all = vehiculeServiceRepository.findAll(pages);
        return all.getContent().stream().map(VehiculeServiceDto::new).toList();
    }
    public void createVehiculeService(VehiculeServiceDto dto){
        //if (vehiculeServiceRepository.findFirstByImmatriculation(dto.getImmatriculation()) != null) throw new RuntimeException("Immatriculation existait !");
        Modele modele = modeleRepository.findModeleByNom(dto.getModele());
        vehiculeServiceRepository.save(new VehiculeService(dto,modele));
    }
    public void deleteVehiculeService(Integer id){
        // = changer le statut en hors service ?
        vehiculeServiceRepository.deleteById(id);
    }
    public void modifyVehiclueService(VehiculeServiceDto dto){
        Modele modele = modeleRepository.findModeleByNom(dto.getModele());
        vehiculeServiceRepository.save(new VehiculeService(dto,modele));
    }

}
