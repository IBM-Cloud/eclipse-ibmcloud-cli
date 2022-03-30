/**
 * Copyright IBM Corporation 2022
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package com.ibmcloud.cli;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;

/**
 * The plug-in runtime class for the IBMCloud CLIe plug-in. Clients may not instantiate or subclass this class.
 *
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @noextend This class is not intended to be subclassed by clients.
 */
public class IBMCloudCLIPlugin extends Plugin {

	/**
	 * Status code indicating an unexpected internal error.
	 *
	 * @since 2.1
	 */
	public static final int INTERNAL_ERROR = 120;

	/**
	 * The single instance of this plug-in runtime class.
	 */
	private static IBMCloudCLIPlugin plugin;

	/**
	 * Unique identifier constant (value <code>"com.ibmcloud.cli"</code>) for the IBMCloud CLI plug-in.
	 */
	public static final String PI_IBMCLOUDCLI = "com.ibmcloud.cli"; //$NON-NLS-1$

	public IBMCloudCLIPlugin() {
		super();
		plugin = this;
	}
	/**
	 * Returns this plug-in instance.
	 *
	 * @return the single instance of this plug-in runtime class
	 */
	public static IBMCloudCLIPlugin getPlugin() {
		return plugin;
	}
	/**
	 * Logs the specified throwable with this plug-in's log.
	 *
	 * @param message
	 *            message to log
	 */
	public static void log(String message) {
		IStatus status = new Status(IStatus.ERROR, PI_IBMCLOUDCLI, message); 
		getPlugin().getLog().log(status);
	}
}
