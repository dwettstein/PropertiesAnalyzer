package trace;

import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.ModificationWatchpointEvent;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.event.ThreadDeathEvent;

interface IThreadTrace {

	public void methodEntryEvent(MethodEntryEvent event);

	public void methodExitEvent(MethodExitEvent event);

	public void fieldWatchEvent(ModificationWatchpointEvent event);

	public void exceptionEvent(ExceptionEvent event);

	public void stepEvent(StepEvent event);

	public void threadDeathEvent(ThreadDeathEvent event);

}