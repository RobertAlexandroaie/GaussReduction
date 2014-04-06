/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * @author Robert
 */
public class StatesAdapter {
    private GaussMatrix gaussMatrix;
    private ArrayList<State> states;
    private int step;

    public StatesAdapter() {
	step = 0;
    }

    /**
     * @return the states
     */
    public ArrayList<State> getStates() {
	return states;
    }

    /**
     * @param states
     *            the states to set
     */
    public void setStates(ArrayList<State> states) {
	this.states = states;
    }

    /**
     * @return the step
     */
    public int getStep() {
	return step;
    }

    public void next() {
	if (step == states.size() - 1) {
	    step = 0;
	} else {
	    step++;
	}
    }

    public void prev() {
	if (step == 0) {
	    step = states.size() - 1;
	} else {
	    step--;
	}
    }

    // colored
    public String sysToColoredString(ArrayList<Double> bVector) {
	State currentState = states.get(step);
	SwapRowPair swapPair = currentState.getSwap();
	StringBuilder sysToColoredString = new StringBuilder("<html><table>");
	int size = bVector.size();
	for (int ec = 0; ec < size; ec++) {
	    sysToColoredString.append(ecToColoredString(ec, currentState, swapPair, bVector));
	}
	sysToColoredString.append("</table></html>");
	return sysToColoredString.toString();
    }

    private String ecToColoredString(int ec, State state, SwapRowPair swapPair, ArrayList<Double> bVector) {
	StringBuilder ecToColoredString = new StringBuilder("<tr>");
	boolean colorable = false;
	int size = bVector.size();
	if (swapPair != null) {
	    colorable = (swapPair.getLineX() == ec || swapPair.getLineY() == ec) && step > 0;
	}
	ArrayList<Double> row = state.getMatrix().get(ec);
	int index = 1;

	// ai*xi
	for (Double coef : row) {
	    if (colorable) {
		ecToColoredString.append("<td><font color=\"#B90000\"><b>");
		if (coef == 0.0) {
		    ecToColoredString.append(" ");
		} else {
		    ecToColoredString.append("(" + coef + ") * x" + index + " ");
		    ecToColoredString.append((index < size) ? "+ " : "");
		}
		ecToColoredString.append("</font></b></td>");
	    } else {
		ecToColoredString.append("<td>");
		if (coef == 0.0) {
		    ecToColoredString.append(" ");
		} else {
		    ecToColoredString.append("(" + coef + ") * x" + index + " ");
		    ecToColoredString.append((index < size) ? "+ " : "");
		}
		ecToColoredString.append("</td>");
	    }
	    index++;
	}

	// =b
	ecToColoredString.append("<td>");
	if (colorable) {
	    ecToColoredString.append("<font color=\"#B90000\"><b>");
	}
	ecToColoredString.append("= " + bVector.get(ec));
	if (colorable) {
	    ecToColoredString.append("</font></b>");
	}
	ecToColoredString.append("</td><td>");

	if (colorable) {
	    ecToColoredString.append("<font color=\"#B90000\"><b>");
	}
	// |+=(f)*L
	if (state.getFactors() != null && !state.getFactors().isEmpty()) {
	    if (ec >= step) {
		ecToColoredString.append("|+= (" + state.getFactors().get(ec - step) + ")*L" + step);
	    }
	}
	if (colorable) {
	    ecToColoredString.append("</font></b>");
	}
	ecToColoredString.append("</td>");

	ecToColoredString.append("</tr>");
	return ecToColoredString.toString();
    }

    public String solToString() {
	ArrayList<Double> solutions = gaussMatrix.getSolutions();
	StringBuilder solToString = new StringBuilder("<html><table>");
	int index = 1;
	for (Double sol : solutions) {
	    solToString.append("<tr>");
	    solToString.append("<td>");
	    solToString.append("x" + index + "=");
	    solToString.append("</td>");

	    solToString.append("<td>");
	    solToString.append(sol);
	    solToString.append("</td>");
	    solToString.append("</tr>");
	    index++;
	}
	solToString.append("</table></html>");
	return solToString.toString();
    }

    /**
     * @param step
     *            the step to set
     */
    public void setStep(int step) {
	this.step = step;
    }

    /**
     * @return the gaussMatrix
     */
    public GaussMatrix getGaussMatrix() {
	return gaussMatrix;
    }

    /**
     * @param gaussMatrix
     *            the gaussMatrix to set
     */
    public void setGaussMatrix(GaussMatrix gaussMatrix) {
	this.gaussMatrix = gaussMatrix;
    }

}
