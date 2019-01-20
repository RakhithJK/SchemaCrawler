package schemacrawler.crawl;


import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import schemacrawler.BaseProductVersion;
import schemacrawler.schema.SchemaReference;
import schemacrawler.schema.Table;

public class EqualsTest
{

  @Test
  public void baseProductVersion()
  {
    EqualsVerifier.forClass(BaseProductVersion.class)
      .suppress(Warning.STRICT_INHERITANCE).verify();
  }

  @Test
  public void namedObject()
  {
    EqualsVerifier.forClass(AbstractNamedObject.class)
      .suppress(Warning.STRICT_INHERITANCE).verify();
  }

  @Test
  public void namedObjectWithAttributes()
  {
    EqualsVerifier.forClass(AbstractNamedObjectWithAttributes.class)
      .withIgnoredFields("remarks", "attributeMap")
      .suppress(Warning.STRICT_INHERITANCE).verify();
  }

  @Test
  public void privilege()
  {
    final Table table1 = new MutableTable(new SchemaReference("catalog",
                                                              "schema"),
                                          "table1");
    final Table table2 = new MutableTable(new SchemaReference("catalog",
                                                              "schema"),
                                          "table2");

    EqualsVerifier.forClass(MutablePrivilege.class)
      .withIgnoredFields("remarks", "grants", "parent", "attributeMap")
      .withPrefabValues(DatabaseObjectReference.class,
                        new TableReference(table1),
                        new TableReference(table2))
      .suppress(Warning.STRICT_INHERITANCE).verify();
  }

}