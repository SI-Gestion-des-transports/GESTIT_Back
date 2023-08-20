package fr.diginamic.gestit_back.entites;

import fr.diginamic.gestit_back.enumerations.CarType;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Objects;

public class Car {

    private String make;
    private int numberOfSeats;
    private CarType type;

    public Car() {
    }

    public Car(String make, int numberOfSeats, CarType type) {
        this.make = make;
        this.numberOfSeats = numberOfSeats;
        this.type = type;
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public CarType getType() {
        return this.type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public Car make(String make) {
        setMake(make);
        return this;
    }

    public Car numberOfSeats(int numberOfSeats) {
        setNumberOfSeats(numberOfSeats);
        return this;
    }

    public Car type(CarType type) {
        setType(type);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Car)) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(make, car.make) && numberOfSeats == car.numberOfSeats && Objects.equals(type, car.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, numberOfSeats, type);
    }

    @Override
    public String toString() {
        return "{" +
                " make='" + getMake() + "'" +
                ", numberOfSeats='" + getNumberOfSeats() + "'" +
                ", type='" + getType() + "'" +
                "}";
    }

}
