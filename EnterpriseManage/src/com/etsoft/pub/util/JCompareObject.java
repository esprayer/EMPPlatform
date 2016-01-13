package com.etsoft.pub.util;

import java.util.Comparator;

import com.dms.persistence.permission.bean.DMS_FOLDER_PERMISSION;

/**
 * <p>Title: 将汇总后的数据集按升序进行排序</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: etSoft</p>
 *
 * @author not ZhangES
 * @version 1.0
 */
public class JCompareObject implements Comparator {

	public int compare(Object o1, Object o2) {
		try {
			DMS_FOLDER_PERMISSION  po1 = (DMS_FOLDER_PERMISSION) o1;
			DMS_FOLDER_PERMISSION  po2 = (DMS_FOLDER_PERMISSION) o2;
			String key1 = "";
			String key2 = "";
			
			if(po1.getF_USERID().trim().equals("") && po2.getF_USERID().trim().equals("")) {
				key1 = po1.getF_USERID().trim().toLowerCase();
				key2 = po2.getF_USERID().trim().toLowerCase();
				return key1.compareTo(key2);
			} else if(!po1.getF_USERID().trim().equals("") && !po2.getF_USERID().trim().equals("")) {
				key1 = po1.getF_USERID().trim().toLowerCase() + "@" + po1.getF_BMBH().trim().toLowerCase();
				key2 = po2.getF_USERID().trim().toLowerCase() + "@" + po2.getF_BMBH().trim().toLowerCase();
				return key1.compareTo(key2);
			} else {
				if(po1.getF_USERID().trim().equals("") && !po2.getF_USERID().trim().equals("")) return -1;
				else return 1;
			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		return 0;
	}

}
