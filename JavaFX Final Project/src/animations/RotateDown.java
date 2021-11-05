package animations;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;
import sample.Bird;

public class RotateDown {
    private RotateTransition rt;
    public RotateDown(Node node) {
        rt = new RotateTransition(Duration.millis(90), node);
        rt.setToAngle(90);
        rt.play();
    }
    public void playAnim() {
        rt.playFromStart();
    }
}
