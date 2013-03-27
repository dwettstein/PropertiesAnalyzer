package props;

public class Call {
	private Location location;
	private String value;

	/**
	 * @param location
	 * @param returnValue
	 */
	public Call(Location location, String returnValue) {
		this.location = location;
		this.value = returnValue;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return this.location;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return "Call [location=" + this.location + ", type=" + this.getClass()
				+ ", value=" + this.value + "]";
	}

}
