package XXLChess.model;

import XXLChess.util.Aim;
import XXLChess.util.Point;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;


public abstract class Chess implements Cloneable{
    protected boolean isWhite;
    protected Point point;
    protected int pix = 48;
    protected int img_x;
    protected int img_y;
    protected double value;
    private PImage sprite;
    private boolean isKing;
    protected List<Chess> chessList;


    public Chess(int x, int y, boolean isWhite, PImage sprite, List<Chess> chessList) {
        this.isWhite = isWhite;
        this.sprite = sprite;
        this.point = new Point(x, y);
        this.img_x = x * pix;
        this.img_y = y * pix;
        this.isKing = false;
        this.value = 0;
        this.chessList = chessList;
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSprite(PImage sprite) {

        this.sprite = sprite;
    }

    public void setPix(int pix) {
        this.pix = pix;
    }

    public void draw(PApplet app) {
        app.image(this.sprite, this.img_x, this.img_y);
    }

    public List<Aim> getMovableLocation(Board board) {

        List<Aim> ls = new ArrayList<Aim>();
        List<Aim> movableLocations = getMovableLocationWithoutCheck(board);
        if (!board.check(isWhite)) {
            ls = getMovableLocationWithoutCheck(board);
        } else {
            for(Aim aim: movableLocations) {
                Board cloned_board = null;
                try {
                    cloned_board = board.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                Chess cloned_chess = cloned_board.findChess(point.getX(),point.getY());
                cloned_board.move(cloned_chess, aim);
                if(!cloned_board.check(isWhite)){
                    ls.add(aim);
                }
            }
        }
        return ls;

    }




    public List<Aim> getMovableLocationWithoutCheck(Board board) {
        List<Point> ls = getLogicalLocation();
        List<Aim> aims = new ArrayList<Aim>();
        for (Point point : ls) {
            if (point.getX() < board.getWidth() && point.getY() < board.getLength() && point.getX() >= 0 && point.getY() >= 0) {   //board boardline
                Chess found = board.findChess(point.getX(), point.getY());
                Aim aim = null;
                if (found == null) {
                    aim = new Aim(point, false);
                    aims.add(aim);
                } else if (found.isWhite() != isWhite) {
                    aim = new Aim(point, true);
                    aims.add(aim);
                }
            }
        }
        return aims;
    }



    public boolean isKing() {
        return isKing;
    }

    public static double distance_between(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    public void setKing(boolean king) {
        isKing = king;
    }
//Wrong chess implementation    every chess goes the same way(available in circle)
    public List<Point> getLogicalLocation() {
        int x = point.getX();
        int y = point.getY();
        Point a = new Point(x - 1, y - 1);
        Point b = new Point(x, y - 1);
        Point c = new Point(x + 1, y - 1);
        Point d = new Point(x - 1, y);
        Point e = new Point(x + 1, y);
        Point f = new Point(x - 1, y + 1);
        Point g = new Point(x, y + 1);
        Point h = new Point(x + 1, y + 1);
        List<Point> ls = new ArrayList<Point>();
        ls.add(a);
        ls.add(b);
        ls.add(c);
        ls.add(d);
        ls.add(e);
        ls.add(f);
        ls.add(g);
        ls.add(h);
        return ls;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean tick(double piece_movement_speed, double max_movement_time, int FPS) {
        int x = point.getX();
        int y = point.getY();      // coordinate of the chess
        if(x*48 == img_x && y*48 == img_y) {            //check the position of the chess and the image
            return false;
        }
        double distance = distance_between(x, y, img_x, img_y);   // if the position is not the same calculate the distance to get the time
        double time = distance / (piece_movement_speed * FPS);
        double speed = piece_movement_speed;

        if (time > max_movement_time) {
            speed = distance / (time * FPS);
        }
        int pix_x = x * 48;
        int pix_y = y * 48;
        if (img_x != pix_x) {
            if (img_x > pix_x) {
                img_x -= speed;
            } else {
                img_x += speed;
            }
        }
        if (img_y != pix_y) {
            if (img_y > pix_y) {
                img_y -= speed;
            } else {
                img_y += speed;
            }
        }
        if (Math.abs(img_x - pix_x) < speed) {
            img_x = pix_x;
        }
        if (Math.abs(img_y - pix_y) < speed) {
            img_y = pix_y;
        }
        return true;
    }

    public int getX() {
        return point.getX();

    }

    public int getY() {
        return point.getY();
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point){
        this.point = point;
    }

    public int getImg_x() {
        return img_x;
    }

    public int getImg_y() {
        return img_y;
    }


    @Override
    public Chess clone() throws CloneNotSupportedException {
        Chess cloned = (Chess) super.clone();
        return cloned;
    }

    public boolean is_opposite(int x, int y)
    {
        for(Chess chess : this.chessList)
        {
            if(this.isWhite != chess.isWhite)
            {
                if(chess.getX() == x && chess.getY() == y)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean is_self(int x, int y)
    {
        for(Chess chess : this.chessList)
        {
            if(this.isWhite == chess.isWhite)
            {
                if(chess.getX() == x && chess.getY() == y)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
