package com.eai.resources;

import java.util.Hashtable;
import javax.swing.ImageIcon;

import com.pub.toolkit.TResource;

public class TImages {
	private static Hashtable mHash = new Hashtable(20);
	private static Object mMine = new TImages();
  
	public static ImageIcon getIcon(String strID) {
		Object pOBJ = mHash.get(strID);
		if (pOBJ != null) {
			return (ImageIcon)pOBJ;
		}
		String strURL = "/com/eai/resources/icons/" + strID + ".gif";
		ImageIcon pIcon = TResource.LoadImageIcon(mMine, strURL);
		if (pIcon != null) {
			mHash.put(strID, pIcon);
			return pIcon;
		}
		return null;
	}
}
