package XXLChess.model;

import XXLChess.App;
import XXLChess.util.Aim;
import XXLChess.util.Color;
import XXLChess.util.Point;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import static XXLChess.App.CELLSIZE;
public class Board implements Cloneable{
    private int width;
    private int length;
    private int squareSize;
    private List<Chess> chessList;

    public Board(int width, int length,int squareSize, List<Chess> chessList) {
        this.width = width;
        this.length = length;
        this.squareSize = squareSize;
        this.chessList = chessList;
    }

    public Board(){
        this.width = 14;
        this.length = 14;
        this.chessList = new ArrayList<Chess>();
    }

    public Board(List<Chess> chessList) {
        this.width = 14;
        this.length = 14;
        this.chessList = chessList;
    }

    public Board(int squareSize, List<Chess> chessList) {
        this.width = 14;
        this.length = 14;
        this.squareSize = squareSize;
        this.chessList = chessList;
    }

    public boolean check(boolean is_white){
        Point kingPoint = getKingLocation(is_white);
//        System.out.println(kingPoint.getX());
//        System.out.println(kingPoint.getY());
//        System.out.println(is_white);
        for(Chess chess: chessList){
            if(chess.isWhite() != is_white){
                List<Aim> aims = chess.getMovableLocationWithoutCheck(this);
                for(Aim aim:aims) {
                    if(aim.getPoint().equals(kingPoint)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkMate(boolean is_white){
        for(Chess chess: chessList){
            if(chess.isWhite() == is_white && chess.getMovableLocation(this).size() != 0){
                return false;
            }
        }
        return true;
    }

    public List<Chess> getAllChecker(boolean is_white) {
        Point kingPoint = getKingLocation(is_white);
        List<Chess> checkers = new ArrayList<Chess>();
        for(Chess chess: chessList){
            if(chess.isWhite() != is_white && chess.isKing() == false){
                List<Aim> aims = chess.getMovableLocationWithoutCheck(this);
                for(Aim aim:aims) {
                    if(aim.getPoint().equals(kingPoint)) {
                        checkers.add(chess);
                        break;
                    }
                }
            }
        }
        return checkers;
    }

    public void drawBoard (PApplet app) {
        int squareSize = CELLSIZE;
        app.noStroke();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                app.fill((i + j) % 2 == 0 ? 0xFFF0D9B5 : 0xFFB58863);          // light brown and dark brown
                app.rect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }
    }



    public void drawChess (PApplet app) {
        for(Chess chess: chessList){
            chess.draw(app);
        }
    }
// move and remove
    public Chess findChess(int x, int y){
        for(Chess chess: chessList) {
            if(chess.getX() == x && chess.getY() == y) {
                return chess;
            }
        }
        return null;
    }
    public void move(Chess chess, Aim aim){
        App.audioMove.play();
        if(aim.isCapture()) {
            App.audioCapture.play();
            removeChess(aim.getPoint());
        }
        chess.setPoint(aim.getPoint());
    }

    public void removeChess(Point point){
        for(Chess chess:chessList){
            if(chess.getPoint().equals(point)){
                chessList.remove(chess);
                break;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public List<Chess> getChessList() {
        return chessList;
    }

    public void addChess(Chess chess) {
        chessList.add(chess);
    }

    public Point getKingLocation(boolean isWhite) {
        for(Chess chess:chessList){
            if(chess.isKing() && chess.isWhite() == isWhite){
                return chess.getPoint();
            }
        }
        return null;
    }

    public List<Aim> selectChess(Chess chess) {
        return chess.getMovableLocation(this);
    }

    @Override
    public Board clone() throws CloneNotSupportedException {    //check if next step can get out from status check
        Board cloned = (Board) super.clone();
        cloned.chessList = new ArrayList<>();
        for(Chess chess: chessList){
            Chess temp = chess.clone();
            cloned.chessList.add(temp);
        }
        return cloned;
    }


}
