package pl.vivaldi.matrix.app;

import pl.vivaldi.matrix.model.Matrix;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Matrices");

        Matrix matrix = new Matrix(4,3);
        MatrixController matrixController = new MatrixController(matrix);

        System.out.println();
        matrixController.operationsLoop();
    }
}