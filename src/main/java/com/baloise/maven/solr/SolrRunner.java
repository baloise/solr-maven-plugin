package com.baloise.maven.solr;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SolrRunner {

  public static void runSolr(File solrInstall, File solrHome, int port) throws Exception {
	
	addShutdownHook(solrInstall, solrHome, port);
	new ProcessBuilder(
			new File(solrInstall , "/bin/solr").getAbsolutePath()
			,"-f" // Start Solr in foreground
			,"-p" // port
			, String.valueOf(port)
			,"-s" // Sets the solr.solr.home system property
			,solrHome.getAbsolutePath()
			)
	  .directory(solrInstall)
	  .inheritIO()
	  .start();
    waitForExit();
  }

  private static void addShutdownHook(File solrInstall, File solrHome, int port) {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        try {
          System.out.println("Shutting down SOLR");
          new ProcessBuilder(
      			new File(solrInstall , "/bin/solr").getAbsolutePath()
      			,"stop"
      			,"-p" // port
      			, String.valueOf(port)
      			)
      	  .directory(solrInstall)
      	  .inheritIO()
      	  .start();
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
