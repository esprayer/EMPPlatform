package com.efounder.form.client.component.infer;

import java.awt.Frame;

import javax.swing.JPanel;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IOpenAffix {
    public void open(Object showOwner,Object res,String fileName,JPanel panel,String order) throws Exception;

	public void open(Frame frame, String title, boolean modal, Object res, String fileName, JPanel panel, String order)  throws Exception;
}
