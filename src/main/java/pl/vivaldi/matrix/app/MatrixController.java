package pl.vivaldi.matrix.app;

import pl.vivaldi.matrix.io.ConsolePrinter;
import pl.vivaldi.matrix.io.DataReader;
import pl.vivaldi.matrix.model.Matrix;
import pl.vivaldi.matrix.operation.MatrixOperations;
import pl.vivaldi.matrix.operation.MatrixRowOperations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.Scanner;

public class MatrixController {
    private final static int INTEGER_MATRIX = 1;
    private final static int DOUBLE_MATRIX = 2;
    private final static int PRINT_MATRIX = 3;
    private final static int ROW_ADDITION = 4;
    private final static int ROW_MULTIPLICATION = 5;
    private final static int ROW_SWITCHING = 6;
    private final static int CREATE_MATRIX = 7;
    private final static int TRANSPOSITION = 8;
    private final static int MULTIPLICATION = 9;
    private final static int SUBMATRIX = 10;
    private final static int EXIT = 0;

    private ConsolePrinter printer = new ConsolePrinter();
    private Random generator = new Random();
    private DataReader dataReader = new DataReader(printer);

    private Matrix matrix;

    public MatrixController() {
        matrix = new Matrix();
    }

    public void operationsLoop() {

        int option;
        do {
            System.out.println("Wybierz: ");
            System.out.println("1 - int , 2 - double, 3 - print , 4 - add");
            System.out.println("5 - multi, 6 - switch, 7 - create matrix, 8 - transpose 0 exit");
            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case INTEGER_MATRIX:
                    fillMatrixWithRandomValues(NumberType.INTEGER);
                    break;
                case DOUBLE_MATRIX:
                    fillMatrixWithRandomValues(NumberType.DOUBLE);
                    break;
                case PRINT_MATRIX:
                    printMatrix();
                    break;
                case ROW_ADDITION:
                    addRows();
                    break;
                case ROW_MULTIPLICATION:
                    multiplyRow();
                    break;
                case ROW_SWITCHING:
                    switchRows();
                    break;
                case CREATE_MATRIX:
                    createMatrix();
                    break;
                case TRANSPOSITION:
                    transpose();
                    break;
                case MULTIPLICATION:
                    multiply();
                    break;
                case SUBMATRIX:
                    createSubMatrix();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLn("Nie ma podanej opcji!");
            }
        } while (option != EXIT);
    }

    private void exit() {
        printer.printLn("Koniec!!!");
    }

    private void printMatrix() {
        printer.printMatrix(matrix);
    }

    private void fillMatrixWithRandomValues(NumberType numberType) {
        printer.printLn("Podaj przedział generowanie elementów [a,b]:");
        printer.printLn("a:");
        int minValue = dataReader.getInt();
        printer.printLn("b:");
        int maxValue = dataReader.getInt();

        for (int i = 0; i < matrix.getRowNumber(); i++) {
            for (int j = 0; j < matrix.getColumnNumber(); j++) {
                double value;
                if (numberType == NumberType.DOUBLE) {
                    int precision = 1;
                    if (i == 0 && j == 0) {
                        printer.printLn("Ustaw precyzję:");
                        precision = dataReader.getInt();
                    }
                    value = generateRandomDouble(minValue, maxValue, precision);
                } else {
                    value = generateRandomInteger(minValue, maxValue);
                }
                matrix.setMatrixElement(i, j, value);
            }
        }
    }

    private double generateRandomDouble(int minValue, int maxValue, int precision) {
        double value = minValue + (maxValue - minValue) * generator.nextDouble();
        return BigDecimal.valueOf(value)
                .setScale(precision, RoundingMode.HALF_UP).doubleValue();
    }

    private int generateRandomInteger(int minValue, int maxValue) {
        return generator.nextInt(maxValue - minValue) + minValue;
    }

    private enum NumberType {
        INTEGER, DOUBLE
    }

    private void addRows() {
        printer.printLn("Podaj wiersz do zmodyfikowania:");
        int row = dataReader.getInt();
        printer.printLn("Który wiersz chcesz dodać:");
        int rowToAdd = dataReader.getInt();
        printer.printLn("i przez jaką wartość chcesz go przemnożyć:");
        double nonZeroConstant = dataReader.getDoubleFromString();
        MatrixRowOperations.rowAddition(matrix, row, rowToAdd, nonZeroConstant);
    }

    private void multiplyRow() {
        printer.printLn("Który wiersz chcesz pomnożyć?");
        int row = dataReader.getInt();
        printer.printLn("i przez jaką wartość?");
        double nonZeroConstant = dataReader.getDoubleFromString();
        MatrixRowOperations.rowMultiplication(matrix, row, nonZeroConstant);
    }

    private void switchRows() {
        printer.printLn("Które wiersze chcesz zamienić?");
        printer.printLn("Wiersz:");
        int row1 = dataReader.getInt();
        printer.printLn("z wierszem:");
        int row2 = dataReader.getInt();
        MatrixRowOperations.rowSwitching(matrix, row1, row2);
    }

    private void createMatrix() {
        matrix = dataReader.createMatrix();
    }

    private void transpose() {
        matrix = MatrixOperations.transposition(matrix);
    }

    private void multiply() {
        Matrix matrixA = dataReader.createMatrix();
        Matrix matrixB = dataReader.createMatrix();
        matrix = MatrixOperations.multiplication(matrixA, matrixB);
    }

    private void createSubMatrix() {
        printer.printLn("Który, wiersz usunąć?");
        int rowToRemove = dataReader.getInt();
        printer.printLn("Którą kolumnę usunąć?");
        int columnToRemove = dataReader.getInt();
        matrix = MatrixOperations.subMatrix(this.matrix, rowToRemove, columnToRemove);
    }
}