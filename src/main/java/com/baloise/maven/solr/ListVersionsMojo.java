package com.baloise.maven.solr;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "versions", defaultPhase = LifecyclePhase.NONE, requiresProject = false)
public class ListVersionsMojo extends org.apache.maven.plugin.AbstractMojo {

	SolrDownloader downloader = new SolrDownloader();

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		for (String version : downloader.getVersions()) {
			getLog().info(version);
		}
	}

}
