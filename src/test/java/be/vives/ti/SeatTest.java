package be.vives.ti;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SeatTest {

    @Test
    void canCreateSeat() {
        Seat seat = new Seat(1, 5);
        assertThat(seat.getRow()).isEqualTo(1);
        assertThat(seat.getSeatNumber()).isEqualTo(5);
    }

    @Test
    void cantCreateSeat_invalidRow_zero() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Seat(0, 5))
                .withMessage("row must be greater than zero");
    }

    @Test
    void cantCreateSeat_invalidRow_negative() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Seat(-1, 5))
                .withMessage("row must be greater than zero");
    }

    @Test
    void cantCreateSeat_invalidSeatNumber_zero() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Seat(1, 0))
                .withMessage("seatNumber must be greater than zero");
    }

    @Test
    void cantCreateSeat_invalidSeatNumber_negative() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Seat(1, -1))
                .withMessage("seatNumber must be greater than zero");
    }
}