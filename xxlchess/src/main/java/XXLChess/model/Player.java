package XXLChess.model;

import processing.core.PApplet;

public abstract class Player {
    private int seconds;        //current time
    private int increment;    //increment time after played

    private boolean is_white;

    public Player(int seconds, int increment, boolean is_white) {
        this.seconds = seconds;
        this.increment = increment;
        this.is_white = is_white;
    }

    public boolean isWhite() {
        return is_white;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getIncrement() {
        return increment;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    public String getFormatTime() {             //transform second to format minute
        int minutes = seconds / 60;
        int remainderSeconds = seconds % 60;
        String formattedTime = String.format("%2d:%02d", minutes, remainderSeconds);
        return formattedTime;
    }


    public abstract void draw(PApplet app);

}
