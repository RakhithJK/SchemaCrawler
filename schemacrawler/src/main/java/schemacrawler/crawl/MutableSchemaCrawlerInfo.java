/* 
 *
 * SchemaCrawler
 * http://sourceforge.net/projects/schemacrawler
 * Copyright (c) 2000-2010, Sualeh Fatehi.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 */

package schemacrawler.crawl;


import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import schemacrawler.Version;
import schemacrawler.schema.SchemaCrawlerInfo;
import schemacrawler.schemacrawler.Config;

/**
 * SchemaCrawler information.
 * 
 * @author Sualeh Fatehi sualeh@hotmail.com
 */
final class MutableSchemaCrawlerInfo
  implements SchemaCrawlerInfo
{

  private static final long serialVersionUID = 4051323422934251828L;

  private static final String NEWLINE = System.getProperty("line.separator");

  private String schemaCrawlerProductName;
  private String schemaCrawlerVersion;
  private String schemaCrawlerAbout;
  /** Needs to be sorted, so serialization does not break. */
  private final Map<String, String> systemProperties = new LinkedHashMap<String, String>();

  public String getSchemaCrawlerAbout()
  {
    return schemaCrawlerAbout;
  }

  public String getSchemaCrawlerProductName()
  {
    return schemaCrawlerProductName;
  }

  public String getSchemaCrawlerVersion()
  {
    return schemaCrawlerVersion;
  }

  /**
   * {@inheritDoc}
   * 
   * @see schemacrawler.schema.SchemaCrawlerInfo#getSystemProperties()
   */
  public Map<String, String> getSystemProperties()
  {
    return Collections.unmodifiableMap(systemProperties);
  }

  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public String toString()
  {
    final StringBuilder info = new StringBuilder();
    info.append("-- generated by: ").append(getSchemaCrawlerProductName())
      .append(" ").append(getSchemaCrawlerVersion()).append(NEWLINE);
    return info.toString();
  }

  void setAdditionalSchemaCrawlerInfo()
  {
    systemProperties.putAll(new Config(System.getProperties()));
  }

  void setSchemaCrawlerInfo()
  {
    schemaCrawlerProductName = Version.getProductName();
    schemaCrawlerVersion = Version.getVersion();
    schemaCrawlerAbout = Version.about();
  }

}
