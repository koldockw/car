// Subsystem class 2: checks that the customer is allowed to drive
public class DriverLicenseChecker {
    private static final int MIN_AGE = 21;
    private static final int LICENSE_LENGTH = 8;

    public boolean isValid(String licenseNumber, int driverAge) {
        if (licenseNumber == null || licenseNumber.length() != LICENSE_LENGTH) {
            return false;
        }
        return driverAge >= MIN_AGE;
    }
}
