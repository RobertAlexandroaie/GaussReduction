/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package model;

/**
 * @author Claudiu
 */
public class SwapRowPair {
    private int lineX;
    private int lineY;

    public SwapRowPair(int lineX, int lineY) {
	this.lineX = lineX;
	this.lineY = lineY;
    }

    public SwapRowPair(SwapRowPair swap) {
	lineX = swap.getLineX();
	lineY = swap.getLineY();
    }

    /**
     * @return the lineX
     */
    public int getLineX() {
	return lineX;
    }

    /**
     * @return the lineY
     */
    public int getLineY() {
	return lineY;
    }

}
