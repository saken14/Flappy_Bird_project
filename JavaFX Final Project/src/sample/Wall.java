package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Wall extends Pane {
    //Rectangle rect;
    Image image;
    public int height;

    public Wall(int height) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src\\styles\\wall.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(40);
        imageView.setFitHeight(height);

        this.height = height;
        getChildren().addAll(imageView); //добавляем этот прямоугольник на панель
    }
}
