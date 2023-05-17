package XXLChess.model.player;

import XXLChess.model.Player;
import processing.core.PApplet;

public class AIPlayer extends Player {
    public AIPlayer(int seconds, int increment, boolean is_white) {
        super(seconds, increment, is_white);
    }

    public void draw(PApplet app) {
        String time = getFormatTime();
        app.textSize(24);
        app.fill(255);
        app.text(time, 700, 50);
    }
}
