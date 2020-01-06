package pl.vivaldi.matrix.model;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

public class Matrix {
    private int rowNumber;
    private int columnNumber;
    private double[][] matrixFields;

    public Matrix() {
        this(1, 1);
    }

    public Matrix(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        matrixFields = new double[rowNumber][columnNumber];
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public double[][] getMatrixFields() {
        return matrixFields;
    }

    public double getElement(int row, int column) {
        return matrixFields[row][column];
    }

    public void fillMatrix() {
        Random random = new Random();
        for (int i = 0; i < matrixFields.length; i++) {
            for (int j = 0; j < matrixFields.length; j++) {
                double d = -2 + (4 +2) * random.nextDouble();
                BigDecimal decimal = BigDecimal.valueOf(d);
                matrixFields[i][j] =decimal.setScale(1, RoundingMode.HALF_UP).doubleValue();
            }
        }
    }

    public void printMatrix() {
        for (int i = 0; i < matrixFields.length; i++) {
            for (int j = 0; j < matrixFields.length; j++) {
                if (j == 0) {
                    System.out.print("[ ");
                }
                System.out.print(matrixFields[i][j] + " ");
                if (j == matrixFields.length - 1) {
                    System.out.print(" ]");
                }
            }
            System.out.println();
        }
    }
}