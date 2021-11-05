package sample;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

//создание птички
public class Bird extends Pane {
    public Point2D velocity;
    Image image;
    public boolean gameOver = false;



    public Bird() throws FileNotFoundException {
        image = new Image(new FileInputStream("src\\styles\\bird.png"));
        ImageView imgv = new ImageView(image);
        velocity = new Point2D(0, 0);
        setTranslateX(100);
        setTranslateY(300);
        getChildren().addAll(imgv);
    }

    public void moveY(int value) {
        boolean moveDown;
        if (value > 0) { //если value меньше нуля, движемся на вверх. Если нет то вниз
            moveDown = true;
            /*RotateDown birdAnim = new RotateDown(Bird.this);
            birdAnim.playAnim();*/
        }
        else {
            moveDown = false;
            /*RotateUp birdAnim = new RotateUp(Bird.this);
            birdAnim.playAnim();*/
        }

        for (int i = 0; i < Math.abs(value); i++) {
            for (Wall w : FlappyBird.walls) {//проверка на столкновения
                if (this.getBoundsInParent().intersects(w.getBoundsInParent())) {
                    if (moveDown) {
                        gameOver=true;
                        setTranslateY(getTranslateY() - 0);
                        return;
                    } else {
                        gameOver=true;
                        setTranslateY(getTranslateY() + 0);
                        return;
                    }
                }
            }

            if (getTranslateY() < 0)
                setTranslateY(0);
            if (getTranslateY() > 570)
                setTranslateY(570);

            //гравитация
            setTranslateY(getTranslateY() + (moveDown ? 1 : -1));//гравитация
        }
    }

    public void moveX(int value) {
        for (int i = 0; i < value; i++) {
            setTranslateX(getTranslateX() + 1);
            for (Wall w : FlappyBird.walls) {
                if (this.getBoundsInParent().intersects(w.getBoundsInParent())) {
                    if (getTranslateX() + 30 == w.getTranslateX()) {
                        gameOver = true;
                        setTranslateX(getTranslateX() - 1);
                        return;
                    }
                }
                if(getTranslateX()==w.getTranslateX()){
                    FlappyBird.score+=10;
                    return;
                }
            }
        }
    }

    public void jump() {
        velocity = new Point2D(2, -13);
    }

}