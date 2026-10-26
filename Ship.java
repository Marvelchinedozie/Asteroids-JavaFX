
package Asteroids;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Ship extends Character {

    private Point2D movement;

    public Ship(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
        this.movement = new Point2D(0, 0);
    }

    public void turnLeft() {
        super.getCharacter().setRotate(
            super.getCharacter().getRotate() - 5
        );
    }

    public void turnRight() {
        super.getCharacter().setRotate(
            super.getCharacter().getRotate() + 5
        );
    }

    public void accelerate() {
        double changeX = Math.cos(
            Math.toRadians(super.getCharacter().getRotate())
        );

        double changeY = Math.sin(
            Math.toRadians(super.getCharacter().getRotate())
        );

        changeX *= 0.05;
        changeY *= 0.05;

        this.movement = this.movement.add(changeX, changeY);
    }

    @Override
    public void move() {
        super.getCharacter().setTranslateX(
            super.getCharacter().getTranslateX() + this.movement.getX()
        );

        super.getCharacter().setTranslateY(
            super.getCharacter().getTranslateY() + this.movement.getY()
        );
    }
}