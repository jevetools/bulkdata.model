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

import com.jevetools.data.model.api.Entity;
import com.jevetools.unmarshal.api.service.UnmarshalService;
import com.jevetools.unmarshal.python.api.PyBase;
import com.jevetools.unmarshal.python.api.PyBool;
import com.jevetools.unmarshal.python.api.PyByte;
import com.jevetools.unmarshal.python.api.PyDBRow;
import com.jevetools.unmarshal.python.api.PyDict;
import com.jevetools.unmarshal.python.api.PyDouble;
import com.jevetools.unmarshal.python.api.PyInt;
import com.jevetools.unmarshal.python.api.PyLong;
import com.jevetools.unmarshal.python.api.PyShort;
import com.jevetools.unmarshal.python.api.PyString;

/**
 * Copyright (c) 2013, jEVETools.
 * 
 * All rights reserved.
 * 
 * @param <T>
 *            type
 * 
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class AbstractAdapter<T extends Entity>
{
  /**
   * @param aClass {@link Class}
   * @return <code>true</code> otherwise <code>false</code>
   * @since 0.0.1
   */
  public abstract boolean matches(final Class<? extends Entity> aClass);

  /**
   * @param aPyDBRow
   *            {@link PyDBRow}
   * @param aService
   *            {@link UnmarshalService}
   * @return <T> instance
   * @since 0.0.1
   */
  public abstract T adapt(final PyDBRow aPyDBRow,
      final UnmarshalService aService);

  /**
   * @param aPyBase
   *            {@link PyBase}
   * @return byte value
   * @since 0.0.1
   */
  protected final int getByteValue(final PyBase aPyBase)
  {
    if (!aPyBase.isByte())
    {
      throw new IllegalArgumentException("Expected PyByte got "
          + aPyBase.getPyType());
    }

    return getValue(aPyBase.asByte());
  }

  /**
   * @param aPyBase
   *            {@link PyBase}
   * @return int value
   * @since 0.0.1
   */
  protected final int getShortValue(final PyBase aPyBase)
  {
    if (!aPyBase.isShort())
    {
      throw new IllegalArgumentException("Expected PyShort got "
          + aPyBase.getPyType());
    }

    return getValue(aPyBase.asShort());
  }

  /**
   * @param aPyBase
   *            {@link PyBase}
   * @return int value
   * @since 0.0.1
   */
  protected final int getIntValue(final PyBase aPyBase)
  {
    if (!aPyBase.isInt())
    {
      throw new IllegalArgumentException("Expected PyInt got "
          + aPyBase.getPyType());
    }

    return getValue(aPyBase.asInt());
  }

  /**
   * @param aPyBase
   *            {@link PyBase}
   * @return long value
   * @since 0.0.1
   */
  protected final long getLongValue(final PyBase aPyBase)
  {
    if (!aPyBase.isLong())
    {
      throw new IllegalArgumentException("Expected PyLong got "
          + aPyBase.getPyType());
    }

    return getValue(aPyBase.asLong());
  }

  /**
   * @param aPyBase
   *            {@link PyBase}
   * @return long value
   * @since 0.0.1
   */
  protected final String getStringValue(final PyBase aPyBase)
  {
    if (!aPyBase.isString() && !aPyBase.isNone())
    {
      throw new IllegalArgumentException("Expected PyString got "
          + aPyBase.getPyType());
    }

    if (aPyBase.isNone())
    {
      return ""; // NOPMD
    }

    return getValue(aPyBase.asString()); // NOPMD
  }

  /**
   * @param aPyBase
   *            {@link PyBase}
   * @return boolean value
   * @since 0.0.1
   */
  protected final boolean getBooleanValue(final PyBase aPyBase)
  {
    if (!aPyBase.isBool())
    {
      throw new IllegalArgumentException("Expected PyBool got "
          + aPyBase.getPyType());
    }

    return getValue(aPyBase.asBool());
  }

  /**
   * @param aPyBase
   *            {@link PyBase}
   * @return boolean value
   * @since 0.0.1
   */
  protected final double getDoubleValue(final PyBase aPyBase)
  {
    if (!aPyBase.isDouble())
    {
      throw new IllegalArgumentException("Expected PyDouble got "
          + aPyBase.getPyType());
    }

    return getValue(aPyBase.asDouble());
  }

  /**
   * @param aPyByte
   *            {@link PyByte}
   * @return byte value
   * @since 0.0.1
   */
  private byte getValue(final PyByte aPyByte)
  {
    return aPyByte.value();
  }

  /**
   * @param aPyShort
   *            {@link PyShort}
   * @return byte value
   * @since 0.0.1
   */
  private short getValue(final PyShort aPyShort) // NOPMD
  {
    return aPyShort.value();
  }

  /**
   * @param aPyInt
   *            {@link PyInt}
   * @return int value
   * @since 0.0.1
   */
  private int getValue(final PyInt aPyInt)
  {
    return aPyInt.value();
  }

  /**
   * @param aPyDouble
   *            {@link PyDouble}
   * @return int value
   * @since 0.0.1
   */
  private double getValue(final PyDouble aPyDouble)
  {
    return aPyDouble.value();
  }

  /**
   * @param aPyString
   *            {@link PyString}
   * @return {@link String}
   * @since 0.0.1
   */
  private String getValue(final PyString aPyString)
  {
    return aPyString.value();
  }

  /**
   * @param aPyBool
   *            {@link PyBool}
   * @return {@link String}
   * @since 0.0.1
   */
  private boolean getValue(final PyBool aPyBool)
  {
    return aPyBool.value();
  }

  /**
   * @param aPyLong
   *            {@link PyLong}
   * @return long value
   * @since 0.0.1
   */
  private long getValue(final PyLong aPyLong)
  {
    return aPyLong.value();
  }

  /**
   * @param aService
   *            {@link UnmarshalService}
   * @param aDict
   *            {@link PyDict}
   * @param aKey
   *            {@link String}
   * @return {@link PyBase}
   * @since 0.0.1
   */
  protected final PyBase getDictValue(final UnmarshalService aService,
      final PyDict aDict, final String aKey)
  {
    return aDict.get(aService.getDictKey(aKey));
  }

  /**
   * @param aDBRow
   *            {@link PyDBRow}
   * @return {@link PyDict}
   * @since 0.0.1
   */
  protected final PyDict getDBRowDict(final PyDBRow aDBRow)
  {
    return aDBRow.getDict();
  }

}