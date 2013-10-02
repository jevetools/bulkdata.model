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
package com.jevetools.bulkdata.model.api.service.impl; // NOPMD

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.log.LogService;

import com.jevetools.bulkdata.model.api.impl.internal.AbstractAdapter;
import com.jevetools.bulkdata.model.api.inventory.impl.BlueprintAdapter;
import com.jevetools.bulkdata.model.api.inventory.impl.CategoryAdapter;
import com.jevetools.bulkdata.model.api.inventory.impl.GroupAdapter;
import com.jevetools.bulkdata.model.api.inventory.impl.MaterialAdapter;
import com.jevetools.bulkdata.model.api.inventory.impl.MetaGroupAdapter;
import com.jevetools.bulkdata.model.api.inventory.impl.MetaTypeAdapter;
import com.jevetools.bulkdata.model.api.inventory.impl.ReactionAdapter;
import com.jevetools.bulkdata.model.api.inventory.impl.TypeAdapter;
import com.jevetools.bulkdata.model.api.pi.impl.PiMaterialAdapter;
import com.jevetools.bulkdata.model.api.pi.impl.PiPinTypeAdapter;
import com.jevetools.bulkdata.model.api.pi.impl.PiSchematicAdapter;
import com.jevetools.bulkdata.model.api.ram.impl.ActivityAdapter;
import com.jevetools.bulkdata.model.api.ram.impl.TypeRequirementAdapter;
import com.jevetools.bulkdata.model.api.service.BulkdataModelService;
import com.jevetools.data.model.api.Entity;
import com.jevetools.unmarshal.api.ParseException;
import com.jevetools.unmarshal.api.service.UnmarshalService;
import com.jevetools.unmarshal.python.api.PyBase;
import com.jevetools.unmarshal.python.api.PyDBRow;
import com.jevetools.unmarshal.python.api.PyObjectEx;

/**
 * Copyright (c) 2013, jEVETools.
 * 
 * All rights reserved.
 * 
 * @version 0.0.1
 * @since 0.0.1
 */
