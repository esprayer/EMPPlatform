package com.efounder.pub.util;

import javax.swing.ImageIcon;

import com.efounder.eai.EAI;
import com.efounder.resource.JResource;

public class EMPStringUtils {
	
	public static ImageIcon createImageIcon(String imageName) {
    	ImageIcon ii = JResource.getImageIcon(EAI.class,"/com/efounder/eai/ide/image",imageName, null);
        if (ii != null) {
            return ii;
        } else {
            System.err.println("Couldn't find file: " + imageName);
            return null;
        }
    }
}
