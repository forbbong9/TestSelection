import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.surefire.AbstractSurefireMojo;
import org.apache.maven.surefire.suite.RunResult;

public class RegressionSurefireMojo extends AbstractSurefireMojo {

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

        try {
			TestMediator.initDependencyTrees();
		 	setExcludes(TestMediator.getExcludedTests());
			super.execute();
			
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public File getBasedir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getClassesDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDebugForkedProcess() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean getFailIfNoSpecifiedTests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getForkedProcessExitTimeoutInSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getForkedProcessTimeoutInSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getParallelTestsTimeoutForcedInSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getParallelTestsTimeoutInSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getReportFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getReportsDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getShutdown() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSkipAfterFailureCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getTestClassesDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPrintSummary() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSkip() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSkipExec() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSkipTests() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUseFile() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUseManifestOnlyJar() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUseSystemClassLoader() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBasedir(File arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClassesDirectory(File arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDebugForkedProcess(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFailIfNoSpecifiedTests(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setForkedProcessExitTimeoutInSeconds(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setForkedProcessTimeoutInSeconds(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParallelTestsTimeoutForcedInSeconds(double arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParallelTestsTimeoutInSeconds(double arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPrintSummary(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setReportFormat(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setReportsDirectory(File arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSkip(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSkipExec(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSkipTests(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTest(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTestClassesDirectory(File arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUseFile(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUseManifestOnlyJar(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUseSystemClassLoader(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String[] getDefaultIncludes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getExcludesFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getIncludes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getIncludesFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPluginName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getReportSchemaLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getRerunFailingTestsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getRunOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File[] getSuiteXmlFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleSummary(RunResult arg0, Exception arg1) throws MojoExecutionException, MojoFailureException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean hasSuiteXmlFiles() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isSkipExecution() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIncludes(List<String> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRunOrder(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSuiteXmlFiles(File[] arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<File> suiteXmlFiles() {
		// TODO Auto-generated method stub
		return null;
	}

}
