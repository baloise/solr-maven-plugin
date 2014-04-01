package org.apache.solr.client.solrj.embedded;

import java.util.SortedMap;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * 
 */
public class PermissiveJettySolrRunner extends JettySolrRunner {

  /**
   * @param solrHome
   * @param context
   * @param port
   */
  public PermissiveJettySolrRunner(String solrHome, String context, int port) {
    super(solrHome, context, port);
  }

  /**
   * @param solrHome
   * @param context
   * @param port
   * @param solrConfigFilename
   * @param schemaFileName
   */
  public PermissiveJettySolrRunner(String solrHome, String context, int port, String solrConfigFilename,
      String schemaFileName) {
    super(solrHome, context, port, solrConfigFilename, schemaFileName);
  }

  /**
   * @param solrHome
   * @param context
   * @param port
   * @param solrConfigFilename
   * @param schemaFileName
   * @param stopAtShutdown
   */
  public PermissiveJettySolrRunner(String solrHome, String context, int port, String solrConfigFilename,
      String schemaFileName, boolean stopAtShutdown) {
    super(solrHome, context, port, solrConfigFilename, schemaFileName, stopAtShutdown);
  }

  /**
   * @param solrHome
   * @param context
   * @param port
   * @param solrConfigFilename
   * @param schemaFileName
   * @param stopAtShutdown
   * @param extraServlets
   */
  public PermissiveJettySolrRunner(String solrHome, String context, int port, String solrConfigFilename,
      String schemaFileName, boolean stopAtShutdown, SortedMap<ServletHolder, String> extraServlets) {
    super(solrHome, context, port, solrConfigFilename, schemaFileName, stopAtShutdown, extraServlets);
  }

  /**
   * @param solrHome
   * @param context
   * @param port
   * @param solrConfigFilename
   * @param schemaFileName
   * @param stopAtShutdown
   * @param extraServlets
   * @param sslConfig
   */
  public PermissiveJettySolrRunner(String solrHome, String context, int port, String solrConfigFilename,
      String schemaFileName, boolean stopAtShutdown, SortedMap<ServletHolder, String> extraServlets, SSLConfig sslConfig) {
    super(solrHome, context, port, solrConfigFilename, schemaFileName, stopAtShutdown, extraServlets, sslConfig);
  }

  /**
   * @param solrHome
   * @param context
   * @param port
   * @param solrConfigFilename
   * @param schemaFileName
   * @param stopAtShutdown
   * @param extraServlets
   * @param sslConfig
   * @param extraRequestFilters
   */
  public PermissiveJettySolrRunner(String solrHome, String context, int port, String solrConfigFilename,
      String schemaFileName, boolean stopAtShutdown, SortedMap<ServletHolder, String> extraServlets,
      SSLConfig sslConfig, SortedMap<Class, String> extraRequestFilters) {
    super(solrHome, context, port, solrConfigFilename, schemaFileName, stopAtShutdown, extraServlets, sslConfig,
        extraRequestFilters);
  }
  
  
  public Server getServer() {
    return server;
  }

}
