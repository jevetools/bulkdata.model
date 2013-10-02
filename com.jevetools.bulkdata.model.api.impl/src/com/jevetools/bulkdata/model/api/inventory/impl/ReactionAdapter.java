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
import com.jevetools.data.model.api.inventory.Reaction;
import com.jevetools.data.model.api.inventory.impl.ReactionImpl;
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
public final class ReactionAdapter
    extends AbstractAdapter<Reaction>
{
  /**
   * Static instance.
   */
  public static final ReactionAdapter INSTANCE = new ReactionAdapter();

  @Override
  public boolean matches(final Class<? extends Entity> aClass)
  {
    return aClass.equals(Reaction.class);
  }

  @Override
  public Reaction adapt(final PyDBRow aPyDBRow, final UnmarshalService aService)
  {
    final PyDict map = getDBRowDict(aPyDBRow);

    final ReactionImpl.Builder builder = new ReactionImpl.Builder();

    final PyBase pyReactionTypeID = getDictValue(aService, map,
        "reactionTypeID");
    final PyBase pyTypeID = getDictValue(aService, map, "typeID");
    final PyBase pyQuantity = getDictValue(aService, map, "quantity");
    final PyBase pyInput = getDictValue(aService, map, "input");

    builder.reactionTypeID(getIntValue(pyReactionTypeID));
    builder.typeID(getIntValue(pyTypeID));
    builder.quantity(getShortValue(pyQuantity));

    if (getIntValue(pyInput) == 1)
    {
      builder.input(true);
    }
    else
    {
      builder.input(false);
    }

    return builder.build();
  }
}
