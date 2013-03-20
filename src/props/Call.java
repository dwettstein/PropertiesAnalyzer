package props;

public class Call {
	private Location location;
	private String type;
	private String value;

	/**
	 * @param location
	 * @param type
	 * @param value
	 */
	public Call(Location location, String type, String value) {
		this.location = location;
		this.type = type;
		this.value = value;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Call [location=" + location + ", type=" + type + ", value="
				+ value + "]";
	}

}
