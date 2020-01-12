package pl.vivaldi.matrix.io.file;

import pl.vivaldi.matrix.io.ConsolePrinter;
import pl.vivaldi.matrix.model.Matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileManager {
    private ConsolePrinter printer;

    public FileManager(ConsolePrinter printer) {
        this.printer = printer;
    }

    public Optional<Matrix> loadMatrixFromFile(String fileName) {
        List<String> fileLines = loadFileLines(fileName);
        return createMatrixFromFileLines(fileLines);
    }

    private Optional<Matrix> createMatrixFromFileLines(List<String> fileLines) {
        if (!fileLines.isEmpty()) {
            List<List<String>> matrixRows = new ArrayList<>();
            for (String fileLine : fileLines) {
                String[] split = fileLine.split(" ");
                matrixRows.add(Arrays.asList(split));
            }
            int rowNumber = matrixRows.size(),
                    columnNumber = matrixRows.get(0).size();
            Matrix matrix = new Matrix(rowNumber, columnNumber);
            for (int i = 0; i < rowNumber; i++) {
                for (int j = 0; j < columnNumber; j++) {
                    double value = Double.parseDouble(matrixRows.get(i).get(j));
                    matrix.setMatrixElement(i, j, value);
                }
            }
            return Optional.of(matrix);
        }
        return Optional.empty();
    }

    private List<String> loadFileLines(String fileName) {
        List<String> fileLines = new ArrayList<>();
        try (
                FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader)
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
                    if (j != matrix.getRowNumber()) bufferedReader.write(" ");
                }
                if (i != matrix.getRowNumber() - 1) bufferedReader.newLine();
            }
        } catch (IOException e) {
            printer.printErr("Cannot write matrix to file " + fileName);
        }
    }
}