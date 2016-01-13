package com.etsoft.pub.enums;

public enum JPermissionEnum {
	
	FOLDERPERMISSION("permissionManagerFlag",0),
	FOLDERDESCRIBE("folderDescribeFlag",1),
	CREATE("createFlag",2),
	EDIT("editFlag",3),
	UPLOAD("uploadFlag",4),
	DELETE("deleteFlag",5),
	PRINT("printFlag",6),
	DOWNLOAD("downloadFlag",7),
	LOOK("lookFlag",8),
	READ("readFlag",9),
	FLAG("flag",10),
	RENAME("renameFlag",11)
	;

	private String key;
	private int value;

	
	private JPermissionEnum(String key, int value)
	{
		this.key = key;
		this.value = value;
	}

	public int getValue()
	{
		return this.value;
	}

	public String getKey()
	{
		return this.key;
	}
}
