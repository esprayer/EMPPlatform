package com.efounder.eai.ide;

import com.efounder.resource.JResource;
import com.efounder.ui.Icons;
import com.efounder.ui.Images;
import java.awt.Image;
import javax.swing.Icon;
import java.util.*;
import javax.swing.*;
import java.net.*;
import java.lang.reflect.*;

public class ExplorerIcons
{
    private static final String ClassName = "com.efounder.eai.ide.ExplorerIcons";
    public static final Icon ICON_ADDBREAKPOINT;
    public static final Icon ICON_ADDWATCH;
    public static final Icon ICON_ADD_CUSTOM_VIEW;
    public static final Icon ICON_ADD_NOTRACING;
    public static final Icon ICON_ANT_FILE;
    public static final Icon ICON_ANT_TARGET;
    public static final Icon ICON_ARRAY;
    public static final Icon ICON_ARRAY_BOUNDARY_DOWN;
    public static final Icon ICON_ARRAY_BOUNDARY_UP;
    public static final Icon ICON_ARRAY_CUSTOM_VIEWED;
    public static final Icon ICON_ATBREAKPOINT;
    public static final Icon ICON_BLANK = Icons.getBlankIcon(16, 16);
    public static final Icon ICON_BRACEMATCH;
    public static final Icon ICON_BREAKPOINT;
    public static final Icon ICON_BREAKPOINTDISABLED;
    public static final Icon ICON_BREAKPOINTINVALID;
    public static final Icon ICON_BREAKPOINTVERIFIED;
    public static final Icon ICON_BREAKPOINT_DISABLED_FOR_PROCESS;
    public static final Icon ICON_CASCADE;
    public static final Icon ICON_CHECKED;
    public static final Icon ICON_CHECKED_DISABLED;
    public static final Icon ICON_CLASS;
    public static final Icon ICON_CLEAN_BUILD;
    public static final Icon ICON_CLOSE;
    public static final Icon ICON_CLOSE_FILE;
    public static final Icon ICON_CLOSE_MODIFIED;
    public static final Icon ICON_CLOSE_PROJECT;
    public static final Icon ICON_CONSOLE;
    public static final Icon ICON_CONSOLEERR;
    public static final Icon ICON_CONSOLEOUT;
    public static final Icon ICON_COPY;
    public static final Icon ICON_CUSTOMTOOLS;
    public static final Icon ICON_CUSTOM_VIEW;
    public static final Icon ICON_CUT;
    public static final Icon ICON_CVS_FILE_ADD;
    public static final Icon ICON_CVS_FILE_CHECKIN;
    public static final Icon ICON_CVS_FILE_REMOVE;
    public static final Icon ICON_CVS_FILE_STATUS;
    public static final Icon ICON_CVS_FILE_UPDATE;
    public static final Icon ICON_CVS_MODULE_CREATE;
    public static final Icon ICON_CVS_PROJ_CHECKIN;
    public static final Icon ICON_DEBUG;
    public static final Icon ICON_DELETE;
    public static final Icon ICON_DIFF_NEXT_DIFF;
    public static final Icon ICON_DIFF_NEXT_DIFF_DISABLED;
    public static final Icon ICON_DIFF_PREV_DIFF;
    public static final Icon ICON_DIFF_PREV_DIFF_DISABLED;
    public static final Icon ICON_DISABLED_DOTDOTDOT;
    public static final Icon ICON_DISABLE_ALL_BREAKPOINTS;
    public static final Icon ICON_DISK;
    public static final Icon ICON_DISKFOLDER;
    public static final Icon ICON_DISKFOLDERNEW;
    public static final Icon ICON_DOTDOTDOT;
    public static final Icon ICON_DTD_GENERATE;
    public static final Icon ICON_EDITOROPTIONS;
    public static final Icon ICON_ENABLE_ALL_BREAKPOINTS;
    public static final Icon ICON_ERROR;
    public static final Icon ICON_EXPORT_TO_ANT;
    public static final Icon ICON_EXPRESSION_ERROR;
    public static final Icon ICON_FIELD_BREAKPOINT;
    public static final Icon ICON_FILEBINARY;
    public static final Icon ICON_FILEHTML;
    public static final Icon ICON_FILEIMAGE;
    public static final Icon ICON_FILESOUND;
    public static final Icon ICON_FILETEXT;
    public static final Icon ICON_FIND;
    public static final Icon ICON_FINDAGAIN;
    public static final Icon ICON_FOLDER;
    public static final Icon ICON_FOLDER_ADD;
    public static final Icon ICON_FOLDER_LOCKED;
    public static final Icon ICON_FOLDER_OPENED;
    public static final Icon ICON_FORMATCODE;
    public static final Icon ICON_FORMAT_ALL;
    public static final Icon ICON_FORMAT_LINE;
    public static final Icon ICON_FORMAT_SELECTION;
    public static final Icon ICON_GENERICBUILD;
    public static final Icon ICON_GENERICCLOSE;
    public static final Icon ICON_GENERIC_NEW;
    public static final Icon ICON_GENERIC_RESOURCEFILE;
    public static final Icon ICON_GOBACK;
    public static final Icon ICON_GOFORWARD;
    public static final Icon ICON_GOHOME;
    public static final Icon ICON_GROUP_CLEAN;
    public static final Icon ICON_GROUP_MAKE;
    public static final Icon ICON_GROUP_REBUILD;
    public static final Icon ICON_HELPBORLANDONLINE;
    public static final Icon ICON_HIDE_MESSAGE;
    public static final Icon ICON_INSPECT;
    public static final Icon ICON_INVALID_ERROR;
    public static final Icon ICON_LINE_NUMBERING;
    public static final Icon ICON_MAC_CLOSE;
    public static final Icon ICON_MAC_CLOSE_MODIFIED;
    public static final Icon ICON_MAKE;
    public static final Icon ICON_MONITORDEADLOCK;
    public static final Icon ICON_MONITORLOCK;
    public static final Icon ICON_NEWBROWSER;
    public static final Icon ICON_NEWFOLDER;
    public static final Icon ICON_NEW_FILE;
    public static final Icon ICON_NEW_OBJECT;
    public static final Icon ICON_NEXT_METHOD;
    public static final Icon ICON_NOTRACING;
    public static final Icon ICON_NOTRACING_CLASSPATTERN;
    public static final Icon ICON_NOTRACING_CLASSPATTERN_DISABLED;
    public static final Icon ICON_NULL;
    public static final Icon ICON_OBJECTS;
    public static final Icon ICON_OBJECTS_CUSTOM_VIEWED;
    public static final Icon ICON_OBSOLETEEXCPOINT;
    public static final Icon ICON_OPEN;
    public static final Icon ICON_OPEN_FILE;
    public static final Icon ICON_OPEN_OBJECT;
    public static final Icon ICON_OPEN_PROJECT;
    public static final Icon ICON_OPTIMIZE;
    public static final Icon ICON_OPTIONS;
    public static final Icon ICON_PASTE;
    public static final Icon ICON_PREV_METHOD;
    public static final Icon ICON_PRIMITIVE;
    public static final Icon ICON_PRINT;
    public static final Icon ICON_PROGRAMPAUSE;
    public static final Icon ICON_PROGRAMRESUME;
    public static final Icon ICON_PROGRAMRUN;
    public static final Icon ICON_PROGRAMSTOP;
    public static final Icon ICON_PROJECT;
    public static final Icon ICON_PROJECTADD;
    public static final Icon ICON_PROJECTOPTIONS;
    public static final Icon ICON_PROJECTREMOVE;
    public static final Icon ICON_PROJECT_GROUP;
    public static final Icon ICON_READONLY_CLOSE;
    public static final Icon ICON_REBUILD;
    public static final Icon ICON_REDO;
    public static final Icon ICON_REFRESH;
    public static final Icon ICON_REMOVE_ALL_BREAKPOINTS;
    public static final Icon ICON_REMOVE_ALL_CUSTOM_VIEW;
    public static final Icon ICON_REMOVE_ALL_NOTRACING;
    public static final Icon ICON_REOPEN;
    public static final Icon ICON_REPLACE;
    public static final Icon ICON_RERUNEXECPOINT;
    public static final Icon ICON_RERUNOBSOLETEEXECPOINT;
    public static final Icon ICON_REVEAL_MESSAGE;
    public static final Icon ICON_RUN;
    public static final Icon ICON_SAVE;
    public static final Icon ICON_SAVEALL;
    public static final Icon ICON_SEARCHRESULTS;
    public static final Icon ICON_SEARCHSOURCEPATH;
    public static final Icon ICON_SHOWEXECPOINT;
    public static final Icon ICON_SHOWFRAME;
    public static final Icon ICON_SMARTPOPFRAME;
    public static final Icon ICON_SMARTSTEP_OFF;
    public static final Icon ICON_SMARTSTEP_ON;
    public static final Icon ICON_SMARTSWAP;
    public static final Icon ICON_SMARTVIEW;
    public static final Icon ICON_SMART_KEY;
    public static final Icon ICON_SPECIALFOLDER;
    public static final Icon ICON_STEPINTO;
    public static final Icon ICON_STEPOUT;
    public static final Icon ICON_STEPOVER;
    public static final Icon ICON_TAB_OPTIONS;
    public static final Icon ICON_TERMINAL;
    public static final Icon ICON_THREAD;
    public static final Icon ICON_THREADBLOCKED;
    public static final Icon ICON_THREADBREAK;
    public static final Icon ICON_THREADBREAKBLOCKED;
    public static final Icon ICON_THREADBREAKUSERSUSPENDED;
    public static final Icon ICON_THREADDEAD;
    public static final Icon ICON_THREADGROUP;
    public static final Icon ICON_THREADSUSPENDED;
    public static final Icon ICON_THREADUSERSUSPENDED;
    public static final Icon ICON_TILE;
    public static final Icon ICON_TILEVERTICAL;
    public static final Icon ICON_TIPOFTHEDAY;
    public static final Icon ICON_UNDO;
    public static final Icon ICON_UNKNOWN;
    public static final Icon ICON_VIEWBREAKPOINTS;
    public static final Icon ICON_VIEWLOADEDCLASSES;
    public static final Icon ICON_WARNING;
    public static final Icon ICON_WATCH;
    public static final Icon ICON_WHERE_AM_I;
    public static final Icon ICON_XML_BROWSER_VIEW_DISABLE;
    public static final Icon ICON_XML_BROWSER_VIEW_ENABLE;
    public static final Icon ICON_XML_COCOON;
    public static final Icon ICON_XML_DEFAULT_STYLE_DISABLE;
    public static final Icon ICON_XML_DEFAULT_STYLE_ENABLE;
    public static final Icon ICON_XML_GENERATE;
    public static final Icon ICON_XML_STYLE_PROPS;
    public static final Icon ICON_XML_TRANSFORM;
    public static final Icon ICON_XML_VALIDATION;
    public static final Icon ICON_XML_VALIDATION_TRACE;
    public static final Icon ICON_XSL_TRACE;
    public static final Image IMAGE_DISABLED_DOTDOTDOT;
    public static final Image IMAGE_DOTDOTDOT;
    public static final Image IMAGE_GENERAL;
    public static final Image IMAGE_GENERAL_DISABLED;
    public static final Image IMAGE_OBJECTS;
    public static final Image IMAGE_OBJECTS_DISABLED;
    public static final Image IMAGE_STATUS_BAR;
    public static final Image IMAGE_STATUS_BAR_DISABLED;
    public static final Image IMAGE_WHERE_AM_I;
    public static final Image TINY_IMAGE_STATUS_BAR;
    static Class a;
    private static final com.efounder.ui.Icons.IconFactory c;
    private static final com.efounder.ui.Icons.IconFactory d;
    private static final com.efounder.ui.Icons.IconFactory e;
    private static final com.efounder.ui.Icons.IconFactory f;
    private static final com.efounder.ui.Icons.IconFactory g;
    private static final com.efounder.ui.Icons.IconFactory h;
    private static final com.efounder.ui.Icons.IconFactory i;




