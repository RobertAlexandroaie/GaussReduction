/**
 * 
 */
package util;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.State;

/**
 * @author Robert
 */
public class StateManager {
    private static ArrayList<State> states;
    static {
	states = new ArrayList<>();
    }

    public static boolean addState(State state) {
	try {
	    if (state == null) {
		throw new NullPointerException();
	    }
	    return states.add(state);
	} catch (NullPointerException e) {
	    Logger.getGlobal().log(Level.SEVERE, "cannot add null state");
	    e.printStackTrace();
	    return false;
	}
    }

    /**
     * @return the states
     */
    public static ArrayList<State> getStates() {
	return states;
    }
}
