package pl.vivaldi.matrix.io;

import pl.vivaldi.matrix.model.Matrix;

import java.util.Optional;
import java.util.Scanner;

public class DataReader {
    ConsolePrinter printer;
    Scanner scanner = new Scanner(System.in);

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public int getInt() {
        try {
            return scanner.nextInt();
        } finally {
            scanner.nextLine();
        }
    }

    public double getDouble() {
        try {
            return scanner.nextDouble();
        } finally {
            scanner.nextLine();
        }
    }

    public double getDoubleFromString() {
        String doubleValue = scanner.nextLine();
        String[] split = doubleValue.split("/");
        if (split.length == 1) {
            return Double.parseDouble(split[0]);
        }
        double numerator = Double.parseDouble(split[0]);
        double denominator = Double.parseDouble(split[1]);
        return numerator / denominator;
    }

    public String getString() {
        return scanner.nextLine();
    }

    public Optional<Matrix> createMatrix() {
        Matrix matrix = setMatrixSize();
        for (int i = 0; i < matrix.getRowNumber(); i++) {
            for (int j = 0; j < matrix.getColumnNumber(); j++) {
                printer.printLn("Insert element M[" + i + "," + j + "]:");
                matrix.setMatrixElement(i, j, getDoubleFromString());
            }
        }
        return Optional.of(matrix);
    }

    public Matrix setMatrixSize() {
        printer.printLn("Insert row number:");
        int rowNumber = getInt();
        printer.printLn("Insert column number:");
        int columnNumber = getInt();
        return new Matrix(rowNumber, columnNumber);
    }
}
