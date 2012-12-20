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
import org.eclipse.core.runtime.Platform;

import de.tuilmenau.ics.extensionpoint.Extension;
import de.tuilmenau.ics.extensionpoint.ExtensionRegistry;

public class EclipseExtensionRegistry extends ExtensionRegistry
{
	@Override
	public Extension[] getExtensionsFor(String extensionPointName)
	{
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(extensionPointName);
		
		EclipseExtension[] res = wrapConfig(config);
		if(res == null) {
			// avoid null pointer
			res = new EclipseExtension[0];
		}
		
		return res;
	}
	
	/**
	 * Wraps the Eclipse objects in own objects
	 */
	public static EclipseExtension[] wrapConfig(IConfigurationElement[] config)
	{
		EclipseExtension[] res = null;
		
		if(config != null) {
			// wrap the Eclipse objects in own objects
			res = new EclipseExtension[config.length]; 
			
			for(int i = 0; i < config.length; i++) {
				res[i] = new EclipseExtension(config[i]);
			}
		}
		
		return res;
	}
}
