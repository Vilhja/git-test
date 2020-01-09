package pl.vivaldi.matrix.io.file;

import pl.vivaldi.matrix.io.ConsolePrinter;
import pl.vivaldi.matrix.io.DataReader;
import pl.vivaldi.matrix.model.Matrix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    private DataReader dataReader;
    private ConsolePrinter printer;

    public FileManager(DataReader dataReader, ConsolePrinter printer) {
        this.dataReader = dataReader;
        this.printer = printer;
    }

    public Matrix loadMatrixFromFile(String fileName) {
        return null;
    }

    public void saveMatrixToFile(Matrix matrix, String fileName) {
        try (
                FileWriter fileWriter = new FileWriter(fileName);
                BufferedWriter bufferedReader = new BufferedWriter(fileWriter)
        ) {
            for (int i = 0; i < matrix.getRowNumber(); i++) {
                for (int j = 0; j < matrix.getColumnNumber(); j++) {
                    double value = matrix.getMatrixElement(i, j);
                    bufferedReader.write(value + "");
                    if (j != matrix.getRowNumber() - 1) bufferedReader.write(" ");
                }
                if (i != matrix.getRowNumber() - 1) bufferedReader.newLine();
            }
        } catch (IOException e) {
            printer.printErr("Cannot write matrix to file " + fileName);
        }
    }
}
