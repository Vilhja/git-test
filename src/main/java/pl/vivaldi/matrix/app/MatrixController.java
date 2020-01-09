package pl.vivaldi.matrix.app;

import pl.vivaldi.matrix.io.ConsolePrinter;
import pl.vivaldi.matrix.io.DataReader;
import pl.vivaldi.matrix.model.Matrix;
import pl.vivaldi.matrix.operation.MatrixOperations;
import pl.vivaldi.matrix.operation.MatrixRowOperations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;


public class MatrixController {
    private ConsolePrinter printer = new ConsolePrinter();
    private Random generator = new Random();
    private DataReader dataReader = new DataReader(printer);

    private Matrix matrix;

    public MatrixController() {
        matrix = new Matrix();
    }

    public void operationsLoop() {

        Option option;
        do {
            printOptions();
            option = getOption();

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
                case ADDITION:
                    add();
                    break;
                case SUBMATRIX:
                    createSubMatrix();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLn("No such option!");
            }
        } while (option != Option.EXIT);
    }

    private Option getOption() {
        boolean isOptionChosen = false;
        Option option = null;
        while (!isOptionChosen) {
            try {
                option = Option.getOption(dataReader.getInt());
                isOptionChosen = true;
            } catch (InputMismatchException e) {
                printer.printErr("Wrong data type, please insert the number:");
            } catch (NoSuchElementException e) {
                printer.printErr(e.getMessage());
            }
        }
        return option;
    }

    private void printOptions() {
        printer.printLn("Choose option:");
        for (Option option : Option.values()) {
            printer.printOption(option.toString());
        }
    }

    private enum Option {
        EXIT(0, "exit program"),
        INTEGER_MATRIX(1, "fill matrix with random integers"),
        DOUBLE_MATRIX(2, "fill matrix with random doubles"),
        PRINT_MATRIX(3, "print matrix"),
        ROW_ADDITION(4, "add rows in matrix"),
        ROW_MULTIPLICATION(5, "multiply row per non-zero constant"),
        ROW_SWITCHING(6, "switch rows"),
        CREATE_MATRIX(7, "insert values to matrix"),
        TRANSPOSITION(8, "transpose matrix"),
        MULTIPLICATION(9, "multiply matrices"),
        ADDITION(10, "add matrices"),
        SUBMATRIX(11, "submatrix");

        private final int value;
        private final String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option getOption(int value) {
            try {
                return Option.values()[value];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException("No such option id: " + value);
            }
        }
    }

    private void exit() {
        printer.printLn("The end has come!!!");
    }

    private void fillMatrixWithRandomValues(NumberType numberType) {
        matrix = dataReader.setMatrixSize();
        printer.printLn("Set the range of randomizing [a,b]:");
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
                        printer.printLn("set precision:");
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

    private void printMatrix() {
        printer.printMatrix(matrix);
    }

    private void addRows() {
        printer.printLn("Which row you want to modify?");
        int row = dataReader.getInt();
        printer.printLn("Which row you want to add to that row?");
        int rowToAdd = dataReader.getInt();
        printer.printLn("What is the value, you want to multiply by?");
        double nonZeroConstant = dataReader.getDoubleFromString();
        MatrixRowOperations.rowAddition(matrix, row, rowToAdd, nonZeroConstant);
    }

    private void multiplyRow() {
        printer.printLn("Which row, you want to multiply?");
        int row = dataReader.getInt();
        printer.printLn("What is the value, you want to multiply by?");
        double nonZeroConstant = dataReader.getDoubleFromString();
        MatrixRowOperations.rowMultiplication(matrix, row, nonZeroConstant);
    }

    private void switchRows() {
        printer.printLn("Which rows , you want to switch?");
        printer.printLn("Row:");
        int row1 = dataReader.getInt();
        printer.printLn("with row:");
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

    private void add() {
        Matrix matrixA = dataReader.createMatrix();
        Matrix matrixB = dataReader.createMatrix();
        matrix = MatrixOperations.addition(matrixA, matrixB);
    }

    private void createSubMatrix() {
        printer.printLn("Which row, you want to remove?");
        int rowToRemove = dataReader.getInt();
        printer.printLn("Which column, you want to remove?");
        int columnToRemove = dataReader.getInt();
        matrix = MatrixOperations.subMatrix(this.matrix, rowToRemove, columnToRemove);
    }
}