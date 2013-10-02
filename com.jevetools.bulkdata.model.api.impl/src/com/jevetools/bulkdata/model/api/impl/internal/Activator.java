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
package com.jevetools.bulkdata.model.api.impl.internal;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import com.jevetools.bulkdata.model.api.service.BulkdataModelService;
import com.jevetools.commons.api.AbstractActivator;

/**
 * Copyright (c) 2013, jEVETools.
 * 
 * All rights reserved.
 * 
 * @version 0.0.1
 * @since 0.0.1
 */
public final class Activator
    extends AbstractActivator
{
  @Override
  public void start(final BundleContext aContext) throws Exception // NOPMD
  {
    final ServiceReference<ConfigurationAdmin> reference = getSrvReference(
        aContext, ConfigurationAdmin.class);

    final ConfigurationAdmin service = getService(aContext, reference);

    final Configuration configuration = getSrvConfiguration(service,
        BulkdataModelService.PID);

    final Dictionary<String, Object> properties = getProperties(aContext
        .getBundle());

    setDirectory(properties);

    updateConfiguration(configuration, properties);
  }

  @Override
  public void stop(final BundleContext aContext) throws Exception // NOPMD
  {
    // NOOP
  }

  /**
   * Sets the {@link BulkdataModelService#DIRECTORY} from the enviroment.
   * 
   * @param aProperties {@link Dictionary}
   */
  private void setDirectory(final Dictionary<String, Object> aProperties)
  {
    final Object object = aProperties.get(BulkdataModelService.DIRECTORY);
    
    if (object != null)
    {
      final String directory = (String) object;
      
      if (directory.isEmpty())
      {
        final String envDirectory = System.getenv("BULKDATA_DIRECTORY");

        if (envDirectory != null) // NOPMD
        {
          if (!envDirectory.isEmpty()) // NOPMD
          {
            aProperties.put(BulkdataModelService.DIRECTORY, envDirectory);
          }
        }
      }
    }
  }

}
