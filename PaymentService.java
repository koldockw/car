// Subsystem class 4: takes money from a bank card
public class PaymentService {
    private static final int CARD_NUMBER_LENGTH = 16;

    public boolean charge(String cardNumber, double amount) {
        if (cardNumber == null || cardNumber.length() != CARD_NUMBER_LENGTH || amount <= 0) {
            return false;
        }
        System.out.println("[Payment] Charged " + amount + " $ from card ****"
                + cardNumber.substring(CARD_NUMBER_LENGTH - 4));
        return true;
    }
}
