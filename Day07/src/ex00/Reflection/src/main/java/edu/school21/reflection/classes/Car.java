package edu.school21.reflection.classes;

import java.util.StringJoiner;

public class Car {
    private String brand;
    private String model;
    private Double price;

    public Car() {
        this.brand = "Default brand";
        this.model = "Default model";
        this.price = 0D;
    }

    public Car(String make, String model, Double price) {
        this.brand = make;
        this.model = model;
        this.price = price;
    }

    public void replaceCar(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public Double increasePrice(Double increased) {
        return price += increased;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[",
                "]")
                .add("brand='" + brand + "'")
                .add("model='" + model + "'")
                .add("price=" + price)
                .toString();
    }
}
