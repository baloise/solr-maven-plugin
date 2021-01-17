package com.baloise.maven.solr;

import static com.baloise.maven.solr.AbstractMojo.SOLR_DEFAULT_VERSION;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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
		mojo.downloader = new SolrDownloader();
	}
	
	@Ignore
	@Test public void init() throws Exception {
		if(new File(mojo.home, "com").exists()) {
			System.out.println(mojo.home + " is not empty. skipping");
		} else {
			System.out.println("bootstrapping from init.zip");
			InitMojo initMojo = new InitMojo();
			initMojo.home = mojo.home;
			initMojo.version = mojo.version;
			initMojo.doExecute();			
		}
	}
	
	@Ignore
	@Test public void testDoExecute() throws Exception {
		init();
		mojo.execute();
	}

}
