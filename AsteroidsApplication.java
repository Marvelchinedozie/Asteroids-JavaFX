package Asteroids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AsteroidsApplication extends Application {

    public static int WIDTH = 300;
    public static int HEIGHT = 200;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);

        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);
        List<Asteroids> asteroids = new ArrayList<>();
        Text text = new Text(10, 20, "Points: 0");
        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroids asteroid = new Asteroids(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT));
            asteroids.add(asteroid);
        }

        pane.getChildren().add(text);
        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
        
        AtomicInteger points = new AtomicInteger();
        
        
        Scene scene = new Scene(pane);
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });
        
        List<Projectile> projectiles = new ArrayList<>();

        new AnimationTimer() {

            
            @Override
            public void handle(long now) {
            
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }
            
                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }
            
                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }
            
                // Shoot projectiles
                if (pressedKeys.getOrDefault(KeyCode.SPACE, false)
                        && projectiles.size() < 3) {
            
                    Projectile projectile = new Projectile(
                            (int) ship.getCharacter().getTranslateX(),
                            (int) ship.getCharacter().getTranslateY()
                    );
            
                    projectile.getCharacter().setRotate(
                            ship.getCharacter().getRotate()
                    );
            
                    projectiles.add(projectile);
                    projectile.accelerate();
                    projectile.setMovement(
                            projectile.getMovement().normalize().multiply(3)
                    );
            
                    pane.getChildren().add(projectile.getCharacter());
                }
            
                // Spawn new asteroids
                if (Math.random() < 0.005) {
                    Random rnd = new Random();
            
                    Asteroids asteroid = new Asteroids(
                            rnd.nextInt(WIDTH),
                            rnd.nextInt(HEIGHT)
                    );
            
                    if (!asteroid.collide(ship)) {
                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }
            
                // Move all objects
                ship.move();
                asteroids.forEach(Asteroids::move);
                projectiles.forEach(Projectile::move);
            
                // Check projectile-asteroid collisions
                projectiles.forEach(projectile -> {
                    asteroids.forEach(asteroid -> {
            
                        if (projectile.isAlive()
                                && asteroid.isAlive()
                                && projectile.collide(asteroid)) {
            
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
            
                            points.addAndGet(1000);
                            text.setText("Points: " + points.get());
                        }
                    });
                });
            
                // Remove dead projectiles from the screen and list
                projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .forEach(projectile ->
                                pane.getChildren().remove(projectile.getCharacter())
                        );
            
                projectiles.removeIf(projectile -> !projectile.isAlive());
            
                // Remove destroyed asteroids from the screen and list
                asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid ->
                                pane.getChildren().remove(asteroid.getCharacter())
                        );
            
                asteroids.removeIf(asteroid -> !asteroid.isAlive());
            }
        }.start();
        

        
        stage.setTitle("Asteroids!");
        stage.setScene(scene);
        stage.show();
    }
    
   
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
