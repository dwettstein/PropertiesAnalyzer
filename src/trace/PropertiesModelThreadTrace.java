package trace;

import java.io.PrintWriter;

import props.Call;
import props.GetCall;
import props.Location;
import props.PropertiesModel;
import props.Property;
import props.SetCall;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.ModificationWatchpointEvent;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.event.ThreadDeathEvent;

public class PropertiesModelThreadTrace extends AbstractThreadTrace {

	private ThreadReference thread;
	private PrintWriter writer;

	private String[] acceptedMethods = { "getProperty", "setProperty" };

	private PropertiesModel model;

	public PropertiesModelThreadTrace(ThreadReference thread, PrintWriter writer) {
		this.thread = thread;
		this.writer = writer;

		this.model = new PropertiesModel();

		writer.println("====== " + thread.name() + " ======");
	}

	/**
	 * Checks if the method of the event is one of our accepted methods.
	 * 
	 * @param event
	 *            the event to check
	 * @return true, if method name accepted
	 */
	private boolean checkMethodName(Object event) {
		int c = 0;
		if (event instanceof MethodEntryEvent) {
			for (String string : acceptedMethods) {
				if (((MethodEntryEvent) event).method().name().equals(string)) {
					return true;
				} else if (c == (acceptedMethods.length - 1))
					return false;
				c++;
			}
		} else if (event instanceof MethodExitEvent) {
			for (String string : acceptedMethods) {
				if (((MethodExitEvent) event).method().name().equals(string)) {
					return true;
				} else if (c == (acceptedMethods.length - 1))
					return false;
				c++;
			}
		}
		return false;
	}

	@Override
	public void methodEntryEvent(MethodEntryEvent event) {
	}

	@Override
	public void methodExitEvent(MethodExitEvent event) {
		try {
			// We only want events with the accepted methods.
			if (!checkMethodName(event))
				return;

			Location location = getLocation(event);

			String returnValue = "";
			if (event.returnValue() != null)
				returnValue = event.returnValue().toString();

			Call call;
			if (event.method().name().equals("getProperty"))
				call = new GetCall(location, returnValue);
			else if (event.method().name().equals("setProperty"))
				call = new SetCall(location, returnValue);
			else
				call = null;

			// Property name
			String name = event.thread().frame(0).getArgumentValues().get(0)
					.toString();

			// Property type (java.lang.System or java.util.Properties)
			String type = event.thread().frame(0).location().declaringType()
					.name();

			Property property = new Property(name, type, call);

			this.model.addProperty(property);

		} catch (IncompatibleThreadStateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fieldWatchEvent(ModificationWatchpointEvent event) {
	}

	@Override
	public void exceptionEvent(ExceptionEvent event) {
	}

	@Override
	public void stepEvent(StepEvent event) {
	}

	@Override
	public void threadDeathEvent(ThreadDeathEvent event) {
		writer.println("====== " + thread.name() + " end ======");
		writer.println();
		writer.println("Number of properties objects: "
				+ this.model.getProperties().size());
		writer.println();
		for (Property property : this.model.getProperties()) {
			writer.println(property.getName());
			writer.println(property.getType());
			for (Call call : property.getCalls()) {
				writer.println(call.toString());
			}
			writer.println();
		}
	}

	public Location getLocation(MethodExitEvent event) {
		try {
			// Getting the last frame
			int f = event.thread().frames().size() - 1;

			// Calling class
			String className = event.thread().frame(f).location()
					.declaringType().name();
			// Calling method
			String methodName = event.thread().frame(f).location().method()
					.toString();
			// Source path
			String sourcePath = "";
			try {
				sourcePath = event.thread().frame(f).location().sourcePath();
			} catch (AbsentInformationException e) {
				// e.printStackTrace();
			}
			// Line number
			int lineNumber = event.thread().frame(f).location().lineNumber();
			// Create location object
			return new Location(className, methodName, sourcePath, lineNumber);

		} catch (IncompatibleThreadStateException e) {
			e.printStackTrace();
		}
		return null;
	}

}