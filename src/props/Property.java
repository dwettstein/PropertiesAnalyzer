package props;

import java.util.ArrayList;

public class Property {
	private String name;
	private String type;
	private ArrayList<Call> calls = new ArrayList<Call>();

	/**
	 * @param name
	 */
	public Property(String name, String type, Call call) {
		this.name = name;
		this.type = type;
		this.calls.add(call);
	}

	public void addCall(Call call) {
		this.calls.add(call);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the name
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @return the calls
	 */
	public ArrayList<Call> getCalls() {
		return this.calls;
	}

}
