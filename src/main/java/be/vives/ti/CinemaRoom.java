package be.vives.ti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CinemaRoom {

    private final String movieName;
    private final List<Seat> availableSeats;
    private final List<Seat> reservedSeats;

    public CinemaRoom(String movieName, int numberOfRows, int seatsPerRow) {
        if (numberOfRows < 1 || seatsPerRow < 1) {
            throw new IllegalArgumentException("Number of rows and seats per row must be greater than 0");
        }

        this.movieName = movieName;
        this.availableSeats = new ArrayList<>();
        this.reservedSeats = new ArrayList<>();
        for (int row = 1; row <= numberOfRows; row++) {
            for (int seatNumber = 1; seatNumber <= seatsPerRow; seatNumber++) {
                availableSeats.add(new Seat(row, seatNumber));
            }
        }
    }

    public String getMovieName() {
        return movieName;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public List<Seat> getReservedSeats() {
        return reservedSeats;
    }

    public List<Seat> reserveConsecutiveSeats(int numberOfSeats) {
        if (numberOfSeats <= 0) {
            throw new IllegalArgumentException("Number of seats must be greater than zero");
        }

        List<Seat> reservedConsecutiveSeats = new ArrayList<>();
        int row = 1;
        while (reservedConsecutiveSeats.isEmpty() && row <= getMaxRow()) {
            reservedConsecutiveSeats = reserveConsecutiveSeatsInRow(numberOfSeats, row);
            row++;
        }
        if (reservedConsecutiveSeats.isEmpty()) {
            throw new NotEnoughConsecutiveSeatsInRowException();
        }
        return reservedConsecutiveSeats;
    }

    private List<Seat> reserveConsecutiveSeatsInRow(int numberOfSeats, int row) {
        List<Seat> availableSeatsInRow = getAvailableSeatsInRow(row);
        List<Seat> consecutiveSeats = new ArrayList<>();
        if (availableSeatsInRow.size() >= numberOfSeats) {
            consecutiveSeats = findConsecutiveSeats(availableSeatsInRow, numberOfSeats);
            if (!consecutiveSeats.isEmpty()) {
                reserveSeats(consecutiveSeats);
            }
        }
        return consecutiveSeats;
    }

    private List<Seat> findConsecutiveSeats(List<Seat> availableSeatsInRow, int numberOfSeats) {
        List<Seat> consecutiveSeats = new ArrayList<>();
        Collections.sort(availableSeatsInRow);

        int firstSeatInConsecutiveRow = 0;
        while (consecutiveSeats.isEmpty() && firstSeatInConsecutiveRow <= availableSeatsInRow.size() - numberOfSeats) {
            boolean isConsecutive = true;
            int numberOfConsecutiveSeatsFound = 0;
            while (isConsecutive && numberOfConsecutiveSeatsFound < numberOfSeats - 1) {
                if (availableSeatsInRow.get(firstSeatInConsecutiveRow + numberOfConsecutiveSeatsFound).getSeatNumber() + 1 != availableSeatsInRow.get(firstSeatInConsecutiveRow + numberOfConsecutiveSeatsFound + 1).getSeatNumber()) {
                    isConsecutive = false;
                }
                numberOfConsecutiveSeatsFound++;
            }
            if (isConsecutive) {
                consecutiveSeats.addAll(availableSeatsInRow.subList(firstSeatInConsecutiveRow, firstSeatInConsecutiveRow + numberOfSeats));
            }
            firstSeatInConsecutiveRow++;
        }
        return consecutiveSeats;
    }

    public List<Seat> reserveSeatByNumber(int rowNumber, int startSeatNumber, int numberOfSeats) {
        if (rowNumber <= 0) {
            throw new IllegalArgumentException("Rownumber must be greater than zero");
        }
        if (numberOfSeats <= 0) {
            throw new IllegalArgumentException("Number of seats must be greater than zero");
        }
        if (startSeatNumber <= 0) {
            throw new IllegalArgumentException("Start seat number must be greater than zero");
        }

        List<Seat> availableSeatsInRow = getAvailableSeatsInRow(rowNumber);
        List<Seat> consecutiveSeats = new ArrayList<>();
        for (int i = 0; i < numberOfSeats; i++) {
            int seatToCheck = startSeatNumber + i;
            Seat seat = new Seat(rowNumber, seatToCheck);
            if (availableSeatsInRow.contains(seat)) {
                consecutiveSeats.add(seat);
            } else {
                throw new NotEnoughConsecutiveSeatsInRowException(startSeatNumber);
            }
        }

        reserveSeats(consecutiveSeats);
        return consecutiveSeats;
    }

    private int getMaxRow() {
        int maxRow = 0;
        for (Seat seat : availableSeats) {
            if (seat.getRow() > maxRow) {
                maxRow = seat.getRow();
            }
        }
        return maxRow;
    }

    private List<Seat> getAvailableSeatsInRow(int row) {
        List<Seat> availableSeatsInRow = new ArrayList<>();
        for (Seat availableSeat : availableSeats) {
            if (availableSeat.getRow() == row) {
                availableSeatsInRow.add(availableSeat);
            }
        }
        return availableSeatsInRow;
    }

    private void reserveSeats(List<Seat> seatsToReserve) {
        reservedSeats.addAll(seatsToReserve);
        availableSeats.removeAll(seatsToReserve);
    }

}
