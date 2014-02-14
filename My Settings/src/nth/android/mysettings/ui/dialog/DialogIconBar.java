package nth.android.mysettings.ui.dialog;

import android.R;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DialogIconBar extends LinearLayout {

	public DialogIconBar(final Dialog dialog, int horizontalWidth, int verticalWidth) {
		super(dialog.getContext());
		setLayoutParams(new LayoutParams(horizontalWidth, verticalWidth));
		setBackgroundColor(Color.DKGRAY);
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
		ImageView icon = new ImageView(getContext());
		icon.setImageResource(R.drawable.ic_menu_close_clear_cancel);
		icon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		addView(icon);
	}
}