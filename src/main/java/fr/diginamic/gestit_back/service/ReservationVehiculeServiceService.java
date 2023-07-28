package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.ReservationVehiculeServiceDto;
import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import fr.diginamic.gestit_back.repository.ReservationVehiculeServiceRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class ReservationVehiculeServiceService {

    private ReservationVehiculeServiceRepository reservationVehiculeServiceRepository;

    public List<ReservationVehiculeService> listeReservationVehiculeService(){
        return this.reservationVehiculeServiceRepository.findAll();
    }

    public void creerReservationVehiculeService(ReservationVehiculeServiceDto res){

        /*ReservationVehiculeService reservationVehiculeService = new ReservationVehiculeService(res.userId(), res.vehiculeServiceId(), res.dateHeureDepart(), res.dateHeureRetour());*/
    }
}