    public ExplorerIcons()
    {

    }

    static Class a(String s)
    {
        try
        {
            return Class.forName(s);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }


    static
    {
        IMAGE_DOTDOTDOT = Images.getImage(a == null ? (a = a("com.efounder.eai.ide.ExplorerIcons")) : a, "image/dotdotdot.gif");
        ICON_DOTDOTDOT = Icons.getIcon(IMAGE_DOTDOTDOT);
        IMAGE_DISABLED_DOTDOTDOT = Images.getDisabledImage(IMAGE_DOTDOTDOT);
        ICON_DISABLED_DOTDOTDOT = Icons.getIcon(IMAGE_DISABLED_DOTDOTDOT);
        IMAGE_GENERAL = Images.getImage(a == null ? (a = a("com.efounder.eai.ide.ExplorerIcons")) : a, "image/general16.gif");
        IMAGE_GENERAL_DISABLED = Images.getDisabledImage(IMAGE_GENERAL);
        IMAGE_OBJECTS = Images.getImage(a == null ? (a = a("com.efounder.eai.ide.ExplorerIcons")) : a, "image/objects16.gif");
        IMAGE_OBJECTS_DISABLED = Images.getDisabledImage(IMAGE_OBJECTS);
        d = Icons.getIconFactory(IMAGE_GENERAL, 16);
        g = Icons.getIconFactory(IMAGE_GENERAL_DISABLED, 16);
        i = Icons.getIconFactory(IMAGE_OBJECTS, 16);
        e = Icons.getIconFactory(IMAGE_OBJECTS_DISABLED, 16);
        ICON_MAC_CLOSE = Icons.getIcon(a == null ? (a = a("com.efounder.eai.ide.ExplorerIcons")) : a, "image/mac_close16.gif");
        ICON_MAC_CLOSE_MODIFIED = Icons.getIcon(a == null ? (a = a("com.efounder.eai.ide.ExplorerIcons")) : a, "image/mac_close_modified16.gif");
        IMAGE_STATUS_BAR = Images.getImage(a == null ? (a = a("com.efounder.eai.ide.ExplorerIcons")) : a, "image/statusbar.gif");
        TINY_IMAGE_STATUS_BAR = Images.getImage(a == null ? (a = a("com.efounder.eai.ide.ExplorerIcons")) : a, "image/tinystatusbar.gif");
        IMAGE_WHERE_AM_I = Images.getImage(a == null ? (a = a("com.efounder.eai.ide.ExplorerIcons")) : a, "image/info.gif");
        ICON_WHERE_AM_I = Icons.getIcon(IMAGE_WHERE_AM_I);
        IMAGE_STATUS_BAR_DISABLED = Images.getDisabledImage(IMAGE_STATUS_BAR);
        c = Icons.getIconFactory(IMAGE_STATUS_BAR, 16, 11);
        h = Icons.getIconFactory(TINY_IMAGE_STATUS_BAR, 11, 7);
        f = Icons.getIconFactory(IMAGE_STATUS_BAR_DISABLED, 16, 11);
        ICON_OBJECTS = i.getIcon(0);
        ICON_NEW_OBJECT = i.getIcon(1);
        ICON_CLASS = ICON_OBJECTS;
        ICON_PROJECTADD = d.getIcon(0);
        ICON_PROJECTREMOVE = d.getIcon(1);
        ICON_NEWBROWSER = d.getIcon(2);
        ICON_CASCADE = d.getIcon(3);
        ICON_OPEN = d.getIcon(4);
        ICON_CLOSE = d.getIcon(5);
        ICON_CUT = d.getIcon(6);
        ICON_COPY = d.getIcon(7);
        ICON_PASTE = d.getIcon(8);
        ICON_CUSTOMTOOLS = d.getIcon(9);
        ICON_DISK = d.getIcon(10);
        ICON_FOLDER = d.getIcon(11);
        ICON_FOLDER_ADD = d.getIcon(12);
        ICON_NEWFOLDER = d.getIcon(13);
        ICON_GENERIC_NEW = d.getIcon(14);
        ICON_GOBACK = d.getIcon(15);
        ICON_GOFORWARD = d.getIcon(16);
        ICON_GOHOME = d.getIcon(17);
        ICON_HELPBORLANDONLINE = d.getIcon(18);
        ICON_OPEN_FILE = d.getIcon(19);
        ICON_OPEN_OBJECT = d.getIcon(20);
        ICON_OPEN_PROJECT = d.getIcon(21);
        ICON_PRINT = d.getIcon(22);
        ICON_REFRESH = d.getIcon(23);
        ICON_REOPEN = d.getIcon(24);
        ICON_SAVE = d.getIcon(25);
        ICON_SAVEALL = d.getIcon(26);
        ICON_TILE = d.getIcon(27);
        ICON_TILEVERTICAL = d.getIcon(28);
        ICON_TIPOFTHEDAY = d.getIcon(29);
        ICON_EDITOROPTIONS = d.getIcon(30);
        ICON_ERROR = d.getIcon(31);
        ICON_WARNING = d.getIcon(32);
        ICON_CLOSE_FILE = d.getIcon(33);
        ICON_CLOSE_PROJECT = d.getIcon(34);
        ICON_FINDAGAIN = d.getIcon(35);
        ICON_FIND = d.getIcon(36);
        ICON_OPTIONS = d.getIcon(37);
        ICON_UNKNOWN = d.getIcon(38);
        ICON_DISKFOLDER = d.getIcon(39);
        ICON_SPECIALFOLDER = d.getIcon(40);
        ICON_SEARCHSOURCEPATH = d.getIcon(41);
        ICON_PROJECTOPTIONS = d.getIcon(42);
        ICON_SMART_KEY = d.getIcon(43);
        ICON_FILESOUND = d.getIcon(44);
        ICON_GENERICCLOSE = d.getIcon(45);
        ICON_CLOSE_MODIFIED = d.getIcon(46);
        ICON_ARRAY = d.getIcon(47);
        ICON_DISKFOLDERNEW = d.getIcon(48);
        ICON_DELETE = d.getIcon(49);
        ICON_UNDO = d.getIcon(50);
        ICON_REDO = d.getIcon(51);
        ICON_SEARCHRESULTS = d.getIcon(52);
        ICON_PROJECT = d.getIcon(53);
        ICON_FILEIMAGE = d.getIcon(54);
        ICON_REPLACE = d.getIcon(55);
        ICON_PRIMITIVE = d.getIcon(56);
        ICON_PROJECT_GROUP = d.getIcon(57);
        ICON_FOLDER_LOCKED = d.getIcon(58);
        ICON_NEW_FILE = d.getIcon(59);
        ICON_FOLDER_OPENED = d.getIcon(60);
        ICON_FILETEXT = d.getIcon(61);
        ICON_FILEBINARY = d.getIcon(62);
        ICON_CHECKED = d.getIcon(63);
        System.getProperties().put("ICON_CHECKED",ICON_CHECKED);
        ICON_CHECKED_DISABLED = g.getIcon(63);
        System.getProperties().put("ICON_CHECKED_DISABLED",ICON_CHECKED_DISABLED);
        ICON_MAKE = d.getIcon(0, 1);
        ICON_REBUILD = d.getIcon(1, 1);
        ICON_CLEAN_BUILD = d.getIcon(2, 1);
        ICON_OPTIMIZE = d.getIcon(3, 1);
        ICON_RUN = d.getIcon(4, 1);
        ICON_DEBUG = d.getIcon(5, 1);
        ICON_ADD_NOTRACING = d.getIcon(6, 1);
        ICON_REMOVE_ALL_NOTRACING = d.getIcon(7, 1);
        ICON_SMARTSTEP_OFF = d.getIcon(8, 1);
        ICON_SMARTSTEP_ON = d.getIcon(9, 1);
        ICON_STEPINTO = d.getIcon(10, 1);
        ICON_STEPOUT = d.getIcon(11, 1);
        ICON_STEPOVER = d.getIcon(12, 1);
        ICON_PROGRAMSTOP = d.getIcon(13, 1);
        ICON_PROGRAMRESUME = d.getIcon(14, 1);
        ICON_THREADDEAD = d.getIcon(15, 1);
        ICON_ADDBREAKPOINT = d.getIcon(16, 1);
        ICON_ATBREAKPOINT = d.getIcon(17, 1);
        ICON_VIEWBREAKPOINTS = ICON_ATBREAKPOINT;
        ICON_THREADBLOCKED = d.getIcon(18, 1);
        ICON_BREAKPOINT = d.getIcon(19, 1);
        ICON_BREAKPOINTDISABLED = d.getIcon(20, 1);
        ICON_DISABLE_ALL_BREAKPOINTS = d.getIcon(21, 1);
        ICON_ENABLE_ALL_BREAKPOINTS = d.getIcon(22, 1);
        ICON_FIELD_BREAKPOINT = d.getIcon(23, 1);
        ICON_INSPECT = d.getIcon(24, 1);
        ICON_BREAKPOINTINVALID = d.getIcon(25, 1);
        ICON_NOTRACING = d.getIcon(26, 1);
        ICON_NOTRACING_CLASSPATTERN = ICON_NOTRACING;
        ICON_NOTRACING_CLASSPATTERN_DISABLED = g.getIcon(26, 1);
        ICON_OBSOLETEEXCPOINT = d.getIcon(27, 1);
        ICON_PROGRAMPAUSE = d.getIcon(28, 1);
        ICON_PROGRAMRUN = d.getIcon(29, 1);
        ICON_REMOVE_ALL_BREAKPOINTS = d.getIcon(30, 1);
        ICON_RERUNEXECPOINT = d.getIcon(31, 1);
        ICON_RERUNOBSOLETEEXECPOINT = d.getIcon(32, 1);
        ICON_SHOWEXECPOINT = d.getIcon(33, 1);
        ICON_SHOWFRAME = d.getIcon(34, 1);
        ICON_THREADSUSPENDED = d.getIcon(35, 1);
        ICON_THREAD = d.getIcon(36, 1);
        ICON_THREADBREAK = d.getIcon(37, 1);
        ICON_THREADGROUP = d.getIcon(38, 1);
        ICON_THREADBREAKBLOCKED = d.getIcon(39, 1);
        ICON_THREADBREAKUSERSUSPENDED = d.getIcon(40, 1);
        ICON_THREADUSERSUSPENDED = d.getIcon(41, 1);
        ICON_BREAKPOINTVERIFIED = d.getIcon(42, 1);
        ICON_VIEWLOADEDCLASSES = d.getIcon(43, 1);
        ICON_WATCH = d.getIcon(44, 1);
        ICON_ADDWATCH = d.getIcon(45, 1);
        ICON_SMARTSWAP = d.getIcon(46, 1);
        ICON_SMARTPOPFRAME = d.getIcon(47, 1);
        ICON_CONSOLE = d.getIcon(48, 1);
        ICON_TERMINAL = ICON_CONSOLE;
        ICON_CONSOLEOUT = d.getIcon(49, 1);
        ICON_CONSOLEERR = d.getIcon(50, 1);
        ICON_NULL = d.getIcon(51, 1);
        ICON_MONITORLOCK = d.getIcon(52, 1);
        ICON_MONITORDEADLOCK = d.getIcon(53, 1);
        ICON_TAB_OPTIONS = d.getIcon(54, 1);
        ICON_INVALID_ERROR = d.getIcon(55, 1);
        ICON_BREAKPOINT_DISABLED_FOR_PROCESS = d.getIcon(56, 1);
        ICON_ARRAY_BOUNDARY_UP = d.getIcon(57, 1);
        ICON_ARRAY_BOUNDARY_DOWN = d.getIcon(58, 1);
        ICON_SMARTVIEW = d.getIcon(59, 1);
        ICON_HIDE_MESSAGE = d.getIcon(0, 2);
        ICON_REVEAL_MESSAGE = d.getIcon(1, 2);
        ICON_EXPRESSION_ERROR = d.getIcon(2, 2);
        ICON_FILEHTML = d.getIcon(3, 2);
        ICON_GROUP_CLEAN = d.getIcon(4, 2);
        ICON_GROUP_MAKE = d.getIcon(5, 2);
        ICON_GROUP_REBUILD = d.getIcon(6, 2);
        ICON_CVS_PROJ_CHECKIN = d.getIcon(7, 2);
        ICON_CVS_MODULE_CREATE = d.getIcon(8, 2);
        ICON_CVS_FILE_ADD = d.getIcon(9, 2);
        ICON_CVS_FILE_STATUS = d.getIcon(10, 2);
        ICON_CVS_FILE_UPDATE = d.getIcon(11, 2);
        ICON_CVS_FILE_REMOVE = d.getIcon(12, 2);
        ICON_CVS_FILE_CHECKIN = d.getIcon(13, 2);
        ICON_GENERICBUILD = d.getIcon(14, 2);
        ICON_GENERIC_RESOURCEFILE = d.getIcon(15, 2);
        ICON_XML_STYLE_PROPS = d.getIcon(16, 2);
        ICON_XSL_TRACE = d.getIcon(17, 2);
        ICON_XML_VALIDATION_TRACE = d.getIcon(18, 2);
        ICON_XML_TRANSFORM = d.getIcon(19, 2);
        ICON_XML_VALIDATION = d.getIcon(20, 2);
        ICON_XML_COCOON = d.getIcon(21, 2);
        ICON_XML_DEFAULT_STYLE_ENABLE = d.getIcon(22, 2);
        ICON_XML_DEFAULT_STYLE_DISABLE = d.getIcon(23, 2);
        ICON_XML_BROWSER_VIEW_ENABLE = d.getIcon(24, 2);
        ICON_XML_BROWSER_VIEW_DISABLE = d.getIcon(25, 2);
        ICON_XML_GENERATE = d.getIcon(26, 2);
        ICON_DTD_GENERATE = d.getIcon(27, 2);
        ICON_EXPORT_TO_ANT = d.getIcon(28, 2);
        ICON_ANT_FILE = d.getIcon(29, 2);
        ICON_ANT_TARGET = d.getIcon(30, 2);
        ICON_CUSTOM_VIEW = d.getIcon(31, 2);
        ICON_ADD_CUSTOM_VIEW = d.getIcon(32, 2);
        ICON_REMOVE_ALL_CUSTOM_VIEW = d.getIcon(33, 2);
        ICON_ARRAY_CUSTOM_VIEWED = d.getIcon(34, 2);
        ICON_OBJECTS_CUSTOM_VIEWED = d.getIcon(35, 2);
        ICON_LINE_NUMBERING = d.getIcon(0, 3);
        ICON_FORMAT_SELECTION = d.getIcon(1, 3);
        ICON_FORMAT_ALL = d.getIcon(2, 3);
        ICON_FORMAT_LINE = d.getIcon(3, 3);
        ICON_BRACEMATCH = d.getIcon(4, 3);
        ICON_FORMATCODE = d.getIcon(5, 3);
        ICON_READONLY_CLOSE = d.getIcon(6, 3);
        ICON_DIFF_PREV_DIFF = c.getIcon(0);
        ICON_DIFF_NEXT_DIFF = c.getIcon(1);
        ICON_DIFF_PREV_DIFF_DISABLED = f.getIcon(0);
        ICON_DIFF_NEXT_DIFF_DISABLED = f.getIcon(1);
        ICON_PREV_METHOD = h.getIcon(0);
        ICON_NEXT_METHOD = h.getIcon(1);

    }
    public static Icon getImageIcon(Object o,String r) {
      return getImageIcon(o,r,null);
    }
    public static Icon getImageIcon(Object o,String r,String Language) {
      Icon ii = getExplorerIcon(r);
      if ( ii != null ) return ii;
      String Key;
      try {
        URL url = JResource.getResource(o, r, Language);
        Key = url.toString();
//        ii = (Icon) ImageList.get(Key);
        if (url != null ) { //&& ii == null) {
          ii = new ImageIcon(url);
//          ImageList.put(Key, ii);
        }
      } catch ( Exception e ) {}
      return ii;
    }
    public static Icon getImageIcon(Object o,String Path,String r,String Language) {
      Icon ii = getExplorerIcon(r);
      if ( ii != null ) return ii;
      String Key;
      try {
        URL url = JResource.getResource(o, Path, r, Language);
        Key = url.toString();
//        ii = (Icon) ImageList.get(Key);
        if (url != null ) { //&& ii == null) {
          ii = new ImageIcon(url);
//          ImageList.put(Key, ii);
        }
      } catch ( Exception e ) {}
      return ii;
    }
    public static Icon getExplorerIcon(String ID) {
      if ( ID == null ) return null;
      Icon icon = null;Field filed = null;
      String iconFileName = "image/"+ID;URL url = null;
      if ( ID.indexOf(".") == -1 ) {
        url = ExplorerIcons.class.getResource(iconFileName + ".gif");
        if (url == null)
          url = ExplorerIcons.class.getResource(iconFileName + ".png");
        if (url == null)
          url = ExplorerIcons.class.getResource(iconFileName + ".jpg");
      } else {
        url = ExplorerIcons.class.getResource(iconFileName);
      }
      if ( url != null ) {
        icon = new ImageIcon(url);
        if ( icon != null ) return icon;
      }
      try {
        // 首先从ExplorerIcons中获取，如果没有，则从
        try {
          filed = ExplorerIcons.class.getField(ID);//"ICON_APPLICATION_CLIENT");
        } catch ( Exception ee ){}
        // EnterpriseIcons中获取
        if ( filed == null ) {
          try {
            filed = EnterpriseIcons.class.getField(ID);//"ICON_APPLICATION_CLIENT");
          } catch ( Exception ee ){}
        }
        if ( filed != null ) {
          Object VIEW_ICON = filed.get(filed);
          if ( VIEW_ICON != null && VIEW_ICON instanceof Icon ) {
            icon = (Icon)VIEW_ICON;
          }
        }
      } catch ( Exception e ) {
        e.printStackTrace();
      }
      return icon;
    }
}
