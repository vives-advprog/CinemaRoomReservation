package be.vives.ti;

import java.util.*;

public class CinemaRoom {

    private final String movieName;
    private final List<Seat> availableSeats;
    private final List<Seat> reservedSeats;

    public CinemaRoom(String movieName, int numberOfRows, int seatsPerRow) {
        if(numberOfRows < 1 || seatsPerRow < 1) {
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

        List<Seat> consecutiveSeats = new ArrayList<>();
        int row = 1;
        while (consecutiveSeats.isEmpty() && row <= getMaxRow()){
            List<Seat> availableSeatsInRow = getAvailableSeatsInRow(row);
            if (availableSeatsInRow.size() >= numberOfSeats) {
                consecutiveSeats = findConsecutiveSeats(availableSeatsInRow, numberOfSeats);
                if (!consecutiveSeats.isEmpty()) {
                    reserveSeats(consecutiveSeats);
                }
            }
            row++;
        }
        if(consecutiveSeats.isEmpty()) {
            throw new NotEnoughConsecutiveSeatsInRowException();
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

    private List<Seat> findConsecutiveSeats(List<Seat> availableSeatsInRow, int numberOfSeats) {
        List<Seat> consecutiveSeats = new ArrayList<>();
        Collections.sort(availableSeatsInRow);

        int i = 0;
        while (consecutiveSeats.isEmpty() && i <= availableSeatsInRow.size() - numberOfSeats) {
            boolean isConsecutive = true;
            int j = 0;
            while (isConsecutive &&  j < numberOfSeats - 1) {
                if (availableSeatsInRow.get(i + j).getSeatNumber() + 1 != availableSeatsInRow.get(i + j + 1).getSeatNumber()) {
                    isConsecutive = false;
                }
                j++;
            }
            if (isConsecutive) {
                consecutiveSeats.addAll(availableSeatsInRow.subList(i, i + numberOfSeats));
            }
            i++;
        }
        return consecutiveSeats;
    }

    private void reserveSeats(List<Seat> seatsToReserve) {
        reservedSeats.addAll(seatsToReserve);
        availableSeats.removeAll(seatsToReserve);
    }


}
