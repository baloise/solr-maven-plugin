package com.baloise.maven.solr;

import static com.baloise.maven.solr.AbstractMojo.SOLR_DEFAULT_VERSION;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
@Ignore
public class SolrDownloaderTest {
	
	
	@Test
	public void test() {
		SolrDownloader downloader = new SolrDownloader();
		
		assertEquals(new File(System.getProperty("user.home")+"/solr"), downloader.getHome());
		assertEquals(SOLR_DEFAULT_VERSION, downloader.getLatestVersion());
		assertEquals(new File(System.getProperty("user.home")+"/solr/solr-"+SOLR_DEFAULT_VERSION), downloader.download());
		
	}

}
