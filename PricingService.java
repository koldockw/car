// Subsystem class 3: calculates rental price and insurance price
public class PricingService {
    private static final int LONG_RENTAL_DAYS = 7;
    private static final double LONG_RENTAL_DISCOUNT = 0.10;
    private static final double INSURANCE_PER_DAY = 15.0;

    public double calculateRentalPrice(CarInventory.Car car, int days) {
        double price = car.getPricePerDay() * days;
        if (days >= LONG_RENTAL_DAYS) {
            price = price - price * LONG_RENTAL_DISCOUNT;
        }
        return price;
    }

    public double calculateInsurancePrice(int days) {
        return INSURANCE_PER_DAY * days;
    }
}
