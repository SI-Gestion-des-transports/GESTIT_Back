package fr.diginamic.gestit_back.repository;

import fr.diginamic.gestit_back.entites.ReservationVehiculeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReservationVehiculeServiceRepositoryTest {

    @Autowired
    ReservationVehiculeServiceRepository reservationVehiculeServiceRepository;

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Sql("scheme.sql")
    @Sql("reservationVS.sql")
    void trouverReservationParId_OK(){
        ReservationVehiculeService reservationVSOK = reservationVehiculeServiceRepository.findById(1).orElseThrow();
        assertThat(reservationVSOK.getCollaborateur().getId()).isEqualTo(1);
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Sql("scheme.sql")
    @Sql("reservationVS.sql")
    void trouverReservationParId_False(){
        Optional<ReservationVehiculeService> reservationVSFalse = reservationVehiculeServiceRepository.findById(10);
        assertThat(reservationVSFalse).isEmpty();
    }

}
