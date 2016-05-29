package com.ginray;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class 	GameUi {

	/**
	 * 游戏属性常量
	 */

	// 帧刷新间隔(单位微妙)
	public static final int GAME_ATTRIBUTE_FRAME_DELAY = 10;

	// 游戏活动对象Y方向的像素密度(将1个单位像素拆分为更小单元)
	public static final int GAME_ATTRIBUTE_PIXEL_DENSITY_Y = 10;

	// 游戏等级提升因数
	public static final int GAME_ATTRIBUTE_LEVEL_UP_FACTOR = 40;

	// 重力速度(即主角离开踏板后的y方向速度)
	public static final int GAME_ATTRIBUTE_GRAVITY_VELOCITY = 10 * GAME_ATTRIBUTE_PIXEL_DENSITY_Y;

	// 游戏状态
	public static final int GAME_STATUS_PAUSE = 0;
	public static final int GAME_STATUS_RUNNING = 1;
	public static final int GAME_STATUS_GAMEOVER = 2;

	// 游戏效果标识(用来控制不同音效和震动的标志)
	                   //无效果 no_effect
	public static final int EFFECT_FLAG_NO_EFFECT = 0;
	                   //正常，标准，normal
	public static final int EFFECT_FLAG_NORMAL = 1;
	                   //不稳定的，动荡的，unstable
	public static final int EFFECT_FLAG_UNSTABLE = 2;
	                    //弹跳的  spring
	public static final int EFFECT_FLAG_SPRING = 3;
	                    //尖的，有锯齿状的，spiked 
	public static final int EFFECT_FLAG_SPIKED = 4;
	                    //移动的，moving
	public static final int EFFECT_FLAG_MOVING = 5;
	                    //工具箱，tools
	public static final int EFFECT_FLAG_TOOLS = 6;

	public static final int EFFECT_FLAG_FALL   =7;



	/**
	 * 游戏属性
	 */

	// 游戏界面属性
	private ScreenAttribute mScreenAttribute;
	private static int ScreenMaxnWeight;
	private static int ScreenMaxnHeight;
	// 游戏状态
	public int mGameStatus = GAME_STATUS_RUNNING;  //1

	// 游戏得分
	private int mScore = 0;

	// 每次加分的分数
	private int mLevel =10;

	// 生命值
	private int mHP = ROLE_ATTRIBUTE_HP_MAX;  //12

	// 游戏等级提升计算器( 等级提升计算器值   等于   等级提升因数时    游戏等级提升1级,等级提升计算器    重置为零)
	private int mLevelUpCounter = 0;

	// 随机数生成器
	private Random mRan = new Random();


	// 游戏效果标志(用于处理主角动作效果,比如:震动,音效)
	private int mEffectFlag = EFFECT_FLAG_NO_EFFECT;

	private int FootboardVelocity=0;

	//踏板要下降的速度



	/**
	 * 主角属性常量
	 */

	// 主角的长度和宽度

	public static final int ROLE_ATTRIBUTE_WIDTH = 150;
			//75;
	public static final int ROLE_ATTRIBUTE_HEITH = 150;


	// 主角帧刷新间隔
	public static final int ROLE_ATTRIBUTE_FRAME_DELAY = 2;

	// 主角最大生命值
	public static final int ROLE_ATTRIBUTE_HP_MAX = 100;
													//由于测试的时候总是莫名其妙死掉，最大生命值暂时设置为100
													//5.15：玛德100生命值也死了

	// 主角状态
	public static final int ROLE_STATUS_ON_FOOTBOARD = 0;
	public static final int ROLE_STATUS_ON_FOOTBOARD_LEFT = 1;  //footboard  踏足板；（床架底部的）竖板
	public static final int ROLE_STATUS_ON_FOOTBOARD_RIGHT = 2;
	public static final int ROLE_STATUS_FREEFALL = 3;   //freefall  自由下坠，自由下落
	public static final int ROLE_STATUS_FREEFALL_LEFT = 4;
	public static final int ROLE_STATUS_FREEFALL_RIGHT = 5;

	// 主角帧
	public static final int ROLE_SHARP_STANDING = 0;
	public static final int ROLE_SHARP_FREEFALL_NO1 = 1;   //freefall  自由下坠，自由下落
	public static final int ROLE_SHARP_FREEFALL_NO2 = 2;
	public static final int ROLE_SHARP_FREEFALL_NO3 = 3;
	public static final int ROLE_SHARP_FREEFALL_NO4 = 4;
	public static final int ROLE_SHARP_MOVE_LEFT_NO1 = 5;
	public static final int ROLE_SHARP_MOVE_LEFT_NO2 = 6;
	public static final int ROLE_SHARP_MOVE_LEFT_NO3 = 7;
	public static final int ROLE_SHARP_MOVE_LEFT_NO4 = 8;
	public static final int ROLE_SHARP_MOVE_RIGHT_NO1 = 9;
	public static final int ROLE_SHARP_MOVE_RIGHT_NO2 = 10;
	public static final int ROLE_SHARP_MOVE_RIGHT_NO3 = 11;
	public static final int ROLE_SHARP_MOVE_RIGHT_NO4 = 12;

	/**
	 * 道具属性常量
	 */

	// 怪物   在屏幕上的滞留时间，也是循环出现的时间间隔
	public static final int MONSTER_ATTRIBUTE_DELAY_TIME = 450;

	public static final int MONSTER_SHOW_TIME =10;

	// 怪物   的大小
	public static final int MONSTER_ATTRIBUTE_IMAGE_SIZE = 24;

	// 踏板的长宽
	public static final int BORDER_ATTRIBUTE_IMAGE_HEITH = 60;
	//24
	public static final int BORDER_ATTRIBUTE_IMAGE_WIDTH = 200;
	//83
	public static final int BORDER_ATTRIBUTE_IMAGE_HEITH_SPRING = 120;
	public static final int BORDER_ATTRIBUTE_IMAGE_WIDTH_SPRING = 200;

	public static final int BORDER_ATTRIBUTE_IMAGE_HEITH_SPRING_DOWN = 90;
	public static final int BORDER_ATTRIBUTE_IMAGE_WIDTH_SPRING_DOWN = 200;
	// 踏板偏向速度
	
	//velocity  速率；迅速；周转率

	public static final int BOARD_ATTRIBUTE_LEFT_VELOCITY = -8;
	public static final int BOARD_ATTRIBUTE_RIGHT_VELOCITY = 8;

	// 不稳定踏板滞留因数(可滞留时间=滞留因数*帧刷新间隔)
	public static final int BOARD_ATTRIBUTE_UNSTABLE_DELAY_FACTOR = 5;

	// 踏板类别
	public static final int FOOTBOARD_TYPE_NORMAL = 0;  //正常，标准，normal
	public static final int FOOTBOARD_TYPE_UNSTABLE = 1;  //不稳定的，动荡的，unstable
	public static final int FOOTBOARD_TYPE_SPRING = 2;     //弹跳的  spring
	public static final int FOOTBOARD_TYPE_SPIKED = 3;   //尖的，有锯齿状的，spiked 
	public static final int FOOTBOARD_TYPE_MOVING_LEFT = 4;   //移动的，moving
	public static final int FOOTBOARD_TYPE_MOVING_RIGHT = 5;
	public static final int FOOTBOARD_TYPE_SHAKE=6;    //左右摇晃的踏板
	public static final int FOOTBOARD_TYPE_RISE=7; 	   //上下摇晃的踏板


		// 怪物类别
	public static final int MONSTERNONE = 0;
	public static final int MONSTER_8 = 4;
	public static final int MONSTER_7 = 6;
	public static final int MONSTER_6 = 8;
	public static final int MONSTER_5 = 10;
	public static final int MONSTER_4 = 12;
	public static final int MONSTER_3 = 14;
	public static final int MONSTER_2 = 16;
	public static final int MONSTER_1 = 20;



	//用来标记踏板是不是会下降

	/**
	 * 游戏主角属性
	 */

	// 游戏主角
	//这里新建了一个主角游戏对象  mRole,  有了 Rose 对象的基本属性了。
	//主角有构造函数   还有返回他的一些基本属性   
	//getRoleSharp() 返回当前主角 此时刻的 动作形状
	private Role mRole;   

	// 主角X方向移动速度
	private int mRoleVelocityX;


	// 主角Y方向移动速度
	private int mRoleVelocityY;

	//主角的方向
	private int mRoleFace;

	// 附加速度(用于控制速度,在选项面板里设定)
	private int mAddVelocity;

	/**
	 * 道具属性
	 */

	// 楼梯间隔距离因数(间隔距离(px)=因数/Y方向像素因数)
	private int mFootboardSpaceFactor = 120 * GAME_ATTRIBUTE_PIXEL_DENSITY_Y; //10

	// 移动间隔计算器
	private int mFootboardSpaceCounter = 0;

	// role的移动速度
	private int mFootboartVelocity = -40 * GAME_ATTRIBUTE_PIXEL_DENSITY_Y;  	//10

	private int SpringJumpNum =2;                                  //弹簧弹跳的倍数

	private int mFootBoartCorrect  = 40 * GAME_ATTRIBUTE_PIXEL_DENSITY_Y;	// 踏板移动速度


	private int MaxScreenFootBoart=9999;							//屏幕上最上面的踏板的Y值


	private static int mFootboartMovingNum= -10 ;							//踏板左右移动的次数

	// 踏板列表
	
	//分析到此处，   LinkedList  与  ArrayList 的区别  LinkedList是一种双向链表
	
	//一Footboard 类为元素创建一个 链表集合，该集合的每一个元素都是一个  Footboard 对象，具有他的属性
	private LinkedList<Footboard> mFootboardList;

            //定义怪物类
	private Monster mCurMonster;

	public GameUi(ScreenAttribute screenAttribute, int addVelocity) {

		ScreenMaxnWeight=screenAttribute.maxX;
		ScreenMaxnHeight=screenAttribute.maxY;


		//初始化屏幕类
		mScreenAttribute = screenAttribute;
		mAddVelocity = addVelocity;
		
		/**
		*Role(int x, int y, int width, int heith, int frameDelay) 	
		*/
		
		mRole = new Role(
				(screenAttribute.maxX - ROLE_ATTRIBUTE_WIDTH) / 2,

				screenAttribute.maxY * 3 / 4,
				
				ROLE_ATTRIBUTE_WIDTH,
				
				ROLE_ATTRIBUTE_HEITH,

				ROLE_ATTRIBUTE_FRAME_DELAY
				);

		mRole.setRoleStatus(ROLE_STATUS_FREEFALL_LEFT);
		mRoleFace=-1;

		//// 重力速度(即主角离开踏板后的y方向速度)
		
		//主角Y轴上的速度mRoleVelocityY  =重力速度   此类中预定义 的 为50
		mRoleVelocityY = GAME_ATTRIBUTE_GRAVITY_VELOCITY;
		
		//初始化跳板集合
		mFootboardList = new LinkedList<Footboard>();
		
		
		/** Footboard 的构造函数
		 * Footboard(int x, int y, int width, int heith, int type,int frameAmount, int frameDelay) {
		 */

		//游戏开始初始化跳板
		mFootboardList.add(new Footboard(
				(screenAttribute.maxX - BORDER_ATTRIBUTE_IMAGE_WIDTH) / 2,

				//从效果可以看出，游戏开始是从 最底下，的中间开始的，所以 宽度会 /2   而高度不会/2
				screenAttribute.maxY - 120, BORDER_ATTRIBUTE_IMAGE_WIDTH,

				BORDER_ATTRIBUTE_IMAGE_HEITH,

				FOOTBOARD_TYPE_NORMAL,

				1,

				1));

		MaxScreenFootBoart=9999;
		while(mFootboardList.size()<50) {
			initFootboard(MaxScreenFootBoart-192);
			Log.i("FootboardList","Ising adding");
			for (Footboard footboard : mFootboardList) {
				if(footboard.getMinY()<MaxScreenFootBoart)
					MaxScreenFootBoart=footboard.getMinY();
			}
		}


		for (Footboard footboard : mFootboardList) {
			if(footboard.getMinY()<MaxScreenFootBoart)
				MaxScreenFootBoart=footboard.getMinX();
		}
		
		/** 
		 * monster 怪物的构造函数
		 * Monster(int monsterReward, int timeCounter, int x, int y, int size)
		 */
		mCurMonster = new Monster(MONSTERNONE, 0, 0, 0, MONSTER_ATTRIBUTE_IMAGE_SIZE);
	}




	//主角之前的位置   用来计算屏幕的移动
	
	

	
	/**
	 * 更新UI模型
	 */


	//根据涂鸦跳跃的操作逻辑，应该是刚开始的时候跳板保持不动；
	//当role能跳到上一层的时候，使跳板下降一段距离(固定的距离)
	public void updateGameUi() {




		if(FootboardVelocity<0)
			FootboardVelocity=0;

		mScore+=FootboardVelocity/100;

		if(FootboardVelocity>0) {
			for (Footboard footboard : mFootboardList) {
				footboard.addY(FootboardVelocity);
			}


			//mCurMonster.mMinY-=FootboardVelocity/10;


			FootboardVelocity -= 10;
			//System.out.println(FootboardVelocity);
			mRoleVelocityY=0;
			mRole.addX(mRoleVelocityX);


			System.out.println("mRole"+mRole.getMaxY());

			if(mRoleVelocityX!=0)
				mRoleFace=mRoleVelocityX;
			if (mRoleVelocityX > 0) {
				mRole.setRoleStatus(ROLE_STATUS_FREEFALL_RIGHT);
			} else if (mRoleVelocityX < 0) {
				mRole.setRoleStatus(ROLE_STATUS_FREEFALL_LEFT);
			} else {
				if(mRoleFace<0)
					mRole.setRoleStatus(ROLE_STATUS_FREEFALL_LEFT);
				else
					mRole.setRoleStatus(ROLE_STATUS_FREEFALL_RIGHT);
			}
			footboardmoving();
			return ;
		}


			//mRole.addY(mFootBoartCorrect);
			//mRole.addY(50);
			//mRoleVelocityY+=3;
			//return;

			//Log.i("FootboardVelocity", "mFootboartVelocity ing……");
			//之前连真机不能显示log  解决方法是
			//拨号盘输入*20121220#   ->  选择日志输出级别  ->  选择Java log level -> 选择LOGD









		/**
		 *
		 * //主角Y轴上的速度mRoleVelocityY  =重力速度   此类中预定义 的 为50
		 * mRoleVelocityY = GAME_ATTRIBUTE_GRAVITY_VELOCITY;
		 *
		 */
		mRole.addX(mRoleVelocityX);
		mRole.addY(mRoleVelocityY);
		
		handleBorder();
		//System.out.println("After handleBorder  " + mEffectFlag);
		handleRoleAction();
		//handleMonster();
		//System.out.println("Y is " + mRole.getMinY());
		screenDecline();

		//generateFoot();
		footboardmoving();

		mFootboardSpaceCounter = mFootboardSpaceCounter + mFootboartVelocity;
		if (mFootboardSpaceCounter >= mFootboardSpaceFactor) {
			mFootboardSpaceCounter -= mFootboardSpaceFactor;
			
			//随机生成 产生跳板和怪物
			/**
			 * 
			 * 随机生成踏板,生成后，就加入到了 跳板 集合链表里面的了
			 * 换句话说，就是向跳板集合里面随机增加一个 跳板 对象
			 */


			//当且仅当屏幕有下降的速度的时候生成踏板
			//if(FootboardVelocity)
			//generateFootboard();			//需要更改   更改成当屏幕上的踏板少于一定值的时候就要填充
											//为了让屏幕下降的时候不会出现没有踏板的情况，产生的踏板最小需要
											//是 (弹簧能弹跳的高度+屏幕的高度)/踏板的间隔


			//generateFoot();
			
			//// 游戏等级提升计算器  mLevelUpCounter
			
			mLevelUpCounter += 1;
			
		     //// 游戏等级提升计算器( 等级提升计算器值   等于   等级提升因数时   
					//     游戏等级提升1级,等级提升计算器    重置为零)
			if (mLevelUpCounter == GAME_ATTRIBUTE_LEVEL_UP_FACTOR) {
				mLevelUpCounter = 0;
				increaseLevel();  //升级
			}
		}



		MaxScreenFootBoart=9999;
		for (Footboard footboard : mFootboardList) {
			if(footboard.getMinY()<MaxScreenFootBoart)
				MaxScreenFootBoart=footboard.getMinY();
		}



		 while(mFootboardList.size()<50  ) {
			initFootboard(MaxScreenFootBoart-192);
			Log.i("FootboardList","Ising adding");
			for (Footboard footboard : mFootboardList) {
				if(footboard.getMinY()<MaxScreenFootBoart)
					MaxScreenFootBoart=footboard.getMinY();
			}
		}

	}

	
	
	
	/**
	 * 
	 * 随机生成踏板,生成后，就加入到了 跳板 集合链表里面的了
	 * 换句话说，就是向跳板集合里面随机增加一个 跳板 对象
	 */
	private void initFootboard(int boardx) {
		int frameAmount = 1;
		int frameDelay = 1;
		int boardnum=1;
		int frameType = FOOTBOARD_TYPE_NORMAL;
		switch (mRan.nextInt(10)) {
			case 0:
				frameType = FOOTBOARD_TYPE_SPRING;
				boardnum++;
				break;
			case 1:
				frameType = FOOTBOARD_TYPE_SPRING;
				boardnum++;
				break;
			case 2:
				frameType = FOOTBOARD_TYPE_UNSTABLE;
				boardnum++;
				break;
			case 3:
				frameType = FOOTBOARD_TYPE_UNSTABLE;
				boardnum++;
				break;
			case 4:
				frameType = FOOTBOARD_TYPE_RISE;
				boardnum++;
				break;
			case 5:
				frameType = FOOTBOARD_TYPE_SHAKE;
				boardnum++;
				break;
			case 6:
				frameType = FOOTBOARD_TYPE_SHAKE;
				boardnum++;
				break;
			case 7:
				frameType = FOOTBOARD_TYPE_SPRING;
				boardnum++;
				break;
			case 8:
				frameType = FOOTBOARD_TYPE_RISE;
				boardnum++;
				break;

			default:
				frameType = FOOTBOARD_TYPE_NORMAL;
		}

		/**
		 * Footboard(int x, int y, int width, int heith, int type,	int frameAmount, int frameDelay)
		 *
		 *
		 */
		int fboardx=mRan.nextInt(ScreenMaxnWeight-BORDER_ATTRIBUTE_IMAGE_WIDTH);

		if(frameType != FOOTBOARD_TYPE_SHAKE) {

			mFootboardList.add(new Footboard(
					fboardx,
					// 没有显示，在屏幕的上方
					boardx + mRan.nextInt(8),
					BORDER_ATTRIBUTE_IMAGE_WIDTH,
					BORDER_ATTRIBUTE_IMAGE_HEITH,
					FOOTBOARD_TYPE_NORMAL,
					1,
					1));
		}
		for (int i = 2; i <= boardnum; i++) {

			fboardx+=mRan.nextInt(200)+BORDER_ATTRIBUTE_IMAGE_WIDTH;
			if(fboardx+BORDER_ATTRIBUTE_IMAGE_WIDTH>ScreenMaxnWeight)
				continue;
			mFootboardList.add(new Footboard(
					fboardx,
					// 没有显示，在屏幕的上方
					boardx+mRan.nextInt(8),
					BORDER_ATTRIBUTE_IMAGE_WIDTH,
					BORDER_ATTRIBUTE_IMAGE_HEITH,
					frameType,
					frameAmount,
					frameDelay));
		}
	}




	private void generateFoot() {

		//System.out.println("mTimeCounter  "+mCurMonster.mTimeCounter);
		if (mCurMonster.mTimeCounter > 0) {

			return;
		}

		switch (mRan.nextInt(25)) {
		case 0:
			mCurMonster.mMonsterReward = MONSTER_1;
			break;
		case 1:
			mCurMonster.mMonsterReward = MONSTER_2;
			break;
		case 2:
		case 3:
		case 4:
			mCurMonster.mMonsterReward = MONSTER_3;
			break;
		case 5:
		case 6:
		case 7:
			mCurMonster.mMonsterReward = MONSTER_4;
			break;
		case 8:
		case 9:
		case 10:
			mCurMonster.mMonsterReward = MONSTER_5;
			break;
		case 11:
		case 12:
		case 13:
			mCurMonster.mMonsterReward = MONSTER_6;
			break;
		case 14:
		case 15:
		case 16:
		case 17:
			mCurMonster.mMonsterReward = MONSTER_7;
			break;
		case 18:
		case 19:
		case 20:
		case 21:
			mCurMonster.mMonsterReward = MONSTER_8;
			break;
		default:
			mCurMonster.mMonsterReward = MONSTERNONE;
			return;
		}

		mCurMonster.mMinX = mRan
				.nextInt((mScreenAttribute.maxX - MONSTER_ATTRIBUTE_IMAGE_SIZE));
		mCurMonster.mMinY = mRan
				.nextInt((mScreenAttribute.maxY - MONSTER_ATTRIBUTE_IMAGE_SIZE));
		mCurMonster.mMaxX = mCurMonster.mMinX + MONSTER_ATTRIBUTE_IMAGE_SIZE;
		mCurMonster.mMaxY = mCurMonster.mMinY + MONSTER_ATTRIBUTE_IMAGE_SIZE;

		// 时间间隔  450
		// 在屏幕上的滞留时间，


		mCurMonster.mTimeCounter = MONSTER_ATTRIBUTE_DELAY_TIME;
		mCurMonster.mTimeSetter  = MONSTER_SHOW_TIME;
}




	private void footboardmoving() {
		if (mFootboartMovingNum > 0) {
			for (Footboard footboard : mFootboardList) {
				if (footboard.getType() == FOOTBOARD_TYPE_SHAKE)
					footboard.addX(3);
				if (footboard.getType() == FOOTBOARD_TYPE_RISE)
					footboard.addY(5);
			}
			mFootboartMovingNum--;
		}
		else{
			for (Footboard footboard : mFootboardList) {
				if (footboard.getType() == FOOTBOARD_TYPE_SHAKE)
					footboard.addX(-3);
				if (footboard.getType() == FOOTBOARD_TYPE_RISE)
					footboard.addY(-5);
			}
			mFootboartMovingNum--;
		}
		if(mFootboartMovingNum>=200)
			mFootboartMovingNum=-200;
		if(mFootboartMovingNum<=-200)
			mFootboartMovingNum=200;
	}
	
	/**
	 * 处理主角移动
	 * 
	 * @powered by Byronlee
	 * mAddVelocity 额外加的速度，在设置里面设置
	 */
	public void handleMoving(float angleValue) {
		if (angleValue < -5) {
			mRoleVelocityX = 10 + mAddVelocity;
		} else if (angleValue >= -5 && angleValue < -4) {
			mRoleVelocityX = 8 + mAddVelocity;
		} else if (angleValue >= -4 && angleValue < -3) {
			mRoleVelocityX = 6 + mAddVelocity;
		} else if (angleValue >= -3 && angleValue < -2) {
			mRoleVelocityX = 5 + mAddVelocity;
		} else if (angleValue >= -2 && angleValue < -1.5) {
			mRoleVelocityX = 4 + mAddVelocity;
		} else if (angleValue >= -1.5 && angleValue < 1.5) {
			mRoleVelocityX = 0;
		} else if (angleValue >= 1.5 && angleValue < 2) {
			mRoleVelocityX = -4 - mAddVelocity;
		} else if (angleValue >= 2 && angleValue < 3) {
			mRoleVelocityX = -5 - mAddVelocity;
		} else if (angleValue >= 3 && angleValue < 4) {
			mRoleVelocityX = -6 - mAddVelocity;
		} else if (angleValue >= 4 && angleValue < 5) {
			mRoleVelocityX = -8 - mAddVelocity;
		} else if (angleValue > 5) {
			mRoleVelocityX = -10 - mAddVelocity;
		}
	}

	/**
	 * 难度提升，游戏升级
	 */
	private void increaseLevel(){
		/*
		mLevel++;

		if (mLevel < 18 || mLevel % 20 == 0) {
			
		   //	mFootboartVelocity 定义的是30 向下！  +5 表示向上的速度增加
			mFootboartVelocity += 5;
			int roleStatus = mRole.getRoleStatus();
			if (roleStatus == ROLE_STATUS_ON_FOOTBOARD
					|| roleStatus == ROLE_STATUS_ON_FOOTBOARD_RIGHT
					|| roleStatus == ROLE_STATUS_ON_FOOTBOARD_LEFT) {
				// 升级后使主角的Y轴上的速度和 跳板Y轴上的速度一样
				mRoleVelocityY = -mFootboartVelocity;
			}
		}

		*/
	}

	
	/**
	 * 处理边界
	 * 对一个时刻的 游戏数据和状态的判断和处理
	 */
	private void handleBorder() {
		
		if (mFootboardList.size() > 0)
		{
			for(Footboard footboard :mFootboardList)
			{
				if(footboard.getMinY() >= mScreenAttribute.maxY) {
					//如果跳板的在Y轴上超过了 上边界，就移除
					mFootboardList.remove(footboard);
					//Log.d("mFootboardList","Is removing");
				}
			}

		}
		
		if (mRole.getMinY() <= mScreenAttribute.minY) {
			
			//如果主角顶部超过了上边界，生命值HP，扣掉3  一种为 12
			mHP -= 3;
			if (mHP <= 0) {				
				//生命值小于0， 结束生命，改变游戏状态
				mGameStatus = GAME_STATUS_GAMEOVER;
				mEffectFlag = EFFECT_FLAG_FALL;
				
			} else if (mRole.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD
					|| mRole.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD_LEFT
					|| mRole.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD_RIGHT) {
				mRole.addY(BORDER_ATTRIBUTE_IMAGE_HEITH
						* GAME_ATTRIBUTE_PIXEL_DENSITY_Y);
			}
			mRoleVelocityY = GAME_ATTRIBUTE_GRAVITY_VELOCITY;
			mEffectFlag = EFFECT_FLAG_SPIKED;
			return;
		}
		
		//如果主角顶部超过了底边界，生命值HP， 结束生命，改变游戏状态
		if (mRole.getMinY() > mScreenAttribute.maxY) {
			mGameStatus = GAME_STATUS_GAMEOVER;
			mEffectFlag = EFFECT_FLAG_FALL;
			return;
		}
		
		//处理游戏左边界
		if (mRole.getMaxX() < mScreenAttribute.minX) {
			mRole.setX(mScreenAttribute.maxX);
			return;
		}
		//处理游戏右边界
		if (mRole.getMinX() > mScreenAttribute.maxX) {
			mRole.setX(    - ROLE_ATTRIBUTE_WIDTH);
			return;
		}
	}

	/**
	 * 处理主角在踏板上的活动
	 */
	private void handleRoleAction() {

		Role role = mRole;
		if(mRoleVelocityY>0) {

			for (Footboard footboard : mFootboardList) {

				if (footboard.getType() == FOOTBOARD_TYPE_UNSTABLE &&footboard.getVisitable()){
					if(footboard.isBoardBreak())
					mFootboardList.remove(footboard);
				}

				if (mRoleVelocityY>=0&&(role.getMaxY() >= footboard.getMinY() && role.getMaxY() <= footboard.getMaxY()-5)
						&& (role.getMaxX() >= footboard.getMinX() && role.getMinX() <= footboard
						.getMaxX())) {   // 判断是在跳板上

					footboard.visitable=true;

					if (role.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD
							|| role.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD_RIGHT
							|| role.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD_LEFT) {

						if (footboard.getType() != FOOTBOARD_TYPE_UNSTABLE) {
							mRoleVelocityY = mFootboartVelocity
									+ GAME_ATTRIBUTE_GRAVITY_VELOCITY;
							//偏转速度-重力速度
							role.addY(-1 * GAME_ATTRIBUTE_PIXEL_DENSITY_Y);
							// 游戏活动对象Y方向的像素密度(将1个单位像素拆分为更小单元)
							updateRoleStatus(ROLE_STATUS_FREEFALL);
						}

						if (footboard.getType() == FOOTBOARD_TYPE_SPRING) {
							mRoleVelocityY = SpringJumpNum*mFootboartVelocity
									+ GAME_ATTRIBUTE_GRAVITY_VELOCITY;
							//偏转速度-重力速度
							role.addY(-1 * GAME_ATTRIBUTE_PIXEL_DENSITY_Y);
							// 游戏活动对象Y方向的像素密度(将1个单位像素拆分为更小单元)
							updateRoleStatus(ROLE_STATUS_FREEFALL);
							return;
						}
						if (footboard.getType() == FOOTBOARD_TYPE_MOVING_LEFT) {
							role.addX(BOARD_ATTRIBUTE_LEFT_VELOCITY);
						} else if (footboard.getType() == FOOTBOARD_TYPE_MOVING_RIGHT) {
							role.addX(BOARD_ATTRIBUTE_RIGHT_VELOCITY);
						}
						updateRoleStatus(ROLE_STATUS_ON_FOOTBOARD);


					} else {
						//mScore += (mLevel+mRan.nextInt(10));
						mRoleVelocityY = GAME_ATTRIBUTE_GRAVITY_VELOCITY;
						role.setVirtualY(footboard.getVirtualY()
								- ROLE_ATTRIBUTE_HEITH
								* GAME_ATTRIBUTE_PIXEL_DENSITY_Y);

						if (footboard.getType() == FOOTBOARD_TYPE_SPIKED) {
							mHP -= 3;
						} else if (mHP < ROLE_ATTRIBUTE_HP_MAX) {
							mHP += 1;
						}
						if (mHP <= 0) {
							mGameStatus = GAME_STATUS_GAMEOVER;
						}
						updateRoleStatus(ROLE_STATUS_ON_FOOTBOARD);
						switch (footboard.getType()) {
							case FOOTBOARD_TYPE_UNSTABLE:
								mEffectFlag = EFFECT_FLAG_UNSTABLE;
								break;
							case FOOTBOARD_TYPE_SPRING:
								mEffectFlag = EFFECT_FLAG_SPRING;
								break;
							case FOOTBOARD_TYPE_SPIKED:
								mEffectFlag = EFFECT_FLAG_SPIKED;
								break;
							case FOOTBOARD_TYPE_MOVING_LEFT:
							case FOOTBOARD_TYPE_MOVING_RIGHT:
								mEffectFlag = EFFECT_FLAG_MOVING;
								break;
							default:
								mEffectFlag = EFFECT_FLAG_NORMAL;
						}
					}
					return;
				}
			}

		}

		if (mRoleVelocityY < -mFootboartVelocity) {
			mRoleVelocityY += 10;
		} else {
			mRoleVelocityY = GAME_ATTRIBUTE_GRAVITY_VELOCITY;
		}


		updateRoleStatus(ROLE_STATUS_FREEFALL);


	}



	private void screenDecline(){
		if(FootboardVelocity!=0)
			return;
		if(mRoleVelocityY<0&&mRole.getMaxY()<ScreenMaxnHeight*1/2) {
				FootboardVelocity=-mRoleVelocityY;
		}
	}


	//处理怪物
	private void handleMonster() {
		Monster monster = mCurMonster;
		monster.mTimeCounter--;

		if (monster.mMonsterReward != MONSTERNONE && monster.mTimeCounter > 0) {
			
			if ((mRole.getMaxX() > monster.mMinX && mRole.getMinX() < monster.mMaxX)   //判断X轴上 y轴上，有交点
					&& ( (mRole.getMaxY() >= monster.mMinY && mRole.getMaxY() < monster.mMaxY)|| (mRole.getMinY() > monster.mMinY && mRole.getMinY() <= monster.mMaxY))  ) {
				mEffectFlag = EFFECT_FLAG_TOOLS;
				mScore += monster.mMonsterReward;
				monster.mMonsterReward = MONSTERNONE;
			}
		}
	}

	//更新主角的状态，在主角是下降的过程中，判断是向左下降还是向右下降
	//在跳板上，判断是向做运动还是向右运动
	private void updateRoleStatus(int status) {
		if(mRoleVelocityX!=0)
			mRoleFace=mRoleVelocityX;
		if (status == ROLE_STATUS_FREEFALL) {
			if (mRoleVelocityX > 0) {
				mRole.setRoleStatus(ROLE_STATUS_FREEFALL_RIGHT);
			} else if (mRoleVelocityX < 0) {
				mRole.setRoleStatus(ROLE_STATUS_FREEFALL_LEFT);
			}
			else {
				if(mRoleFace<0)
					mRole.setRoleStatus(ROLE_STATUS_FREEFALL_LEFT);
				else
					mRole.setRoleStatus(ROLE_STATUS_FREEFALL_RIGHT);
			}
		} else {
			if (mRoleVelocityX > 0) {
				mRole.setRoleStatus(ROLE_STATUS_ON_FOOTBOARD_RIGHT);
			} else if (mRoleVelocityX < 0) {
				mRole.setRoleStatus(ROLE_STATUS_ON_FOOTBOARD_LEFT);
			}
			else {
				if(mRoleFace<0)
					mRole.setRoleStatus(ROLE_STATUS_ON_FOOTBOARD_LEFT);
				else
					mRole.setRoleStatus(ROLE_STATUS_ON_FOOTBOARD_RIGHT);
			}
		}
	}

	/**
	 * 清除操作
	 */



	public void destroy() {
		mScreenAttribute = null;
		mRole = null;
		mRan = null;
		mFootboardList.clear();
		mFootboardList = null;
	}

	public Role getRoleUIObject() {
		return mRole;
	}

	public List<Footboard> getFootboardUIObjects() {
		return mFootboardList;
	}

	public Monster getMonster() {
		return mCurMonster;
	}

	public int getEffectFlag() {
		try {
			return mEffectFlag;
		} finally {
			mEffectFlag = EFFECT_FLAG_NO_EFFECT;
		}
	}

	public String getLevel() {
		return "等级: " + mLevel;
	}

	public String getScoreStr() {
		return "score: " + mScore;
	}

	public int getScore() {
		return mScore;
	}

	public float getHp() {
		return (float) mHP / ROLE_ATTRIBUTE_HP_MAX;
	}

	public static final String PREFERENCE_KEY_SOUNDS = "com.byronlee.sounds";
	public static final String PREFERENCE_KEY_VIBRATE = "com.byronlee.vibrate";
	public static final String PREFERENCE_KEY_SHOWTIPS = "com.byronlee.showtips";
	public static final String PREFERENCE_KEY_POWER = "com.byronlee.power";

	public static final String PREFERENCE_RANKING_INFO = "ByronleeTeeter_RANKING_INFOS";
	public static final String PREFERENCE_KEY_RANKING_UID = "com.byronlee.ranking.uid";
	public static final String PREFERENCE_KEY_RANKING_NAME = "com.byronlee.ranking.name";
	public static final String PREFERENCE_KEY_RANKING_SCORE = "com.byronlee.ranking.score";
	public static final String PREFERENCE_KEY_RANKING_DATE = "com.byronlee.ranking.date";
	public static final String PREFERENCE_KEY_RANKING_FLAG = "com.byronlee.ranking.flag";
}
