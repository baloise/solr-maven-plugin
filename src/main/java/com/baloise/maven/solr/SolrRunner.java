package com.baloise.maven.solr;

import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class SolrRunner {

  public static void runSolr(File solrInstall, File solrHome, int port) throws Exception {
	
	addShutdownHook(solrInstall, solrHome, port);
	runProc(solrInstall,
			new File(solrInstall , solrCmd()).getAbsolutePath()
			,"-f" // Start Solr in foreground
			,"-p" // port
			, String.valueOf(port)
			,"-s" // Sets the solr.solr.home system property
			,solrHome.getAbsolutePath()
			);
    waitForExit();
  }

  private static void runProc(File directory, String ... cmd) throws IOException {
	  System.out.println(String.format("%s> %s", directory, Stream.of(cmd).collect(joining(" "))));
	  new ProcessBuilder(cmd)
    	  .directory(directory)
    	  .inheritIO().start();
  }
  
  private static void addShutdownHook(File solrInstall, File solrHome, int port) {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        try {
          System.out.println("Shutting down SOLR");
          runProc(solrInstall,
        		  new File(solrInstall , solrCmd()).getAbsolutePath()
        		  ,"stop"
          		  ,"-p" // port
          		  , String.valueOf(port)
      			  );
        }
        catch (Exception e) {}
      }
    });
  }

 protected static String solrCmd() {
	switch (OperatingSystem.CURRENT) {
	case WINDOWS:
		return "/bin/solr.cmd";
	default:
		return "/bin/solr";
	}
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
    System.out.println("To exit type 'exit'");
  }

}
