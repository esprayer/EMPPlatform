package org.openide;



import java.io.*;
import java.text.*;
import java.util.*;

import org.openide.util.*;

/**
 * A class that provides logging facility for the IDE - once instantiated, it
 * redirects the System.err into a log file.
 * @author Ian Formanek, Ales Novak, Jesse Glick
 */
public class TopLogging
{
  public static final String LOGG_SPACE = "LoggSpace";
    /** The name of the log file */
    public static final String LOG_ERROR_NAME = "StdErr.log"; // NOI18N
    public static final String LOG_OUTPUT_NAME = "StdOut.log"; // NOI18N

    private static boolean disabledConsole = true;

    public static void setDisabledConsole(boolean b) {
      disabledConsole = b;
    }
    public static boolean getDisabledConsole() {
      return disabledConsole;
    }

    private final PrintStream errLogPrintStream;
    private final PrintStream outLogPrintStream;
    private static  StreamDemultiplexorOut sdemulout;
    private static  StreamDemultiplexorErr sdemulerr;
    private static Logview logView = null;

    private static TopLogging topLogging;

    /** Maximal size of log file.*/
    private static final long LOG_MAX_SIZE =
        Long.getLong("com.efounder.core.TopLogging.LOG_MAX_SIZE", 0x40000).longValue(); // NOI18N

    /** Number of old log files that are maintained.*/
    private static final int LOG_COUNT =
        Integer.getInteger("com.efounder.core.TopLogging.LOG_COUNT", 3).intValue(); // NOI18N
//    private static boolean disableLog = false;   // ȫ����־
    private static boolean disableOutLog = true;// �����־
    private static boolean disableErrLog = true;// ������־
    public static void setDisableErrLog(boolean b) {
      disableErrLog = b;
    }
    public  static void truncationLog(){
      try {
        sdemulout.truncation();
        sdemulerr.truncation();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    public static boolean getDisableErrLog() {
      return disableErrLog;
    }
    public static void setDisableOutLog(boolean b) {
      disableOutLog = b;
    }
    public static boolean getDisableOutLog() {
      return disableOutLog;
    }
  public synchronized static void setLogView(Logview lv) {
    logView = lv;
  }

  public synchronized static Logview getLogView() {
    return logView;
  }
  /** Creates a new TopLogging - redirects the System.err to a log file.
     * @param logDir A directory for the log file
     */
    TopLogging (String logDir) throws IOException  {
        topLogging = this;

        File logFileDir = new File (logDir);
        if (! logFileDir.exists () && ! logFileDir.mkdirs ()) {
            throw new IOException ("Cannot make directory to contain log file"); // NOI18N
        }
        File errFile = createLogFile (logFileDir, LOG_ERROR_NAME);
        File outFile = createLogFile (logFileDir, LOG_OUTPUT_NAME);
        if ((errFile.exists() && !errFile.canWrite()) || errFile.isDirectory()) {
            throw new IOException ("Cannot write to error file"); // NOI18N
        }
        if ((outFile.exists() && !outFile.canWrite()) || outFile.isDirectory()) {
            throw new IOException ("Cannot write to output file"); // NOI18N
        }
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.ENGLISH);
        java.util.Date date = new java.util.Date();
        disabledConsole =  true;//Boolean.getBoolean("com.efounder.console"); // NOI18N

        OutputStream errLog = null;

             errLog = new BufferedOutputStream(new FileOutputStream(errFile.getAbsolutePath(), true));

        final PrintStream stderr = System.err;
        sdemulerr=new StreamDemultiplexorErr(stderr, errLog,errFile,1);
        errLogPrintStream = new PrintStream(sdemulerr, false, (String)System.getProperty("file.encoding","GBK")); // NOI18N
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                errLogPrintStream.flush(); // #31519
                errLogPrintStream.close();
            }
        });
        errLogPrintStream.println("-------------------------------------------------------------------------------"); // NOI18N
        errLogPrintStream.println(">Log Session: "+ df.format (date)); // NOI18N
        errLogPrintStream.println(">System Info: "); // NOI18N
//        printSystemInfo(errLogPrintStream);
        errLogPrintStream.println("-------------------------------------------------------------------------------"); // NOI18N
        System.setErr(errLogPrintStream);

        OutputStream outLog = null;
