package pl.vivaldi.matrix.solver;

import pl.vivaldi.matrix.model.Matrix;
import pl.vivaldi.matrix.operation.MatrixRowOperations;

public class AutomaticMatrixSolver {

    public Matrix solve(Matrix matrix) {
        for (int i = 0; i < matrix.getRowNumber(); i++) {
            double diagonalElement = matrix.getMatrixElement(i, i);
            if (diagonalElement != 1) {
                if (i != matrix.getRowNumber() - 1 && diagonalElement == 0) {
                    MatrixRowOperations.rowSwitching(matrix, i, i + 1);
                }
                makeOne(matrix, i);
            }

            makeOne(matrix, i);
            makeZeroes(matrix, i);
        }
        return matrix;
    }

    private void makeOne(Matrix matrix, int row) {
        double matrixElement = matrix.getMatrixElement(row, row);
        double multiplier = findMultiplierToMakeOne(matrixElement);
        MatrixRowOperations.rowMultiplication(matrix, row, multiplier);
    }

    private void makeZeroes(Matrix matrix, int row) {
        for (int i = 0; i < matrix.getRowNumber(); i++) {
            if (i != row) {
                double matrixElement = matrix.getMatrixElement(i, row);
                double multiplier = findMultiplierToMakeZeroes(matrixElement);
                MatrixRowOperations.rowAddition(matrix, i, row, multiplier);
            }
        }
    }

    private double findMultiplierToMakeOne(double matrixElement) {
        return 1 / matrixElement;
    }

    private double findMultiplierToMakeZeroes(double matrixElement) {
        return -matrixElement;
    }
}
