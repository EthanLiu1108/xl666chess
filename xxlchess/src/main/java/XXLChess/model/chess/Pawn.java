package XXLChess.model.chess;

import XXLChess.model.Chess;
import XXLChess.util.Point;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import static XXLChess.App.CELLSIZE;

public class Pawn extends Chess {

    boolean isQueen = false;
    PImage queenImage;

    public Pawn(int x, int y, boolean isWhite, PImage sprite, List<Chess> chessList, PImage queenImage) {
        super(x, y, isWhite, sprite, chessList);
        this.value = 1;
        this.queenImage = queenImage;
    }

    @Override
    public List<Point> getLogicalLocation() {
        int x = point.getX();
        int y = point.getY();
        List<Point> ls = new ArrayList<Point>();
        if(this.isQueen)
        {
            int dr[] = {1, 0, -1, 0, 1, 1, -1, -1};
            int dc[] = {0, 1, 0, -1, 1, -1, 1, -1};
            for(int i=0;i<dr.length;i++)
            {
                for(int j=1;j<100;j++)
                {
                    int curx = x + dr[i] * j;
                    int cury = y + dc[i] * j;
                    if(this.is_opposite(curx, cury))
                    {
                        ls.add(new Point(curx, cury));
                        break;
                    }
                    if(this.is_self(curx, cury))
                    {
                        break;
                    }
                    ls.add(new Point(curx, cury));
                }
            }
        }
        else if(this.isWhite && y == 12)
        {
            int dr[] = {0};
            int dc[] = {-1};
            for(int i=0;i<dr.length;i++)
            {
                for(int j=1;j<=2;j++)
                {
                    int curx = x + dr[i] * j;
                    int cury = y + dc[i] * j;
                    if(this.is_opposite(curx, cury))
                    {
                        ls.add(new Point(curx, cury));
                        break;
                    }
                    if(this.is_self(curx, cury))
                    {
                        break;
                    }
                    ls.add(new Point(curx, cury));
                }
            }
        }
        else if(!this.isWhite && y == 1)
        {
            int dr[] = {0};
            int dc[] = {1};
            for(int i=0;i<dr.length;i++)
            {
                for(int j=1;j<=2;j++)
                {
                    int curx = x + dr[i] * j;
                    int cury = y + dc[i] * j;
                    if(this.is_opposite(curx, cury))
                    {
                        ls.add(new Point(curx, cury));
                        break;
                    }
                    if(this.is_self(curx, cury))
                    {
                        break;
                    }
                    ls.add(new Point(curx, cury));
                }
            }
        }
        else
        {
            int dy = 1;
            if(this.isWhite && y == 12)
                dy = -2;
            else if(this.isWhite)
                dy = -1;
            else if(!this.isWhite && y == 1)
                dy = 2;
            else if(!this.isWhite)
                dy = 1;
            int curx = x;
            int cury = y + dy;
            if(!this.is_opposite(curx, cury) && !this.is_self(curx, cury))
                ls.add(new Point(curx, cury));
            curx = x - 1;
            cury = y + dy;
            if(this.is_opposite(curx, cury))
                ls.add(new Point(curx, cury));
            curx = x + 1;
            cury = y + dy;
            if(this.is_opposite(curx, cury))
                ls.add(new Point(curx, cury));
        }

        if(this.isWhite)
        {
            if(y == 7)
            {
                this.isQueen = true;
            }
        }
        else
        {
            if(y == 8)
            {
                this.isQueen = true;
            }
        }

        return ls;
    }

    public void draw(PApplet app) {
        if(!isQueen)
            super.draw(app);
        else
            app.image(this.queenImage, this.img_x, this.img_y);
    }
}