//        if ( Boolean.getBoolean("com.efounder.outlog") )
             outLog = new BufferedOutputStream(new FileOutputStream(outFile.getAbsolutePath(), true));

        final PrintStream stdout = System.out;
        sdemulout=new StreamDemultiplexorOut(stdout, outLog,outFile);
        outLogPrintStream = new PrintStream(sdemulout, false, "GBK"); // NOI18N
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                outLogPrintStream.flush(); // #31519
                outLogPrintStream.close();
            }
        });
        outLogPrintStream.println("-------------------------------------------------------------------------------"); // NOI18N
        outLogPrintStream.println(">Log Session: "+ df.format (date)); // NOI18N
        outLogPrintStream.println(">System Info: "); // NOI18N
//        printSystemInfo(outLogPrintStream);
        outLogPrintStream.println("-------------------------------------------------------------------------------"); // NOI18N
        System.setOut(outLogPrintStream);
    }

    public static TopLogging getDefault() {
        if (topLogging == null) {
            try {
              String Dir = (String)System.getProperty ("eai.home") + LOGG_SPACE;
              new TopLogging(Dir);
            } catch (IOException x) {
                org.openide.ErrorManager.getDefault().notify(x);
            }
        }
        return topLogging;
    }

    /** This method limits size of log files. There is kept: actual log file
     *  and old log files. This method prevents from growing log file infinitely.*/
    private static File createLogFile (File parent, String chld) {
        long firstModified = 0;
        File renameTo = null;
        File retFile = new File (parent, chld);

        if (!retFile.exists() || retFile.length() < LOG_MAX_SIZE)
            return retFile;

        for (int i = 1; i < LOG_COUNT;i++) {
            String logName = chld + "."+i; // NOI18N
            File logFile = new File (parent, logName);

            if (!logFile.exists()) {
                renameTo = logFile;
                break;
            }

            long logModif = logFile.lastModified();
            if ((firstModified == 0 || logModif < firstModified) &&  logModif > 0) {
                firstModified = logModif;
                renameTo = logFile;
            }
        }

        if (renameTo != null) {
            if (renameTo.exists()) renameTo.delete();
            retFile.renameTo(renameTo);
        }

        return retFile;
    }

    public static void printSystemInfo(PrintStream ps) {
        String buildNumber = System.getProperty ("efounder.buildnumber"); // NOI18N
//        String currentVersion = NbBundle.getMessage(TopLogging.class, "currentVersion", buildNumber );
//        ps.println("  Product Version       = " + currentVersion); // NOI18N
        ps.println("  Operating System      = " + System.getProperty("os.name", "unknown")
                   + " version " + System.getProperty("os.version", "unknown")
                   + " running on " +  System.getProperty("os.arch", "unknown"));
        ps.println("  Java; VM; Vendor      = " + System.getProperty("java.version", "unknown") + "; " +
                   System.getProperty("java.vm.name", "unknown") + " " + System.getProperty("java.vm.version", "") + "; " +
                   System.getProperty("java.vendor", "unknown"));
        //ps.println("  Java Vendor URL          = " + System.getProperty("java.vendor.url", "unknown"));
        ps.println("  Java Home             = " + System.getProperty("java.home", "unknown"));
        //ps.println("  Java Class Version       = " + System.getProperty("java.class.version", "unknown"));
        ps.print  ("  System Locale; Encod. = " + Locale.getDefault()); // NOI18N
//        String branding = NbBundle.getBranding ();
//        if (branding != null) {
//            ps.print(" (" + branding + ")"); // NOI18N
//        }
        ps.println("; " + System.getProperty("file.encoding", "unknown")); // NOI18N
        ps.println("  Home Dir; Current Dir = " + System.getProperty("user.home", "unknown") + "; " +
                   System.getProperty("user.dir", "unknown"));
//        ps.println("  IDE Install; User Dir = " + Main.getHomeDir () + "; " + // NOI18N
//                   Main.getUserDir ()); // NOI18N
        //ps.println("  System Directory         = " + Main.getSystemDir ()); // NOI18N
        ps.println("  CLASSPATH             = " + System.getProperty("java.class.path", "unknown")); // NOI18N
        ps.println("  Boot & ext classpath  = " + createBootClassPath()); // NOI18N
        ps.println("  Dynamic classpath     = " + System.getProperty("efounder.dynamic.classpath", "unknown")); // NOI18N
    }

    // Copied from NbClassPath:
    private static String createBootClassPath() {
        // boot
        String boot = System.getProperty("sun.boot.class.path"); // NOI18N
        StringBuffer sb = (boot != null ? new StringBuffer(boot) : new StringBuffer());

        // std extensions
        String extensions = System.getProperty("java.ext.dirs"); // NOI18N
        if (extensions != null) {
            for (StringTokenizer st = new StringTokenizer(extensions, File.pathSeparator); st.hasMoreTokens();) {
                File dir = new File(st.nextToken());
                File[] entries = dir.listFiles();
                if (entries != null) {
                    for (int i = 0; i < entries.length; i++) {
                        String name = entries[i].getName().toLowerCase(Locale.US);
                        if (name.endsWith(".zip") || name.endsWith(".jar")) { // NOI18N
                            if (sb.length() > 0) {
                                sb.append(File.pathSeparatorChar);
                            }
                            sb.append(entries[i].getPath());
                        }
                    }
                }
            }
        }

        return sb.toString();
    }

    protected void finalize() throws Throwable {
        errLogPrintStream.flush();
        errLogPrintStream.close();
        outLogPrintStream.flush();
        outLogPrintStream.close();
    }

    static PrintStream getLogOutputStream() {
//        if (System.getProperty("netbeans.user") == null) { // NOI18N
//            // No user directory. E.g. from <makeparserdb>. Skip ide.log.
//            return System.err;
//        }
        return TopLogging.getDefault().outLogPrintStream;
    }
    static PrintStream getLogErrorStream() {
//        if (System.getProperty("netbeans.user") == null) { // NOI18N
//            // No user directory. E.g. from <makeparserdb>. Skip ide.log.
//            return System.err;
//        }
        return TopLogging.getDefault().errLogPrintStream;
    }
    private static final class StreamDemultiplexorOut extends OutputStream implements Runnable {
      private int style = 0;// 0: std out 1: std err
        /** task to flush the log file, or null */
        private RequestProcessor.Task logFlushTask;

        /** processor in which to flush them */
        private static final RequestProcessor RP = new RequestProcessor("Flush ide.log"); // NOI18N

        /** a lock for flushing */
        private static final Object FLUSH_LOCK = new String("com.efounder.core.TopLogging.StreamDemultiplexor.FLUSH_LOCK"); // NOI18N

        /** delay for flushing */
        private static final int FLUSH_DELAY = Integer.getInteger("efounder.logger.flush.delay", 15000).intValue(); // NOI18N

        private final OutputStream stderr;
        private  OutputStream log;
        File logfile;
        StreamDemultiplexorOut(PrintStream stderr, OutputStream log,File logfile) {
          this(stderr,log,logfile,0);
        }
        String date="";
        StreamDemultiplexorOut(PrintStream stderr, OutputStream log,File logfile,int style) {
            this.stderr = stderr;
            this.log = log;
            this.style = style;
            this.logfile=logfile;
             SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
             date=sdf.format(new Date());
        }
        public void truncation() throws Exception {
          log.flush();
          log.close();
          String file = logfile.getAbsolutePath();
          Date date=new Date();
          SimpleDateFormat sdf=new SimpleDateFormat("MMdd-HHmm");
          File rn = new File(file + sdf.format(date));
          logfile.renameTo(rn);
          logfile.delete();
          logfile = new File(file);
          log = new BufferedOutputStream(new FileOutputStream(logfile.
              getAbsolutePath(), true));

        }
        public void write(int b) throws IOException {
          Logview logView = TopLogging.getLogView();
          if ( logView != null ) logView.write(b,style);
            if ( log!= null && !disableOutLog )
              log.write(b);
            if (! disabledConsole)
                stderr.write(b);
            flushLog();
        }

        public void write(byte b[]) throws IOException {
          Logview logView = TopLogging.getLogView();
          if ( logView != null ) logView.write(b,style);
            if ( log!= null && !disableOutLog )
              log.write(b);
            if (! disabledConsole)
              stderr.write(b);
            flushLog();
        }

        public void write(byte b[],
                          int off,
                          int len)
            throws IOException {
          Logview logView = TopLogging.getLogView();
          if ( logView != null ) logView.write(b,off,len,style);
            if ( log!= null && !disableOutLog )
              log.write(b, off, len);
            if (! disabledConsole)
              stderr.write(b, off, len);
            flushLog();
        }

        public void flush() throws IOException {
            if ( log!= null ) log.flush();
            stderr.flush();
        }

        public void close() throws IOException {
            if ( log!= null ) log.close();
            stderr.close();
        }

        /**
         * Flush the log file asynch.
         * Waits for e.g. 15 seconds after the first write.
         * Note that this is only a delay to force a flush; if there is a lot
         * of content, the buffer will fill up and it may have been written out
         * long before. This just catches any trailing content.
         * @see "#31519"
         */
        private void flushLog() {
            synchronized (FLUSH_LOCK) {
                if (logFlushTask == null) {
                    logFlushTask = RP.create(this);
                    logFlushTask.schedule(FLUSH_DELAY);
                }
            }
        }

        /**
         * Flush log messages periodically.
         */
        public void run() {
            synchronized (FLUSH_LOCK) {
                try {
                  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                  String aa=sdf.format(new Date());
                    flush();
                    if(!aa.equals(date)){
                        date=aa;
                        this.truncation();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logFlushTask = null;
            }
        }

    }
    private static final class StreamDemultiplexorErr extends OutputStream implements Runnable {
      private int style = 0;// 0: std out 1: std err
        /** task to flush the log file, or null */
        private RequestProcessor.Task logFlushTask;

        /** processor in which to flush them */
        private static final RequestProcessor RP = new RequestProcessor("Flush ide.log"); // NOI18N

        /** a lock for flushing */
        private static final Object FLUSH_LOCK = new String("com.efounder.core.TopLogging.StreamDemultiplexor.FLUSH_LOCK"); // NOI18N

        /** delay for flushing */
        private static final int FLUSH_DELAY = Integer.getInteger("efounder.logger.flush.delay", 15000).intValue(); // NOI18N

        private final OutputStream stderr;
        private  OutputStream log;
        File logfile;
        String date="";
        StreamDemultiplexorErr(PrintStream stderr, OutputStream log,File logfile) {
          this(stderr,log,logfile,0);
        }

        StreamDemultiplexorErr(PrintStream stderr, OutputStream log,File logfile,int style) {
            this.stderr = stderr;
            this.log = log;
            this.style = style;
            this.logfile=logfile;
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            date=sdf.format(new Date());
        }
        public void truncation() throws Exception {
          log.flush();
          log.close();
          String file = logfile.getAbsolutePath();
          Date date = new Date();
          SimpleDateFormat sdf = new SimpleDateFormat("MMdd-HHmm");
          File rn = new File(file + sdf.format(date));
          logfile.renameTo(rn);
          logfile.delete();
          logfile = new File(file);
          log = new BufferedOutputStream(new FileOutputStream(logfile.
          getAbsolutePath(), true));

        }
        public void write(int b) throws IOException {
          Logview logView = TopLogging.getLogView();
          if ( logView != null ) logView.write(b,style);
            if ( log!= null && !disableErrLog )
              log.write(b);
            if (! disabledConsole)
                stderr.write(b);
            flushLog();
        }

        public void write(byte b[]) throws IOException {
          Logview logView = TopLogging.getLogView();
          if ( logView != null ) logView.write(b,style);
            if ( log!= null && !disableErrLog )
              log.write(b);
            if (! disabledConsole)
              stderr.write(b);
            flushLog();
        }

        public void write(byte b[],
                          int off,
                          int len)
            throws IOException {
          Logview logView = TopLogging.getLogView();
          if ( logView != null ) logView.write(b,off,len,style);
            if ( log!= null && !disableErrLog )
              log.write(b, off, len);
            if (! disabledConsole)
              stderr.write(b, off, len);
            flushLog();
        }

        public void flush() throws IOException {
            if ( log!= null ) log.flush();
            stderr.flush();
        }

        public void close() throws IOException {
            if ( log!= null ) log.close();
            stderr.close();
        }

        /**
         * Flush the log file asynch.
         * Waits for e.g. 15 seconds after the first write.
         * Note that this is only a delay to force a flush; if there is a lot
         * of content, the buffer will fill up and it may have been written out
         * long before. This just catches any trailing content.
         * @see "#31519"
         */
        private void flushLog() {
            synchronized (FLUSH_LOCK) {
                if (logFlushTask == null) {
                    logFlushTask = RP.create(this);
                    logFlushTask.schedule(FLUSH_DELAY);
                }
            }
        }

        /**
         * Flush log messages periodically.
         */
        public void run() {
            synchronized (FLUSH_LOCK) {
                try {
                    flush();
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                    String aa=sdf.format(new Date());
                    if(!aa.equals(date)){
                        date=aa;
                        truncation();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logFlushTask = null;
            }
        }

    }
}
