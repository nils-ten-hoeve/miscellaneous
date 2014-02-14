package nth.android.mysettings.ui.dialog;

import java.net.URI;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.net.Uri;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogTitleBar extends LinearLayout {

	private static final int DIALOG_TITLEBAR_HEIGHT = 50;
	private ImageView iconView;
	private TextView titleView;
	private DialogIconBar toolBarView;

	public DialogTitleBar(Dialog dialog) {
		super(dialog.getContext());
		setOrientation(LinearLayout.HORIZONTAL);
		setBackgroundColor(Color.DKGRAY);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, DIALOG_TITLEBAR_HEIGHT));
		setGravity(Gravity.CENTER_VERTICAL);

		titleView = createTitle(dialog.getContext());
		addView(titleView);

		toolBarView = new DialogIconBar(dialog, LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		addView(toolBarView);
	}

	public DialogTitleBar(Dialog dialog, String title) {
		this(dialog);
		// set subtitle
		setTitle(title);
	}

	public TextView createTitle(Context context) {
		ApplicationInfo appInfo = context.getApplicationInfo();
		String appTitle = context.getPackageManager().getApplicationLabel(appInfo).toString();

		TextView titleView = new TextView(getContext());
		titleView.setTextColor(Color.WHITE);
		titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		titleView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1f));
		titleView.setText(appTitle);
		return titleView;
	}

	public void setIconURI(URI iconURI) {
		if (iconURI != null) {
			Uri iconUri = Uri.parse(iconURI.toString());
			iconView.setImageURI(iconUri);
		}
	}

	public void setTitle(String title) {
		titleView.setText(title);
	}


}
