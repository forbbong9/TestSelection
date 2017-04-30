package dependencyTrees;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import utils.PackageHandler;

// Note: Consider Extract superclass from ClassNode
public class TestNode {
	public static Map<String, TestNode> instances = new HashMap<String, TestNode>();
	
	private Set<TestNode> parents; // TODO: Test if set is needed
	private Set<ClassNode> dependencies; // TODO: Test if set is needed
	private String className;
	private boolean needToRetest;
	
	private TestNode(String className) {
		this.parents = new HashSet<TestNode>();
		this.dependencies = new HashSet<ClassNode>();
		this.className = className;
		this.needToRetest = false;
	}
	
	public static void InitTestTree() throws IOException {
		InitTestTreeNodes(PackageHandler.getTestPath());
		
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("jdeps -verbose:class -filter:none " + PackageHandler.getTestPath());
        BufferedReader jDepsReader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        
        String jDepsLine = null;
        TestNode testNode = null;
        while ((jDepsLine = jDepsReader.readLine()) != null) {
        	System.out.println(jDepsLine);
        	jDepsLine = jDepsLine.trim();
        	
        	// Determine if a new testNode is being referenced
        	if (jDepsLine.startsWith(PackageHandler.getTestPackageName())) {
        		if (!jDepsLine.contains("$")) {
            		String[] fullClassName = jDepsLine.split("\\.");
            		String className = fullClassName[fullClassName.length - 1].split("\\s+")[0];
            		
            		testNode = TestNode.instances.get(className);
        		}
        		else {
        			testNode = null;
        		}
        	}
        	else if (null != testNode) {
        		// TODO: Refactor to rename fullClassName separate from above. Extract Method.
        		String fullClassNameStr = jDepsLine.split("\\s+")[1];
        		if (fullClassNameStr.startsWith(PackageHandler.getTestPackageName()) && !fullClassNameStr.contains("$")) {
            		String[] fullClassName = fullClassNameStr.split("\\.");
            		String dependencyName = fullClassName[fullClassName.length - 1].split("\\s+")[0];
            		
            		// Determine if this is a class dependency or a test dependency
            		// Note: only class dependencies end with "not found"
            		
            		if (jDepsLine.endsWith("not found")) {
            			testNode.dependencies.add(ClassNode.instances.get(dependencyName));
            		}
            		else {
                		// When a 'parent' class depends on a 'child' class 
                		// we add the 'parent' to the child's list of 'parents'

                		TestNode.instances.get(dependencyName).addParent(testNode);
            		}
            		
        		}
        	}
        }
	}
	
	public static void InitTestTreeNodes(String directoryName) {
		 File directory = new File(directoryName);
		 
		// get all the files from a directory
        File[] allFiles = directory.listFiles();
        for (File file : allFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                	addTestNode(fileName.split("\\.")[0]);
                }
            } 
            else if (file.isDirectory()) {
            	InitTestTreeNodes(file.getAbsolutePath());
            }
        }
	}

	public static void addTestNode(String className) {
    	TestNode node = new TestNode(className);
		TestNode.instances.put(className, node);
	}
	
	public void addParent(TestNode parent) {
		this.parents.add(parent);
	}

	public void checkIfNeedRetest(){
		for(ClassNode dependency : dependencies){
			if(dependency.isNeedToRetest()){
				this.needToRetest = true;
			}
		}
	}

	@Override
	public String toString() {
		String desc = className + "\n: parents - ";
		
		for (TestNode parent : parents) {
			desc += parent.getClassName() + ", ";
		}
		
		desc += "\n: dependencies - ";
		for (ClassNode dependency : dependencies) {
			desc += dependency.getClassName() + ", ";
		}
		System.out.println("");
		
		return desc;
	}
	
	public String getClassName() {
		return className;
	}

	public boolean isNeedToRetest() {
		return needToRetest;
	}

	public void setNeedToRetest(boolean needToRetest) {
		// Only update if not already true and attempting to set true
		if (!this.needToRetest && needToRetest) 
		{ 
			this.needToRetest = true;
			for (TestNode parent : parents) {
				parent.setNeedToRetest(true);
			}
		}
	}
}
