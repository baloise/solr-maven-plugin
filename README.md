solr-maven-plugin
=================

A maven plugin to help develop and deploy SOLR based applications.

The design goals are

- keep your POM clean: no dependencies
- keep your workspace clean : flexibilty in layout with good defaults
 
It is planned to support deploy life cycle too (create, update, delete, migrate cores on production servers).

Quick start
-----------

Prerequisites
- Maven installed
- a solr core to play with


> mvn -DinteractiveMode=false -DarchetypeCatalog=http://sourcesense.github.com/solr-packager/archetype-catalog.xml -DarchetypeGroupId=com.sourcesense.solr -DarchetypeArtifactId=solr-standalone-archetype -DgroupId=com.example -DartifactId=solrTest -Dversion=0.0.1-SNAPSHOT archetype:generate


mvn archetype:generate -DarchetypeArtifactId=maven-archetype-quickstart -DgroupId=com.example -DartifactId=solrTest -Dversion=0.0.1-SNAPSHOT
cd solrTest

mvn com.baloise.maven:solr-maven-plugin:run -Dsolr.home=C:/Users/Public/dev/solr-4.7.0/example/solr

Of course you have all the options as where to set the properties


  @Parameter(defaultValue = "${basedir}/src/solr/resources", property = "solr.home", required = true)
  private File home;
  
  @Parameter(defaultValue = "8983", property = "solr.port", required = true)
  int port;

  @Parameter(defaultValue = "/solr", property = "solr.context", required = true)
  String context;
  
  @Parameter(property = "solr.war", required = false)
  String war = null;
  
  
  
Shutting down
via http://localhost:8983/solr/shutdown
type exit in the console (also works when launched in eclipse via m2 or external task)

