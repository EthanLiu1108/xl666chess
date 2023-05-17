package XXLChess.model.chess;

import XXLChess.model.Chess;
import processing.core.PImage;

import java.util.List;

public class Queen extends Chess {

    public Queen(int x, int y, boolean isWhite, PImage sprite, List<Chess> chessList) {
        super(x, y, isWhite, sprite, chessList);
        this.value = 9.5;
    }
}
