package app.yli.org.flickriptranslator;

import com.google.common.base.Strings;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yli on 2014/7/20.
 */
public class FlickrIpResolver {

  private static final String FLICKR_TEST_PAGE_URL = "http://www.flickr.com/help/test";

  private static final Pattern SERVER_PATTERN = Pattern.compile("http[s]{0,1}://([^/]+)/");

  private static final int FARM_NAME_COL_INDEX = 0;
  private static final int GLOBAL_SERVER_COL_INDEX = 1;
  private static final int EAST_SERVER_COL_INDEX = 2;

  /**
   * Translate the IP of flickr servers.
   * @return
   */
  public static List<FlickrFarm> find() {

    List<FlickrFarm> farms = new ArrayList<FlickrFarm>();

    try {
      Document doc = Jsoup.connect(FLICKR_TEST_PAGE_URL).get();

      Elements elements = doc.select("div#Main").first().select("table").first().select("tr");

      StringBuilder sb = new StringBuilder();
      for (Element element : elements) {
        Elements tdElements = element.select("td");

        String farmerName = tdElements.get(FARM_NAME_COL_INDEX).text();

        if (!Strings.isNullOrEmpty(farmerName)) {
          Element globalServerElm = tdElements.get(GLOBAL_SERVER_COL_INDEX).select("a[href]").first();
          if (globalServerElm == null) {
            continue;
          }

          String globalServername = getServerHost(globalServerElm.attr("href"));
          if (Strings.isNullOrEmpty(globalServername)) {
            continue;
          }

          Element eastServerElm = tdElements.get(EAST_SERVER_COL_INDEX).select("a[href]").first();
          if (eastServerElm != null) {
            String eastServerName = getServerHost(eastServerElm.attr("href"));
            if (!Strings.isNullOrEmpty(eastServerName)) {
              String ip = InetAddress.getByName(eastServerName).getHostAddress();

              farms.add(new FlickrFarm(farmerName, globalServername, eastServerName, ip));
            }
          }
        }
      }

      return farms;

    } catch (IOException e) {
      return null;
    }
  }

  private static String getServerHost(String href) {
    Matcher matcher = SERVER_PATTERN.matcher(href);

    if (matcher.find()) {
      return matcher.group(1);
    } else {
      return "";
    }
  }

  public static void main(String[] args) {
    System.out.println(FlickrIpResolver.find());
  }
}
