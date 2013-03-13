package props;

import java.io.PrintWriter;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.ModificationWatchpointEvent;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.event.ThreadDeathEvent;

public class SystemPropertiesThreadTrace extends AbstractThreadTrace {

	private ThreadReference thread;
	private PrintWriter writer;

	private String[] acceptedMethods = { "getProperty", "setProperty" };

	public SystemPropertiesThreadTrace(ThreadReference thread,
			PrintWriter writer) {
		this.thread = thread;
		this.writer = writer;

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
			// We only want events with the accepted methods
			if (!checkMethodName(event))
				return;

			// Getting the last frame
			int f = event.thread().frames().size() - 1;

			writer.println(event.method().name());
			writer.println(event.thread().frame(f).getArgumentValues());
			writer.println(event.returnValue());
			writer.println(event.thread().frame(f).location().declaringType()
					.name());
			writer.println(event.thread().frame(f).location().method());
			try {
				writer.println(event.thread().frame(f).location().sourcePath());
			} catch (AbsentInformationException e) {
				// e.printStackTrace();
			}
			writer.println(event.thread().frame(f).location().lineNumber());
			writer.println();
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
	}

}
