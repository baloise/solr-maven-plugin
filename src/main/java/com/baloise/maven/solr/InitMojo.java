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
		try {
			unzip(getClass().getResourceAsStream("init.zip"), home);
		} catch (IOException e) {
			throw new MojoExecutionException("",e);
		}
	}

}
