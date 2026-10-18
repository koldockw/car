import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Subsystem class 1: keeps all cars and knows which of them are free
public class CarInventory {

    public static class Car {
        private final String brand;
        private final String model;
        private final String type;
        private final double pricePerDay;
        private boolean available = true;

        public Car(String brand, String model, String type, double pricePerDay) {
            this.brand = brand;
            this.model = model;
            this.type = type;
            this.pricePerDay = pricePerDay;
        }

        public String getType() { return type; }
        public double getPricePerDay() { return pricePerDay; }
        public boolean isAvailable() { return available; }
        public void setAvailable(boolean available) { this.available = available; }

        @Override
        public String toString() {
            return brand + " " + model;
        }
    }

    private final List<Car> cars = new ArrayList<>();

    public CarInventory() {
        cars.add(new Car("Toyota", "Corolla", "ECONOMY", 30.0));
        cars.add(new Car("Hyundai", "Accent", "ECONOMY", 28.0));
        cars.add(new Car("Toyota", "Camry", "BUSINESS", 55.0));
        cars.add(new Car("BMW", "X5", "SUV", 90.0));
    }

    public Optional<Car> findAvailableCar(String type) {
        for (Car car : cars) {
            if (car.isAvailable() && car.getType().equalsIgnoreCase(type)) {
                return Optional.of(car);
            }
        }
        return Optional.empty();
    }

    public void markAsRented(Car car) {
        car.setAvailable(false);
    }

    public void markAsAvailable(Car car) {
        car.setAvailable(true);
    }
}
