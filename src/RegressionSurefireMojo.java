import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.surefire.SurefirePlugin;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.ResolutionScope;

import utils.TestMediator;

@Mojo(name = "TestSelection", defaultPhase = LifecyclePhase.TEST, threadSafe = true, requiresDependencyResolution = ResolutionScope.TEST)
public class RegressionSurefireMojo extends SurefirePlugin {

    @Parameter (property = "args")
    private String[] args;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		System.out.println("\n\nStarting RTS");
        try {

            TestMediator.setParameters(args);
			TestMediator.initDependencyTrees();
			
			List<String> excludedTests = TestMediator.getExcludedTests();
			if (null != getExcludes()) {
				excludedTests.addAll(getExcludes());
			}
		 	setExcludes(excludedTests);
		 	
			super.execute();
			
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
