package sample;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Controller {

    public TextArea enter;
    public TextArea exit;
//    private boolean[] brain = new boolean[10_000_000];
    private Stage stage;
    private Words words = new Words();

    private ChangeListener<String> changeListener = (observable, oldValue, newValue) -> change();

    public void init() {
        enter.textProperty().addListener(changeListener);
        words.init();

//        float[] inputs = {-1, -0.5f};
//        Perceptron p = new Perceptron(inputs.length);
//        int output = p.guess(inputs);
//        System.out.println(output);
//        Point2D pt = new Point2D(1, 1);
    }


    private void change() {
        String text = this.enter.textProperty().getValue();
        exit.textProperty().setValue(words.parseText(text));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
