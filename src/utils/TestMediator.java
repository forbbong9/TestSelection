package utils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import dependencyTrees.ClassNode;
import dependencyTrees.TestNode;

// TODO: Singleton? Anti-Pattern?
public class TestMediator {
	
	public static final String TEST_CHECKSUM_FILE = "testChecksum.txt";
	public static final String CLASS_CHECKSUM_FILE = "classChecksum.txt";
	
    public static String rootPath;
    public static String classPackageName;
    public static String testPackageName;
    
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

		// Compute Test checksums and mark associated nodes
		CheckSumHandler testCheckSumHandler = new CheckSumHandler(TEST_CHECKSUM_FILE);
		testCheckSumHandler.doChecksum(PackageHandler.getTestPath());
		for (String dangerousTest : testCheckSumHandler.getDangerousClasses()) {
			TestNode.instances.get(dangerousTest).setNeedToRetest(true);
		}

		// Compute Class checksums and mark associated nodes
		CheckSumHandler classCheckSumHandler = new CheckSumHandler(CLASS_CHECKSUM_FILE);
		classCheckSumHandler.doChecksum(PackageHandler.getClassPath());
		for (String dangerousClass : classCheckSumHandler.getDangerousClasses()) {
			ClassNode.instances.get(dangerousClass).setNeedToRetest(true);
		}

		// Mark tests which depend on a dangerous class
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

	public static void setParameters(String[] args){
		rootPath = args[0];
		classPackageName = args[1];
		testPackageName = args[2];
	}
}
