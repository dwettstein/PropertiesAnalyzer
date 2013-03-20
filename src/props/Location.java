package props;

public class Location {

	private String className;
	private String sourcePath;
	private int lineNumber;

	public Location(String className, String sourcePath, int lineNumber) {
		this.className = className;
		this.sourcePath = sourcePath;
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @return the sourcePath
	 */
	public String getSourcePath() {
		return sourcePath;
	}

	/**
	 * @return the lineNumber
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	@Override
	public String toString() {
		return "Location [className=" + className + ", sourcePath="
				+ sourcePath + ", lineNumber=" + lineNumber + "]";
	}

}
