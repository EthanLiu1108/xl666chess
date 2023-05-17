package XXLChess.model;

import processing.core.PImage;
import XXLChess.model.chess.*;
import XXLChess.model.player.*;
import XXLChess.util.Aim;
import XXLChess.util.Color;
import XXLChess.util.Point;
import processing.core.PApplet;
import processing.data.JSONObject;

import java.io.*;
import java.util.List;
import java.util.Random;
import static XXLChess.App.CELLSIZE;


public class Game {
    private Board board;
    private Player player;
    private Player cpu;
    private double piece_movement_speed;
    private double max_movement_time;
    private boolean checking = false;
    private Point previous_location;
    private Point current_location;
    private Chess selected_Chess;
    private List<Aim> aims;
    private Player current;
    private Chess moving;
    private boolean win = false;
    private int count = 0;

    JSONObject levelConfiguration;
    PApplet app;

    public Game(JSONObject levelConfiguration, PApplet app){

        initLevel(levelConfiguration, app);
        this.levelConfiguration = levelConfiguration;
        this.app = app;
    }

    public void restart()
    {
        this.initLevel(this.levelConfiguration, this.app);
    }



    private void initLevel(JSONObject config, PApplet app) {          //inisialize game
        this.board = new Board();                                               //create a new board to config the files
        String layout = config.getString("layout");
        try (BufferedReader reader = new BufferedReader(new FileReader(layout))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                if(!line.equals("")) {
                    int width = line.length();
                    for(int j = 0; j < width; j ++) {
                        ChessImageToBoard(line.charAt(j),j, lineNumber,app);           // add the chess to the board
                    }
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject timeControls = config.getJSONObject("time_controls");
        JSONObject player = timeControls.getJSONObject("player");
        int playerSeconds = player.getInt("seconds");
        int playerIncrement = player.getInt("increment");
        JSONObject cpu = timeControls.getJSONObject("cpu");
        int cpuSeconds = cpu.getInt("seconds");
        int cpuIncrement = cpu.getInt("increment");
        String playerColour = config.getString("player_colour");
        boolean is_white = false;
        if(playerColour.equals("white")) {
            is_white = true;
        }
        this.player = new HumanPlayer(playerSeconds, playerIncrement, is_white);
        this.cpu = new AIPlayer(cpuSeconds, cpuIncrement, !is_white);
        if(is_white){
            current = this.player;
        } else{
            current = this.cpu;
        }
        piece_movement_speed = config.getDouble("piece_movement_speed");
        max_movement_time = config.getDouble("max_movement_time");

    }
    private boolean ChessImageToBoard(char type, int x, int y, PApplet app){
        PImage b_amazon, b_archbishop, b_bishop, b_camel, b_chancellor, b_king, b_knight, b_knight_King, b_pawn, b_queen, b_rook, w_amazonImg, w_archbishopImg, w_bishop, w_camel, w_chancellor, w_king, w_knight, w_knightKing, w_pawn, w_queen, w_rook;

        b_amazon = app.loadImage("src/main/resources/XXLChess/b-amazon.png");
        b_amazon.resize(CELLSIZE,CELLSIZE);
        b_archbishop = app.loadImage("src/main/resources/XXLChess/b-archbishop.png");
        b_archbishop.resize(CELLSIZE,CELLSIZE);
        b_bishop = app.loadImage("src/main/resources/XXLChess/b-bishop.png");
        b_bishop.resize(CELLSIZE,CELLSIZE);
        b_camel = app.loadImage("src/main/resources/XXLChess/b-camel.png");
        b_camel.resize(CELLSIZE,CELLSIZE);
        b_chancellor = app.loadImage("src/main/resources/XXLChess/b-chancellor.png");
        b_chancellor.resize(CELLSIZE,CELLSIZE);
        b_king = app.loadImage("src/main/resources/XXLChess/b-king.png");
        b_king.resize(CELLSIZE,CELLSIZE);
        b_knight = app.loadImage("src/main/resources/XXLChess/b-knight.png");
        b_knight.resize(CELLSIZE,CELLSIZE);
        b_knight_King = app.loadImage("src/main/resources/XXLChess/b-knight-king.png");
        b_knight_King.resize(CELLSIZE,CELLSIZE);
        b_pawn = app.loadImage("src/main/resources/XXLChess/b-pawn.png");
        b_pawn.resize(CELLSIZE,CELLSIZE);
        b_queen = app.loadImage("src/main/resources/XXLChess/b-queen.png");
        b_queen.resize(CELLSIZE,CELLSIZE);
        b_rook = app.loadImage("src/main/resources/XXLChess/b-rook.png");
        b_rook.resize(CELLSIZE,CELLSIZE);
        w_amazonImg = app.loadImage("src/main/resources/XXLChess/w-amazon.png");
        w_amazonImg.resize(CELLSIZE,CELLSIZE);
        w_archbishopImg = app.loadImage("src/main/resources/XXLChess/w-archbishop.png");
        w_archbishopImg.resize(CELLSIZE,CELLSIZE);
        w_bishop = app.loadImage("src/main/resources/XXLChess/w-bishop.png");
        w_bishop.resize(CELLSIZE,CELLSIZE);
        w_camel = app.loadImage("src/main/resources/XXLChess/w-camel.png");
        w_camel.resize(CELLSIZE,CELLSIZE);
        w_chancellor = app.loadImage("src/main/resources/XXLChess/w-chancellor.png");
        w_chancellor.resize(CELLSIZE,CELLSIZE);
        w_king = app.loadImage("src/main/resources/XXLChess/w-king.png");
        w_king.resize(CELLSIZE,CELLSIZE);
        w_knight = app.loadImage("src/main/resources/XXLChess/w-knight.png");
        w_knight.resize(CELLSIZE,CELLSIZE);
        w_knightKing = app.loadImage("src/main/resources/XXLChess/w-knight-king.png");
        w_knightKing.resize(CELLSIZE,CELLSIZE);
        w_pawn = app.loadImage("src/main/resources/XXLChess/w-pawn.png");
        w_pawn.resize(CELLSIZE,CELLSIZE);
        w_queen = app.loadImage("src/main/resources/XXLChess/w-queen.png");
        w_queen.resize(CELLSIZE,CELLSIZE);
        w_rook = app.loadImage("src/main/resources/XXLChess/w-rook.png");
        w_rook.resize(CELLSIZE,CELLSIZE);

        Chess temp = null;
        switch (type) {
            case 'A':
               temp = new Amazon(x,y,false, b_amazon, board.getChessList());
               break;
            case 'a':
                temp = new Amazon(x,y,true, w_amazonImg, board.getChessList());
                break;
            case 'H':
                temp = new Archbishop(x,y,false, b_archbishop, board.getChessList());
                break;
            case 'h':
                temp = new Archbishop(x,y,true, w_archbishopImg, board.getChessList());
                break;
            case 'C':
                temp = new Camel(x,y,false, b_camel, board.getChessList());
                break;
            case 'c':
                temp = new Camel(x,y,true, w_camel, board.getChessList());
                break;
            case 'E':
                temp = new Chancellor(x,y,false, b_chancellor, board.getChessList());
                break;
            case 'e':
                temp = new Chancellor(x,y,true, w_chancellor, board.getChessList());
                break;
            case 'K':
                temp = new King(x,y,false, b_king, board.getChessList());
                temp.setKing(true);
                break;
            case 'k':
                temp = new King(x,y,true, w_king, board.getChessList());
                temp.setKing(true);
                break;
            case 'N':
                temp = new Knight(x,y,false, b_knight, board.getChessList());
                break;
            case 'n':
                temp = new Knight(x,y,true, w_knight, board.getChessList());
                break;
            case 'G':
                temp = new KnightKing(x,y,false, b_knight_King, board.getChessList());
                break;
            case 'g':
                temp = new KnightKing(x,y,true, w_knightKing, board.getChessList());
                break;
            case 'P':
                temp = new Pawn(x,y,false, b_pawn, board.getChessList(), b_queen);
                break;
            case 'p':
                temp = new Pawn(x,y,true, w_pawn, board.getChessList(), w_queen);
                break;
            case 'Q':
                temp = new Queen(x,y,false, b_queen, board.getChessList());
                break;
            case 'q':
                temp = new Queen(x,y,true, w_queen, board.getChessList());
                break;
            case 'R':
                temp = new Rook(x,y,false, b_rook, board.getChessList());
                break;
            case 'B':
                temp = new Bishop(x,y,false, b_bishop, board.getChessList());
                break;
            case 'b':
                temp = new Bishop(x,y,true, w_bishop, board.getChessList());
                break;
            case 'r':
                temp = new Rook(x,y,true, w_rook, board.getChessList());
        }
        if(temp != null) {
            this.board.addChess(temp);
            return true;
        }
        return false;
    }

    public Board getBoard(){

        return this.board;
    }













///////piece of shit here for the AI move
    public void aiMove() {

        if(board.checkMate(current.isWhite())) {
            System.out.println(board.getAllChecker(current.isWhite()));
            System.out.println("You win!");    // should be in the sidebar
            System.exit(0);
        }

        current.setSeconds(current.getSeconds() + 3);

        List<Chess> chessList = board.getChessList();
        Random random = new Random();
        for(Chess chess : chessList) {
            if(chess.isWhite() == cpu.isWhite()) {
                List<Aim> ls = chess.getMovableLocation(board);
                int randomNumber = random.nextInt(ls.size() + 1) - 1;
                int i = 0;
                for(Aim aim :ls) {
                    if(i == randomNumber) {
                        previous_location = chess.getPoint();
                        current_location = aim.getPoint();
                        board.move(chess, aim);
                        moving = chess;
                        return;
                    }
                    i += 1;
                }
            }
        }

    }








////Player move
    public void start(int x, int y) {
        if(board.check(current.isWhite())) {
            this.checking = true;
            if(board.checkMate(current.isWhite())) {
                System.out.println("You lose");
                System.exit(0);
            }
        }

        x = x/48;
        y = y/48;
        Chess chess = board.findChess(x,y);
        if(selected_Chess == null) {                                               //none chess selected
            if(chess != null && chess.isWhite() == current.isWhite()) {             // chess color same to player selected
                selected_Chess = chess;
                aims = chess.getMovableLocation(board);
            }
        } else{
            if(chess != selected_Chess){
                for(Aim aim: aims) {
                    if(aim.getPoint().getX() == x && aim.getPoint().getY() == y) {
                        current.setSeconds(current.getSeconds() + 3);
                        previous_location = selected_Chess.getPoint();              //
                        current_location = aim.getPoint();                          //located after moved
                        board.move(selected_Chess, aim);                            // move the chess to selected place
                        moving = selected_Chess;                                    // chess that is moving which is selected chess
                        break;
                    }
                }
            }
            selected_Chess = null;
        }
    }

    int counter = 0;

    public void draw(PApplet app) {
//        if(current.getSeconds() == 0){
//            System.out.println("You lose");
//            System.exit(0);
//        }
//        app.background(0xD3D3D3);
        app.fill(204,204,204);
        app.rect(0,0,2000,2000);
//        app.clear();
        if(counter == 60){
            current.setSeconds(current.getSeconds() - 1);
            counter = 0;
        }
        counter += 1;
        board.drawBoard(app);
        int squareSize = 48;       // draw board
        if(previous_location != null) {
            app.fill((previous_location.getX() + previous_location.getY())%2 == 0 ? Color.LIGHT_GREEN: Color.DARK_GREEN);
            app.rect(previous_location.getX() * squareSize, previous_location.getY()* squareSize, squareSize, squareSize);
            app.fill((current_location.getX() + current_location.getY())%2 == 0 ? Color.LIGHT_GREEN: Color.DARK_GREEN);
            app.rect(current_location.getX() * squareSize, current_location.getY()* squareSize, squareSize, squareSize);
        }
        if(selected_Chess != null) {
            app.noStroke();

            for(Aim aim:aims) {                                                             //chess moveable
                int x = aim.getPoint().getX();
                int y = aim.getPoint().getY();
                if(!aim.isCapture()) {
                    app.fill((x+y)%2 == 0 ? 0xFFC4E0E8: 0xFFAAD2DD);             // by odd or even draw light blue or dark blue
                } else {
                    app.fill(Color.LIGHT_RED);                                              // if isCapture fill light red
                }
                app.rect(x * squareSize, y * squareSize, squareSize, squareSize);
            }
            app.fill(Color.GREEN);                                                //display dark green when selected
            app.rect(selected_Chess.getX() * squareSize, selected_Chess.getY()* squareSize, squareSize, squareSize);

        } else if(moving!= null){
            if(!moving.tick(6,1,60)){
                moving = null;                                                  //check of the chess that is moving is none
                if(current == cpu){
                    current = player;                                           //change ai or human
                } else{
                    current = cpu;
                    aiMove();

                }
                count = 0;
            }
        }

        player.draw(app);
        cpu.draw(app);
        board.drawChess(app);


    }



}
