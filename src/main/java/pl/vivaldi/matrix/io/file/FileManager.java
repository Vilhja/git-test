package pl.vivaldi.matrix.io.file;

import pl.vivaldi.matrix.io.ConsolePrinter;
import pl.vivaldi.matrix.io.DataReader;
import pl.vivaldi.matrix.model.Matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private DataReader dataReader;
    private ConsolePrinter printer;

    public FileManager(DataReader dataReader, ConsolePrinter printer) {
        this.dataReader = dataReader;
        this.printer = printer;
    }

    public Matrix loadMatrixFromFile(String fileName) {
        List<String> fileLines = loadFileLines(fileName);
        return createMatrixFromFileLines(fileLines);
    }

    private Matrix createMatrixFromFileLines(List<String> fileLines) {
        List<String[]> matrixRows = new ArrayList<>();
        for (String fileLine : fileLines) {
            String[] split = fileLine.split(" ");
            matrixRows.add(split);
        }
        int rowNumber = matrixRows.size(),
                columnNumber = matrixRows.get(0).length;
        Matrix matrix = new Matrix(rowNumber, columnNumber);
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                double value = Double.parseDouble(matrixRows.get(i)[j]);
                matrix.setMatrixElement(i, j, value);
            }
        }
        return matrix;
    }

    private List<String> loadFileLines(String fileName) {
        List<String> fileLines = new ArrayList<>();
        try (
                FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {

            String line = bufferedReader.readLine();
            while (line != null) {
                fileLines.add(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            printer.printErr("File " + fileName + " not found");
        } catch (IOException e) {
            printer.printErr("Cannot load matrix form file " + fileName);
        }
        return fileLines;
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