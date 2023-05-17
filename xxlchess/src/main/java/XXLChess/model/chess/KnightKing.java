package XXLChess.model.chess;

import XXLChess.model.Chess;
import XXLChess.util.Point;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class KnightKing extends Chess {
    public KnightKing(int x, int y, boolean isWhite, PImage sprite, List<Chess> chessList) {
        super(x, y, isWhite, sprite, chessList);
        this.value = 5;
    }
    @Override
    public List<Point> getLogicalLocation() {
        int x = point.getX();
        int y = point.getY();
        List<Point> ls = new ArrayList<Point>();
        {
            int dr[] = {1, 1, -1, -1, 2, 2, -2, -2};
            int dc[] = {2, -2, 2, -2, 1, -1, 1, -1};
            for(int i=0;i<dr.length;i++)
            {
                ls.add(new Point(x + dr[i], y + dc[i]));
            }
        }
        {
            Point a = new Point(x - 1, y - 1);
            Point b = new Point(x, y - 1);
            Point c = new Point(x + 1, y - 1);
            Point d = new Point(x - 1, y);
            Point e = new Point(x + 1, y);
            Point f = new Point(x - 1, y + 1);
            Point g = new Point(x, y + 1);
            Point h = new Point(x + 1, y + 1);
            ls.add(a);
            ls.add(b);
            ls.add(c);
            ls.add(d);
            ls.add(e);
            ls.add(f);
            ls.add(g);
            ls.add(h);
        }
        return ls;
    }
}
