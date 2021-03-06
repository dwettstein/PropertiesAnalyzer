package trace;

import java.util.Properties;

class Count {
	public static void main(String[] args) {
		String s = "JavaTraceExample";
		char c = 'a';

		System.out.println("The char " + c + " appears " + count(s, c)
				+ " times in your word " + s + ".");

		Properties props = new Properties();

		props.setProperty("haba", "1");

		props.list(System.out);

		String name = props.getProperty("haba", "default");

		System.out.println("Property: " + ", value: " + name);

		String osName = System.getProperty("os.name");
		System.out.println("OS: " + osName);
	}

	public static int count(String s, char c) {
		int counter = 0;

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == c)
				counter++;
		}

		return counter;
	}
}