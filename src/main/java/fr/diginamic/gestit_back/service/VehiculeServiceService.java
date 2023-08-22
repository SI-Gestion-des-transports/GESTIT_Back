package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.VehiculeServiceDto;
import fr.diginamic.gestit_back.entites.Modele;
import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.entites.VehiculeService;
import fr.diginamic.gestit_back.enumerations.Statut;
import fr.diginamic.gestit_back.repository.ModeleRepository;
import fr.diginamic.gestit_back.repository.VehiculeServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Transactional
public class VehiculeServiceService {
    private VehiculeServiceRepository vehiculeServiceRepository;
    private ModeleRepository modeleRepository;
    //private ReservationVehiculeServiceService reservationVehiculeServiceService;

    public VehiculeService trouverParId(Integer id) {
        return this.vehiculeServiceRepository.findById(id).orElseThrow();
    }

    public List<VehiculeServiceDto> listVehiculeService(Integer start, Integer size) {
        Pageable pages = PageRequest.of(start, size);
        Page<VehiculeService> all = vehiculeServiceRepository.findAll(pages);
        return all.getContent().stream().map(VehiculeServiceDto::new).toList();
    }

    public List<VehiculeService> listVehiculeServiceEnService() {
        return vehiculeServiceRepository.findVehiculeServiceByStatut(Statut.EN_SERVICE);
    }

    /*public List<VehiculeService> listVehiculeServiceDispo(LocalDateTime dateHeureDepart, LocalDateTime dateHeureRetour) {
        List<VehiculeService> all = listVehiculeServiceEnService();
        List<VehiculeService> listNonDispo = reservationVehiculeServiceService.listReservationVehiculeServiceByDateDepartEtRetour(dateHeureDepart, dateHeureRetour)
                .stream().map(ReservationVehiculeService::getVehiculeService).collect(Collectors.toList());
        all.removeAll(listNonDispo);
        return all;

    }*/

    public void createVehiculeService(VehiculeServiceDto dto) {
        //if (vehiculeServiceRepository.findFirstByImmatriculation(dto.getImmatriculation()) != null) throw new RuntimeException("Immatriculation existait !");
        Modele modele = modeleRepository.findModeleByNom(dto.getModele());
        vehiculeServiceRepository.save(new VehiculeService(dto, modele));
    }

    public void deleteVehiculeService(Integer id) {
        // = changer le statut en hors service ?
        Optional<VehiculeService> vehiculeService = vehiculeServiceRepository.findById(id);
        vehiculeService.ifPresent(vs -> vs.setStatut(Statut.HORS_SERVICE));
    }

    public void modifyVehiclueService(VehiculeServiceDto dto) {
        Modele modele = modeleRepository.findModeleByNom(dto.getModele());
        vehiculeServiceRepository.save(new VehiculeService(dto, modele));
    }

}
