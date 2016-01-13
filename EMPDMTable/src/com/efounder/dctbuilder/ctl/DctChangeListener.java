package com.efounder.dctbuilder.ctl;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface DctChangeListener extends java.util.EventListener {
    public static final int DATA_LOADED  = 0x0005;
    public void stateChanged(DctChangeEvent e);
}
