package news.dvlp.dvlpsophix;

/**
 * Created by liubaigang on 2018/12/20.
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Keep;
import android.util.Log;
import android.widget.Toast;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";
    public static final String HOTFIX_NEED_RESTART_BROADCAST = "hotfix.need_restart";
    private final String idSecret = "25430819-1";
    private final String appSecret = "226b9d5e1c6f6b231849765a895c0236";
    private final String rsaSecret = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHb2FxK9kcEClVG3iN24ha7y11ZO0UyFNH1mvXd9B+UV1qH6OXs00MdFR1nlSLU+w2wQX+7Ogr71v22PoTPADp88Qe/e5Ch2Gn/86EsQFjlfWavbqeaTF34kT2aQPBgWyHFUCncPPM1aymp7rh1aG6QhaVWNHKYkbxWwB3Dhw3+avkiQsT+unafIgRFIdBYJIV0yU5w0wAVJKqP2wkr/DIPVjcUE6fhlZLztGkzD4ymnuLFVinppBboRagM40zF+MMWtZU0CgdArM/+yoI56yaADOR03CPfcA7+ClgO28hpGQAERHkgqaKC+KWypPPw5G3mVxSfMoFP5nQXJv9hhTRAgMBAAECggEAS8wdMLIFlhDeZKZIL0IclNMKZ++v25I3dLodyoqTBy9NXaQgvOs8gVtFLl66qyl+QWEjMjxAEVKrlSnqAZ1wi3pGM7E2+LoTd5JVn0DW0k6QRfuODd7VGkc5Bp3q+4SBBuYg6IKZZmRfdgeRk3tqOCweASz8rvqjE+AOUTFfsDXbPkE/m0pIwfkbvu+KIgH/8rT6XN9/jTqkqCx2JYcvcGcp9jkoYt/BEEhx6OajIWkC85OJBzFAx8sczaKhGLvi6phMvP0gG3k568HJ0d3Ddg0GQigyavv4D3Xu4+7mjTsv5oKJv+Ao0mNItQfXcGbovuUK9c8fj46ZEq9TV+7IsQKBgQD8RpoViBWoXbg6GLO52DAzO87Z4Dhj3r/eOyBefnjO+xQoc8LtYQIFPz1ljTmdLkS8P03qJssHhZ6bJ6p6ymCnqNDKyj/WBFsd7vbXG3mvrm0PJWn7YT405Cc7mCDiEzS8lkvSCsT8EPVI0dr35C/dS1jPDjpTzS2K8uqN+/nUnwKBgQDKYRVrkkoZ74fFZefsmTSgQJ78MQdjK3dU/F5CNc2r8IftItYpQBPgu5A1HMnMzMrJkVrZvBNSo/p1oBcf2+ZEU1l1WTYHbprIh/HOwhpFkNZQoGR6NGPwB8sGl/wvcdNLzL3OPDlSTeEVQ60rG7w6jOrQ0wSIu5A43hll3lWwjwKBgCE1XgUlk8xFAgJLzCTTq4yi+9i8Zk/nO5SSbfFibp64eG/WxTsgQPbV20wp6gKh25R4NNZ04tcLpNRBxXRCfbS0ST/YncybaC8pjL7GNB+HM34B69CWfWzS98MlhHaeFpwYoMR2fHW9+bvrPArTs+VKjNc8xTrQ0ITw8yVbnQh7AoGBAK/opRrEpH5YstYFFwuTu6eTo92nrDkp/ea0GiZI6AYM48len3JpjR2pStavuES4uYdtPd/GV7FccQau6Yq6tDMtk7OpoInjPM0TRT6nfvh6xZ59W/jQ8xEfZPzNPNnsgXIAe/b4aAa/ROcb1TiEOh9Wf4CV+vnytpCHlOHz2N3HAoGAcuLCIqdMj2kAlxSO2eXJwX82PK7we/Et7KO0LDVGYCEe8ma+fl8h8MyMOfAqh2gLyoYB3lLJlKblbxgu5/LazZddpbQohmDFXilA06j6DUMEQq5ypopdA5bhuP5OyO1un4eAzT7rr2C3hG8Xe2GKVzQGT6ajsw5d3krYk8CPFv4=";

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//        MultiDex.install(this);
        initSophix();
    }

    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
            Toast.makeText(this,"version=="+appVersion,Toast.LENGTH_LONG).show();

        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(idSecret, appSecret, rsaSecret)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }else{
                            Log.i(TAG, "sophix other code is "+code);
                        }
                        Intent intent = new Intent(HOTFIX_NEED_RESTART_BROADCAST);
                        intent.putExtra("code", code);
                        sendBroadcast(intent);
                    }
                }).initialize();
    }
}
