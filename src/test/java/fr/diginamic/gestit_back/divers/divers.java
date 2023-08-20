package fr.diginamic.gestit_back.divers;

import org.junit.jupiter.api.Test;

import fr.diginamic.gestit_back.dto.CarDto;
import fr.diginamic.gestit_back.entites.Car;
import fr.diginamic.gestit_back.enumerations.CarType;
import fr.diginamic.gestit_back.mapper.CarMapper;
import static org.assertj.core.api.Assertions.assertThat;

public class divers {

    @Test
    public void shouldMapCarToDto() {
        // given
        Car car = new Car("Morris", 5, CarType.MONOSPACE);

        // when
        CarDto carDto = CarMapper.INSTANCE.carToCarDto(car);

        // then
        assertThat(carDto).isNotNull();
        assertThat(carDto.getMake()).isEqualTo("Morris");
        assertThat(carDto.getSeatCount()).isEqualTo(5);
        assertThat(carDto.getType()).isEqualTo("MONOSPACE");
    }

}
