package pl.vivaldi.matrix.app;


public class MainApp {
    public static void main(String[] args) {
        System.out.println("Matrices");

        MatrixController matrixController = new MatrixController();
        matrixController.operationsLoop();

    }
}