public final class BulkdataModelServiceImpl // NOPMD
    implements BulkdataModelService, ManagedService // NOPMD
{ // NOPMD
  /**
   * {@link LogService} reference.
   */
  private transient LogService mLogService;

  /**
   * Directory where all jEVETools related files should be stored.
   */
  private transient Path mDirectory;

  /**
   * {@link UnmarshalService} reference.
   */
  private transient UnmarshalService mUnmarshalService;

  /**
   * @param aService
   *            {@link UnmarshalService}
   * @since 0.0.1
   */
  protected void bindUnmarshalService(final UnmarshalService aService)
  {
    mUnmarshalService = aService;
  }

  /**
   * @param aService
   *            {@link UnmarshalService}
   * @since 0.0.1
   */
  protected void unbindUnmarshalService(final UnmarshalService aService)
  {
    if (mUnmarshalService == aService) // NOPMD
    {
      mUnmarshalService = null; // NOPMD
    }
  }

  /**
   * @param aService
   *            {@link LogService} reference
   * @since 0.0.1
   */
  protected void bindLogService(final LogService aService)
  {
    mLogService = aService;
  }

  /**
   * @param aService
   *            {@link LogService} reference
   * @since 0.0.1
   */
  protected void unbindLogService(final LogService aService)
  {
    if (mLogService == aService) // NOPMD
    {
      mLogService = null; // NOPMD
    }
  }

  @Override
  public Path getDirectory()
  {
    return mDirectory;
  }

  @Override
  public <T extends Entity> List<T> load(final Class<T> aClass)
      throws IOException
  {
    final String cacheFile = getCacheFile(aClass);

    final PyBase pyBase = read(Paths.get(mDirectory.toString(), cacheFile));

    final List<T> list = new ArrayList<T>();

    final AbstractAdapter<T> adapter = getAdapter(aClass);

    if (adapter == null)
    {
      mUnmarshalService.dump(pyBase, System.out);
    }
    else
    {
      try
      {
        final Iterator<PyBase> rows = getRows(pyBase);

        while (rows.hasNext())
        {
          final PyBase pyRow = rows.next();

          final PyDBRow dbRow = convertToPyDBRow(pyRow);

          final T item = convertDBRow(adapter, dbRow);

          list.add(item);
        }
      }
      catch (InvalidElementException exception)
      {
        log(exception);
      }
    }

    return list;
  }

  @Override
  public void updated(final Dictionary<String, ?> aProperties)
      throws ConfigurationException
  {
    if (aProperties != null) // NOPMD
    {
      setDirectory(aProperties);
    }
  }

  /**
   * @param <T> {@link Entity}
   * @param aClass
   *            {@link Class}
   * @return {@link Entity}
   * @since 0.0.1
   */
  @SuppressWarnings("unchecked")
  private <T extends Entity> AbstractAdapter<T> getAdapter(// NOPMD
      final Class<T> aClass) // NOPMD
  {
    if (TypeAdapter.INSTANCE.matches(aClass))
    {
      return (AbstractAdapter<T>) TypeAdapter.INSTANCE;
    }

    if (CategoryAdapter.INSTANCE.matches(aClass))
    {
      return (AbstractAdapter<T>) CategoryAdapter.INSTANCE;
    }

    if (GroupAdapter.INSTANCE.matches(aClass))
    {
      return (AbstractAdapter<T>) GroupAdapter.INSTANCE;
    }

    if (MaterialAdapter.INSTANCE.matches(aClass))
    {
      return (AbstractAdapter<T>) MaterialAdapter.INSTANCE;
    }

    if (MetaGroupAdapter.INSTANCE.matches(aClass))
    {
      return (AbstractAdapter<T>) MetaGroupAdapter.INSTANCE;
    }

    if (MetaTypeAdapter.INSTANCE.matches(aClass))
    {
      return (AbstractAdapter<T>) MetaTypeAdapter.INSTANCE;
    }

    if (ReactionAdapter.INSTANCE.matches(aClass))
    {
      return (AbstractAdapter<T>) ReactionAdapter.INSTANCE;
    }

    if (BlueprintAdapter.INSTANCE.matches(aClass))
    {
      return (AbstractAdapter<T>) BlueprintAdapter.INSTANCE;
    }

    if (PiSchematicAdapter.INSTANCE.matches(aClass))
    {
      return (AbstractAdapter<T>) PiSchematicAdapter.INSTANCE;
    }

    if (PiMaterialAdapter.INSTANCE.matches(aClass))
    {
      return (AbstractAdapter<T>) PiMaterialAdapter.INSTANCE;
    }

    if (PiPinTypeAdapter.INSTANCE.matches(aClass))
    {
      return (AbstractAdapter<T>) PiPinTypeAdapter.INSTANCE;
    }

    if (ActivityAdapter.INSTANCE.matches(aClass))
    {
      return (AbstractAdapter<T>) ActivityAdapter.INSTANCE;
    }

    if (TypeRequirementAdapter.INSTANCE.matches(aClass))
    {
      return (AbstractAdapter<T>) TypeRequirementAdapter.INSTANCE;
    }

    return null;
  }

  /**
   * @param aClass
   *            {@link Class}
   * @return {@link String}
   * @since 0.0.1
   */
  private String getCacheFile(final Class<? extends Entity> aClass) // NOPMD
  {
    if (TypeAdapter.INSTANCE.matches(aClass))
    {
      return "600004.cache2";
    }

    if (CategoryAdapter.INSTANCE.matches(aClass))
    {
      return "600001.cache2";
    }

    if (GroupAdapter.INSTANCE.matches(aClass))
    {
      return "600002.cache2";
    }

    if (MaterialAdapter.INSTANCE.matches(aClass))
    {
      return "600005.cache2";
    }

    if (MetaGroupAdapter.INSTANCE.matches(aClass))
    {
      return "600006.cache2";
    }

    if (MetaTypeAdapter.INSTANCE.matches(aClass))
    {
      return "600007.cache2";
    }

    if (ReactionAdapter.INSTANCE.matches(aClass))
    {
      return "600010.cache2";
    }

    if (BlueprintAdapter.INSTANCE.matches(aClass))
    {
      return "1200001.cache2";
    }

    if (PiSchematicAdapter.INSTANCE.matches(aClass))
    {
      return "7300004.cache2";
    }

    if (PiMaterialAdapter.INSTANCE.matches(aClass))
    {
      return "7300005.cache2";
    }

    if (PiPinTypeAdapter.INSTANCE.matches(aClass))
    {
      return "7300003.cache2";
    }

    if (ActivityAdapter.INSTANCE.matches(aClass))
    {
      return "1800003.cache2";
    }

    if (TypeRequirementAdapter.INSTANCE.matches(aClass))
    {
      return "1800001.cache2";
    }

    return "1800001.cache2";
  }

  /**
   * @param <T>
   *            type
   * @param aAdapter
   *            {@link AbstractAdapter}
   * @param aDBRow
   *            {@link PyDBRow}
   * @return {@link Entity}
   * @since 0.0.1
   */
  private <T extends Entity> T convertDBRow(final AbstractAdapter<T> aAdapter,
      final PyDBRow aDBRow)
  {
    return aAdapter.adapt(aDBRow, mUnmarshalService);
  }

  /**
   * @param aPyRow
   *            {@link PyBase}
   * @return {@link PyDBRow}
   * @throws InvalidElementException
   *             on error
   * @since 0.0.1
   */
  private PyDBRow convertToPyDBRow(final PyBase aPyRow)
      throws InvalidElementException
  {
    if (!aPyRow.isDBRow())
    {
      throw new InvalidElementException("Expected PyDBRow got "
          + aPyRow.getPyType());
    }

    return aPyRow.asDBRow();
  }

  /**
   * @param aException
   *            {@link InvalidElementException}
   * @since 0.0.1
   */
  private void log(final InvalidElementException aException)
  {
    mLogService.log(LogService.LOG_ERROR, aException.getMessage(), aException);
  }

  /**
   * @param aPyBase
   *            {@link PyBase}
   * @return {@link Iterator} of {@link PyBase}
   * @throws InvalidElementException
   *             on error
   * @since 0.0.1
   */
  private Iterator<PyBase> getRows(final PyBase aPyBase)
      throws InvalidElementException
  {
    if (!aPyBase.isObjectEx())
    {
      throw new InvalidElementException("Expected ObjectEx got "
          + aPyBase.getPyType());
    }

    final PyObjectEx asObjectEx = aPyBase.asObjectEx();

    return getIterator(asObjectEx);
  }

  /**
   * @param aPyObjectEx
   *            {@link PyObjectEx}
   * @return {@link Iterator} of {@link PyBase}
   * @since 0.0.1
   */
  private Iterator<PyBase> getIterator(final PyObjectEx aPyObjectEx)
  {
    return aPyObjectEx.getList().iterator();
  }

  /**
   * @param aPath
   *            {@link Path}
   * @return {@link PyBase}
   * @throws IOException
   *             on error
   * @since 0.0.1
   */
  private PyBase read(final Path aPath) throws IOException
  {
    try (final InputStream inputStream = Files.newInputStream(aPath))
    {
      return read(inputStream);
    }
  }

  /**
   * @param aInputStream
   *            {@link InputStream}
   * @return {@link PyBase}
   * @throws IOException
   *             on error
   * @since 0.0.1
   */
  private PyBase read(final InputStream aInputStream) throws IOException
  {
    try
    {
      return mUnmarshalService.read(aInputStream);
    }
    catch (ParseException exception)
    {
      throw new IOException("Unable to read from stream", exception);
    }
  }

  /**
   * @param aProperties
   *            properties
   * 
   * @since 0.0.1
   */
  private void setDirectory(final Dictionary<String, ?> aProperties)
  {
    mDirectory = Paths.get((String) aProperties.get("directory"));
  }
}
