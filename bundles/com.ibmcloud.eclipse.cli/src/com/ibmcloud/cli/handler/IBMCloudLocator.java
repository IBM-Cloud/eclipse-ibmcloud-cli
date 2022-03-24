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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.eclipse.core.runtime.Platform;

class IBMCloudLocator {
	
	public static final String IBMCLOUD_COMMAND_NAME = "ibmcloud";
	private static final String IBMCLOUD_CLI_PATH = "com.ibmcloud.eclipse.cli.path";
	
	private static final String IBMCloudLocation;

	static {
		String os = Platform.getOS();
		String output = "";
		if (Platform.OS_MACOSX.equals(os) || Platform.OS_LINUX.equals(os)) {
	        ProcessBuilder processBuilder;
	        try {
	        	String[] cmd = {"/bin/bash", "-c", "which " + IBMCLOUD_COMMAND_NAME};
	            processBuilder = new ProcessBuilder(cmd);
	            
	            /*
	             *  Set the ibmcloud cli path to the PATH environment variable.
	             *  Read the system property if set, if not use the hardcoded value
	             */
	            String cliPath = System.getProperty(IBMCLOUD_CLI_PATH);
	    		if (cliPath == null || cliPath.isBlank()) cliPath = ":/usr/local/bin";
	            String pathKey = "PATH";
	            Map<String, String> env = processBuilder.environment();
				String path = env.get(pathKey);
	            path = path.concat(cliPath);
	            env.put(pathKey, path);
	            
	            Process process = processBuilder.start();
	            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String s = "";
	            while ((s = br.readLine()) != null) output += s;
	            process.waitFor();
	            if (process.exitValue() != 0) {
	            	handleError();
	            }
	            process.destroy();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	handleError();
	        }
		} else if (Platform.OS_WIN32.equals(os)) {
	        ProcessBuilder processBuilder;
			try {
	        	String[] cmd = {"cmd", "/c", "where " + IBMCLOUD_COMMAND_NAME};
	            processBuilder = new ProcessBuilder(cmd);
	            Process process = processBuilder.start();
	            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            String s = "";
	            while ((s = br.readLine()) != null) output += s;
	            process.waitFor();
	            if (process.exitValue() != 0) {
	            	handleError();
	            }
	            process.destroy();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	handleError();
	        }
		}
		
		if (output == null || output.isBlank()) {
			handleError();
			System.exit(0);
		}
		IBMCloudLocation = output;
	}
	
	static String getIBMCloudLocation() {
		return IBMCloudLocation;
	}
	
	private static void handleError() {
		System.out.println("ibmcloud command not found. Please install IBM Cloud CLI from https://cloud.ibm.com/docs/apps?topic=cli-getting-started#step1-install-idt");
	}
}
