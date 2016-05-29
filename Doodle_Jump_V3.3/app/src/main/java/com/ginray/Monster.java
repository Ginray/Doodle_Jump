package com.ginray;


public class Monster {

	//怪物类型
	public int mMonsterReward;


	public int mTimeCounter;     //中间产生的时间差

	public int mTimeSetter;      //中间停留的时间差

	public int mMinX;

	public int mMinY;

	public int mMaxX;

	public int mMaxY;


	public Monster(int monsterReward, int timeCounter, int x, int y, int size) {
		mMonsterReward = monsterReward;
		mTimeCounter = timeCounter;
		mMinX = x;
		mMinY = y;
		mMaxX = x + size;
		mMaxY = y + size;
	}
}
