package com.baloise.maven.solr;

import static com.baloise.maven.solr.RunMojo.SOLR_ARTIFACT_ID;
import static com.baloise.maven.solr.RunMojo.SOLR_GROUP_ID;
import static org.junit.Assert.assertEquals;

import org.codehaus.plexus.component.repository.ComponentDependency;
import org.junit.Before;
import org.junit.Test;

public class RunMojoTest {

  @Test
  public void makePath() throws Exception {
    ComponentDependency dep = new ComponentDependency();
    dep.setGroupId(SOLR_GROUP_ID);
    dep.setArtifactId(SOLR_ARTIFACT_ID);
    dep.setVersion("4.7.0");
    dep.setType("war");
    assertEquals("org/apache/solr/solr/4.7.0/solr-4.7.0.war", new RunMojo().makePath(dep));
  }

}
