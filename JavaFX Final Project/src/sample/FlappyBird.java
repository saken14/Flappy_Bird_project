package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FlappyBird extends Application {
    public static Pane appRoot = new Pane(); //панель самомого прлиложения
    public static Pane gameRoot = new Pane(); //Поля где будут располжены все стенки (wall)

    public static ArrayList<Wall> walls = new ArrayList<>(); //здесь будет хронятся все стенки, чтобы проверить столкновение
    Bird bird = new Bird(); // создаем самого птичку
    public static int score = 0; //счет
    public Label scoreLabel = new Label("Score: " + score); //поле где будет записываться наш счет
    public Label gameOverLabel = new Label("Game Over");
    //public boolean gameOver = false;


    public FlappyBird() throws FileNotFoundException {
    }

    public Parent createContent() throws FileNotFoundException { //за создание сцены отвечает метод createContent()
        gameRoot.setPrefSize(600, 684);

        //создание стен
        for (int i = 0; i < 100; i++) {//каждую итерцию будет создаваться стена
            int enter = (int) (Math.random() * 80 + 120); // 100 - 180
            // избегаем от очень маленьких стен у которых высота (height<100) пикселей
            int height = (int)(Math.random() * (400-enter) + 100); // от 100 до 400-enter

            //создание 1-ой стены
            Wall wall = new Wall(height);
            wall.setTranslateX(i * 350 + 600); //расстояние между стен - 350 пикселей
            wall.setTranslateY(0); // стена сверху
            wall.setRotate(180);
            walls.add(wall);

            //создание 2-ой стены
            Wall wall2 = new Wall(600 - enter - height);
            wall2.setTranslateX(i * 350 + 600);
            wall2.setTranslateY(height + enter);
            walls.add(wall2);

            gameRoot.getChildren().addAll(wall, wall2);
        }

        gameRoot.getChildren().add(bird);
        appRoot.getChildren().addAll(gameRoot);
        return appRoot;
    }

    public void update() { //этот метод будет вызываться каждый кадр
        if (bird.velocity.getY() < 4) {
            bird.velocity = bird.velocity.add(0, 1);
        }

        bird.moveX((int) bird.velocity.getX());
        bird.moveY((int) bird.velocity.getY());
        scoreLabel.setText("Score: " + score); //обновления счета

        bird.translateXProperty().addListener((obs, old, newValue) -> { //движение панеля влево
            int offset = newValue.intValue();
            if (offset > 250)
                gameRoot.setLayoutX(-(offset - 250));
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());//создается сцена

        /*Image image = new Image(getClass().getResourceAsStream("bg.png"));
        ImageView img = new ImageView(image);
        img.setFitWidth(600);
        img.setFitHeight(600);
        Label labelimg = new Label();
        labelimg.setGraphic(img);
        gameRoot.getChildren().addAll(labelimg);*/

        primaryStage.setResizable(false);
        primaryStage.setTitle("Flappy Bird (IITU)");
        //scene.getStylesheets().add("C:\\Users\\Saken\\IdeaProjects\\JavaFX Final Project\\src\\styles\\style.css");
        scoreLabel.setFont(new Font("Century Gothic", 30));
        scoreLabel.setTranslateX(10);
        scoreLabel.setTranslateY(5);

        gameOverLabel.setFont(new Font("Cooper", 35));
        gameOverLabel.setTextFill(Color.BROWN);
        gameOverLabel.setLayoutY(300);
        gameOverLabel.setLayoutX(250);

        Image image = new Image(new FileInputStream("src\\styles\\bg2.jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        /*Image imageGif = new Image(new FileInputStream("C:\\Users\\Saken\\Downloads\\df.gif"));
        ImageView imageViewGif = new ImageView(imageGif);
        imageViewGif.setFitHeight(38);
        imageViewGif.setFitWidth(600);
        imageViewGif.setTranslateY(600);*/

        appRoot.setBackground(background);
        appRoot.getChildren().addAll(scoreLabel);
        primaryStage.getIcons().add(new Image(new FileInputStream("src\\styles\\birdOrig.png")));

        scene.setOnMouseClicked(event -> gameOverLabel.setVisible(false));
        scene.setOnMouseClicked(event -> bird.jump()); //при клике будет прыжок птички
        primaryStage.setScene(scene); //устанавливаем на наше окно
        primaryStage.show();


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(bird.gameOver) {
                    update();
                    return;
                }
                //if(!bird.gameOver)
                    update();
                /*else {
                    appRoot.getChildren().addAll(gameOverLabel);
                    timer.stop();
                }*/
            }
        };
        if(bird.gameOver) {
            appRoot.getChildren().addAll(gameOverLabel);
            timer.stop();
        }
        else
            timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
