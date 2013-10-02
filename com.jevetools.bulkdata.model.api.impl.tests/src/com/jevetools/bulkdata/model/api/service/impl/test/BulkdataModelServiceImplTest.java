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
package com.jevetools.bulkdata.model.api.service.impl.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.jevetools.bulkdata.model.api.service.BulkdataModelService;
import com.jevetools.commons.test.AbstractTest;
import com.jevetools.data.model.api.Entity;
import com.jevetools.data.model.api.inventory.Blueprint;
import com.jevetools.data.model.api.inventory.Category;
import com.jevetools.data.model.api.inventory.Group;
import com.jevetools.data.model.api.inventory.Material;
import com.jevetools.data.model.api.inventory.MetaGroup;
import com.jevetools.data.model.api.inventory.MetaType;
import com.jevetools.data.model.api.inventory.Reaction;
import com.jevetools.data.model.api.inventory.Type;
import com.jevetools.data.model.api.pi.PiMaterial;
import com.jevetools.data.model.api.pi.PiPinType;
import com.jevetools.data.model.api.pi.PiSchematic;
import com.jevetools.data.model.api.ram.Activity;
import com.jevetools.data.model.api.ram.TypeRequirement;

/**
 * Copyright (c) 2013, jEVETools.
 * 
 * All rights reserved.
 * 
 * @version 0.0.1
 * @since 0.0.1
 */
public final class BulkdataModelServiceImplTest
    extends AbstractTest
{ // NOPMD
  /**
   */
  @Test
  public void testService()
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    assertThat(service, not(nullValue()));
  }

  /**
   */
  @Test
  public void testServiceReference()
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    assertThat(getServiceReference(context, BulkdataModelService.class),
        not(nullValue()));
  }

  /**
   * @throws Exception
   *             on error
   */
  @Test
  public void testServiceType() throws Exception
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    final List<Type> list = load(service, Type.class);

    assertThat(list.size(), not(equalTo(0))); // NOPMD
  }

  /**
   * @throws Exception
   *             on error
   */
  @Test
  public void testServiceCategory() throws Exception
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    final List<Category> list = load(service, Category.class);

    assertThat(list.size(), not(equalTo(0))); // NOPMD
  }

  /**
   * @throws Exception
   *             on error
   */
  @Test
  public void testServiceGroup() throws Exception
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    final List<Group> list = load(service, Group.class);

    assertThat(list.size(), not(equalTo(0))); // NOPMD
  }

  /**
   * @throws Exception
   *             on error
   */
  @Test
  public void testServiceMaterial() throws Exception
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    final List<Material> list = load(service, Material.class);

    assertThat(list.size(), not(equalTo(0))); // NOPMD
  }

  /**
   * @throws Exception
   *             on error
   */
  @Test
  public void testServiceMetaGroup() throws Exception
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    final List<MetaGroup> list = load(service, MetaGroup.class);

    assertThat(list.size(), not(equalTo(0))); // NOPMD
  }

  /**
   * @throws Exception
   *             on error
   */
  @Test
  public void testServiceMetaType() throws Exception
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    final List<MetaType> list = load(service, MetaType.class);

    assertThat(list.size(), not(equalTo(0))); // NOPMD
  }

  /**
   * @throws Exception
   *             on error
   */
  @Test
  public void testServiceReaction() throws Exception
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    final List<Reaction> list = load(service, Reaction.class);

    assertThat(list.size(), not(equalTo(0))); // NOPMD
  }

  /**
   * @throws Exception
   *             on error
   */
  @Test
  public void testServiceBlueprint() throws Exception
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    final List<Blueprint> list = load(service, Blueprint.class);

    assertThat(list.size(), not(equalTo(0))); // NOPMD
  }

  /**
   * @throws Exception
   *             on error
   */
  @Test
  public void testServiceSchematic() throws Exception
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    final List<PiSchematic> list = load(service, PiSchematic.class);

    assertThat(list.size(), not(equalTo(0))); // NOPMD
  }

  /**
   * @throws Exception
   *             on error
   */
  @Test
  public void testServicePiMaterial() throws Exception
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    final List<PiMaterial> list = load(service, PiMaterial.class);

    assertThat(list.size(), not(equalTo(0))); // NOPMD
  }

  /**
   * @throws Exception
   *             on error
   */
  @Test
  public void testServicePiPinType() throws Exception
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    final List<PiPinType> list = load(service, PiPinType.class);

    assertThat(list.size(), not(equalTo(0))); // NOPMD
  }

  /**
   * @throws Exception
   *             on error
   */
  @Test
  public void testServiceRamActivity() throws Exception
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    final List<Activity> list = load(service, Activity.class);

    assertThat(list.size(), not(equalTo(0))); // NOPMD
  }

  /**
   * @throws Exception
   *             on error
   */
  @Test
  public void testServiceRamTypeRequirement() throws Exception
  {
    final Bundle testSubject = getTestSubject();

    final BundleContext context = getBundleContext(testSubject);

    final BulkdataModelService service = getService(context,
        getServiceReference(context, BulkdataModelService.class));

    final List<TypeRequirement> list = load(service, TypeRequirement.class);

    assertThat(list.size(), not(equalTo(0))); // NOPMD
  }

  //	/**
  //	 * @throws Exception
  //	 *             on error
  //	 */
  //	@Test
  //	public void testServiceEntity() throws Exception
  //	{
  //		final Bundle testSubject = getTestSubject();
  //
  //		final BundleContext context = getBundleContext(testSubject);
  //
  //		final BulkdataModelService service = getService(context,
  //				getServiceReference(context, BulkdataModelService.class));
  //
  //		final List<Entity> list = load(service, Entity.class);
  //		
  //		assertThat(list.size(), equalTo(0)); // NOPMD
  //	}

  /**
   * @param <T> {@link Entity}
   * @param aService
   *            {@link BulkdataModelService}
   * @param aClass
   *            {@link Class}
   * @return {@link List} of {@link Type}s
   * @throws IOException
   *             on error
   * @since 0.0.1
   */
  private <T extends Entity> List<T> load(final BulkdataModelService aService,
      final Class<T> aClass) throws IOException
  {
    return aService.load(aClass);
  }

  /**
   * @param aBundle
   *            {@link Bundle}
   * 
   * @return {@link BundleContext}
   * 
   * @since 0.0.1
   */
  private BundleContext getBundleContext(final Bundle aBundle)
  {
    return aBundle.getBundleContext();
  }

  /**
   * @param <T>
   *            type
   * @param aBundleContext
   *            {@link BundleContext}
   * @param aClass
   *            type
   * 
   * @return {@link ServiceReference}
   * 
   * @since 0.0.1
   */
  private <T> ServiceReference<T> getServiceReference(
      final BundleContext aBundleContext, final Class<T> aClass)
  {
    return aBundleContext.getServiceReference(aClass);
  }

  /**
   * @param <T>
   *            type
   * @param aBundleContext
   *            {@link BundleContext}
   * @param aServiceReference
   *            {@link ServiceReference}
   * 
   * @return service
   * 
   * @since 0.0.1
   */
  private <T> T getService(final BundleContext aBundleContext,
      final ServiceReference<T> aServiceReference)
  {
    return aBundleContext.getService(aServiceReference);
  }
}
