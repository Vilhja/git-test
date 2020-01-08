package pl.vivaldi.matrix.operation;

import pl.vivaldi.matrix.model.Matrix;

public class MatrixRowOperations {
    public static void rowAddition(Matrix matrix, int row, int rowToAdd, double noZeroConstant) {
        if (noZeroConstant != 0) {
            for (int i = 0; i < matrix.getColumnNumber(); i++) {
                double rowValue = matrix.getMatrixElement(row, i);
                double rowToAddValue = matrix.getMatrixElement(rowToAdd, i) * noZeroConstant;
                matrix.setMatrixElement(row, i, rowValue + rowToAddValue);
            }
        }
    }

    public static void rowMultiplication(Matrix matrix, int row, double nonZeroConstant) {
        if (nonZeroConstant != 0) {
            for (int i = 0; i < matrix.getColumnNumber(); i++) {
                double value = matrix.getMatrixElement(row, i) * nonZeroConstant;
                matrix.setMatrixElement(row, i, value);
            }
        }
    }

    public static void rowSwitching(Matrix matrix, int row1, int row2) {
        for (int i = 0; i < matrix.getColumnNumber(); i++) {
            double firstRowValue = matrix.getMatrixElement(row1, i);
            double secondRowValue = matrix.getMatrixElement(row2, i);
            matrix.setMatrixElement(row1, i, secondRowValue);
            matrix.setMatrixElement(row2, i, firstRowValue);
        }
    }
}
