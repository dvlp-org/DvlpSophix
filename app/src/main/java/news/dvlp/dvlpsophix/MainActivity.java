package news.dvlp.dvlpsophix;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.taobao.sophix.SophixManager;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReceiver = new HotFixReceiver();
        registerReceiver(mReceiver, new IntentFilter(SophixStubApplication.HOTFIX_NEED_RESTART_BROADCAST));
    }

    public void checkPatch(View view) {
        Toast.makeText(MainActivity.this,"热更新",Toast.LENGTH_SHORT).show();
        StoragePermissions.verifyStoragePermissions(this);
        SophixManager.getInstance().queryAndLoadNewPatch();//检查热更新入口方法
    }

    public void cleanPatch(View view) {
        SophixManager.getInstance().cleanPatches();//清空补丁
    }

    public void killSafely(View view) {
        SophixManager.getInstance().killProcessSafely();//安全退出程序
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
