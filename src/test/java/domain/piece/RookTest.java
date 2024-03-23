package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import domain.coordinate.Coordinate;
import domain.direction.StraightDirection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RookTest {

    @DisplayName("룩은 방향만 맞으면 거리와 상관없이 이동할 수 있다.")
    @Test
    void cantMoveTest() {
        Coordinate start = new Coordinate(3, 3);
        Coordinate destination = new Coordinate(3, 0);
        Rook rook = new Rook(Color.BLACK);

        assertFalse(rook.cantMove(start, destination));
    }

    @Nested
    @DisplayName("룩이 이동할 수 있는 방향을 확인한다.")
    class GetDirectionTest {

        Rook rook = new Rook(Color.BLACK);
        Coordinate start = new Coordinate(3, 3);
        Coordinate destination;

        @DisplayName("위 방향")
        @Test
        void up() {
            destination = new Coordinate(1, 3);
            assertThat(rook.getDirection(start, destination))
                    .isEqualTo(StraightDirection.UP);
        }

        @DisplayName("아래 방향")
        @Test
        void down() {
            destination = new Coordinate(7, 3);
            assertThat(rook.getDirection(start, destination))
                    .isEqualTo(StraightDirection.DOWN);
        }

        @DisplayName("왼쪽 방향")
        @Test
        void left() {
            destination = new Coordinate(3, 0);
            assertThat(rook.getDirection(start, destination))
                    .isEqualTo(StraightDirection.LEFT);
        }

        @DisplayName("오른쪽 방향")
        @Test
        void right() {
            destination = new Coordinate(3, 7);
            assertThat(rook.getDirection(start, destination))
                    .isEqualTo(StraightDirection.RIGHT);
        }
    }
}
