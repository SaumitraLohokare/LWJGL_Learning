package com.saumi.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FileUtils {
	
	public static String loadAsString(String path) {
		
		StringBuilder sb = new StringBuilder();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream(path)))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load file : " + path);
		}
		
		return sb.toString();
	}

}
