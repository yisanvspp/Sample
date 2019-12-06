package com.yisan.base.crash;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author：wzh
 * @description: 程序奔溃捕获、处理
 * @packageName: com.yisan.base.crash
 * @date：2019/12/5 0005 上午 9:29
 */
@Deprecated
public class CrashProtectManager {

    private volatile static CrashProtectManager instance;

    private static Context mContext;

    private CrashProtectManager() {
    }

    public static CrashProtectManager getInstance(Context context) {
        if (instance == null) {
            //生命周期必须为application的context，防止内存泄漏
            mContext = context.getApplicationContext();

            synchronized (CrashProtectManager.class) {
                if (instance == null) {
                    instance = new CrashProtectManager();
                }
            }
        }
        return instance;
    }


    /**
     * 初始化
     * 方法：setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler())
     * <p>
     * 描述：主线程发生奔溃话系统调用setDefaultUncaughtExceptionHandler()方法、关闭应用杀死进程。
     * <p>
     * 自定义处理奔溃：
     * <p>
     * ActivityThread中main函数中：Loop.loop()会阻塞主、虽然APP没有奔溃，但是界面会卡住
     * <p>
     * 手动调用Loop.loop() 防止APP卡死
     */
    public void init() {

        //捕获全局异常
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {

                handlerFileException(throwable);
//
//                if (thread == Looper.getMainLooper().getThread()) {
//
//                    handlerMainThread(throwable);
//
//                }

            }
        });
    }


    /**
     * 奔溃信息写入到- data/data/packageName/cache/ 目录下
     * 奔溃文件名称： crash-2019-12-05-11-04-30.txt
     *
     * @param t Throwable
     */
    private void handlerFileException(Throwable t) {

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        t.printStackTrace(printWriter);
        printWriter.close();
        String result = writer.toString();
        //定义文件名
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String time = dateFormat.format(new Date());
        String fileName = "crash-" + time + ".txt";
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File cacheDir = mContext.getCacheDir();
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs();
                }
                File cacheFile = new File(cacheDir.getAbsolutePath(), fileName);
                if (!cacheDir.exists()) {
                    cacheFile.createNewFile();
                }
                //把字符串写入到文件
                FileOutputStream outputStream = new FileOutputStream(cacheFile);
                outputStream.write(result.getBytes());
                outputStream.close();
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }


    /**
     * 处理Loop.loop()阻塞的问题
     *
     * @param throwable Throwable
     */
    private void handlerMainThread(Throwable throwable) {

//        while (true) {
//            try {
//                Looper.loop();
//            } catch (Throwable e) {
//                handlerFileException(e);
//            }
//        }
    }

}
