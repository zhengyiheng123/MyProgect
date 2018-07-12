package cloudlive;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.talkfun.sdk.log.TalkFunLogger;
import com.talkfun.sdk.offline.PlaybackDownloader;
import com.tencent.bugly.crashreport.CrashReport;

import cloudlive.consts.MainConsts;
import cloudlive.util.ActivityStacks;

public class TFApplication extends Application {
    public static RefWatcher getRefWatcher(Context context) {
        TFApplication application = (TFApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        //初始化点播下载
        initPlaybackDownLoader();
        TalkFunLogger.setLogEnable(true);
        TalkFunLogger.setLogLevel(TalkFunLogger.LogLevel.ALL);
        CrashReport.initCrashReport(getApplicationContext(), MainConsts.BUGLY_ID, true);

    }

    public void initPlaybackDownLoader() {
        PlaybackDownloader.getInstance().init(this);
        PlaybackDownloader.getInstance().setDownLoadThreadSize(3);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();

        System.exit(0);
    }

    public static void exit() {
        /**终止应用程序对象时调用，不保证一定被调用 ,退出移除所有的下载任务*/
        ActivityStacks.getInstance().finishAllActivity();
        PlaybackDownloader.getInstance().destroy();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}
