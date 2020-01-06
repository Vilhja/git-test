package pl.vivaldi.matrix.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;


public class Matrix {
    private int rowNumber;
    private int columnNumber;
    private double[][] matrixFields;

    private Random random = new Random();

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
                matrixFields[i][j] = generateRandomDouble(-4, 10, 1);
            }
        }
    }

    public void printMatrix() {
        for (int i = 0; i < matrixFields.length; i++) {
            for (int j = 0; j < matrixFields.length; j++) {
                if (j == 0) {
                    System.out.print("[ ");
                }
                System.out.print(matrixFields[i][j]);
                if (j != matrixFields.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println(" ]");
        }
    }

    private double generateRandomDouble(int minValue, int maxValue, int precision) {
        double value = minValue + (maxValue - minValue) * random.nextDouble();
        return BigDecimal.valueOf(value)
                .setScale(precision, RoundingMode.HALF_UP).doubleValue();
    }
}