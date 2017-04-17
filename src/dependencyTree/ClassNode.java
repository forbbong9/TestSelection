package dependencyTree;

import java.util.ArrayList;
import java.util.List;

public class ClassNode {
	private List<ClassNode> parents; // TODO: Test if set is needed
	private String className;
	private boolean needToRetest;
	
	public ClassNode(String className) {
		parents = new ArrayList<ClassNode>();
		this.className = className;
		this.needToRetest = false;
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
