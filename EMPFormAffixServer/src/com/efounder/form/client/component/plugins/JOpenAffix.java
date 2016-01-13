package com.efounder.form.client.component.plugins;

import com.efounder.form.client.component.infer.IOpenAffix;
import javax.swing.JPanel;

import java.awt.Frame;
import java.io.File;

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
public class JOpenAffix implements IOpenAffix {
    public JOpenAffix() {}

    public void open(Object showOwner,Object res,String fileName,JPanel panel,String order) throws Exception{};

	public void open(Frame frame, String title, boolean modal, Object res, String fileName, JPanel panel, String order) throws Exception{};
}
