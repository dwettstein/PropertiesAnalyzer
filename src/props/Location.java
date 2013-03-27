package props;

public class Location {

	private String className;
	private String methodName;
	private String sourcePath;
	private int lineNumber;

	public Location(String className, String methodName, String sourcePath,
			int lineNumber) {
		this.className = className;
		this.methodName = methodName;
		this.sourcePath = sourcePath;
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return this.methodName;
	}

	/**
	 * @return the sourcePath
	 */
	public String getSourcePath() {
		return this.sourcePath;
	}

	/**
	 * @return the lineNumber
	 */
	public int getLineNumber() {
		return this.lineNumber;
	}

	@Override
	public String toString() {
		return "Location [className=" + this.className + ", methodName="
				+ this.methodName + ", sourcePath=" + this.sourcePath
				+ ", lineNumber=" + this.lineNumber + "]";
	}

}
