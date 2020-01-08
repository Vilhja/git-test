package pl.vivaldi.matrix.operation;

import pl.vivaldi.matrix.model.Matrix;

public class MatrixOperations {
    public static Matrix addition(Matrix matrixA, Matrix matrixB) {
        if (checkAdditionCondition(matrixA, matrixB)) {
            Matrix resultMatrix = new Matrix(matrixA.getRowNumber(), matrixA.getColumnNumber());
            for (int i = 0; i < resultMatrix.getRowNumber(); i++) {
                for (int j = 0; j < resultMatrix.getColumnNumber(); j++) {
                    double valueA = matrixA.getMatrixElement(i, j);
                    double valueB = matrixB.getMatrixElement(i, j);
                    resultMatrix.setMatrixElement(i, j, valueA + valueB);
                }
            }
            return resultMatrix;
        }
        //optional needed
        return null;
    }

    private static boolean checkAdditionCondition(Matrix matrixA, Matrix matrixB) {
        if (matrixA.getRowNumber() != matrixB.getRowNumber()) return false;
        return matrixA.getColumnNumber() == matrixB.getColumnNumber();
    }

    public static void scalarMultiplication(Matrix matrix, double nonZeroConstant) {
        for (int i = 0; i < matrix.getRowNumber(); i++) {
            MatrixRowOperations.rowMultiplication(matrix, i, nonZeroConstant);
        }
    }

    public static Matrix transposition(Matrix matrix) {
        Matrix transposedMatrix = new Matrix(matrix.getColumnNumber(), matrix.getRowNumber());
        for (int i = 0; i < transposedMatrix.getRowNumber(); i++) {
            for (int j = 0; j < transposedMatrix.getColumnNumber(); j++) {
                double value = matrix.getMatrixElement(j, i);
                transposedMatrix.setMatrixElement(i, j, value);
            }
        }
        return transposedMatrix;
    }
}
