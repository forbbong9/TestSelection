package utils;

// Could be a singleton
public class PackageHandler {
	private static String rootPath;     // Path to root of project
	private static String testPath;     // Path to test .class files
	private static String classPath;    // Path to source .class files
	private static String packageName;  // Name of class, period delimited

	public static void initialize(String _rootPath, String _packageName) {
		rootPath = _rootPath.trim();
		testPath = rootPath + "/target/test-classes/" + _packageName.trim().replace(".", "/");
		classPath = rootPath + "/target/classes/" + _packageName.trim().replace(".", "/");
		packageName = _packageName.trim();
	}
	
	public static String getRootPath() {
		return rootPath;
	}
	public static String getTestPath() {
		return testPath;
	}
	public static String getClassPath() {
		return classPath;
	}
	public static String getPackageName() {
		return packageName;
	}
}
