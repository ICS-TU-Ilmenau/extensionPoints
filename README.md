extensionPoints
===============

The [Eclipse Extension Points](http://wiki.eclipse.org/FAQ_What_are_extensions_and_extension_points%3F) provide a flexible mechanism to link software components.

They enable the definition of "points", which can be extended by others.
An Eclipse plug-in L can define such extension points via its plugin.xml file.
For example, it can announce the possibility to extend a list.
Another Eclipse plug-in E can define an extension again via its plugin.xml.
It can specify an extension for that list.
Typical the extension specifies a name, which should be displayed in the list.
During runtime, the plug-in L can get a list of all extensions from the Eclipse runtime.
In particular, it can access this list without actually loading E.
Only if the user selects E from the list, L can trigger the startup of E.
Thus, the Eclipse runtime can handle a lot of extensions in a scalable way.

Why a new implementation?
-------------------------

The Eclipse runtime has its own implementation of the extension point mechanism.
It is not part of the underlying OSGi standard.
Thus, the extension point implementation is available under the Eclipse Public License (EPL), only.
However, it is not recommended to use such dependencies in an application under the General Public License (GPL) since both licenses are not compatible ([FSF](http://www.fsf.org/blogs/licensing/using-the-gpl-for-eclipse-plug-ins) and [Eclipse](http://mmilinkov.wordpress.com/2010/04/06/epl-gpl-commentary/) statements).   
Since we would like to have such features in an GPL-application, we had to re-implement the mechanism.

What does the implementation support?
-------------------------------------

The core implementation is an OSGi bundle.
It mimics the behavior of the original Eclipse Extension Points.
In particular, it uses the same plugin.xml file format.
It scans a directory (typically the "plugin" directory) for JAR files, which contain "plugin.xml" files.
All extensions in such files are collected and can be queried by extension point implementations via a global extension point registry.
There is no verification if the extension is a valid one.

For developers, who start a second Eclipse instance directly in order test their plug-ins projects, the core implementation is not sufficient.
Eclipse does not create JAR files; it seems to load the classes directly from the workspace folders.
You have to install the bundle fragment, too.
It wraps the original Eclipse installation. 


Which licenses?
---------------

The core bundle is dual-licensed under either the terms of the Eclipse Public License v1.0 as published by the Eclipse Foundation (see <a href="http://www.eclipse.org/legal/epl-v10.html">http://www.eclipse.org/legal/epl-v10.html</a>)
or (per the licensee's choosing)
under the terms of the GNU General Public License version 2 as published by the Free Software Foundation (see <a href=http://www.gnu.org/licenses/gpl-2.0.txt>http://www.gnu.org/licenses/gpl-2.0.txt</a>).

The bundle fragment is licensed under the Eclipse Public License v1.0 as published by the Eclipse Foundation (see <a href="http://www.eclipse.org/legal/epl-v10.html">http://www.eclipse.org/legal/epl-v10.html</a>).

Detailed information can be found in the about.html files located in each subfolder.
