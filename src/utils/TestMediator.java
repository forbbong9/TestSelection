package utils;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import dependencyTrees.ClassNode;
import dependencyTrees.TestNode;

// TODO: Singleton? Anti-Pattern?
public class TestMediator {
    public static String rootPath = "C:/Users/Cheng/git/commons-dbutils"; //"/Users/HL/Desktop/joda-time-master";
    public static String classPackageName = "org.apache.commons.dbutils";
    public static String testPackageName = "org.apache.commons.dbutils";
    
	public static List<String> getSelectedTests() throws IOException, NoSuchAlgorithmException {
		List<String> selectedTests = new ArrayList<String>();
        for(TestNode instance: TestNode.instances.values()){
            if(instance.isNeedToRetest()){
            	selectedTests.add(instance.getClassName());
            }
        }
        return selectedTests;
	}
	
	public static List<String> getExcludedTests() throws IOException, NoSuchAlgorithmException {
		List<String> excludedTests = new ArrayList<String>();
        for(TestNode instance: TestNode.instances.values()){
            if(!instance.isNeedToRetest()){
            	excludedTests.add(instance.getClassName());
            }
        }
        return excludedTests;
	}

	public static void initDependencyTrees() throws IOException, NoSuchAlgorithmException {
		// Compute Class and Test Dependency Trees
		PackageHandler.initialize(rootPath, classPackageName, testPackageName);
		ClassNode.InitClassTree();
		TestNode.InitTestTree();

		// Compute checksums and dangerous classes
//      // TODO: Add checking for testfile changes
//		CheckSumHandler.doChecksum(PackageHandler.getTestPath());
//		for (String dangerousTest : CheckSumHandler.getDangerousClasses()) {
//			TestNode.instances.get(dangerousTest).setNeedToRetest(true);
//		}
		CheckSumHandler.doChecksum(PackageHandler.getClassPath());
		for (String dangerousClass : CheckSumHandler.getDangerousClasses()) {
			ClassNode.instances.get(dangerousClass).setNeedToRetest(true);
		}
		for (TestNode instance : TestNode.instances.values()) {
			instance.checkIfNeedRetest();
		}
	}
	
	public static void printDependencyTrees() {
		System.out.println("Class Tree: ");
		for (ClassNode node : ClassNode.instances.values()) {
			System.out.println(node.toString());
		}

		System.out.println("Test Tree: ");
		for (TestNode node : TestNode.instances.values()) {
			System.out.println(node.toString());
		}
	}
}
