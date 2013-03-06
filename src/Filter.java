import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;

/**
 * This class is used to filter for events targeting Java Properties.
 * 
 * It is derived from the JDI example Trace, so it uses similar methods.
 * 
 * @author dwettstein
 * 
 */
public class Filter {

	// Running remote VM
	private final VirtualMachine vm;

	// Thread transferring remote error stream to our error stream
	private Thread errThread = null;

	// Thread transferring remote output stream to our output stream
	private Thread outThread = null;

	// The events we want to filter for
	private String classFilter = "java.util.Properties";

	// The class we want to target
	private String targetClass;

	/**
	 * Main method. Creates a new filter for properties.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Filter(args);
	}

	private Filter(String[] args) {
		targetClass = args[0];
		vm = launchTarget(targetClass);
		genOutput();
	}

	/**
	 * Generate the output with an EventFilterThread.
	 */
	void genOutput() {
		EventFilterThread filterThread = new EventFilterThread(vm, classFilter,
				targetClass);
		filterThread.setEventRequests();
		filterThread.start();
		redirectOutput();
		vm.resume();

		// Shutdown begins when event thread terminates
		try {
			filterThread.join();
			errThread.join(); // Make sure output is forwarded
			outThread.join(); // before we exit
		} catch (InterruptedException exc) {
			// we don't interrupt
		}
	}

	/*
	 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
	 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
	 * 
	 * 
	 * 
	 * INFO: The following methods are completely copied from the JDI example
	 * Trace written by Robert Field, Oracle.
	 */

	/**
	 * Launch target VM. Forward target's output and error.
	 */
	VirtualMachine launchTarget(String mainArgs) {
		LaunchingConnector connector = findLaunchingConnector();
		Map<String, Connector.Argument> arguments = connectorArguments(
				connector, mainArgs);
		try {
			return connector.launch(arguments);
		} catch (IOException exc) {
			throw new Error("Unable to launch target VM: " + exc);
		} catch (IllegalConnectorArgumentsException exc) {
			throw new Error("Internal error: " + exc);
		} catch (VMStartException exc) {
			throw new Error("Target VM failed to initialize: "
					+ exc.getMessage());
		}
	}

	/**
	 * Find a com.sun.jdi.CommandLineLaunch connector
	 */
	LaunchingConnector findLaunchingConnector() {
		List<Connector> connectors = Bootstrap.virtualMachineManager()
				.allConnectors();
		for (Connector connector : connectors) {
			if (connector.name().equals("com.sun.jdi.CommandLineLaunch")) {
				return (LaunchingConnector) connector;
			}
		}
		throw new Error("No launching connector");
	}

	/**
	 * Return the launching connector's arguments.
	 */
	Map<String, Connector.Argument> connectorArguments(
			LaunchingConnector connector, String mainArgs) {
		Map<String, Connector.Argument> arguments = connector
				.defaultArguments();
		Connector.Argument mainArg = (Connector.Argument) arguments.get("main");
		if (mainArg == null) {
			throw new Error("Bad launching connector");
		}
		mainArg.setValue(mainArgs);

		return arguments;
	}

	/**
	 * This method is used to copy the target's output from the VM. It uses a
	 * StreamRedirectThread, which is written by Robert Field from Oracle in the
	 * JDI Trace example.
	 */
	void redirectOutput() {
		Process process = vm.process();

		// Copy target's output and error to our output and error.
		errThread = new StreamRedirectThread("error reader",
				process.getErrorStream(), System.err);
		outThread = new StreamRedirectThread("output reader",
				process.getInputStream(), System.out);
		errThread.start();
		outThread.start();
	}
}
