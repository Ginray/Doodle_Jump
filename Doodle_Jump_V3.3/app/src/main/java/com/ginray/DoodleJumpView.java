package com.ginray;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


//要引用的 Android 包
//import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class DoodleJumpView extends SurfaceView implements SurfaceHolder.Callback{


	public static int Role_Normal=1;
	public static int Role_Bunny =2;
	public static int Role_doodlestein=3;
	public static int Role_ghost =4;
	public static int Role_ice=5;
	public static int Role_jungle =6;
	public static int Role_ninja=7;
	public static int Role_snow=8;
	public static int Role_soccer=9;
	public static int Role_space =10;
	public static int Role_underwater=11;



	public static int BackGround_Normal=1;
	public static int BackGround_Bunny =2;
	public static int BackGround_doodlestein=3;
	public static int BackGround_ghost =4;
	public static int BackGround_ice=5;
	public static int BackGround_jungle =6;
	public static int BackGround_ninja=7;
	public static int BackGround_snow=8;
	public static int BackGround_soccer=9;
	public static int BackGround_space =10;
	public static int BackGround_underwater=11;


	public static int NowRoleImag=Role_Normal;
	public static int NowBackGroundImag=BackGround_Normal;



	/*
	  SurfaceHolder.Callback是SurfaceHolder接口内部的静态子接口，SurfaceHolder.Callback中定义了三个接口方法：
  	  1：public void sufaceChanged(SurfaceHolder holder,int format,int width,int height){}//Surface的大小发生改变时调用。
 	  2： public void surfaceCreated(SurfaceHolder holder){}//Surface创建时激发，一般在这里调用画面的线程。
	   3： public void surfaceDestroyed(SurfaceHolder holder){}//销毁时激发，一般在这里将画面的线程停止、释放。
	* */

	private static final String HANDLE_MESSAGE_GAME_SCORE = "1";

	private ByronleeTeeterThread mByronleeTeeterThread;

	private Context mContext;             //Context提供了关于应用环境全局信息的接口

	private Handler mHandler;             //handler可以分发Message对象和Runnable对象到主线程中

	private ScreenAttribute mScreenAttribute;

	private int mActionPower;

	private boolean mVibratorFlag;
	private Vibrator mVibrator;            //获得振动服务

	private boolean mSoundsFlag;
	private SoundPool soundPool;


	/*
	* MediaPlayer存在如下缺点：

	1) 延时时间较长，且资源占用率高。

	2) 不支持多个音频同时播放。

    所以选用SoundPool   可以开始就加载20个音效，以后在程序中按音效的ID进行播放
	SoundPool(int maxStreams, int streamType, int srcQuality)：
	第一个参数指定支持多少个声音；第二个参数指定声音类型：第三个参数指定声音品质。

	*/
	private HashMap<Integer, Integer> soundPoolMap;

	private Bitmap mBackgroundImage;



	private Bitmap mRoleMovingLeftImage;
	private Bitmap mRoleMovingRightImage;

	private Bitmap bunny_mRoleMovingLeftImage;
	private Bitmap bunny_mRoleMovingRightImage;

	private Bitmap doodlestein_mRoleMovingLeftImage;
	private Bitmap doodlestein_mRoleMovingRightImage;

	private Bitmap ice_mRoleMovingLeftImage;
	private Bitmap ice_mRoleMovingRightImage;

	private Bitmap jungle_mRoleMovingLeftImage;
	private Bitmap jungle_mRoleMovingRightImage;

	private Bitmap ninja_mRoleMovingLeftImage;
	private Bitmap ninja_mRoleMovingRightImage;

	private Bitmap snow_mRoleMovingLeftImage;
	private Bitmap snow_mRoleMovingRightImage;

	private Bitmap soccer_mRoleMovingLeftImage;
	private Bitmap soccer_mRoleMovingRightImage;

	private Bitmap space_mRoleMovingLeftImage;
	private Bitmap space_mRoleMovingRightImage;

	private Bitmap underwater_mRoleMovingLeftImage;
	private Bitmap underwater_mRoleMovingRightImage;



	private Bitmap mFootboardNormalImage;
	private Bitmap mFootboardUnstableImage1;
	private Bitmap mFootboardUnstableImage2;
	private Bitmap mFootboardSpringImage;
	private Bitmap mFootboardSpringImage2;
	private Bitmap mFootboardSpikedImage;
	private Bitmap mFootboardMovingLeftImage1;
	private Bitmap mFootboardMovingLeftImage2;
	private Bitmap mFootboardMovingRightImage1;
	private Bitmap mFootboardMovingRightImage2;
	private Bitmap mFootboardShake;
	private Bitmap mFootboardRise;



	private Bitmap bunny_bgImage;
	private Bitmap doodlestein_bgImage;
	private Bitmap ghost_bgImage;
	private Bitmap ice_bgImage;
	private Bitmap jungle_bgImage;
	private Bitmap ninja_bgImage;
	private Bitmap snow_bgImage;
	private Bitmap soccer_bgImage;
	private Bitmap space_bgImage;
	private Bitmap underwater_bgImage;

	private Bitmap normal_bgImage;



	private Bitmap mFoodImage1;
	private Bitmap mFoodImage2;
	private Bitmap mFoodImage3;
	private Bitmap mFoodImage4;
	private Bitmap mFoodImage5;
	private Bitmap mFoodImage6;
	private Bitmap mFoodImage7;
	private Bitmap mFoodImage8;

	private Drawable mTopBarImage;
	private Drawable mHpBarTotalImage;
	private Drawable mHpBarRemainImage;

	private Paint mGameMsgRightPaint;
	private Paint mGameMsgLeftPaint;

	
	public DoodleJumpView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		SurfaceHolder holder = getHolder();

		/*
		SurfaceHolder.Callback

   		SurfaceHolder.Callback是SurfaceHolder接口内部的静态子接口，SurfaceHolder.Callback中定义了三个接口方法：
   		1：public void sufaceChanged(SurfaceHolder holder,int format,int width,int height){}//Surface的大小发生改变时调用。
  		2： public void surfaceCreated(SurfaceHolder holder){}//Surface创建时激发，一般在这里调用画面的线程。
  		3： public void surfaceDestroyed(SurfaceHolder holder){}//销毁时激发，一般在这里将画面的线程停止、释放。
		* */


		holder.addCallback(this);
		//为SurfaceHolder添加一个SurfaceHolder.Callback回调接口。


		mHandler = new Handler() {
			@Override
			public void handleMessage(Message m) {
				// 更新本地记录文件
				int curScore = m.getData().getInt(HANDLE_MESSAGE_GAME_SCORE);
				boolean recordRefreshed = updateLocalRecord(curScore);

				Bundle bundle = new Bundle();//创建一个句柄
				bundle.putString("score", "" + m.getData().getInt(HANDLE_MESSAGE_GAME_SCORE));//将nameinfo填充入句柄


				Intent mIntent =new Intent(mContext,Gameover.class);
				mIntent.putExtras(bundle);
				mContext.startActivity(mIntent);
				((mainActivity) mContext).finish();
				((mainActivity) mContext).overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
			}
		};
		// 初始化资源
		mByronleeTeeterThread = new ByronleeTeeterThread(holder, context, mHandler);
		setFocusable(true);
	}
 
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	//获取View中的Canvas对象，绘制一些自定义形状，然后调用View. invalidate方法让View重新刷新，
	// 然后绘制一个新的形状，这样达到2D动画效果
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
		// TODO Auto-generated method stub
		mScreenAttribute = new ScreenAttribute(0, 20, width, height);
		mByronleeTeeterThread.initGameUi(mScreenAttribute);
		mByronleeTeeterThread.setRunning(true);
		mByronleeTeeterThread.start();
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		mByronleeTeeterThread.setRunning(false);
		while (retry) {
			try {
				mByronleeTeeterThread.join();
				retry = false;
			} catch (InterruptedException e) {
				Log.d("", "Surface destroy failure:", e);
			}
		}
		
	}
	
	
	
	
	
	
	
	
	// 处理当屏幕重力感应后传递随重力感应变化而变化的x 的值，  这个函数是在mainActivity 里面在调用
	//这是属于View的 处理动态变化的x 的值
	// view 调用 他的 handleMoving（）；view.handleMoving() 里面有调用 GameUi里面的  handleMoving();
	public void handleMoving(float data_x_value) {
		if (mByronleeTeeterThread != null) {
			mByronleeTeeterThread.handleMoving(data_x_value);
		}
	}
	
	
	 //重启游戏
	public void restartGame() {
		mByronleeTeeterThread = new ByronleeTeeterThread(this.getHolder(), this.getContext(),
				mHandler);
		mByronleeTeeterThread.initGameUi(mScreenAttribute);
		mByronleeTeeterThread.setRunning(true);
		mByronleeTeeterThread.start();
	}
	
	//更新游戏记录
	public boolean updateLocalRecord(int score) {

		//对手机文件建立联系，如果没有此文件，则创建此文件
		SharedPreferences rankingSettings = mContext.getSharedPreferences(				
				GameUi.PREFERENCE_RANKING_INFO, 0);
		
		if (rankingSettings.getInt(GameUi.PREFERENCE_KEY_RANKING_SCORE, 0) < score) {
			//如果现在的得分大于之前的记录，则跟新文件里面的游戏得分记录
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			rankingSettings.edit().putInt(
					GameUi.PREFERENCE_KEY_RANKING_SCORE, score)
					.putString(GameUi.PREFERENCE_KEY_RANKING_DATE,
							formatter.format(new Date())).apply();
			return true;
		}
		return false;
	}

	
	/**
	 * 初始化资源，也就是，对外界资源的引用全部赋值给设定的变量，
	 * 这样在后面就可以，利用外面的这些资源文件了，尤其是大量的图片
	 */
	private void initRes() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		mSoundsFlag = preferences.getBoolean(
				GameUi.PREFERENCE_KEY_SOUNDS, true);
		mVibratorFlag = preferences.getBoolean(
				GameUi.PREFERENCE_KEY_VIBRATE, true);
		mActionPower = preferences
				.getInt(GameUi.PREFERENCE_KEY_POWER, 60);

		// 初始化活动音效
		soundPool = new SoundPool(10, AudioManager.STREAM_RING, 5);
		soundPoolMap = new HashMap<Integer, Integer>();
		soundPoolMap.put(GameUi.EFFECT_FLAG_NORMAL, soundPool.load(
				getContext(), R.raw.jump, 1));
		soundPoolMap.put(GameUi.EFFECT_FLAG_UNSTABLE, soundPool.load(
				getContext(), R.raw.unstable, 1));
		soundPoolMap.put(GameUi.EFFECT_FLAG_SPRING, soundPool.load(
				getContext(), R.raw.spring, 1));
		soundPoolMap.put(GameUi.EFFECT_FLAG_SPIKED, soundPool.load(
				getContext(), R.raw.spiked, 1));
		soundPoolMap.put(GameUi.EFFECT_FLAG_MOVING, soundPool.load(
				getContext(), R.raw.moving, 1));
		soundPoolMap.put(GameUi.EFFECT_FLAG_FALL, soundPool.load(
				getContext(), R.raw.fall, 1));
		//soundPoolMap.put(GameUi.EFFECT_FLAG_TOOLS, soundPool.load(
		//		getContext(), R.raw.tools, 1));

		mGameMsgLeftPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mGameMsgLeftPaint.setColor(Color.BLACK);
		mGameMsgLeftPaint.setStyle(Style.FILL);
		mGameMsgLeftPaint.setTextSize(60f);
		mGameMsgLeftPaint.setTextAlign(Paint.Align.LEFT);


		Typeface typeFace =Typeface.createFromAsset(getResources().getAssets(),"wawati.TTF");

		mGameMsgLeftPaint.setTypeface(typeFace);

		mGameMsgRightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mGameMsgRightPaint.setColor(Color.BLACK);
		mGameMsgRightPaint.setStyle(Style.FILL);
		mGameMsgRightPaint.setTextSize(15f);
		mGameMsgRightPaint.setTextAlign(Paint.Align.RIGHT);
		mGameMsgRightPaint.setTypeface(Typeface.DEFAULT_BOLD);

		Resources res = mContext.getResources();

		mTopBarImage = res.getDrawable(R.drawable.topbar);
		mHpBarTotalImage = res.getDrawable(R.drawable.topbar);
		mHpBarRemainImage = res.getDrawable(R.drawable.topbar);



		mRoleMovingLeftImage = Bitmap.createScaledBitmap(BitmapFactory
						.decodeResource(res, R.drawable.role_left),
				GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
				true);
		mRoleMovingRightImage = Bitmap.createScaledBitmap(BitmapFactory
						.decodeResource(res, R.drawable.role_right),
				GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
				true);

		if(NowRoleImag==Role_Bunny) {
			bunny_mRoleMovingLeftImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.bunny_role_l),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
			bunny_mRoleMovingRightImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.bunny_role_r),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
		}
		else if(NowRoleImag==Role_doodlestein) {
			doodlestein_mRoleMovingLeftImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.doodlestein_role_l),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
			doodlestein_mRoleMovingRightImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.doodlestein_role_r),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
		}

		else if(NowRoleImag==Role_ice) {
			ice_mRoleMovingLeftImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.ice_role_l),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
			ice_mRoleMovingRightImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.ice_role_r),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
		}
		else if(NowRoleImag==Role_jungle) {
			jungle_mRoleMovingLeftImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.jungle_role_l),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
			jungle_mRoleMovingRightImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.jungle_role_r),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
		}
		else if(NowRoleImag==Role_ninja) {
			ninja_mRoleMovingLeftImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.ninja_role_l),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
			ninja_mRoleMovingRightImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.ninja_role_r),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
		}
		else if(NowRoleImag==Role_snow) {
			snow_mRoleMovingLeftImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.snow_role_l),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
			snow_mRoleMovingRightImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.snow_role_r),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
		}
		else if(NowRoleImag==Role_soccer) {
			soccer_mRoleMovingLeftImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.soccer_role_l),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
			soccer_mRoleMovingRightImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.soccer_role_r),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
		}
		else if(NowRoleImag==Role_space) {
			space_mRoleMovingLeftImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.space_role_l),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
			space_mRoleMovingRightImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.space_role_r),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
		}
		else if(NowRoleImag==Role_underwater) {
			underwater_mRoleMovingLeftImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.underwater_role_l),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
			underwater_mRoleMovingRightImage = Bitmap.createScaledBitmap(BitmapFactory
							.decodeResource(res, R.drawable.underwater_role_r),
					GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
					true);
		}


		mFootboardNormalImage = Bitmap.createScaledBitmap(BitmapFactory
						.decodeResource(res, R.drawable.normal),
				GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
				GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
		mFootboardUnstableImage1 = Bitmap.createScaledBitmap(BitmapFactory
						.decodeResource(res, R.drawable.unsafe),
				GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
				GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
		mFootboardUnstableImage2 = Bitmap.createScaledBitmap(BitmapFactory
						.decodeResource(res, R.drawable.unsafe2),
				GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
				GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
		mFootboardSpringImage = Bitmap.createScaledBitmap(BitmapFactory
						.decodeResource(res, R.drawable.spring0),
				GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH_SPRING_DOWN,
				GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH_SPRING_DOWN, true);
		mFootboardSpringImage2 = Bitmap.createScaledBitmap(BitmapFactory
						.decodeResource(res, R.drawable.spring),
				GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH_SPRING,
				GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH_SPRING, true);
		mFootboardSpikedImage = Bitmap.createScaledBitmap(BitmapFactory
				.decodeResource(res, R.drawable.bat3),
				GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
				GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
		mFootboardShake = Bitmap.createScaledBitmap(BitmapFactory
						.decodeResource(res, R.drawable.shake),
				GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
				GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
		mFootboardRise = Bitmap.createScaledBitmap(BitmapFactory
						.decodeResource(res, R.drawable.rise),
				GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
				GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);





		normal_bgImage = BitmapFactory
				.decodeResource(res, R.drawable.background);
		if(NowBackGroundImag==BackGround_Bunny)
		bunny_bgImage=BitmapFactory
				.decodeResource(res, R.drawable.bunny_bg);
		if(NowBackGroundImag==BackGround_doodlestein)
		doodlestein_bgImage=BitmapFactory
				.decodeResource(res, R.drawable.doodlestein_bg);
		if(NowBackGroundImag==BackGround_ice)
		ice_bgImage=BitmapFactory
				.decodeResource(res, R.drawable.ice_bg);
		if(NowBackGroundImag==BackGround_jungle)
		jungle_bgImage=BitmapFactory
				.decodeResource(res, R.drawable.jungle_bg);
		if(NowBackGroundImag==BackGround_ninja)
		ninja_bgImage=BitmapFactory
				.decodeResource(res, R.drawable.ninja_bg);
		if(NowBackGroundImag==BackGround_snow)
		snow_bgImage=BitmapFactory
				.decodeResource(res, R.drawable.snow_bg);
		if(NowBackGroundImag==BackGround_soccer)
		soccer_bgImage=BitmapFactory
				.decodeResource(res, R.drawable.soccer_bg);
		if(NowBackGroundImag==BackGround_space)
		space_bgImage=BitmapFactory
				.decodeResource(res, R.drawable.space_bg);
		if(NowBackGroundImag==BackGround_underwater)
		underwater_bgImage=BitmapFactory
				.decodeResource(res, R.drawable.underwater_bg);
	}




	//主线程，控制游戏UI的动态跟新 和 游戏的进行
	
  class  ByronleeTeeterThread extends Thread{
		
		//对sufceVIew   的控制
		private SurfaceHolder mSurfaceHolder;
		private Context mContext;
		private Handler mHandler;
		
		
		// 运行标志
		private boolean mRun = true;
		// 游戏UI模型实例
		private GameUi mGameUi;
		// 时间记录器
		private long mTimeLogger;
		
		public ByronleeTeeterThread(SurfaceHolder surfaceHolder, Context context,Handler handler) {
			this.mSurfaceHolder = surfaceHolder;
			this.mContext = context;
			this.mHandler = handler;
		}
		
		public void run(){
			initRes();
			while (mRun) {
				Canvas c = null;
				try {
					mTimeLogger = System.currentTimeMillis();
					try {
						mGameUi.updateGameUi();
						c = mSurfaceHolder.lockCanvas(null);
						synchronized (mSurfaceHolder) {
							doDraw(c);
						}
						handleEffect(mGameUi.getEffectFlag());
					} catch (Exception e) {
						System.out.println("Canvas"+e);
						Log.e("Canvas", "Error");
					} finally {
						/*
						 这里会发生ConcurrentModificationException错误而导致c有可能是null；
						 这个bug调试了一周多，真是醉了；
						 关于ConcurrentModificationException:

						 文档中说明，对Vector、ArrayList在迭代的时候如果同时
						 对其进行修改就会抛出java.util.ConcurrentModificationException异常。
						 因为我在GameUi 中使用了一个	LinkedList 在updateGameUi()的时候
						 访问了两次的LinkedList造成了这个错误,然而我并没有remove  也没有使用多线程；
						 因此这个报错就显得非常蛋疼，让我一阵不知所措;

						 javadoc 里面指出： it would be wrong to write a program that depended
						 on this exception for its correctness: ConcurrentModificationException
						 should be used only to detect bugs.


						 说人话就是  这个应该用于发bug的东西变成了一个bug，困扰了我一周多；
						 ！！！！！


						* */
							//if (c != null)
								mSurfaceHolder.unlockCanvasAndPost(c);
								Log.v("Canvas","draw");
					}
					mTimeLogger = System.currentTimeMillis() - mTimeLogger;
					if (mTimeLogger < GameUi.GAME_ATTRIBUTE_FRAME_DELAY) {
						Thread.sleep(GameUi.GAME_ATTRIBUTE_FRAME_DELAY
								- mTimeLogger);
					}
					if (mGameUi.mGameStatus == GameUi.GAME_STATUS_GAMEOVER) {
						handleEffect(mGameUi.getEffectFlag());
						Message message = new Message();
						Bundle bundle = new Bundle();
						//　Bundle 用于不同Activity之间的数据传递
						bundle.putInt(DoodleJumpView.HANDLE_MESSAGE_GAME_SCORE,
								mGameUi.getScore());
						message.setData(bundle);
						mHandler.sendMessage(message);
						mRun = false;
					}
				} catch (Exception ex) {
					Log.d("", "Error at 'run' method", ex);
				}
			}
		}
		
		//do drow ,绘画 游戏界面
		private void doDraw(Canvas canvas) {
			Bitmap tempBitmap = null;


			switch (NowBackGroundImag){
				case 1://BackGround_Normal
					mBackgroundImage=normal_bgImage;
					break;
				case 2://BackGround_Bunny
					mBackgroundImage=bunny_bgImage;
					break;
				case 3://doodlestein_bgImage;
					mBackgroundImage=doodlestein_bgImage;
					break;
				case 4://ghost_bgImage;
					mBackgroundImage=ghost_bgImage;
					break;
				case 5://ice_bgImage;
					mBackgroundImage=ice_bgImage;
					break;
				case 6:
					mBackgroundImage=jungle_bgImage;
					break;
				case 7:
					mBackgroundImage= ninja_bgImage;
					break;
				case 8:
					mBackgroundImage= snow_bgImage;
					break;
				case 9:
					mBackgroundImage= soccer_bgImage;
					break;
				case 10:
					mBackgroundImage= space_bgImage;
					break;
				case 11:
					mBackgroundImage= underwater_bgImage;
			}

			canvas.drawBitmap(mBackgroundImage, 0, 0, null);


			List<Footboard> footboards = mGameUi.getFootboardUIObjects();
			for (Footboard footboard : footboards) {
				switch (footboard.getType()) {
				case GameUi.FOOTBOARD_TYPE_UNSTABLE:
					if (!footboard.isMarked()) {
						tempBitmap = mFootboardUnstableImage1;
					} else {
						tempBitmap = mFootboardUnstableImage2;
					}
					break;
				case GameUi.FOOTBOARD_TYPE_SPRING:
					if (!footboard.getVisitable()) {
						tempBitmap = mFootboardSpringImage;
					} else {
						tempBitmap = mFootboardSpringImage2;
					}
					break;
				case GameUi.FOOTBOARD_TYPE_SPIKED:
					tempBitmap = mFootboardSpikedImage;
					break;
				case GameUi.FOOTBOARD_TYPE_MOVING_LEFT:
					if (footboard.nextFrame() == 0) {
						tempBitmap = mFootboardMovingLeftImage1;
					} else {
						tempBitmap = mFootboardMovingLeftImage2;
					}
					break;
				case GameUi.FOOTBOARD_TYPE_MOVING_RIGHT:
					if (footboard.nextFrame() == 0) {
						tempBitmap = mFootboardMovingRightImage1;
					} else {
						tempBitmap = mFootboardMovingRightImage2;
					}
					break;

				case GameUi.FOOTBOARD_TYPE_SHAKE:
					tempBitmap = mFootboardShake;
					break;

				case GameUi.FOOTBOARD_TYPE_RISE:
					tempBitmap = mFootboardRise;
					break;

				default:
					tempBitmap = mFootboardNormalImage;
				}
				canvas.drawBitmap(tempBitmap, footboard.getMinX(), footboard
						.getMinY(), null);
			}

			Role role = mGameUi.getRoleUIObject();
			if (mGameUi.mGameStatus == GameUi.GAME_STATUS_GAMEOVER) {
				canvas.drawBitmap(mRoleMovingLeftImage, role.getMinX(), role
						.getMinY(), null);
			} else {
				switch (role.getRoleSharp()) {

				case GameUi.ROLE_SHARP_MOVE_LEFT_NO1:
					tempBitmap = mRoleMovingLeftImage;
					break;
				case GameUi.ROLE_SHARP_MOVE_LEFT_NO2:
					tempBitmap = mRoleMovingLeftImage;
					break;
				case GameUi.ROLE_SHARP_MOVE_LEFT_NO3:
					tempBitmap = mRoleMovingLeftImage;
					break;
				case GameUi.ROLE_SHARP_MOVE_LEFT_NO4:
					tempBitmap = mRoleMovingLeftImage;
					break;
				case GameUi.ROLE_SHARP_MOVE_RIGHT_NO1:
					tempBitmap = mRoleMovingRightImage;
					break;
				case GameUi.ROLE_SHARP_MOVE_RIGHT_NO2:
					tempBitmap = mRoleMovingRightImage;
					break;
				case GameUi.ROLE_SHARP_MOVE_RIGHT_NO3:
					tempBitmap = mRoleMovingRightImage;
					break;
				case GameUi.ROLE_SHARP_MOVE_RIGHT_NO4:
					tempBitmap = mRoleMovingRightImage;
					break;
				default:
					tempBitmap = mRoleMovingLeftImage;
				}

				switch (NowRoleImag){
					case 1://normal
						break;
					case 2://bunny
					{
						if(tempBitmap==mRoleMovingLeftImage)
							tempBitmap=bunny_mRoleMovingLeftImage;
						else
							tempBitmap=bunny_mRoleMovingRightImage;
						break;
					}
					case 3:
					{
						if(tempBitmap==mRoleMovingLeftImage)
							tempBitmap=doodlestein_mRoleMovingLeftImage;
						else
							tempBitmap=doodlestein_mRoleMovingRightImage;
						break;
					}
					case 5:
					{
						if(tempBitmap==mRoleMovingLeftImage)
							tempBitmap=ice_mRoleMovingLeftImage;
						else
							tempBitmap=ice_mRoleMovingRightImage;
						break;
					}
					case 6:
					{
						if(tempBitmap==mRoleMovingLeftImage)
							tempBitmap=jungle_mRoleMovingLeftImage;
						else
							tempBitmap=jungle_mRoleMovingRightImage;
						break;
					}
					case 7:
					{
						if(tempBitmap==mRoleMovingLeftImage)
							tempBitmap=ninja_mRoleMovingLeftImage;
						else
							tempBitmap=ninja_mRoleMovingRightImage;
						break;
					}
					case 8:
					{
						if(tempBitmap==mRoleMovingLeftImage)
							tempBitmap=snow_mRoleMovingLeftImage;
						else
							tempBitmap=snow_mRoleMovingRightImage;
						break;
					}
					case 9:
					{
						if(tempBitmap==mRoleMovingLeftImage)
							tempBitmap=soccer_mRoleMovingLeftImage;
						else
							tempBitmap=soccer_mRoleMovingRightImage;
						break;
					}
					case 10:
					{
						if(tempBitmap==mRoleMovingLeftImage)
							tempBitmap=space_mRoleMovingLeftImage;
						else
							tempBitmap=space_mRoleMovingRightImage;
						break;
					}
					case 11:
					{
						if(tempBitmap==mRoleMovingLeftImage)
							tempBitmap=underwater_mRoleMovingLeftImage;
						else
							tempBitmap=underwater_mRoleMovingRightImage;
						break;
					}

				}
				canvas.drawBitmap(tempBitmap, role.getMinX(), role.getMinY(),
						null);
			}

			Food food = mGameUi.getFood();
			if (food.mFoodReward != GameUi.FOOD_NONE && food.mTimeCounter > 0) {
				switch (food.mFoodReward) {
				case GameUi.FOOD_1:
					tempBitmap = mFoodImage1;
					break;
				case GameUi.FOOD_2:
					tempBitmap = mFoodImage2;
					break;
				case GameUi.FOOD_3:
					tempBitmap = mFoodImage3;
					break;
				case GameUi.FOOD_4:
					tempBitmap = mFoodImage4;
					break;
				case GameUi.FOOD_5:
					tempBitmap = mFoodImage5;
					break;
				case GameUi.FOOD_6:
					tempBitmap = mFoodImage6;
					break;
				case GameUi.FOOD_7:
					tempBitmap = mFoodImage7;
					break;
				case GameUi.FOOD_8:
					tempBitmap = mFoodImage8;
					break;
				}
				canvas.drawBitmap(tempBitmap, food.mMinX, food.mMinY, null);
			}

			mTopBarImage.setBounds(0, 0,
					DoodleJumpView.this.mScreenAttribute.maxX, 160);
			mTopBarImage.draw(canvas);
			
            canvas.drawText(mGameUi.getScoreStr(), (float) 30, 70, mGameMsgLeftPaint);


			
			//画生命值的总长度
			//mHpBarTotalImage.setBounds(
			 //public void setBounds (int left, int top, int right, int bottom)表示四个顶点坐标
			//		0,0,DoodleJumpView.this.mScreenAttribute.maxX,15);
			//mHpBarTotalImage.draw(canvas);
			
			
                //画剩余生命的值
			//mHpBarRemainImage
			//		.setBounds(0,0,(int)(DoodleJumpView.this.mScreenAttribute.maxX* mGameUi.getHp()),50);
			//mHpBarRemainImage.draw(canvas);
                 
			//右上角显示游戏等级
			//canvas.drawText(mGameUi.getLevel(),
			//		(float) (DoodleJumpView.this.mScreenAttribute.maxX - 5),50,mGameMsgRightPaint);
		}
		
		
		//初始化，游戏的 gameUi 
		public void initGameUi(ScreenAttribute screenAttribut) {
			initRes();
			switch (NowBackGroundImag){
				case 1://BackGround_Normal
					mBackgroundImage=normal_bgImage;
					break;
				case 2://BackGround_Bunny
					mBackgroundImage=bunny_bgImage;
					break;
				case 3://doodlestein_bgImage;
					mBackgroundImage=doodlestein_bgImage;
					break;
				case 4://ghost_bgImage;
					mBackgroundImage=ghost_bgImage;
					break;
				case 5://ice_bgImage;
					mBackgroundImage=ice_bgImage;
					break;
				case 6:
					mBackgroundImage=jungle_bgImage;
					break;
				case 7:
					mBackgroundImage= ninja_bgImage;
					break;
				case 8:
					mBackgroundImage= snow_bgImage;
					break;
				case 9:
					mBackgroundImage= soccer_bgImage;
					break;
				case 10:
					mBackgroundImage= space_bgImage;
					break;
				case 11:
					mBackgroundImage= underwater_bgImage;
			}
			mBackgroundImage = Bitmap.createScaledBitmap(mBackgroundImage,
					screenAttribut.maxX, screenAttribut.maxY, true);
			if (mGameUi != null) {
				mRun = false;
				mGameUi.destroy();
			}
			int addVelocity = 0;
			if (mActionPower < 10) {
				addVelocity = -2;
			} else if (mActionPower < 25) {
				addVelocity = -1;
			} else if (mActionPower < 50) {
				addVelocity = 0;
			} else if (mActionPower < 60) {
				addVelocity = 1;
			} else if (mActionPower < 70) {
				addVelocity = 2;
			} else if (mActionPower < 80) {
				addVelocity = 3;
			} else if (mActionPower < 90) {
				addVelocity = 4;
			} else {
				addVelocity = 5;
			}
			mGameUi = new GameUi(screenAttribut, addVelocity);
		}
		
		
		
		
		// 处理当屏幕重力感应后传递随重力感应变化而变化的x 的值， 
		//这是线程的 处理移动
		public void handleMoving(float data_x_Value) {
			if (mGameUi != null) {
				mGameUi.handleMoving(data_x_Value);
			}
		}
		
		//处理音效文件
		private void handleEffect(int effectFlag) {
			if (effectFlag == mGameUi.EFFECT_FLAG_NO_EFFECT)
				return;
			// 处理音效
			if (mSoundsFlag) {
				playSoundEffect(effectFlag);
			}
			// 处理震动
			if (mVibratorFlag) {
				if (mVibrator == null) {
					mVibrator = (Vibrator) mContext
							.getSystemService(Context.VIBRATOR_SERVICE);
				}
				mVibrator.vibrate(25);
			}
		}
		
		
		/**
		 * 播放音效
		 *
		 */
		private void playSoundEffect(int soundId) {
			try {
				AudioManager mgr = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
				 //获取当前声音大小
				float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_RING);
				  //获取系统最大声音大小
				float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_RING);
				
				float volume = streamVolumeMax;

				Log.e("soundId"," ");
				soundPool.play(soundPoolMap.get(soundId), volume, volume, 1, 0,
						1f);
				//该方法的第一个参数指定播放哪个声音；leftVolume、rightVolume指定左、右的音量：priority指定播放声音的优先级，
				//数值越大，优先级越高；loop指定是否循环，0为不循环，-1为循环；rate指定播放的比率，数值可从0.5到2， 1为正常比率
			} catch (Exception e) {
				Log.d("PlaySounds", e.toString());
			}
		}
		
		
		//设置控制线程开始或结束的变量
		public void setRunning(boolean run) {
			mRun = run;
		}


	}
		
}
	
