package com.saumi.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.saumi.utils.FileUtils;

public class Shader {
	
	private String vertexFile, fragmentFile;
	private int vertID, fragID, programID;
	
	public Shader (String vert, String frag) {
		vertexFile = FileUtils.loadAsString(vert);
		fragmentFile = FileUtils.loadAsString(frag);
	}
	
	public void create() {
		
		programID = GL20.glCreateProgram();
		
		vertID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertID, vertexFile);
		GL20.glCompileShader(vertID);
		
		if (GL20.glGetShaderi(vertID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Vertex : " + GL20.glGetShaderInfoLog(vertID));			
			return;
		}
		
		fragID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragID, fragmentFile);
		GL20.glCompileShader(fragID);
		
		if (GL20.glGetShaderi(fragID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Fragment : " + GL20.glGetShaderInfoLog(fragID));			
			return;
		}
		
		GL20.glAttachShader(programID, vertID);
		GL20.glAttachShader(programID, fragID);
		
		GL20.glLinkProgram(programID);	// linking the shaders		
		if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			System.err.println("Program shader linking error : " + GL20.glGetProgramInfoLog(programID));
			return;
		}
		
		GL20.glValidateProgram(programID);	// bunch of steps to make sure the program is not messed up.
		if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Program shader validation error : " + GL20.glGetProgramInfoLog(programID));
			return;
		}
		
		GL20.glDeleteShader(vertID);
		GL20.glDeleteShader(fragID);
	}
	
	public void bind() {
		GL20.glUseProgram(programID);
	}
	
	public void unbind() {
		GL20.glUseProgram(0);
	}
	
	public void destroy() {
		GL20.glDeleteProgram(programID);
	}

}
