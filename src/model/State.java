/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * @author Robert
 */
public class State {
    private ArrayList<ArrayList<Double>> matrix;
    private ArrayList<Double> b;
    private SwapRowPair swap;
    private ArrayList<Double> factors;
    private static ArrayList<Double> solutions;

    public State(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> b, SwapRowPair swap, ArrayList<Double> factors) {
	this.matrix = getClone(matrix);
	this.b = new ArrayList<>(b);
	if (swap != null) {
	    this.swap = new SwapRowPair(swap);
	}
	if (factors != null) {
	    this.factors = new ArrayList<>(factors);
	}
    }

    private ArrayList<ArrayList<Double>> getClone(ArrayList<ArrayList<Double>> matrix) {
	ArrayList<ArrayList<Double>> clone = new ArrayList<ArrayList<Double>>();
	for (ArrayList<Double> row : matrix) {
	    clone.add(new ArrayList<Double>(row));
	}
	return clone;
    }

    /**
     * @return the matrix
     */
    public ArrayList<ArrayList<Double>> getMatrix() {
	return matrix;
    }

    /**
     * @param matrix
     *            the matrix to set
     */
    public void setMatrix(ArrayList<ArrayList<Double>> matrix) {
	this.matrix = matrix;
    }

    /**
     * @return the b
     */
    public ArrayList<Double> getB() {
	return b;
    }

    /**
     * @param b
     *            the b to set
     */
    public void setB(ArrayList<Double> b) {
	this.b = b;
    }

    /**
     * @return the swap
     */
    public SwapRowPair getSwap() {
	return swap;
    }

    /**
     * @param swap
     *            the swap to set
     */
    public void setSwap(SwapRowPair swap) {
	this.swap = swap;
    }

    /**
     * @return the factor
     */
    public ArrayList<Double> getFactors() {
	return factors;
    }

    /**
     * @param factor
     *            the factor to set
     */
    public void setFactors(ArrayList<Double> factors) {
	this.factors = factors;
    }

    /**
     * @return the solutions
     */
    public static ArrayList<Double> getSolutions() {
	return solutions;
    }

    /**
     * @param solutions
     *            the solutions to set
     */
    public static void setSolutions(ArrayList<Double> solutions) {
	solutions = new ArrayList<Double>(solutions);
    }

}
