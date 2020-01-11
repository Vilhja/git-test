package pl.vivaldi.matrix.model;

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

    public double getMatrixElement(int row, int column) {
        return matrixFields[row][column];
    }

    public void setMatrixElement(int row, int column, double value) {
        matrixFields[row][column] = value;
    }
}