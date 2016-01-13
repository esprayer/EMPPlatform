package com.efounder.document;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class IntegerDocument extends PlainDocument {
	public void insertString(int offset, String s, AttributeSet attributeSet) throws BadLocationException {
		if(s.equals(".") || s.equals("-")) {
			return;
		}
		
		try {
			Double.parseDouble(s);
		} catch (Exception ex) {
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		super.insertString(offset, s, attributeSet);
	}
}