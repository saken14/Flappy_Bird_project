package animations;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class RotateUp {
    private RotateTransition rt;
    public RotateUp(Node node) {
        rt = new RotateTransition(Duration.millis(1), node);
        rt.setToAngle(-40);
        rt.play();
    }
    public void playAnim() {
        rt.playFromStart();
    }
}
