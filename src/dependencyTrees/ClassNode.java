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

public class ClassNode {
	public static Map<String, ClassNode> instances = new HashMap<String, ClassNode>();
	
	private Set<ClassNode> parents; // TODO: Test if set is needed
	private String className;
	private boolean needToRetest;
	
	private ClassNode(String className) {
		this.parents = new HashSet<ClassNode>();
		this.className = className;
		this.needToRetest = false;
	}
	
	public static void InitClassTree() throws IOException {
		InitClassTreeNodes(PackageHandler.getClassPath());
		
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("jdeps -verbose:class -filter:none " + PackageHandler.getClassPath());
        BufferedReader jDepsReader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        
        String jDepsLine = null;
        ClassNode classNode = null;
        while ((jDepsLine = jDepsReader.readLine()) != null) {
        	jDepsLine = jDepsLine.trim();
        	
        	// Determine if a new classNode is being referenced
        	if (jDepsLine.startsWith(PackageHandler.getClassPackageName())) {
        		if (!jDepsLine.contains("$")) {
            		String[] fullClassName = jDepsLine.split("\\.");
            		String className = fullClassName[fullClassName.length - 1].split("\\s+")[0];
            		
            		classNode = ClassNode.instances.get(className);
        		}
        		else {
        			classNode = null;
        		}
        	}
        	else if (null != classNode) {
        		// TODO: Refactor to rename fullClassName separate from above. Extract Method.
        		String fullClassNameStr = jDepsLine.split("\\s+")[1];
        		if (fullClassNameStr.startsWith(PackageHandler.getClassPackageName()) && !fullClassNameStr.contains("$")) {
            		String[] fullClassName = fullClassNameStr.split("\\.");
            		String dependencyName = fullClassName[fullClassName.length - 1].split("\\s+")[0];
            		
            		// When a 'parent' class depends on a 'child' class 
            		// we add the 'parent' to the child's list of 'parents'
            		
            		ClassNode.instances.get(dependencyName).addParent(classNode);
        		}
        	}
        }
	}
	
	public static void InitClassTreeNodes(String directoryName) {
		 File directory = new File(directoryName);
		 
		// get all the files from a directory
        File[] allFiles = directory.listFiles();
        for (File file : allFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                	addClassNode(fileName.split("\\.")[0]);
                }
            } 
            else if (file.isDirectory()) {
            	InitClassTreeNodes(file.getAbsolutePath());
            }
        }
	}

	public static void addClassNode(String className) {
    	ClassNode node = new ClassNode(className);
		ClassNode.instances.put(className, node);
	}
	
	public void addParent(ClassNode parent) {
		this.parents.add(parent);
	}
	
	@Override
	public String toString() {
		String desc = className + ": ";
		
		for (ClassNode parent : parents) {
			desc += parent.getClassName() + ", ";
		}
		
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
			for (ClassNode parent : parents) {
				parent.setNeedToRetest(true);
			}
		}
	}
}
