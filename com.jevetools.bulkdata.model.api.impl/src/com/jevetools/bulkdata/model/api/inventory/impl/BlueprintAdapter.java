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
import com.jevetools.data.model.api.inventory.Blueprint;
import com.jevetools.data.model.api.inventory.impl.BlueprintImpl;
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
public final class BlueprintAdapter
    extends AbstractAdapter<Blueprint>
{
  /**
   * Static instance.
   */
  public static final BlueprintAdapter INSTANCE = new BlueprintAdapter();

  @Override
  public boolean matches(final Class<? extends Entity> aClass)
  {
    return aClass.equals(Blueprint.class);
  }

  @Override
  public Blueprint adapt(final PyDBRow aPyDBRow, final UnmarshalService aSrv)
  {
    final PyDict map = getDBRowDict(aPyDBRow);

    final BlueprintImpl.Builder builder = new BlueprintImpl.Builder();

    final PyBase pyBlueprintTypeID = getDictValue(aSrv, map,
        "blueprintTypeID");
    final PyBase pyChance = getDictValue(aSrv, map,
        "chanceOfReverseEngineering");
    final PyBase pyMaterialModifier = getDictValue(aSrv, map,
        "materialModifier");
    final PyBase pyMaxProductionLimit = getDictValue(aSrv, map,
        "maxProductionLimit");
    final PyBase pyParentBlueprintTypeID = getDictValue(aSrv, map,
        "parentBlueprintTypeID");
    final PyBase pyProductTypeID = getDictValue(aSrv, map, "productTypeID");
    final PyBase pyProductionTime = getDictValue(aSrv, map,
        "productionTime");
    final PyBase pyProductivityModifier = getDictValue(aSrv, map,
        "productivityModifier");
    final PyBase pyResearchCopyTime = getDictValue(aSrv, map,
        "researchCopyTime");
    final PyBase pyResearchMaterialTime = getDictValue(aSrv, map,
        "researchMaterialTime");
    final PyBase pyResearchProductTime = getDictValue(aSrv, map,
        "researchProductivityTime");
    final PyBase pyResearchTechTime = getDictValue(aSrv, map,
        "researchTechTime");
    final PyBase pyTechLevel = getDictValue(aSrv, map, "techLevel");
    final PyBase pyWasteFactor = getDictValue(aSrv, map, "wasteFactor");

    builder.blueprintTypeID(getIntValue(pyBlueprintTypeID));
    builder.chanceOfReverseEngineering(getDoubleValue(pyChance));
    builder.materialModifier(getShortValue(pyMaterialModifier));
    builder.maxProductionLimit(getIntValue(pyMaxProductionLimit));
    builder.parentBlueprintTypeID(getIntValue(pyParentBlueprintTypeID));
    builder.productTypeID(getIntValue(pyProductTypeID));
    builder.productionTime(getIntValue(pyProductionTime));
    builder.productivityModifier(getIntValue(pyProductivityModifier));
    builder.researchCopyTime(getIntValue(pyResearchCopyTime));
    builder.researchMaterialTime(getIntValue(pyResearchMaterialTime));
    builder.researchProductivityTime(getIntValue(pyResearchProductTime));
    builder.researchTechTime(getIntValue(pyResearchTechTime));
    builder.techLevel(getShortValue(pyTechLevel));
    builder.wasteFactor(getShortValue(pyWasteFactor));

    return builder.build();
  }
}
