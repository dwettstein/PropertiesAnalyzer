package props;

import java.util.ArrayList;

public class PropertiesModel {
	private ArrayList<Property> properties;

	/**
	 * @param properties
	 */
	public PropertiesModel() {
		this.properties = new ArrayList<Property>();
	}

	/**
	 * @return the properties
	 */
	public ArrayList<Property> getProperties() {
		return properties;
	}
}
