package XXLChess.util;

public class Aim {
    private Point point;
    private boolean capture;

    public Aim(Point point, boolean capture) {
        this.point = point;
        this.capture = capture;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }
}
