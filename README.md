# solr-maven-plugin

A maven plugin to help develop and deploy SOLR based applications.

The design goals are

- keep your POM clean: no dependencies
- keep your workspace clean : flexibilty in layout with good defaults
 
It is planned to support deploy life cycle too (create, update, delete, migrate cores on production servers).

## Quick start

Prerequisite
- Maven is working. That's all. No pom.xml required.

lets go some where safe ...

`> mkdir /tmp/solr-test; cd /tmp/solr-test`

init a core ...

`> mvn com.baloise.maven:solr-maven-plugin:init`

run that ...

`> mvn com.baloise.maven:solr-maven-plugin:run`

go play at [http://localhost:8983/solr/](http://localhost:8983/solr/)

shut down via 

[http://localhost:8983/solr/shutdown](http://localhost:8983/solr/shutdown)

or type *exit* in the console (also works when launched in eclipse via m2 or external task)

## Make your life easier with plugin groups

Add the following to your *~/.m2/settings.xml*

```
<pluginGroups>
	<pluginGroup>com.baloise.maven</pluginGroup>
</pluginGroups>
```

now you can use

`> mvn solr:run`

[(tell me more)](http://maven.apache.org/guides/introduction/introduction-to-plugin-prefix-mapping.html#Configuring_Maven_to_Search_for_Plugins)

## Configuration options (TBD)

Of course you have all the options as where to set the properties


  @Parameter(defaultValue = "${basedir}/src/solr/resources", property = "solr.home", required = true)
  private File home;
  
  @Parameter(defaultValue = "8983", property = "solr.port", required = true)
  int port;

[(tell me more)](http://docs.codehaus.org/display/MAVENUSER/MavenPropertiesGuide)


## Similar projects

[Boris Naguets solr-maven-plugin](https://github.com/BorisNaguet/solr-maven-plugin)
