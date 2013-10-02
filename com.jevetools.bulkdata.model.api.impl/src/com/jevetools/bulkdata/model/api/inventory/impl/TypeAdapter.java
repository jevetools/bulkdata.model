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
package com.jevetools.bulkdata.model.api.inventory.impl;

import com.jevetools.bulkdata.model.api.impl.internal.AbstractAdapter;
import com.jevetools.data.model.api.Entity;
import com.jevetools.data.model.api.inventory.Type;
import com.jevetools.data.model.api.inventory.impl.TypeImpl;
import com.jevetools.unmarshal.api.service.UnmarshalService;
import com.jevetools.unmarshal.python.api.PyBase;
import com.jevetools.unmarshal.python.api.PyDBRow;
import com.jevetools.unmarshal.python.api.PyDict;

/**
 * Copyright (c) 2013, jEVETools.
 * 
 * All rights reserved.
 * 
 * @version 0.0.1
 * @since 0.0.1
 */
public final class TypeAdapter
    extends AbstractAdapter<Type>
{
  /**
   * Static instance.
   */
  public static final AbstractAdapter<Type> INSTANCE = new TypeAdapter();

  @Override
  public boolean matches(final Class<? extends Entity> aClass)
  {
    return aClass.equals(Type.class);
  }

  @Override
  public Type adapt(final PyDBRow aPyDBRow, final UnmarshalService aService)
  {
    final PyDict map = getDBRowDict(aPyDBRow);

    final TypeImpl.Builder builder = new TypeImpl.Builder();

    final PyBase pyTypeID = getDictValue(aService, map, "typeID");
    final PyBase pyGroupID = getDictValue(aService, map, "groupID");
    final PyBase pyTypeName = getDictValue(aService, map, "typeName");
    final PyBase pyDescription = getDictValue(aService, map, "description");
    final PyBase pyGraphicID = getDictValue(aService, map, "graphicID");
    final PyBase pyRadius = getDictValue(aService, map, "radius");
    final PyBase pyMass = getDictValue(aService, map, "mass");
    final PyBase pyVolume = getDictValue(aService, map, "volume");
    final PyBase pyCapacity = getDictValue(aService, map, "capacity");
    final PyBase pyPortionSize = getDictValue(aService, map, "portionSize");
    final PyBase pyRaceID = getDictValue(aService, map, "raceID");
    final PyBase pyBasePrice = getDictValue(aService, map, "basePrice");
    final PyBase pyPublished = getDictValue(aService, map, "published");
    final PyBase pyMarketGroupID = getDictValue(aService, map, "marketGroupID");
    final PyBase pyChance = getDictValue(aService, map, "chanceOfDuplicating");
    final PyBase pySoundID = getDictValue(aService, map, "soundID");
    final PyBase pyIconID = getDictValue(aService, map, "iconID");

    builder.typeID(getIntValue(pyTypeID));
    builder.groupID(getIntValue(pyGroupID));
    builder.typeName(getStringValue(pyTypeName));
    builder.description(getStringValue(pyDescription));
    builder.graphicID(getIntValue(pyGraphicID));
    builder.radius(getDoubleValue(pyRadius));
    builder.mass(getDoubleValue(pyMass));
    builder.volume(getDoubleValue(pyVolume));
    builder.capacity(getDoubleValue(pyCapacity));
    builder.portionSize(getIntValue(pyPortionSize));
    builder.raceID(getByteValue(pyRaceID));
    builder.basePrice(getLongValue(pyBasePrice));
    builder.published(getBooleanValue(pyPublished));
    builder.marketGroupID(getIntValue(pyMarketGroupID));
    builder.chanceOfDuplicating(getDoubleValue(pyChance));
    builder.soundID(getIntValue(pySoundID));
    builder.iconID(getIntValue(pyIconID));

    return builder.build();
  }
}
