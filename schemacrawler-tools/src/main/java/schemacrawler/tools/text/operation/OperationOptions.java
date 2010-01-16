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

package schemacrawler.tools.text.operation;


import schemacrawler.schemacrawler.Config;
import schemacrawler.schemacrawler.Options;

/**
 * Operator options.
 * 
 * @author Sualeh Fatehi
 */
public final class OperationOptions
  implements Options
{

  private static final long serialVersionUID = -7977434852526746391L;

  private static final String SHOW_LOBS = "schemacrawler.data.show_lobs";
  private static final String MERGE_ROWS = "schemacrawler.data.merge_rows";

  private boolean mergeRows;
  private boolean showLobs;

  /**
   * Operator options, defaults.
   */
  public OperationOptions()
  {
    this(null);
  }

  /**
   * Operator options from properties. Constructor.
   * 
   * @param outputOptions
   *        Output options
   * @param operation
   *        Operation
   * @param config
   *        Config
   */
  public OperationOptions(final Config config)
  {
    if (config == null)
    {
      mergeRows = false;
      showLobs = false;
    }
    else
    {
      mergeRows = config.getBooleanValue(MERGE_ROWS);
      showLobs = config.getBooleanValue(SHOW_LOBS);
    }
  }

  /**
   * Whether to merge similar rows.
   * 
   * @return Whether to merge similar rows.
   */
  public boolean isMergeRows()
  {
    return mergeRows;
  }

  /**
   * Whether to show LOBs.
   * 
   * @return Whether to show LOBs.
   */
  public boolean isShowLobs()
  {
    return showLobs;
  }

  /**
   * Whether to merge similar rows.
   * 
   * @param mergeRows
   *        Whether to merge similar rows
   */
  public void setMergeRows(final boolean mergeRows)
  {
    this.mergeRows = mergeRows;
  }

  /**
   * Whether to show LOBs.
   * 
   * @param showLobs
   *        Whether to show LOBs
   */
  public void setShowLobs(final boolean showLobs)
  {
    this.showLobs = showLobs;
  }

}
