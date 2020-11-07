package com.baloise.maven.solr;

import static java.lang.String.format;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.codehaus.plexus.archiver.tar.TarGZipUnArchiver;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.codehaus.plexus.util.IOUtil;

public class SolrDownloader {

	private static final String SOLR_DOWNLOAD_URL_PROPERTY = "solrDownloadURL";
	private File home = new File(System.getProperty("user.home") + "/solr");
	private List<String> versions = new ArrayList<>();
	String context = "lucene/solr/";

	public List<String> getVersions() {
		if (versions.isEmpty()) {
			try {
				try (Scanner s = new Scanner(new URL(getRemote() + context).openStream())) {
					String html = s.useDelimiter("\\Z").next();
					Matcher m = Pattern.compile("<img src=\"/icons/folder.gif\"[^<]*?<a href=\"(\\d\\..+?)/\"")
							.matcher(html);
					while (m.find()) {
						versions.add(m.group(1));
					}
				}
			} catch (Exception e) {
				handle(e);
			}
		}
		return versions;
	}

	private void handle(Exception e) {
		System.err.println(format("maybe you should set %s as system property or environment variable", SOLR_DOWNLOAD_URL_PROPERTY));
		e.printStackTrace();
		throw new IllegalStateException(e);
	}

	public String getLatestVersion() {
		return getVersions().get(getVersions().size() - 1);
	}

	public File getHome() {
		return home;
	}

	public File download() {
		return download(getLatestVersion());
	}

	public File download(String version) {
		File ret = new File(home, "solr-" + version);
		System.out.println("loading " + ret.getName());
		if (!ret.exists()) {
			try {
				URL url = new URL(String.format("%s%s%s/solr-%s.tgz", getRemote(), context, version, version));
				File tmpFile = File.createTempFile("solr-" + version, ".tgz");
				System.out.println(String.format("downloading %s to %s", url, tmpFile));
				try (InputStream in = url.openStream()) {
					try (OutputStream out = new FileOutputStream(tmpFile)) {
						IOUtil.copy(in, out);
					}
				}
				final TarGZipUnArchiver ua = new TarGZipUnArchiver();
				ua.enableLogging(new ConsoleLogger(Logger.LEVEL_INFO, "console"));
				ua.setSourceFile(tmpFile);
				home.mkdirs();
				ua.setDestDirectory(home);
				ua.extract();
				tmpFile.delete();
			} catch (Exception e) {
				handle(e);
			}
		} else {
			System.out.println("already got " + ret);
		}
		return ret;

	}

	public String getRemote() {
		return Stream.of(
				System.getProperty(SOLR_DOWNLOAD_URL_PROPERTY), 
				System.getenv(SOLR_DOWNLOAD_URL_PROPERTY)
				)
		.filter(Objects::nonNull)
        .findFirst()
        .orElse("http://archive.apache.org/dist/");
	}

}
