java -classpath ../../_schemacrawler/config:$(echo ../../_schemacrawler/lib/*.jar | tr ' ' ':'):../../_schemacrawler/config schemacrawler.Main --server=hsqldb --database=schemacrawler --user=sa --password= --info-level=standard -c=tables.select "$*"
