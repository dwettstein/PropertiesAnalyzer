package props;

import org.junit.Before;
import org.junit.Test;

public class PropsTest {

	private Location location;
	private String value;
	private Call call;

	private String name;
	private String type;
	private Property property;
	private PropertiesModel model;

	@Before
	public void setUp() {
		location = new Location("java.lang.System", "getProperty", "?", 709);
		value = "Windows 7";
		call = new GetCall(location, value);

		name = "os.name";
		type = "java.lang.System";
		property = new Property(name, type, call);
		model = new PropertiesModel();
		model.addProperty(property);
	}

	@Test
	public void nullTest() {
		assert (!location.equals(null));
		assert (!value.equals(null));
		assert (!call.equals(null));
		assert (!name.equals(null));
		assert (!model.equals(null));
		assert (!property.equals(null));
	}

	@Test
	public void callTest() {
		assert (location.getClassName().equals("java.lang.System"));
		assert (location.getMethodName().equals("getProperty"));
		assert (location.getSourcePath().equals("?"));
		assert (location.getLineNumber() == 709);

		assert (value.equals("Windows 7"));
		assert (call.getClass().equals(GetCall.class));
		assert (call.getLocation().equals(location));
		assert (call.getValue().equals(value));
	}

	@Test
	public void propertyTest() {
		assert (name.equals("os.name"));
		assert (property.getName().equals(name));
		assert (property.getCalls().contains(call));
		assert (model.getProperties().contains(property));
	}
}
