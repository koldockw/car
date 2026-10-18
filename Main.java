// Client: uses only the Facade
public class Main {

    public static void main(String[] args) {
        CarRentalFacade rental = new CarRentalFacade();

        System.out.println("=== 1. Price quote ===");
        try {
            double price = rental.getPriceQuote("SUV", 4, true);
            System.out.println("SUV for 4 days with insurance: " + price + " $");
        } catch (CarRentalFacade.RentalException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== 2. Successful rental ===");
        tryToRent(rental, new CarRentalFacade.RentalRequest(
                "Aidar", "KZ123456", 22, "ECONOMY", 3, true, "1234567812345678"));

        System.out.println("\n=== 3. Driver is too young ===");
        tryToRent(rental, new CarRentalFacade.RentalRequest(
                "Dana", "KZ654321", 19, "SUV", 2, false, "1111222233334444"));

        System.out.println("\n=== 4. Payment fails (bad card) ===");
        tryToRent(rental, new CarRentalFacade.RentalRequest(
                "Ali", "KZ777777", 30, "BUSINESS", 5, false, "123"));

        System.out.println("\n=== 5. Same car can be rented after the failed payment ===");
        tryToRent(rental, new CarRentalFacade.RentalRequest(
                "Ali", "KZ777777", 30, "BUSINESS", 8, true, "9999888877776666"));

        System.out.println("\n=== 6. No more business cars left ===");
        tryToRent(rental, new CarRentalFacade.RentalRequest(
                "Mira", "KZ999999", 25, "BUSINESS", 1, false, "5555666677778888"));
    }

    private static void tryToRent(CarRentalFacade rental, CarRentalFacade.RentalRequest request) {
        try {
            System.out.println(rental.rentCar(request));
        } catch (CarRentalFacade.RentalException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
