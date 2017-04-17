package utils;

// Could be a singleton
public class PackageHandler {
	private static String rootPath;     // Path to root of project
	private static String testPath;     // Path to test .class files
	private static String packagePath;  // Path to .class files
	private static String packageName;  // Name of class, period delimited

	public static void initialize(String _rootPath, String _packageName) {
		rootPath = _rootPath.trim();
		testPath = rootPath + "/target/test-classes/" + _packageName.trim().replace(".", "/");
		packagePath = rootPath + "/target/classes/" + _packageName.trim().replace(".", "/");
		packageName = rootPath + "/" + _packageName.trim();
	}
	
	public static String getRootPath() {
		return rootPath;
	}
	public static String getTestPath() {
		return testPath;
	}
	public static String getPackagePath() {
		return packagePath;
	}
	public static String getPackageName() {
		return packageName;
	}
}
