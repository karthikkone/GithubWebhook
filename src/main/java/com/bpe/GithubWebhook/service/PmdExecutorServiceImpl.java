package com.bpe.GithubWebhook.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("pmdExecutorService")
public class PmdExecutorServiceImpl implements PmdExecutorService {
	@Value("${pmd.path}")
	private String pmdPath;
	
	@Value("${workspace.path:/tmp}")
	private String workspace;
	
	@Value("${pmd.rulespath}")
	private String rulesPath;
	
	@Value("${pmd.output:text}")
	private String pmdOutputFormat;
	
	@Override
	public String executeOnDir(File rootDir) throws IOException {
		if (rootDir != null && rootDir.exists()) {
			
			List<String> cmd = new ArrayList<String>();
			cmd.add(pmdPath);
			cmd.add("-d");
			cmd.add(rootDir.getAbsolutePath());
			cmd.add("-f");
			cmd.add(pmdOutputFormat);
			cmd.add("-R");
			cmd.add(rulesPath);
			
			System.out.println("CMD = "+cmd);
			ProcessBuilder pb = new ProcessBuilder(cmd);
			Process process = pb.start();
			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder strBuilder = new StringBuilder();
			String line = null;
			String output = null;
			while((line = reader.readLine()) != null) {
				line = line.substring(workspace.length()+1, line.length());
				strBuilder.append(line);
				strBuilder.append(System.getProperty("line.separator"));
			}
			
			try {
				int exitCode = process.waitFor();
				
				System.out.println("pmd results : ");
				System.out.println(strBuilder.toString());
				if (exitCode == 0) {
					output = strBuilder.toString();
				} else {
					System.out.println("pmd scan failed with exit code "+exitCode);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return output;
		}
		return null;
	}

}
