package news.dvlp.dvlpsophix;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.taobao.sophix.PatchStatus;

/**
 * Created by liubaigang on 2018/12/20.
 */

public class HotFixReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(SophixStubApplication.HOTFIX_NEED_RESTART_BROADCAST.equals(action)){
            int code = intent.getIntExtra("code", 0);
            if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                Toast.makeText(context,"patch应用成功",Toast.LENGTH_LONG).show();
            } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                Toast.makeText(context,"patch将在重启后生效",Toast.LENGTH_LONG).show();
            }
        }
    }
}


