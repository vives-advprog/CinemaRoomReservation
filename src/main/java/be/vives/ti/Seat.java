package be.vives.ti;

import java.util.Objects;

public class Seat implements Comparable<Seat> {

    private final int row;
    private final int seatNumber;


    public Seat(int row, int seatNumber) {
        if (row <= 0) {
            throw new IllegalArgumentException("row must be greater than zero");
        }
        if (seatNumber <= 0) {
            throw new IllegalArgumentException("seatNumber must be greater than zero");
        }

        this.row = row;
        this.seatNumber = seatNumber;
    }

    public int getRow() {
        return row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    @Override
    public String toString() {
        return "Seat at row " + row + ", number " + seatNumber;
    }

    @Override
    public int compareTo(Seat other) {
        if (this.row != other.row) {
            return Integer.compare(this.row, other.row);
        } else {
            return Integer.compare(this.seatNumber, other.seatNumber);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return row == seat.row && seatNumber == seat.seatNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, seatNumber);
    }
}
