package pl.vivaldi.matrix.app;

import pl.vivaldi.matrix.io.ConsolePrinter;
import pl.vivaldi.matrix.model.Matrix;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.Scanner;

public class MatrixController {
    private final static int INTEGER_MATRIX = 1;
    private final static int DOUBLE_MATRIX = 2;
    private final static int PRINT_MATRIX = 3;
    private final static int EXIT = 0;

    private ConsolePrinter printer = new ConsolePrinter();
    private Random generator = new Random();

    private Matrix matrix;

    public void operationsLoop() {

        int option;
        do {
            System.out.println("Wybierz: ");
            System.out.println("1 - int , 2 - double, 3 - print");
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

    public MatrixController(Matrix matrix) {
        this.matrix = matrix;
    }

    public void printMatrix() {
        printer.printMatrix(matrix);
    }

    private void fillMatrixWithRandomValues(NumberType numberType) {
        for (int i = 0; i < matrix.getRowNumber(); i++) {
            for (int j = 0; j < matrix.getColumnNumber(); j++) {
                double value;
                if (numberType == NumberType.DOUBLE) {
                    value = generateRandomDouble(-5, 5, 1);
                } else {
                    value = generateRandomInteger(-5, 5);
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

}
