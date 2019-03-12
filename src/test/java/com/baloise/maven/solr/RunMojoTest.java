package com.baloise.maven.solr;

import static com.baloise.maven.solr.AbstractMojo.SOLR_DEFAULT_VERSION;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
@Ignore
public class RunMojoTest {

	RunMojo mojo;

	@Before
	public void setUp() throws Exception {
		mojo = new RunMojo();
		mojo.port = 8983;
		mojo.context = "/solr";
		mojo.version = SOLR_DEFAULT_VERSION;
		mojo.home = new File("tmp");
		mojo.home.mkdirs();
		if(!new File(mojo.home, "com").exists()) {
			System.out.println("bootstrapping from init.zip");
			InitMojo initMojo = new InitMojo();
			initMojo.home = mojo.home;
			initMojo.version = SOLR_DEFAULT_VERSION;
			initMojo.doExecute();
		}
		mojo.downloader = new SolrDownloader();
	}

	@Test
	public void testDoExecute() throws Exception {
		mojo.execute();
	}

}
