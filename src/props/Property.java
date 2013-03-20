package props;

import java.util.ArrayList;

public class Property {
	private String name;
	private ArrayList<Call> calls;

	/**
	 * @param name
	 */
	public Property(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the calls
	 */
	public ArrayList<Call> getCalls() {
		return calls;
	}

}
