package app.yli.org.flickriptranslator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Strings;


public class MainActivity extends Activity {

  private TextView outputTextView;

  static{
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    outputTextView = (TextView)findViewById(R.id.outpout_textview);
    outputTextView.setMovementMethod(ScrollingMovementMethod.getInstance());

    TextView infoTextView = (TextView)findViewById(R.id.info_textview);
    infoTextView.setMovementMethod(LinkMovementMethod.getInstance());
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
    Context context = getApplicationContext();

    int id = item.getItemId();
    if (id == R.id.refresh) {
      ProgressDialog pd = ProgressDialog.show(MainActivity.this, "", "正在查找可用的服务器...");
      new TranslatingTask(getApplicationContext(), outputTextView, pd).execute("", "", "");
    } else if (id == R.id.copy) {
      CharSequence output = outputTextView.getText();

      CharSequence toastText = context.getText(R.string.copy_fail_info);
      if (!Strings.isNullOrEmpty(output.toString())) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", output);
        clipboard.setPrimaryClip(clip);
        toastText = context.getText(R.string.copy_info);
      }

      int duration = Toast.LENGTH_SHORT;

      Toast toast = Toast.makeText(context, toastText, duration);
      toast.show();
    } else if (id == R.id.about) {
      Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bloodlee.com/wordpress/?page_id=792"));
      startActivity(browserIntent);
    }
    return super.onOptionsItemSelected(item);
  }
}
