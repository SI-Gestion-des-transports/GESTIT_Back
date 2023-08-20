package fr.diginamic.gestit_back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Objects;

@AllArgsConstructor
@Data
public class CarDto {

    private String make;
    private int seatCount;
    private String type;

    public CarDto() {
    }

}
