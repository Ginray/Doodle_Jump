    日志：
        修复bug；
        在函数DoodleJumpView中
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
            /
             这里会发生ConcurrentModificationException错误而导致c有可能是null；
             这个bug调试了一周多，真是醉了；
             关于ConcurrentModificationException:

             文档中说明，对Vector、ArrayList在迭代的时候如果同时
             对其进行修改就会抛出java.util.ConcurrentModificationException异常。
             因为我在GameUi 中使用了一个    LinkedList 在updateGameUi()的时候
             访问了两次的LinkedList造成了这个错误,然而我并没有remove  也没有使用多线程；
             因此这个报错就显得非常蛋疼，让我一阵不知所措;

             javadoc 里面指出： it would be wrong to write a program that depended
             on this exception for its correctness: ConcurrentModificationException
             should be used only to detect bugs.


             说人话就是  这个应该用于发bug的东西变成了一个bug，困扰了我一周多；
             ！！！！！


             */
                //if (c != null)
                    mSurfaceHolder.unlockCanvasAndPost(c);
                    Log.v("Canvas","draw");
        }

