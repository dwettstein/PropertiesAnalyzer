import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.MethodExitRequest;

/**
 * This class is used to get the events from the class we want to filter in a
 * thread during runtime.
 * 
 * It is derived from the JDI example Trace, so it uses similar methods.
 * 
 * @author dwettstein
 * 
 */
public class EventFilterThread extends Thread {

	private final VirtualMachine vm; // Running VM
	private final String classFilter; // Class to filter for
	private final String targetClass;

	private boolean connected = true; // Connected to VM

	EventFilterThread(VirtualMachine vm, String classFilter, String targetClass) {
		super("event-handler");
		this.vm = vm;
		this.classFilter = classFilter;
		this.targetClass = targetClass;
	}

	/**
	 * Create the desired event requests, and enable them so that we will get
	 * events.
	 * 
	 * @param classFilter
	 *            Class pattern for which we want to filter
	 */
	void setEventRequests() {
		EventRequestManager mgr = vm.eventRequestManager();

		MethodExitRequest mexr = mgr.createMethodExitRequest();
		mexr.addClassFilter(classFilter);
		mexr.setSuspendPolicy(EventRequest.SUSPEND_ALL);
		mexr.enable();
	}

	/**
	 * Run the event handling thread. As long as we are connected, get event
	 * sets off the queue and dispatch the events within them.
	 */
	@Override
	public void run() {
		EventQueue queue = vm.eventQueue();
		while (connected) {
			try {
				EventSet eventSet = queue.remove();
				EventIterator it = eventSet.eventIterator();
				while (it.hasNext()) {
					Event event = it.nextEvent();
					if (event instanceof MethodExitEvent) {
						methodExitEvent((MethodExitEvent) event);
					}
				}
				eventSet.resume();
			} catch (Exception exc) {
				break;
			}
		}
	}

	private void methodExitEvent(MethodExitEvent event) {

		System.out.println();

		try {
			if (event.virtualMachine().classesByName(targetClass).size() != 0) {
				System.out.println("Method: "
						+ event.method().name()
						+ "\n Arguments: "
						+ event.thread().frame(0).getArgumentValues()
						+ "\n Thread: "
						+ event.thread().name()
						+ "\n Value: "
						+ event.returnValue()
						+ "\n Called from class: "
						+ event.virtualMachine().classesByName(targetClass)
								.get(0).name() + "\n");
			} else {
				System.out.println("Method: "
						+ event.method().name()
						+ "\n Arguments: "
						+ event.thread().frame(0).getArgumentValues()
						+ "\n Thread: "
						+ event.thread().name()
						+ "\n Return-Value: "
						+ event.returnValue()
						+ "\n Called from class: "
						+ event.virtualMachine().topLevelThreadGroups()
								.getClass().getName() + "\n");
			}
		} catch (IncompatibleThreadStateException e) {
			// Ignore
			// e.printStackTrace();
		}
	}
}
