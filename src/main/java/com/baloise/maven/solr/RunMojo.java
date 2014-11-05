package com.baloise.maven.solr;

import static com.baloise.maven.solr.JettyRunner.runJetty;
import static java.lang.String.format;

import java.io.File;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.Settings;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;


@Mojo(name = "run", defaultPhase = LifecyclePhase.NONE, requiresProject=false)
public class RunMojo extends AbstractMojo {

  static final String SOLR_GROUP_ID = "org.apache.solr";
  static final String SOLR_ARTIFACT_ID = "solr";

  @Parameter(defaultValue = "${basedir}")
  private File private__basedir;

  @Parameter(property = "solr.home")
  private File home;
  
  @Parameter(defaultValue = "8983", property = "solr.port", required = true)
  int port;

  @Parameter(defaultValue = "/solr", property = "solr.context", required = true)
  String context;

  @Parameter(property = "solr.war", required = false)
  String war = null;
  
  @Parameter(defaultValue = "4.10.1", property = "solr.version", required = false)
  String version = null;

  @Component
  private Settings settings;

  @Component
  private PluginDescriptor plugin;

  @Component
  private RepositorySystem repoSystem;

  @Parameter(defaultValue = "${repositorySystemSession}")
  private RepositorySystemSession repoSession;

  @Parameter(defaultValue = "${project.remoteProjectRepositories}")
  private List<RemoteRepository> projectRepos;

  public void execute() throws MojoExecutionException, MojoFailureException {
    adjustHome();
    if (context.charAt(0) != '/') context = "/" + context;
    getLog().info("solr.home: " + home.getAbsolutePath());
    //TODO solr.home has to contain either solr.xml or collection1 core
    //TODO otherwise generate solr.xml on classpath or handle error gracefully
    getLog().info("solr.port: " + port);
    getLog().info("solr.context: " + context);
    getLog().info("solr.version: " + version);
    if (war == null) war =  resolve(SOLR_GROUP_ID+":"+SOLR_ARTIFACT_ID+":war:"+version).getArtifact().getFile().getAbsolutePath();
    getLog().info("solr.war: " + war);
    getLog().info(format("Starting SOLR server at http://%s:%s%s", getHostName(), port, context));
    try {
      runJetty(home, context, port, war);
    }
    catch (Exception e) {
      getLog().error(e);
    }
  }

  private void adjustHome() {
    if(home == null) {
      home = hasPom() ?  new File(private__basedir, "/src/solr/resources") : private__basedir;
    }
  }

  private String getHostName() {
    try {
      return java.net.InetAddress.getLocalHost().getHostName();
    }
    catch (UnknownHostException e) {
      return "localhost";
    }
  }
  
  private boolean hasPom() {
    return new File("pom.xml").exists();
  }

  public ArtifactResult resolve(String artifactCoords) throws MojoExecutionException, MojoFailureException {
    Artifact artifact;
    try {
      artifact = new DefaultArtifact(artifactCoords);
    }
    catch (IllegalArgumentException e) {
      throw new MojoFailureException(e.getMessage(), e);
    }

    ArtifactRequest request = new ArtifactRequest();
    request.setArtifact(artifact);
    request.setRepositories(projectRepos);

    getLog().info("Resolving artifact " + artifact + " from " + projectRepos);

    ArtifactResult result;
    try {
      result = repoSystem.resolveArtifact(repoSession, request);
    }
    catch (ArtifactResolutionException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }

    getLog().info("Resolved artifact " + artifact + " to " + result.getArtifact().getFile() + " from " + result.getRepository());
    return result;
  }

}
