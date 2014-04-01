package com.baloise.maven.solr;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.solr.client.solrj.embedded.JettySolrRunner;
import org.apache.solr.client.solrj.embedded.PermissiveJettySolrRunner;
import org.codehaus.plexus.util.FileUtils;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyRunner {

  public static void main(String[] args) throws Exception {
    
    int port = 8983;
    File solrHome = new File("C:\\Users\\Public\\dev\\ws\\gwt\\solr-some\\src\\solr\\resources");
    System.out.println(solrHome);
    String context = "/solr";
    String solrWar = "C:\\Users\\Public\\dev\\Maven\\repository\\org\\apache\\solr\\solr\\4.7.0\\solr-4.7.0.war";
    runJetty(solrHome, context, port, solrWar);
  }

  /**
   * @param solrHome
   * @param context
   * @param port
   * @param war
   * @throws Exception
   */
  public static void runJetty(File solrHome, String context, int port, String solrWar) throws Exception {
    PermissiveJettySolrRunner jettySolr = new PermissiveJettySolrRunner(solrHome.getAbsolutePath(), context, port);
    addShutdownHook(jettySolr);

    WebAppContext webapp = new WebAppContext();
    webapp.setContextPath(context);
    webapp.setWar(solrWar);
    webapp.setExtractWAR(false);
    jettySolr.getServer().setHandler(webapp);
    jettySolr.start();
    waitForExit();
  }

  private static void addShutdownHook(final JettySolrRunner jettySolr) {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        try {
          System.out.println("Shutting down SOLR");
          jettySolr.stop();
        }
        catch (Exception e) {}
      }
    });
  }

  private static void waitForExit() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    printMessage();
    while (true) {
      try {
        if("exit".equalsIgnoreCase(br.readLine())) {
          System.exit(0);
        } else {
          printMessage();
        }
      } catch (IOException ioe) {
         ioe.printStackTrace();
         System.exit(-1);
      }
    }
  }

  private static void printMessage() {
    System.out.flush();
    System.err.flush();
    System.out.println("To quit type 'exit'");
  }

}
