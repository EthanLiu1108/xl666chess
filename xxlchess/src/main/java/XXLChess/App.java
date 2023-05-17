package XXLChess;

//import org.reflections.Reflections;
//import org.reflections.scanners.Scanners;

import XXLChess.model.Game;
import processing.core.PApplet;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;

public class App extends PApplet {

    public static final int SPRITESIZE = 480;
    public static final int CELLSIZE = 48;
    public static final int SIDEBAR = 120;
    public static final int BOARD_WIDTH = 14;

    public static int WIDTH = CELLSIZE * BOARD_WIDTH + SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH * CELLSIZE;

    public static final int FPS = 60;

    public String configPath;

    public Game game;

    public static AudioClip audioMove;
    public static AudioClip audioCapture;


    public App() {

        this.configPath = "config.json";

        try {
            audioMove = Applet.newAudioClip(new File("src/main/resources/XXLChess/move-self.wav").toURI().toURL());
            audioCapture = Applet.newAudioClip(new File("src/main/resources/XXLChess/capture.wav").toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialise the setting of the window size.
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
    public void setup() {
        frameRate(FPS);
        // LOAD IMAGE

        // load config
        JSONObject conf = loadJSONObject(new File(this.configPath));
        game = new Game(conf, this);

    }






    /**
     * Receive key pressed signal from the keyboard.
     */
    public void keyPressed() {
        if(this.key == 'e')
        {
            game.restart();
            JSONObject conf = loadJSONObject(new File(this.configPath));
            game = new Game(conf, this);
            draw();
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    public void keyReleased() {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        game.start(mouseX,mouseY);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }








    /**
     * Draw all elements in the game by current frame.
     */
    public void draw() {

        game.draw(this);

    }











    // Add any additional methods or attributes you want. Please put classes in different files.


    public static void main(String[] args) {

        PApplet.main("XXLChess.App");
    }

}
