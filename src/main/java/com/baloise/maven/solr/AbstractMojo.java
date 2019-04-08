package com.baloise.maven.solr;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractMojo extends org.apache.maven.plugin.AbstractMojo {

	static final String SOLR_GROUP_ID = "org.apache.solr";
	static final String SOLR_ARTIFACT_ID = "solr";
	static final String SOLR_DEFAULT_VERSION = "7.7.1";

	@Parameter(defaultValue = "${basedir}")
	protected File private__basedir;

	@Parameter(property = "solr.home")
	protected File home;
	
	@Parameter(defaultValue = SOLR_DEFAULT_VERSION, property = "solr.version", required = false)
	String version = null;

	public void execute() throws MojoExecutionException, MojoFailureException {
		adjustHome();
		getLog().info("solr.home: " + home.getAbsolutePath());
		getLog().info("solr.version: " + version);
		doExecute();
	}

	protected abstract void doExecute() throws MojoExecutionException, MojoFailureException;

	private void adjustHome() {
		if (home == null) {
			home = hasPom() ? new File(private__basedir, "/src/solr/resources") : private__basedir;
		}
	}

	private boolean hasPom() {
		return new File("pom.xml").exists();
	}

}
