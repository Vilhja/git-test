package pl.vivaldi.matrix.io;

import pl.vivaldi.matrix.model.Matrix;

public class ConsolePrinter {
    public void printLn(String text) {
        System.out.println(text);
    }

    public void print(String text) {
        System.out.print(text);
    }

    public void printMatrix(Matrix matrix) {
        double[][] matrixFields = matrix.getMatrixFields();
        for (int i = 0; i < matrixFields.length; i++) {
            for (int j = 0; j < matrixFields[i].length; j++) {
                if (j == 0) {
                    print("[ ");
                }
                System.out.print(matrixFields[i][j]);
                if (j != matrixFields[i].length - 1) {
                    print(" ");
                }
            }
            printLn("]");
        }
    }
}
