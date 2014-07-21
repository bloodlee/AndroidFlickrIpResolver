package app.yli.org.flickriptranslator;

/**
 * Created by yli on 2014/7/21.
 */
public class FlickrFarm {

  private String name;

  private String globalHostName;

  private String eastHostName;

  private String eastHostIp;

  public FlickrFarm(String name, String globalHostName, String eastHostName, String eastHostIp) {
    this.name = name;
    this.globalHostName = globalHostName;
    this.eastHostName = eastHostName;
    this.eastHostIp = eastHostIp;
  }

  public String getName() {
    return name;
  }

  public String getGlobalHostName() {
    return globalHostName;
  }

  public String getEastHostName() {
    return eastHostName;
  }

  public String getEastHostIp() {
    return eastHostIp;
  }
}
