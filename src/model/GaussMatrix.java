/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

import util.StateManager;

/**
 * @author Claudiu
 */
public class GaussMatrix {

    private int size;
    private ArrayList<ArrayList<Double>> aInit;
    private ArrayList<Double> bInit;
    private ArrayList<ArrayList<Double>> matrix;
    private ArrayList<ArrayList<Double>> independentB;
    private ArrayList<Double> bVector;
    private ArrayList<Double> solutions;
    private ArrayList<SwapRowPair> swaps;
    private double eps;
    private boolean singular;

    public GaussMatrix(int n) {
	size = n;
	matrix = new ArrayList<>();
	bVector = new ArrayList<>();
	swaps = new ArrayList<>();
	eps = Math.pow(10, -7);
	for (int i = 0; i < size; i++) {
	    bVector.add(null);
	    matrix.add(new ArrayList<Double>());
	    for (int j = 0; j < size; j++) {
		matrix.get(i).add(null);
	    }
	}
    }

    public void loadIndependentBVectorFromFile(String filePath) {
	independentB = new ArrayList<>();
	BufferedReader reader = null;
	try {
	    reader = new BufferedReader(new FileReader(filePath));
	    String line = null;

	    int index = 0;
	    while ((line = reader.readLine()) != null) {
		String bValues[] = line.split(" ");
		independentB.add(new ArrayList<Double>());
		for (String value : bValues) {
		    independentB.get(index).add(Double.parseDouble(value));
		}
		index++;
	    }

	} catch (IOException ex) {
	    System.out.println("Incarcarea fisierului a esuat!");
	} catch (NumberFormatException ex) {
	    System.out.println("Formatul fisierului este invalid!");
	} finally {
	    if (reader != null) {
		try {
		    reader.close();
		} catch (IOException ex) {
		    System.out.println("Eroare la inchiderea fisierului!");
		}
	    }
	}
    }

    public static GaussMatrix loadGaussMatrixFromFile(String filePath) {
	GaussMatrix matrix = null;
	BufferedReader reader = null;
	try {
	    reader = new BufferedReader(new FileReader(filePath));
	    String line = null;
	    line = reader.readLine();
	    int matrixSize = Integer.parseInt(line);
	    matrix = new GaussMatrix(matrixSize);
	    int index = 0;
	    while ((line = reader.readLine()) != null) {
		String matrixValues[] = line.split(" ");
		if (index < matrixSize) { // valorile matricii
		    for (int i = 0; i < matrixValues.length; i++) {
			matrix.getMatrix().get(index).set(i, Double.parseDouble(matrixValues[i]));

		    }
		} else { // am ajuns la valorile vectorului b
		    for (int i = 0; i < matrixValues.length; i++) {
			matrix.getbVector().set(i, Double.parseDouble(matrixValues[i]));
		    }
		}
		++index;
	    }
	    matrix.storeInitialData();
	} catch (IOException ex) {
	    System.out.println("Incarcarea fisierului a esuat!");
	} catch (NumberFormatException ex) {
	    System.out.println("Formatul fisierului este invalid!");
	} finally {
	    if (reader != null) {
		try {
		    reader.close();
		} catch (IOException ex) {
		    System.out.println("Eroare la inchiderea fisierului!");
		}
	    }
	}
	return matrix;
    }

    public static GaussMatrix generateRandomGaussMatrix(int size) {
	GaussMatrix gaussMatrix = new GaussMatrix(size);
	Random random = new Random();
	int range = 5 - (-5) + 1;

	for (int i = 0; i < size; i++) {
	    for (int j = 0; j < size; j++) {
		Double value = (double) random.nextInt(range) + (-5);
		gaussMatrix.getMatrix().get(i).set(j, value);
	    }
	}
	gaussMatrix.storeInitialData();
	return gaussMatrix;
    }

    public void storeInitialData() {
	// pastram datele initiale in aInit, respectiv bInit
	aInit = new ArrayList<>();
	bInit = new ArrayList<>();
	for (int i = 0; i < size; i++) {
	    aInit.add(new ArrayList<Double>());
	    bInit.add(bVector.get(i));
	    for (int j = 0; j < size; j++) {
		aInit.get(i).add(matrix.get(i).get(j));
	    }
	}
    }

    public void displayMatrix() {
	if (matrix != null) {
	    for (ArrayList<Double> row : matrix) {
		System.out.print(System.lineSeparator());
		for (Double item : row) {
		    System.out.print(item + " ");
		}
	    }
	    System.out.println(System.lineSeparator());
	} else {
	    System.out.println("Matricea este nula");
	}
    }

    public void displayBVector() {
	if (bVector != null) {
	    for (Double value : bVector) {
		System.out.print(value + " ");
	    }
	}
    }

    public boolean startGaussReduction() {
	int l = 0;
	SwapRowPair pair = searchPivot(l);
	ArrayList<Double> factors = new ArrayList<Double>();
	State initialState = new State(aInit, bInit, pair, factors);

	if (pair != null) {
	    swapRows(pair);
	}
	StateManager.addState(initialState);

	while (l < size - 1 && !(Math.abs(matrix.get(l).get(l)) < eps)) {
	    State newState = new State(matrix, bVector, pair, factors);
	    StateManager.addState(newState);
	    for (int i = l + 1; i < size; i++) {
		Double f = -matrix.get(i).get(l) / matrix.get(l).get(l);
		factors.add(f);
		for (int j = l + 1; j < size; j++) {
		    matrix.get(i).set(j, matrix.get(i).get(j) + f * matrix.get(l).get(j));
		}
		bVector.set(i, bVector.get(i) + f * bVector.get(l));
		matrix.get(i).set(l, 0.0);
	    }
	    newState.setFactors(factors);
	    factors = new ArrayList<Double>();

	    ++l;
	    pair = searchPivot(l);
	    if (pair != null) {
		swapRows(pair);
	    }
	}

	return isSingular(l);
	// solveUpperTriangularSystem(l);
    }

