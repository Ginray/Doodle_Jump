package com.ginray;


public class ScreenAttribute {
	// 最小X坐标
	public int minX;
	// 最小Y坐标
	public int minY;
	// 最大X坐标
	public int maxX;
	// 最大Y坐标
	public int maxY;

	public int RoleWidth;
	//用来表示主角的最大宽度，因为需要和屏幕有关，所以定义在这里；

	public int RoleHigth;
	//用来表示主角的最大高度

	public ScreenAttribute(int minX, int minY, int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.minX = minX;
		this.minY = minY;

		RoleWidth=maxX/6;
		RoleHigth=maxX/6;
	}
}
