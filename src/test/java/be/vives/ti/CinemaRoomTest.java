package be.vives.ti;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CinemaRoomTest {

    private CinemaRoom cinemaRoom;

    @BeforeEach
    void setUp() {
        cinemaRoom = new CinemaRoom("The Dark Knight", 5, 10);
    }

    @Test
    void canCreateCinemaRoom(){
        assertThat(cinemaRoom).isNotNull();
        assertThat(cinemaRoom.getMovieName()).isEqualTo("The Dark Knight");
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(50);
        assertThat(cinemaRoom.getReservedSeats()).isEmpty();
    }

    @Test
    void cantCreateCinemaRoom_invalidNumberOfRows_zeroRows() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new CinemaRoom("The Dark Knight", 0, 25))
                .withMessage("Number of rows and seats per row must be greater than 0");
    }

    @Test
    void cantCreateCinemaRoom_invalidNumberOfRows_negativeNumberOfRows() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new CinemaRoom("The Dark Knight", -1, 25))
                .withMessage("Number of rows and seats per row must be greater than 0");
    }

    @Test
    void cantCreateCinemaRoom_invalidNumberOfSeats_zeroSeats() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new CinemaRoom("The Dark Knight", 10, 0))
                .withMessage("Number of rows and seats per row must be greater than 0");
    }

    @Test
    void cantCreateCinemaRoom_invalidNumberOfSeats_negativeNumberOfSeats() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new CinemaRoom("The Dark Knight", 10, -25))
                .withMessage("Number of rows and seats per row must be greater than 0");
    }

    @Test
    void reserveConsecutiveSeatsInEmptyRoom() {
        // build
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(50);
        assertThat(cinemaRoom.getReservedSeats()).isEmpty();

        // operate
        List<Seat> reservedConsecutiveSeats = cinemaRoom.reserveConsecutiveSeats(5);

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(45);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(5);

        assertThat(reservedConsecutiveSeats).containsExactly(new Seat(1,1),
                                                                new Seat(1,2),
                                                                new Seat(1,3),
                                                                new Seat(1,4),
                                                                new Seat(1,5));
        assertThat(cinemaRoom.getReservedSeats()).containsSequence(reservedConsecutiveSeats);
    }

    @Test
    void reserveTheNextConsecutiveSeatsInABigRow() {
        // build
        cinemaRoom.reserveConsecutiveSeats(5);
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(45);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(5);

        // operate
        List<Seat> reservedConsecutiveSeats = cinemaRoom.reserveConsecutiveSeats(3);

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(42);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(8);

        assertThat(reservedConsecutiveSeats).containsExactly(new Seat(1,6),
                new Seat(1,7),
                new Seat(1,8));
        assertThat(cinemaRoom.getReservedSeats()).containsSequence(reservedConsecutiveSeats);
    }

    @Test
    void reserveTheNextConsecutiveSeatsAndFillTheEntireRow() {
        // build
        cinemaRoom.reserveConsecutiveSeats(5);
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(45);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(5);

        // operate
        List<Seat> reservedConsecutiveSeats = cinemaRoom.reserveConsecutiveSeats(5);

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(40);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(10);

        assertThat(reservedConsecutiveSeats).containsExactly(new Seat(1,6),
                                                            new Seat(1,7),
                                                            new Seat(1,8),
                                                            new Seat(1,9),
                                                            new Seat(1,10));
        assertThat(cinemaRoom.getReservedSeats()).containsSequence(reservedConsecutiveSeats);
    }

    @Test
    void reserveTheNextConsecutiveSeats_NoSpaceAnymoreInTheRow_ReserveInNextRow() {
        // build
        cinemaRoom.reserveConsecutiveSeats(8);
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(42);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(8);

        // operate
        List<Seat> reservedConsecutiveSeats = cinemaRoom.reserveConsecutiveSeats(5);

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(37);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(13);

        assertThat(reservedConsecutiveSeats).containsExactly(new Seat(2,1),
                                                            new Seat(2,2),
                                                            new Seat(2,3),
                                                            new Seat(2,4),
                                                            new Seat(2,5));
        assertThat(cinemaRoom.getReservedSeats()).containsSequence(reservedConsecutiveSeats);


    }

    @Test
    void reserveEntireRowOnce() {
        // build
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(50);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(0);

        // operate
        List<Seat> reservedConsecutiveSeats = cinemaRoom.reserveConsecutiveSeats(10);

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(40);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(10);

        assertThat(reservedConsecutiveSeats).containsExactly(new Seat(1,1),
                                                            new Seat(1,2),
                                                            new Seat(1,3),
                                                            new Seat(1,4),
                                                            new Seat(1,5),
                                                            new Seat(1,6),
                                                            new Seat(1,7),
                                                            new Seat(1,8),
                                                            new Seat(1,9),
                                                            new Seat(1,10));
        assertThat(cinemaRoom.getReservedSeats()).containsSequence(reservedConsecutiveSeats);

    }

    @Test
    void reserveEntireRowTwice() {
        // build
        cinemaRoom.reserveConsecutiveSeats(10);
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(40);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(10);

        // operate
        List<Seat> reservedConsecutiveSeats = cinemaRoom.reserveConsecutiveSeats(10);

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(30);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(20);

        assertThat(reservedConsecutiveSeats).containsExactly(new Seat(2,1),
                                                            new Seat(2,2),
                                                            new Seat(2,3),
                                                            new Seat(2,4),
                                                            new Seat(2,5),
                                                            new Seat(2,6),
                                                            new Seat(2,7),
                                                            new Seat(2,8),
                                                            new Seat(2,9),
                                                            new Seat(2,10));
        assertThat(cinemaRoom.getReservedSeats()).containsSequence(reservedConsecutiveSeats);
    }

    @Test
    void tryToReserveANumberOfConsecutiveSeatsThatIsBiggerThanTheRowLength() {
        // build
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(50);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(0);

        // operate
        assertThatThrownBy(() -> cinemaRoom.reserveConsecutiveSeats(11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough consecutive seats available in any row");

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(50);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(0);
    }

    @Test
    void onlySpaceLeftOnTheLastRow() {
        // build
        for (int i = 1; i<=4; i++){
            cinemaRoom.reserveConsecutiveSeats(10);
        }
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(10);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(40);

        // operate
        List<Seat> reservedConsecutiveSeats = cinemaRoom.reserveConsecutiveSeats(5);

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(5);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(45);

        assertThat(reservedConsecutiveSeats).containsExactly(new Seat(5,1),
                                                            new Seat(5,2),
                                                            new Seat(5,3),
                                                            new Seat(5,4),
                                                            new Seat(5,5));
        assertThat(cinemaRoom.getReservedSeats()).containsSequence(reservedConsecutiveSeats);
    }

    @Test
    void reserveTheLastSeatOnTheLastRow() {
        // build
        for (int i = 1; i<=4; i++){
            cinemaRoom.reserveConsecutiveSeats(10);
        }
        cinemaRoom.reserveConsecutiveSeats(5);
        cinemaRoom.reserveConsecutiveSeats(4);
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(1);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(49);

        // operate
        List<Seat> reservedConsecutiveSeats = cinemaRoom.reserveConsecutiveSeats(1);

        // test
        assertThat(cinemaRoom.getAvailableSeats()).isEmpty();
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(50);

        assertThat(reservedConsecutiveSeats).containsExactly(new Seat(5,10));
        assertThat(cinemaRoom.getReservedSeats()).containsSequence(reservedConsecutiveSeats);
    }

    @Test
    void onlyOneRowLeftToReserveSeats_butNotTheLastRow() {
        // build
        cinemaRoom.reserveConsecutiveSeats(8);
        cinemaRoom.reserveConsecutiveSeats(9);
        cinemaRoom.reserveConsecutiveSeats(6);// 4 seats left on the 3th row
        cinemaRoom.reserveConsecutiveSeats(9);
        cinemaRoom.reserveConsecutiveSeats(8);
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(10);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(40);

        // operate
        List<Seat> reservedConsecutiveSeats = cinemaRoom.reserveConsecutiveSeats(3);

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(7);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(43);

        assertThat(reservedConsecutiveSeats).containsExactly(new Seat(3,7),
                new Seat(3,8),
                new Seat(3,9));
        assertThat(cinemaRoom.getReservedSeats()).containsSequence(reservedConsecutiveSeats);
    }

    @Test
    void reserveTheLastSeat_NotOnTheLastRow() {
        // build
        cinemaRoom.reserveConsecutiveSeats(10);
        cinemaRoom.reserveConsecutiveSeats(9);
        cinemaRoom.reserveConsecutiveSeats(10);
        cinemaRoom.reserveConsecutiveSeats(10);
        cinemaRoom.reserveConsecutiveSeats(10);
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(1);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(49);

        // operate
        List<Seat> reservedConsecutiveSeats = cinemaRoom.reserveConsecutiveSeats(1);

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(0);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(50);

        assertThat(reservedConsecutiveSeats).containsExactly(new Seat(2,10));
        assertThat(cinemaRoom.getReservedSeats()).containsSequence(reservedConsecutiveSeats);
    }

    @Test
    void reserveTwoSeatsButOnlyOneLeft() {
        // build
        for (int i = 1; i<=4; i++){
            cinemaRoom.reserveConsecutiveSeats(10);
        }
        cinemaRoom.reserveConsecutiveSeats(5);
        cinemaRoom.reserveConsecutiveSeats(4);
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(1);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(49);

        // operate
        assertThatThrownBy(() -> cinemaRoom.reserveConsecutiveSeats(2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough consecutive seats available in any row");

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(1);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(49);
    }

    @Test
    void thereAreEnoughSeatsAvailableOnARowButNotEnoughConsecutive() {
        // build
        for (int i = 1; i <= 5; i++) {
            cinemaRoom.reserveSeatByNumber(i, 1, 3);
            cinemaRoom.reserveSeatByNumber(i, 6, 3);
        }
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(20);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(30);

        // operate
        assertThatThrownBy(() -> cinemaRoom.reserveConsecutiveSeats(3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough consecutive seats available in any row");

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(20);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(30);
    }

    @Test
    void reserveZeroConsecutiveSeats() {
        // build
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(50);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(0);

        // operate
        assertThatThrownBy(() -> cinemaRoom.reserveConsecutiveSeats(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Number of seats must be greater than zero");

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(50);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(0);
    }

    @Test
    void reserveNegativeConsecutiveSeats() {
        // build
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(50);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(0);

        // operate
        assertThatThrownBy(() -> cinemaRoom.reserveConsecutiveSeats(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Number of seats must be greater than zero");

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(50);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(0);
    }

    @Test
    void reserveBySeatNumber() {
        // build
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(50);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(0);

        // operate
        List<Seat> reservedConsecutiveSeats = cinemaRoom.reserveSeatByNumber(3, 3, 3);
        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(47);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(3);

        assertThat(reservedConsecutiveSeats).containsExactly(new Seat(3,3),
                                                        new Seat(3,4),
                                                        new Seat(3,5));
        assertThat(cinemaRoom.getReservedSeats()).containsSequence(reservedConsecutiveSeats);

    }

    @Test
    void reserveBySeatNumber_justEnoughSeatsOnTheRow() {
        // build
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(50);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(0);

        // operate
        List<Seat> reservedConsecutiveSeats = cinemaRoom.reserveSeatByNumber(3, 5, 6);

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(44);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(6);

        assertThat(reservedConsecutiveSeats).containsExactly(new Seat(3,5),
                                        new Seat(3,6),
                                        new Seat(3,7),
                                        new Seat(3,8),
                                        new Seat(3,9),
                                        new Seat(3,10));
        assertThat(cinemaRoom.getReservedSeats()).containsSequence(reservedConsecutiveSeats);

    }

    @Test
    void reserveBySeatNumber_NotEnoughSeatsOnThatRow() {
        // build
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(50);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(0);

        // operate
        assertThatThrownBy(() -> cinemaRoom.reserveSeatByNumber(3, 5, 7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough consecutive seats available starting from seat 5");

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(50);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(0);
    }

    @Test
    void startSeatIsNotAvailable() {
        // build
        cinemaRoom.reserveSeatByNumber(3, 5, 1);
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(49);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(1);

        // operate
        assertThatThrownBy(() -> cinemaRoom.reserveSeatByNumber(3, 5, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough consecutive seats available starting from seat 5");

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(49);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(1);
    }

    @Test
    void startSeatNotAvailable_ButTheConsecutiveSeatsAreNot() {
        // build
        cinemaRoom.reserveSeatByNumber(3, 5, 2);
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(48);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(2);

        // operate
        assertThatThrownBy(() -> cinemaRoom.reserveSeatByNumber(3, 4, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough consecutive seats available starting from seat 4");

        // test
        assertThat(cinemaRoom.getAvailableSeats().size()).isEqualTo(48);
        assertThat(cinemaRoom.getReservedSeats().size()).isEqualTo(2);
    }

    @Test
    void reserveSeatByNumber_RowNumberIsZero() {
        assertThatThrownBy(() -> cinemaRoom.reserveSeatByNumber(0, 5, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rownumber must be greater than zero");
    }

    @Test
    void reserveSeatByNumber_RowNumberIsNegative() {
        assertThatThrownBy(() -> cinemaRoom.reserveSeatByNumber(-2, 5, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rownumber must be greater than zero");
    }

    @Test
    void reserveSeatByNumber_StartSeatNumberIsZero() {
        assertThatThrownBy(() -> cinemaRoom.reserveSeatByNumber(5, 0, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Start seat number must be greater than zero");
    }

    @Test
    void reserveSeatByNumber_StartSeatNumberIsNegative() {
        assertThatThrownBy(() -> cinemaRoom.reserveSeatByNumber(5, -5, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Start seat number must be greater than zero");
    }

    @Test
    void reserveSeatByNumber_NumberOfSeatsIsZero() {
        assertThatThrownBy(() -> cinemaRoom.reserveSeatByNumber(5, 5, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Number of seats must be greater than zero");
    }

    @Test
    void reserveSeatByNumber_NumberOfSeatsIsNegative() {
        assertThatThrownBy(() -> cinemaRoom.reserveSeatByNumber(5, 5, -3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Number of seats must be greater than zero");
    }


}