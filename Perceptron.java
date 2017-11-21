package sample;

import javafx.scene.shape.Circle;

public class Perceptron {

    private final int RADIUS = 6;
    private float[] weights;
    private Circle circle = new Circle();

    public Perceptron(float[] weights) {
        this.weights = weights;
    }

    public Perceptron(int n) {
        this.weights = new float[n];
    }

    public Perceptron(int x, int y) {
        this.circle = new Circle(x, y, RADIUS);
    }

    public void initWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = (float) (Math.random() - 0.5) * 2;
        }
    }

    public int guess(float[] inputs) {
        float sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += inputs[i] * weights[i];
        }
        return sign(sum);
    }
    // The activation function
    private int sign(float n) {
        return n > 0 ? 1 : -1;
    }
}
