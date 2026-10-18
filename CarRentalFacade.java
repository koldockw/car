// Facade: the client calls rentCar() or getPriceQuote() and does not need to know
// which subsystem classes are used and in which order.
public class CarRentalFacade {

    // Groups the data of one rental, so rentCar() does not need 7 parameters
    public static class RentalRequest {
        private final String customerName;
        private final String licenseNumber;
        private final int driverAge;
        private final String carType;
        private final int days;
        private final boolean withInsurance;
        private final String cardNumber;

        public RentalRequest(String customerName, String licenseNumber, int driverAge,
                             String carType, int days, boolean withInsurance, String cardNumber) {
            this.customerName = customerName;
            this.licenseNumber = licenseNumber;
            this.driverAge = driverAge;
            this.carType = carType;
            this.days = days;
            this.withInsurance = withInsurance;
            this.cardNumber = cardNumber;
        }
    }

    // The only exception the client has to handle
    public static class RentalException extends Exception {
        public RentalException(String message) {
            super(message);
        }
    }

    private final CarInventory inventory = new CarInventory();
    private final DriverLicenseChecker licenseChecker = new DriverLicenseChecker();
    private final PricingService pricingService = new PricingService();
    private final PaymentService paymentService = new PaymentService();

    public String rentCar(RentalRequest request) throws RentalException {
        checkDriver(request);
        CarInventory.Car car = findCar(request.carType);
        double totalPrice = calculateTotalPrice(car, request.days, request.withInsurance);

        // reserve the car first, so nobody else can take it while we are paying
        inventory.markAsRented(car);

        if (!paymentService.charge(request.cardNumber, totalPrice)) {
            inventory.markAsAvailable(car); // rollback
            throw new RentalException("Payment failed. Please check your card.");
        }

        return request.customerName + " rented " + car + " for " + request.days
                + " days. Total: " + totalPrice + " $";
    }

    public double getPriceQuote(String carType, int days, boolean withInsurance) throws RentalException {
        CarInventory.Car car = findCar(carType);
        return calculateTotalPrice(car, days, withInsurance);
    }

    private void checkDriver(RentalRequest request) throws RentalException {
        if (!licenseChecker.isValid(request.licenseNumber, request.driverAge)) {
            throw new RentalException("Driver license is not valid or driver is too young.");
        }
    }

    private CarInventory.Car findCar(String carType) throws RentalException {
        return inventory.findAvailableCar(carType)
                .orElseThrow(() -> new RentalException("No available cars of type " + carType));
    }

    private double calculateTotalPrice(CarInventory.Car car, int days, boolean withInsurance) {
        double total = pricingService.calculateRentalPrice(car, days);
        if (withInsurance) {
            total += pricingService.calculateInsurancePrice(days);
        }
        return total;
    }
}
