/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package main;

import model.GaussMatrix;

/**
 * @author Claudiu
 */
public class GaussReduction {

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
	// GaussMatrix gaussMatrix = GaussMatrix.generateRandomGaussMatrix(4);
	GaussMatrix gaussMatrix = GaussMatrix.loadGaussMatrixFromFile("GaussMatrixSource.src");
	gaussMatrix.loadIndependentBVectorFromFile("GaussIndependentBs.src");
	gaussMatrix.displayMatrix();
	gaussMatrix.startGaussReduction();
	gaussMatrix.displayMatrix();
	gaussMatrix.displayBVector();
    }
}
