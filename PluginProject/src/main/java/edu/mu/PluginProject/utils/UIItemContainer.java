package edu.mu.PluginProject.utils;

public class UIItemContainer {
	
	String title;
	int x;
	int y;
	int z;
	
	public UIItemContainer(String s, int x, int y, int z)
	{
		this.title = s;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public String getTitle() {
		return title;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
}
