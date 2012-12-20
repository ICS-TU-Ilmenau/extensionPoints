/*******************************************************************************
 * Extension points
 * Copyright (C) 2012, Integrated Communication Systems Group, TU Ilmenau.
 * 
 * This program and the accompanying materials are dual-licensed under either
 * the terms of the Eclipse Public License v1.0 as published by the Eclipse
 * Foundation
 *  
 *   or (per the licensee's choosing)
 *  
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 ******************************************************************************/
package de.tuilmenau.ics.extensionpoint;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import java.util.Properties;

import de.tuilmenau.ics.extensionpoint.simple.XMLExtensionRegistry;


public abstract class ExtensionRegistry
{
	/**
	 * Relativ directory where plugins are located. The std value is
	 * suitable for OSGi applications started from command line. 
	 */
	private static final String FOLDER_FOR_PLUGINS = ".\\plugins";
	
	/**
	 * Filter for the plugin filename. Currently it loads only the
	 * plugins from ICS group.
	 */
	private static final String REG_EXP_FILTER_PLUGINS = "de\\.tuilmenau\\.ics\\..*jar";
	
	/**
	 * Name of the property file, which should contain the {@link CONFIG_PROPERTIES_KEY}. 
	 */
	private static final String CONFIG_PROPERTIES_FILENAME = "config.properties";
	
	private static final String CONFIG_PROPERTIES_KEY = "registry";
	
	
	/**
	 * @return Global extension point registry (!= null)
	 */
	public static ExtensionRegistry getInstance()
	{
		if(registrySingleton == null) {
			try {
				// locate file in bundle
				URL url = ExtensionRegistry.class.getClassLoader().getResource(CONFIG_PROPERTIES_FILENAME);
				
				if(url != null) {
					// open file and parse it
					Properties config = new Properties();
					config.load(url.openStream());
	
					String registryClassName = config.getProperty(CONFIG_PROPERTIES_KEY);
					
					if(registryClassName != null) {
						try {
							registrySingleton = (ExtensionRegistry) ExtensionRegistry.createObjectViaDefaultConstructor(registryClassName);
						}
						catch(Exception exc) {
							throw new RuntimeException("Can not create extension registry.", exc);
						}
					}
					// else: file does not define the key -> use default
				}
				// else: file not found
			}
			catch(IOException exc) {
				// errors in config file -> use default
				exc.printStackTrace(System.err);
			}
			
			// if none defined, use default one
			if(registrySingleton == null) {
				registrySingleton = new XMLExtensionRegistry(FOLDER_FOR_PLUGINS, REG_EXP_FILTER_PLUGINS);
			}
		}
		
		return registrySingleton;
	}
	
	public static void setInstance(ExtensionRegistry registry)
	{
		registrySingleton = registry;
	}
	
	private static ExtensionRegistry registrySingleton = null;
	
	/**
	 * Returns all extensions for an extension point.
	 * 
	 * @param extensionPointName Name of the extension point
	 * @return List of extensions (!= null)
	 */
	public abstract Extension[] getExtensionsFor(String extensionPointName);
	
	/**
	 * Helper method for creating object via their default (zero-argument) constructor.

	 * @return Object (!= null)
	 */
	public static Object createObjectViaDefaultConstructor(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException 
	{
		if(className != null) {
			// Fetch class object ...
			Class<?> classObj = Class.forName(className);
						
			// ... get std constructor for class ...
			Constructor<?> tConstructor = classObj.getConstructor();
			
			// ... and generate object
			return tConstructor.newInstance();
		} else {
			throw new ClassNotFoundException("Can not create object since no class name is given.");
		}
	}
}
