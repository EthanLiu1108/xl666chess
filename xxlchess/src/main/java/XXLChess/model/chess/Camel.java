package XXLChess.model.chess;

import XXLChess.model.Board;
import XXLChess.model.Chess;
import XXLChess.util.Aim;
import XXLChess.util.Point;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Camel extends Chess {

    public Camel(int x, int y, boolean isWhite, PImage sprite, List<Chess> chessList) {

        super(x, y, isWhite, sprite, chessList);
        this.value = 2;
    }

//    @Override
//    public List<Aim> getMovableLocationWithoutCheck(Board board) {
//    }


    @Override
    public List<Point> getLogicalLocation() {
        int x = point.getX();
        int y = point.getY();
        List<Point> ls = new ArrayList<Point>();
        int dr[] = {3, 3, 1, 1, -3, -3, -1, -1};
        int dc[] = {1, -1, 3, -3, -1, 1, -3, 3};
        for(int i=0;i<dr.length;i++)
        {
            ls.add(new Point(x + dr[i], y + dc[i]));
        }
        return ls;
    }
}



