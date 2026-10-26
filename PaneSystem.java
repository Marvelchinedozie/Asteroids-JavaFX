package Asteroids;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class PaneSystem extends Application
{
    @Override
    public void start(Stage stage)
    {
        Pane pane = new Pane();
        pane.setPrefSize(300, 200);
        
        Polygon ship = new Polygon(0, 0, 100, 0, 100, 50, 0, 50);
        ship.setTranslateX(100);
        ship.setTranslateY(50);
        
        pane.getChildren().add(ship);
        
        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                ship.setRotate(ship.getRotate() - 5);
            }

            if (event.getCode() == KeyCode.RIGHT) {
                ship.setRotate(ship.getRotate() + 5);
            }
        });
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args)
    {
        launch(PaneSystem.class);
    }
}
