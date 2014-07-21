package app.yli.org.flickriptranslator;

import android.app.Activity;
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
      int id = item.getItemId();
      if (id == R.id.refresh) {
        new TranslatingTask(getApplicationContext(), outputTextView).execute("", "", "");
      } else if (id == R.id.copy) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", outputTextView.getText());
        clipboard.setPrimaryClip(clip);

        Context context = getApplicationContext();
        CharSequence text = context.getText(R.string.copy_info);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
      } else if (id == R.id.about) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bloodlee.com"));
        startActivity(browserIntent);
      }
      return super.onOptionsItemSelected(item);
  }
}
