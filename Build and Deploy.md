See 
http://central.sonatype.org/pages/ossrh-guide.html
http://central.sonatype.org/pages/apache-maven.html
see https://confluence.baloisenet.com/confluence/display/java/Baloise+Open+Source+Accounts

GPG setup

MVN settings 
<properties>
        <gpg.passphrase>****</gpg.passphrase>
<properties>

Install gnupg
Copy keys to %home%\.gnupg

Deploy plugin setup
MVN settings 
 <servers>
    <server>
      <id>sonatype-nexus-snapshots</id>
      <username>****</username>
      <password>****</password>
    </server>
    <server>
      <id>sonatype-nexus-staging</id>
      <username>****</username>
      <password>****</password>
    </server>
  </servers>
  
Performing a Snapshot Deployment
> mvn clean deploy

Performing a Release Deployment
> mvn versions:set -DnewVersion=1.2.3
> mvn clean install -P release
Once you have updated all the versions and ensured that your build passes without deployment you can perform the deployment with the usage of the release profile with
> mvn clean deploy -P release

Stage to central

> mvn nexus-staging:release
  