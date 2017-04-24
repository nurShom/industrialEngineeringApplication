/**
 * 
 */
package edu.ohio.ise.ise6900.MfgSystem.model;

import java.util.HashMap;

import edu.ohio.ise.ise6900.MfgSystem.model.exceptions.UnknownStateException;
import javafx.scene.paint.Color;
/**
 * @author na551411
 *
 */
public enum StateType {
	IDLE(Color.LIGHTPINK), DOWN(Color.BISQUE), BLOCKED(Color.MEDIUMVIOLETRED), BUSY(Color.LIGHTCORAL);
	private Color color;
	
	StateType(Color color){
		this.color = color;
	}
	
	private static HashMap<String, StateType> types = new HashMap<String, StateType>(); 
	
	static {
		types.put("idle", IDLE);
		types.put("down", DOWN);
		types.put("blocked", BLOCKED);
		types.put("busy", BUSY);
	}
	
	public static StateType findStateType(String type) throws UnknownStateException{
		StateType st = StateType.types.get(type.trim().toLowerCase());
		if(st==null){
			throw new UnknownStateException("Invalid StateType string '" + type + "' provided! "
						+ "Valid states include: " + types.values().toString() );
		}
		return st;
	}
	
	public static String getAllValidStates(){
		return types.values().toString();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
