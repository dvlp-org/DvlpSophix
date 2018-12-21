package news.dvlp.dvlpsophix;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by liubaigang on 2018/12/20.
 */

public class MyApplication2 extends Application {

    private final String idSecret = "25430819-1";
    private final String appSecret = "226b9d5e1c6f6b231849765a895c0236";
    private final String rsaSecret = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHb2FxK9kcEClVG3iN24ha7y11ZO0UyFNH1mvXd9B";

    @Override
        public void onCreate() {
            super.onCreate();

            // 查询服务器是否有可用补丁
            // SDK内部限制连续两次queryAndLoadNewPatch()方法调用不能短于3s, 否则的话就会报code:19的错误码.
            SophixManager.getInstance().queryAndLoadNewPatch();
        }

        @Override
        protected void attachBaseContext(Context base) {
            super.attachBaseContext(base);
            initHotFix();
        }

        private void initHotFix() {
            String appVersion;
            try {
                appVersion = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
                Toast.makeText(this,"version=="+appVersion,Toast.LENGTH_LONG).show();
            } catch (PackageManager.NameNotFoundException e) {
                appVersion = "1.0";
            }
            SophixManager.getInstance().setContext(this)
                    .setAppVersion(appVersion) //appVersion与app版本无关可以自己定义只要与自己控制台中版本一致即可
                    .setSecretMetaData(idSecret, appSecret, rsaSecret)
                    .setAesKey(null)
                    .setEnableDebug(true) //正式发布该参数必须为false, false会对补丁做签名校验, 否则就可能存在安全漏洞风险
                    .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                        @Override
                        public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                            // 补丁加载回调通知
                            Log.i("tag", "onLoad  code:---------->"+code);
                            if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                                // 表明补丁加载成功
                            } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                                // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                                // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                                SophixManager.getInstance().killProcessSafely();//安全自杀应用推荐
                            }else if (code == PatchStatus.CODE_LOAD_FAIL) {
                                // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                                SophixManager.getInstance().cleanPatches();
                            } else {
                                // 其它错误信息, 查看PatchStatus类说明
                            }
                        }
                    }).initialize();
        }
    }

