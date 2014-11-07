package app.yli.org.flickriptranslator;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yli on 2014/7/20.
 */
public class TranslatingTask extends AsyncTask<String, String, String> {

  private final TextView outputTextView;

  private final Context context;

  private final ProgressDialog pd;

  public TranslatingTask(Context context, TextView outputTextView, ProgressDialog pd) {
    this.context = context;
    this.outputTextView = outputTextView;
    this.pd = pd;
  }

  @Override
  protected String doInBackground(String... params) {
    List<FlickrFarm> farms = FlickrIpResolver.find();
    if (farms != null) {
      StringBuilder sb = new StringBuilder();
      for (FlickrFarm farm : farms) {
        sb.append(String.format("%s %s%n", farm.getEastHostIp(), farm.getGlobalHostName()));
      }
      return sb.toString();
    } else {
      return context.getText(R.string.error_info).toString();
    }
  }

  @Override
  protected void onPostExecute(String s) {
    outputTextView.setText(s);
    pd.dismiss();
  }
}
