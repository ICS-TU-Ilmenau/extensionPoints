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

public interface Extension
{
	/**
	 * @return Name of the extension
	 */
	public String getName();
	
	/**
	 * Used default constructor to create an object representing the extension.
	 * 
	 * @return Object, which class name is stored in the attribute with the given name (!= null)
	 */
	public Object create(String attributeName) throws Exception;
	
	/**
	 * @return The value of the attribute with the given name; null if the attribute is not specified
	 */
	public String getAttribute(String name);
	
	/**
	 * @return Sub-extensions of the extension or null if no available
	 */
	public Extension[] getChildren();
}
