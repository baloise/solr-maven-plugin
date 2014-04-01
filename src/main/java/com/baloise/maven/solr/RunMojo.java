package com.baloise.maven.solr;

import static com.baloise.maven.solr.JettyRunner.runJetty;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.component.repository.ComponentDependency;

@Mojo(name = "run", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class RunMojo extends AbstractMojo {
 
  static final String SOLR_GROUP_ID = "org.apache.solr";
  static final String SOLR_ARTIFACT_ID = "solr";

  @Parameter(defaultValue = "${basedir}/src/solr/resources", property = "solr.home", required = true)
  private File home;
  
  @Parameter(defaultValue = "8983", property = "solr.port", required = true)
  int port;

  @Parameter(defaultValue = "/solr", property = "solr.context", required = true)
  String context;
  
  @Parameter(property = "solr.war", required = false)
  String war = null;

  @Component
  private Settings settings;
  
  @Component
  private PluginDescriptor plugin;
  
  public void execute() throws MojoExecutionException {
      if(war == null) war = getSolrWarLocation().getAbsolutePath();
      if(context.charAt(0) != '/') context = "/"+context;
      getLog().info("solr.home: "+home.getAbsolutePath());
      getLog().info("solr.port: "+port);
      getLog().info("solr.context: "+context);
      getLog().info("solr.war: "+war);
      try {
        runJetty(home, context, port, war);
      }
      catch (Exception e) {
       getLog().error(e);
      }
    }

  File getSolrWarLocation() {
    File repo = new File(settings.getLocalRepository());
    List<ComponentDependency> dependencies = plugin.getDependencies();
    for (ComponentDependency dependency : dependencies) {
      if(SOLR_GROUP_ID.equals(dependency.getGroupId()) && SOLR_ARTIFACT_ID.equals(dependency.getArtifactId())) {
        return new File(repo, makePath(dependency));
      }
    }
    return null;
  }

  String makePath(ComponentDependency dependency) {
    return String.format("%s/%s/%s/%s-%s.%s", 
        dependency.getGroupId().replace(".", "/"),
        dependency.getArtifactId(),
        dependency.getVersion(),
        dependency.getArtifactId(),
        dependency.getVersion(),
        dependency.getType()
        );
  }
  
}
