package props;

import java.io.PrintWriter;

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
			if (!checkMethodName(event))
				return;

			if (event.thread().frames().size() > 1) {
				writer.println(event.method().name());
				writer.println(event.thread().frame(1).location().getClass());
				writer.println(event.thread().frame(1).location().method());
				writer.println(event.thread().frame(1).getArgumentValues());
				writer.println(event.returnValue());
				// writer.println(event.thread().frame(1).location().lineNumber());
				writer.println();
			}

			else {
				writer.println(event.method().name());
				writer.println(event.thread().frame(0).location().getClass());
				writer.println(event.thread().frame(0).location().method());
				writer.println(event.thread().frame(0).getArgumentValues());
				writer.println(event.returnValue());
				writer.println();
			}
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
