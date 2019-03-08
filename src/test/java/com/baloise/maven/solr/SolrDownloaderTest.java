package com.baloise.maven.solr;

import static com.baloise.maven.solr.AbstractMojo.SOLR_DEFAULT_VERSION;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
@Ignore
public class SolrDownloaderTest {
	
	//TODO put this into settings.xml or system property
	final static String remote = "http://archive.apache.org/dist/";
	
	@Test
	public void test() {
		SolrDownloader downloader = new SolrDownloader().withRemote(remote);
		assertEquals(remote, downloader.getRemote());
		assertEquals(new File(System.getProperty("user.home")+"/solr"), downloader.getHome());
		assertEquals(SOLR_DEFAULT_VERSION, downloader.getLatestVersion());
		assertEquals(new File(System.getProperty("user.home")+"/solr/solr-"+SOLR_DEFAULT_VERSION), downloader.download());
		
	}

}
