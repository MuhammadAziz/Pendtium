package islam.adhanalarm.receiver;

import islam.adhanalarm.Notifier;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.pendtium.base.activities.HomeActivity;

public class ClickNotificationReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Notifier.stop();
		
		Intent i = new Intent(context, HomeActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}
}