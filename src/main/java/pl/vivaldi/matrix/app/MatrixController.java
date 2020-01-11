package pl.vivaldi.matrix.app;

import pl.vivaldi.matrix.exception.NonZeroConstantException;
import pl.vivaldi.matrix.io.ConsolePrinter;
import pl.vivaldi.matrix.io.DataReader;
import pl.vivaldi.matrix.io.file.FileManager;
import pl.vivaldi.matrix.model.Matrix;
import pl.vivaldi.matrix.operation.MatrixOperations;
import pl.vivaldi.matrix.operation.MatrixRowOperations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;


public class MatrixController {
    private final static String USER_MATRICES_DIRECTORY_PATH = "src/main/resources/matrices/user/";
    private final static String TEST_MATRICES_DIRECTORY_PATH = "src/main/resources/matrices/test/";

    private ConsolePrinter printer = new ConsolePrinter();
    private Random generator = new Random();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager = new FileManager(printer);

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
                case SAVE_TO_FILE:
                    saveMatrixToFile();
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
        CREATE_MATRIX(7, "create matrix (inserting or file loading)"),
        TRANSPOSITION(8, "transpose matrix"),
        MULTIPLICATION(9, "multiply matrices"),
        ADDITION(10, "add matrices"),
        SUBMATRIX(11, "submatrix"),
        SAVE_TO_FILE(12, "save matrix to file");

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
        try {
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
        } catch (InputMismatchException e) {
            printer.printErr("Wrong type of data!");
        } catch (NegativeArraySizeException e) {
            printer.printErr("Matrix cant has negative size!");
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

    private double insertNonZeroConstant() {
        double nonZeroConstant = dataReader.getDoubleFromString();
        if (nonZeroConstant == 0) {
            throw new NonZeroConstantException("Illegal operation! Constant cannot be zero!");
        }
        return nonZeroConstant;
    }

    private enum NumberType {
        INTEGER, DOUBLE
    }

    private void printMatrix() {
        printer.printMatrix(matrix);
    }

    private void addRows() {
        try {
            printer.printLn("Which row you want to modify?");
            int row = dataReader.getInt();
            printer.printLn("Which row you want to add to that row?");
            int rowToAdd = dataReader.getInt();
            printer.printLn("What is the value, you want to multiply by?");
            double nonZeroConstant = insertNonZeroConstant();
            MatrixRowOperations.rowAddition(matrix, row, rowToAdd, nonZeroConstant);
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printErr("Inserted row number doesn't exist!");
        } catch (InputMismatchException e) {
            printer.printErr("Wrong type of data!");
        } catch (NonZeroConstantException e) {
            printer.printErr(e.getMessage());
        }
    }

    private void multiplyRow() {
        try {
            printer.printLn("Which row, you want to multiply?");
            int row = dataReader.getInt();
            printer.printLn("What is the value, you want to multiply by?");
            double nonZeroConstant = insertNonZeroConstant();
            MatrixRowOperations.rowMultiplication(matrix, row, nonZeroConstant);
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printErr("Inserted row number doesn't exist!");
        } catch (InputMismatchException e) {
            printer.printErr("Wrong type of data!");
        } catch (NonZeroConstantException e) {
            printer.printErr(e.getMessage());
        }


    }

    private void switchRows() {
        try {
            printer.printLn("Which rows , you want to switch?");
            printer.printLn("Row:");
            int row1 = dataReader.getInt();
            printer.printLn("with row:");
            int row2 = dataReader.getInt();
            MatrixRowOperations.rowSwitching(matrix, row1, row2);

        } catch (InputMismatchException e) {
            printer.printErr("Wrong type of data!");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printErr("Inserted row or column number doesn't exist!");
        }
    }

    private void createMatrix() {
        createMatrixOptional().ifPresent(value -> matrix = value);
    }

    private Matrix createMatrixFromOptional() {
        createMatrix();
        return matrix;
    }

    private Optional<Matrix> createMatrixOptional() {
        try {
            int option = chooseCreatingOption();
            switch (option) {
                case 1:
                    return dataReader.createMatrix();
                case 2:
                    int loadOption = chooseLoadingOption();
                    switch (loadOption) {
                        case 1:
                            return loadMatrixFromFile(TEST_MATRICES_DIRECTORY_PATH);
                        case 2:
                            return loadMatrixFromFile(USER_MATRICES_DIRECTORY_PATH);
                        default:
                            printer.printErr("No such loading option!");
                    }
                    break;
                default:
                    printer.printErr("No such creating option!");

            }
        } catch (InputMismatchException e) {
            printer.printErr("Wrong type of data!");
        } catch (NumberFormatException e) {
            printer.printErr("Inserted data is not a number!");
        } catch (NegativeArraySizeException e) {
            printer.printErr("Matrix cant has negative size!");
        }
        return Optional.empty();
    }

    private int chooseCreatingOption() {
        printer.printLn("Create matrix by:");
        printer.printLn("1 - inserting values");
        printer.printLn("2 - loading from file");
        return dataReader.getInt();
    }

    private void transpose() {
        matrix = MatrixOperations.transposition(matrix);
    }

    private void multiply() {
        Matrix matrixA = createMatrixFromOptional();
        Matrix matrixB = createMatrixFromOptional();
        MatrixOperations.multiplication(matrixA, matrixB).ifPresentOrElse(
                value -> matrix = value, () -> {
                    printer.printErr("Matrices cannot be multiplied!");
                });
    }

    private void add() {
        Matrix matrixA = createMatrixFromOptional();
        Matrix matrixB = createMatrixFromOptional();
        MatrixOperations.addition(matrixA, matrixB).ifPresentOrElse(
                value -> matrix = value,
                () -> {
                    printer.printErr("Matrices cannot be added!");
                });
    }

    private void createSubMatrix() {
        int rowToRemove = 0;
        int columnToRemove = 0;
        try {
            printer.printLn("Which row, you want to remove?");
            rowToRemove = dataReader.getInt();
            printer.printLn("Which column, you want to remove?");
            columnToRemove = dataReader.getInt();
        } catch (InputMismatchException e) {
            printer.printErr("Wrong type of data!");
        }
        MatrixOperations.subMatrix(matrix, rowToRemove, columnToRemove).ifPresentOrElse(
                value -> matrix = value,
                () -> {
                    printer.printErr("Cannot create submatrix of matrix!");
                });
    }

    private void saveMatrixToFile() {
        String fileName = "src/main/resources/matrices/user/";
        printer.printLn("Insert filename:");
        fileName += dataReader.getString();
        fileName += ".txt";
        fileManager.saveMatrixToFile(matrix, fileName);
    }

    private Optional<Matrix> loadMatrixFromFile(String fileName) {
        printer.printLn("Insert filename:");
        fileName += dataReader.getString();
        fileName += ".txt";
        return fileManager.loadMatrixFromFile(fileName);
    }

    private int chooseLoadingOption() {
        printer.printLn("Load matrix from:");
        printer.printLn("1 - test directory");
        printer.printLn("2 - user directory");
        return dataReader.getInt();
    }
}