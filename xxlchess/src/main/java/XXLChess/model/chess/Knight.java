package XXLChess.model.chess;

import XXLChess.model.Chess;
import XXLChess.util.Point;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Chess {
    public Knight(int x, int y, boolean isWhite, PImage sprite, List<Chess> chessList) {
        super(x, y, isWhite, sprite, chessList);
        this.value = 2;
    }

    @Override
    public List<Point> getLogicalLocation() {
        int x = point.getX();
        int y = point.getY();
        List<Point> ls = new ArrayList<Point>();
        int dr[] = {1, 1, -1, -1, 2, 2, -2, -2};
        int dc[] = {2, -2, 2, -2, 1, -1, 1, -1};
        for(int i=0;i<dr.length;i++)
        {
            ls.add(new Point(x + dr[i], y + dc[i]));
        }
        return ls;
    }
}
