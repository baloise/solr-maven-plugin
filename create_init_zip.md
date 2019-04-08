Download relase from http://archive.apache.org/dist/lucene/solr/
cd <unzipped>

delete contents of <unzipped>\example\techproducts\solr\techproducts\data

bin\solr -e techproducts


http://localhost:8983/solr/#/techproducts/documents
insert '''
{
        "id":"com.baloise.maven:solr-maven-plugin",
        "name":"Solr Maven Plugin",
        "manu":"Baloise AG",
        "cat":["software",
          "maven plugin"],
        "features":["keep your POM clean: no dependencies",
          "keep your workspace clean : flexibilty in layout with good defaults"],
        "price":0.0,
        "price_c":"0,USD",
        "popularity":10,
        "inStock":true
 }
'''


bin\solr stop -p 8983

in  <unzipped>\example\techproducts\solr\techproducts\core.properties set name=com.baloise.example
rename/move  <unzipped>\example\techproducts\solr\techproducts\ to com.baloise.examle
zip com folder into init<majorversion>.zip