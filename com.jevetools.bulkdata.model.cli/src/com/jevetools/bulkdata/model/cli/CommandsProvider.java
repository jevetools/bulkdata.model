/*
 * Copyright (c) 2013, jEVETools
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the names of the contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.jevetools.bulkdata.model.cli;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import com.jevetools.bulkdata.model.api.service.BulkdataModelService;
import com.jevetools.commons.api.AbstractUtility;

/**
 * Copyright (c) 2013, jEVETools.
 * 
 * All rights reserved.
 * 
 * @version 0.0.1
 * @since 0.0.1
 */
public final class CommandsProvider
    extends AbstractUtility
    implements CommandProvider
{
  /**
   * @param aInterpreter {@link CommandInterpreter}
   * @return <code>null</code>
   */
  public Object _evebulkdatadir(final CommandInterpreter aInterpreter)
  {
    final String directory = aInterpreter.nextArgument();

    if (directory != null) // NOPMD
    {
      try
      {
        setBulkdataDirectory(directory, aInterpreter);
      }
      catch (IOException exception)
      {
        throw new IllegalStateException(exception);
      }
    }

    try
    {
      final File current = getBulkdataDirectory();

      aInterpreter.println("Bulkdata directory: " + getAbsolutePath(current));
    }
    catch (IOException exception)
    {
      throw new IllegalStateException(exception);
    }

    return null;
  }

  /**
   * @param aDirectory String
   * @param aInterpreter {@link CommandInterpreter}
   * @throws IOException on error
   */
  private void setBulkdataDirectory(final String aDirectory,
      final CommandInterpreter aInterpreter) throws IOException
  {
    final File directory = new File(aDirectory);

    if (!directory.exists() || !directory.isDirectory())
    {
      aInterpreter.println("Invalid bulkdata directory: " + aDirectory);
    }

    final Bundle bundle = getBundle();

    final BundleContext bundleContext = getBundleContext(bundle);

    final ServiceReference<ConfigurationAdmin> reference = getSrvReference(
        bundleContext, ConfigurationAdmin.class);

    final ConfigurationAdmin service = getService(bundleContext, reference);

    final Configuration configuration = getSrvConfiguration(service,
        BulkdataModelService.PID);

    final Dictionary<String, Object> properties = getProperties(configuration);

    setBulkdataDirectory(aDirectory, properties);

    updateConfiguration(configuration, properties);
  }

  /**
   * @param aDirectory {@link String}
   * @param aProperties {@link Dictionary}
   */
  private void setBulkdataDirectory(final String aDirectory,
      final Dictionary<String, Object> aProperties)
  {
    aProperties.put(BulkdataModelService.DIRECTORY, aDirectory);
  }

  /**
   * @param aFile {@link File}
   * @return {@link String}
   */
  private String getAbsolutePath(final File aFile)
  {
    if (aFile != null)
    {
      return aFile.getAbsolutePath();
    }

    return "null";
  }

  /**
   * @return {@link File}
   * @throws IOException on error
   */
  private File getBulkdataDirectory() throws IOException
  {
    final Bundle bundle = getBundle();

    final BundleContext bundleContext = getBundleContext(bundle);

    final ServiceReference<ConfigurationAdmin> reference = getSrvReference(
        bundleContext, ConfigurationAdmin.class);

    final ConfigurationAdmin service = getService(bundleContext, reference);

    final Configuration configuration = getSrvConfiguration(service,
        BulkdataModelService.PID);

    final Dictionary<String, Object> properties = getProperties(configuration);

    final String bulkdataDirectory = getBulkdataDirectory(properties);

    if (bulkdataDirectory != null)
    {
      return new File((String) bulkdataDirectory);
    }

    return null;
  }

  /**
   * @param aProperties {@link Dictionary}
   * @return {@link String}
   */
  private String getBulkdataDirectory(
      final Dictionary<String, Object> aProperties)
  {
    if (aProperties != null)
    {
      final Object object = aProperties.get(BulkdataModelService.DIRECTORY);

      if (object != null)
      {
        return (String) object;
      }
    }

    return null;
  }

  /**
   * @param aConfiguration {@link Configuration}
   * @return {@link Dictionary}
   */
  private Dictionary<String, Object> getProperties(
      final Configuration aConfiguration)
  {
    return aConfiguration.getProperties();
  }

  /**
   * @param aBundle {@link Bundle}
   * @return {@link BundleContext}
   */
  private BundleContext getBundleContext(final Bundle aBundle)
  {
    return aBundle.getBundleContext();
  }

  /**
   * @return {@link Bundle}
   */
  private Bundle getBundle()
  {
    return FrameworkUtil.getBundle(this.getClass()); // NOPMD
  }

  @Override
  public String getHelp()
  {
    final StringBuffer buffer = new StringBuffer(75);

    buffer.append("---EVE Online Tools---\n"
        + "\tevebulkdatadir [directory] - Bulkdata directory\n");

    return buffer.toString();
  }
}
