package utils;

// Could be a singleton
public class PackageHandler {
	private static String rootPath;     // Path to root of project
	private static String classPath;    // Path to source .class files
	private static String testPath;     // Path to test .class files
	private static String classPackageName;  // Name of class package, period delimited
	private static String testPackageName;  // Name of test package, period delimited

	public static void initialize(String _rootPath, String _classPackageName, String _testPackageName) {
		rootPath = _rootPath.trim();
		classPackageName = _classPackageName.trim();
		testPackageName = _testPackageName.trim();
		classPath = rootPath + "/target/classes/" + classPackageName.replace(".", "/");
		testPath = rootPath + "/target/test-classes/" + testPackageName.replace(".", "/");
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
	public static String getClassPackageName() {
		return classPackageName;
	}
	public static String getTestPackageName() {
		return testPackageName;
	}
}
