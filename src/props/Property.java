package props;

import java.util.ArrayList;

public class Property {
	private String name;
	private ArrayList<Call> calls = new ArrayList<Call>();

	/**
	 * @param name
	 */
	public Property(String name, Call call) {
		this.name = name;
		this.calls.add(call);
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
