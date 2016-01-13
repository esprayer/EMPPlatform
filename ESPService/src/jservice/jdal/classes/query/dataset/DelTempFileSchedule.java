package jservice.jdal.classes.query.dataset;

import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.util.Date;

import com.efounder.pub.util.Debug;

public class DelTempFileSchedule {
  private Timer timer = new Timer();
  private int hours;
  private int minutes;
  private int seconds;
  private long intevel;
  private String filePath;
  private DelTempFileTask task;
  public DelTempFileSchedule(String filePath,
                             int hours,
                             int minutes,
                             int seconds) {
    this.filePath = filePath;
    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
    this.intevel = (hours * 60 * 60 + minutes * 60 + seconds) * 1000;
    this.task = new DelTempFileTask(filePath);
  }

  public void start() {
    timer.schedule(task, 0, intevel);
  }
}

class DelTempFileTask
    extends TimerTask {

  private String filePath;
  public DelTempFileTask(String filePath) {
    this.filePath = filePath;
  }

  public void run() {
    delTempFile();
  }

  private void delTempFile() {
    //当前时间
    Date date = new Date();
    long currentTime = date.getTime();
    //删除 10 分钟之前的文件
    currentTime -= 10 * 60 * 1000;
    String currentTimeStr = Long.toString(currentTime);

    File file = new File(filePath);
    File[] fileList = file.listFiles();
    for (int i = 0; i < fileList.length; i++) {
      if (isMayDel(fileList[i], currentTimeStr)) {
        fileList[i].delete();
        Debug.PrintlnMessageToSystem("删除临时文件 " +fileList[i].getName());
      }
    }
  }

  private boolean isMayDel(File file, String currentTimeStr) {
    String fileName = file.getName();
    int index=fileName.indexOf("_");
    if(index>-1){
      if (fileName.substring(index+1).compareTo(currentTimeStr) < 0) {
        return true;
      }
      else {
        return false;
      }
    }
    return false;
  }
}
