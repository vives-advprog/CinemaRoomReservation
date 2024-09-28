package be.vives.ti;

public class NotEnoughConsecutiveSeatsInRowException extends RuntimeException {

    public NotEnoughConsecutiveSeatsInRowException() {
        super("Not enough consecutive seats available in any row");
    }

    public NotEnoughConsecutiveSeatsInRowException(int startSeatNumber) {
        super("Not enough consecutive seats available starting from seat " + startSeatNumber);
    }
}