    private SwapRowPair searchPivot(int startLine) {
	SwapRowPair pair = null;
	Double max = matrix.get(startLine).get(startLine);
	int lineY = -1;
	for (int index = startLine + 1; index < size; index++) {
	    if (matrix.get(index).get(startLine) > max) {
		lineY = index;
		max = matrix.get(index).get(startLine);
	    }
	}
	if (max != matrix.get(startLine).get(startLine)) {
	    pair = new SwapRowPair(startLine, lineY);
	}
	return pair;
    }

    private void swapRows(SwapRowPair pair) {
	// memoram fiecare swap
	swaps.add(pair);
	// Swap valorile din b
	Double temp = bVector.get(pair.getLineX());
	bVector.set(pair.getLineX(), bVector.get(pair.getLineY()));
	bVector.set(pair.getLineY(), temp);
	// Swap valorile din matrice
	for (int i = 0; i < size; i++) {
	    temp = matrix.get(pair.getLineX()).get(i);
	    matrix.get(pair.getLineX()).set(i, matrix.get(pair.getLineY()).get(i));
	    matrix.get(pair.getLineY()).set(i, temp);
	}
    }

    private boolean isSingular(int l) {
	if (Math.abs(matrix.get(l).get(l)) < eps) {
	    singular = true;
	    return true;
	} else {
	    singular = false;
	    return false;
	}
    }

    public boolean solveUpperTriangularSystem(ArrayList<Double> bVector) {
	if (singular) {
	    return false;
	} else {
	    solutions = new ArrayList<>(size);
	    for (int i = 0; i < size; i++) {
		solutions.add(null);
	    }
	    for (int i = size - 1; i >= 0; i--) {
		Double sum = bVector.get(i);
		for (int j = i + 1; j < size; j++) {
		    Double value = matrix.get(i).get(j);
		    Double x = solutions.get(j);
		    sum -= matrix.get(i).get(j) * solutions.get(j);
		}
		solutions.set(i, sum / matrix.get(i).get(i));
	    }
	    State.setSolutions(solutions);

	    this.checkSolution();
	    return true;
	}
    }

    public void solveUpperTriangularSystemForIndependentsB() {
	doSwaps();
    }

    private void doSwaps() {// Aplicam swap-urile efectuate pe matrice si pe
			    // vectorii b
	for (SwapRowPair pair : swaps) {
	    for (int i = 0; i < independentB.size(); i++) {
		double temp = independentB.get(i).get(pair.getLineX());
		independentB.get(i).set(pair.getLineX(), independentB.get(i).get(pair.getLineY()));
		independentB.get(i).set(pair.getLineY(), temp);
	    }
	}
    }

    private void checkSolution() {
	ArrayList<Double> yVector = new ArrayList<>();
	ArrayList<BigDecimal> zI = new ArrayList<>();
	for (int i = 0; i < size; i++) {
	    Double yValue = 0.0;
	    for (int j = 0; j < size; j++) {
		yValue += solutions.get(j) * aInit.get(i).get(j);
	    }
	    yVector.add(yValue);
	}

	for (int i = 0; i < size; i++) {
	    BigDecimal a = new BigDecimal(yVector.get(i)).setScale(15, RoundingMode.HALF_EVEN);
	    System.out.println(a);
	    BigDecimal b = new BigDecimal(bInit.get(i)).setScale(15, RoundingMode.HALF_EVEN);
	    System.out.println(b);
	    zI.add(a.subtract(b));
	}
	System.out.println(zI);

	// TODO: BONUS
	// solveUpperTriangularSystemForIndependentsB();
	// Double euclidianNorm = 0.0;
	// for (int i = 0; i < size; i++) {
	// euclidianNorm += Math.abs(yVector.get(i) * yVector.get(i));
	// }
	// euclidianNorm = (Double) Math.sqrt(euclidianNorm);

    }

    /**
     * @return the matrix
     */
    public ArrayList<ArrayList<Double>> getMatrix() {
	return matrix;
    }

    /**
     * @return the bVector
     */
    public ArrayList<Double> getbVector() {
	return bVector;
    }

    /**
     * @return the aInit
     */
    public ArrayList<ArrayList<Double>> getaInit() {
	return aInit;
    }

    /**
     * @param aInit
     *            the aInit to set
     */
    public void setaInit(ArrayList<ArrayList<Double>> aInit) {
	this.aInit = aInit;
    }

    /**
     * @return the bInit
     */
    public ArrayList<Double> getbInit() {
	return bInit;
    }

    /**
     * @param bInit
     *            the bInit to set
     */
    public void setbInit(ArrayList<Double> bInit) {
	this.bInit = bInit;
    }

    /**
     * @return the solutions
     */
    public ArrayList<Double> getSolutions() {
	return solutions;
    }

    /**
     * @return the singular
     */
    public boolean isSingular() {
	return singular;
    }

}
