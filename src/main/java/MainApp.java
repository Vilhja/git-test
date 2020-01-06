import pl.vivaldi.matrix.model.Matrix;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Matrices");

        Matrix matrix = new Matrix(4,4);
        matrix.fillMatrix();
        matrix.printMatrix();
    }
}