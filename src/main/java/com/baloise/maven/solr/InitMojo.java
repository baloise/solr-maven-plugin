package com.baloise.maven.solr;

import static com.baloise.maven.solr.UnZip.unzip;

import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "init", defaultPhase = LifecyclePhase.NONE, requiresProject = false)
public class InitMojo extends AbstractMojo {

	protected void doExecute() throws MojoExecutionException, MojoFailureException {
		String majorVersion = version.trim().split("\\.")[0];
		String zip = "init"+majorVersion+".zip" ;
		try {
			unzip(getClass().getResourceAsStream(zip), home);
		} catch (IOException e) {
			getLog().error("Please provide an initial core for the major version "+majorVersion+".\nSee https://github.com/baloise/solr-maven-plugin/blob/master/create_init_zip.md");
			throw new MojoExecutionException("could not load "+ zip,e);
		}
	}

}
