package XXLChess.model.chess;

import XXLChess.model.Chess;
import XXLChess.util.Point;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Archbishop extends Chess {

    public Archbishop(int x, int y, boolean isWhite, PImage sprite, List<Chess> chessList) {

        super(x, y, isWhite, sprite, chessList);
        this.value = 7.5;
    }
    @Override
    public List<Point> getLogicalLocation() {
        int x = point.getX();
        int y = point.getY();
        List<Point> ls = new ArrayList<Point>();
        {
            int dr[] = {1, 1, -1, -1};
            int dc[] = {1, -1, 1, -1};
            for (int i = 0; i < dr.length; i++) {
                for (int j = 1; j < 100; j++) {
                    int curx = x + dr[i] * j;
                    int cury = y + dc[i] * j;
                    if (this.is_opposite(curx, cury)) {
                        ls.add(new Point(curx, cury));
                        break;
                    }
                    if (this.is_self(curx, cury)) {
                        break;
                    }
                    ls.add(new Point(curx, cury));
                }
            }
        }
        {
            int dr[] = {1, 1, -1, -1, 2, 2, -2, -2};
            int dc[] = {2, -2, 2, -2, 1, -1, 1, -1};
            for (int i = 0; i < dr.length; i++) {
                ls.add(new Point(x + dr[i], y + dc[i]));
            }
        }
        return ls;
    }
}
