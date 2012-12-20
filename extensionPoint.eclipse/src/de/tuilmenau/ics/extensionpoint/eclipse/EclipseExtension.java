/*******************************************************************************
 * Extension points wrapper for Eclipse
 * Copyright (c) 2012, Integrated Communication Systems Group, TU Ilmenau.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 ******************************************************************************/
package de.tuilmenau.ics.extensionpoint.eclipse;

import org.eclipse.core.runtime.IConfigurationElement;

import de.tuilmenau.ics.extensionpoint.Extension;


public class EclipseExtension implements Extension
{
	public EclipseExtension(IConfigurationElement element)
	{
		this.element = element;
	}

	@Override
	public String getName()
	{
		return element.getName();
	}

	@Override
	public Object create(String attributeName) throws Exception
	{
		return element.createExecutableExtension(attributeName);
	}

	@Override
	public String getAttribute(String name)
	{
		return element.getAttribute(name);
	}

	@Override
	public Extension[] getChildren()
	{
		return EclipseExtensionRegistry.wrapConfig(element.getChildren());
	}


	private IConfigurationElement element;
}
