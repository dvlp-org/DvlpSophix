package news.dvlp.dvlpsophix;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.taobao.sophix.SophixManager;

/**
 * Created by liubaigang on 2018/12/20.
 */

public class MyApplication extends Application {
    private int alive_count;//当前存活activity总数量

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                alive_count++;
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                alive_count--;

                check4Restart();
            }
        });
    }

    private void check4Restart() {
        SharedPreferences sp = getSharedPreferences("hotfix", MODE_PRIVATE);
        boolean shouldRestart = sp.getBoolean("hotfix_need_restart", false);
        if (alive_count == 0 && shouldRestart) {
            sp.edit().clear().commit();
            SophixManager.getInstance().killProcessSafely();
        }
    }


}
