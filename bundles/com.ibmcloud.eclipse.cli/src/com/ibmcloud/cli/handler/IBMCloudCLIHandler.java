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

package com.ibmcloud.cli.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.externaltools.internal.IExternalToolConstants;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;

@SuppressWarnings("restriction")
public class IBMCloudCLIHandler extends AbstractHandler{
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		try {
			String commandName = event.getCommand().getName();
			ILaunchConfigurationType configType = manager.getLaunchConfigurationType(IExternalToolConstants.ID_PROGRAM_BUILDER_LAUNCH_CONFIGURATION_TYPE);
			if (configType != null) {
					 ILaunchConfigurationWorkingCopy config = configType.newInstance(null, commandName);
					 if (config != null) {
						 config.setAttribute(IExternalToolConstants.ATTR_LOCATION,  IBMCloudLocator.getIBMCloudLocation());
						 int index = commandName.indexOf(IBMCloudLocator.IBMCLOUD_COMMAND_NAME);
						 if (index < 0) return null;
						 String arguments = commandName.substring(index + 8);
						 if (arguments != null && !arguments.isEmpty()) {
							 arguments = getInputFromUserIfRequired(arguments);
							 config.setAttribute(IExternalToolConstants.ATTR_TOOL_ARGUMENTS, arguments.trim());
						 }
						 DebugUITools.buildAndLaunch(config, ILaunchManager.RUN_MODE, null);
						 return config;
					 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getInputFromUserIfRequired(String arguments) {
		int index = arguments.indexOf("$");
		if (index != -1) {
			String name = arguments.substring(index + 1);
			InputDialog dialog = new InputDialog(null, name, "Enter the name or ID of " + name, "", null);
			if (dialog.open() == Window.OK) {
				String input = dialog.getValue();
				arguments = arguments.replace("$" + name, input);
				System.out.println(arguments);
			}
		}
		return arguments;
	}

}
