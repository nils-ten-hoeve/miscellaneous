package nth.android.mysettings.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import android.R;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class Dialog extends android.app.Dialog {

	private static final int NO_SELECTION = -2;
	private static final int NO_SELECTED = -1;

	private DialogTitleBar titleBarView;
	private View contentView;
	private LinearLayout layout;

	public Dialog(Context context) {
		super(context);
		// setOwnerActivity(context);// check if needed

		// remove default title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		layout = new LinearLayout(context);
		super.setContentView(layout);

		// vertical orientation
		layout.setOrientation(LinearLayout.VERTICAL);

		// create TitleBarView
		titleBarView = new DialogTitleBar(this);
		layout.addView(titleBarView);

		// create ContentView
		contentView = new LinearLayout(context);
		contentView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		layout.addView(contentView);

	}

	public Dialog(Context context, String title) {
		this(context);
		getTitleBarView().setTitle(title);
	}

	public Dialog(Context context, String title, CharSequence message) {
		this(context, title);

		TextView text = new TextView(context);
		text.setText(message);
		text.setMovementMethod(new ScrollingMovementMethod());

		setContentView(text);
	}

	public Dialog(Context context, String title, final List<Item> items) {
		this(context, title, items, NO_SELECTION);
	}

	public Dialog(Context context, String title, final List<Item> items, int selectedItem) {
		this(context, title);

		ArrayList<String> texts = new ArrayList<String>();
		for (Item item : items) {
			texts.add(item.getText());
		}

		ListView listView = new ListView(context);
		ArrayAdapter<String> listAdapter = null;
		if (selectedItem == NO_SELECTION) {
			listAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_list_item_1, texts);
		} else {
			listAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_list_item_single_choice, texts);
		}
		
		listView.setAdapter(listAdapter);
		if (selectedItem > NO_SELECTED) {
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			listView.setItemChecked(selectedItem, true);
		}

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
				dismiss();
				Item item = items.get(index);
				item.getRunnable().run();
			}
		});
		setContentView(listView);

	}

	public DialogTitleBar getTitleBarView() {
		return titleBarView;
	}

	public View getContentView() {
		return contentView;
	}

	public void setContentView(View contentView) {
		layout.removeView(this.contentView);
		contentView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		layout.addView(contentView);
		this.contentView = contentView;
	}

}
