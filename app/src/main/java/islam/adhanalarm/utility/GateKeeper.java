package islam.adhanalarm.utility;

import islam.adhanalarm.VARIABLE;
import android.content.Context;
import android.content.pm.PackageManager;

public class GateKeeper {
	public static String getVersionName(Context context) {
		String versionName = "undefined";
		try {
			versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA).versionName;
		} catch(Exception ex) { }
		return versionName;
	}
}