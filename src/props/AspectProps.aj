package props;

public aspect AspectProps {
	before(): call(* java.util.Properties.* (..)) {
		System.out.println("\n[INFO]: " + thisJoinPoint);
	}
	
	before(): call(* java.lang.System.*Propert*(..)) {
		System.out.println("\n[INFO]: " + thisJoinPoint);
	}
}