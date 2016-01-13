package com.efounder.eai.registry;

import java.io.*;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//------------------------------------------------------------------------------------------------
// 描述:
// 设计: Skyline(2001.12.29)
// 实现: Skyline
// 修改:
// ------------------------------------------------------------------------------------------------
public class JLocalRegistry {
	private java.util.Properties LocalRegistry = new java.util.Properties();
	private String LocalRegistryFileName;
	//  private static String LocalFilePath="";
	// ------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: Skyline(2001.12.29)
	// 实现: Skyline
	// 修改:
	// ------------------------------------------------------------------------------------------------
	public void setLocalFilePath(String tmp) {
//    LocalFilePath = tmp;
	}
	//------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: Skyline(2001.12.29)
	// 实现: Skyline
	// 修改:
	// ------------------------------------------------------------------------------------------------
	public String getLocalFilePath() {
		return null;//LocalFilePath;
	}
	// ------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: Skyline(2001.12.29)
	// 实现: Skyline
	// 修改:
	// ------------------------------------------------------------------------------------------------
	public JLocalRegistry() {
	}
	// ------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: Skyline(2001.12.29)
	// 实现: Skyline
	// 修改:
	// ------------------------------------------------------------------------------------------------
	public void Open(String FileName) {
		File file;
		try {
			LocalRegistryFileName = FileName;
			file = new File(LocalRegistryFileName);
			if ( file.exists() ) {
				FileInputStream FIS = new FileInputStream(file);
				LocalRegistry.load(FIS);
				FIS.close();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	// ------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: Skyline(2001.12.29)
	// 实现: Skyline
	// 修改:
	// ------------------------------------------------------------------------------------------------
	public void Close() {
		return;
	}

	// ------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: Skyline(2001.12.29)
	// 实现: Skyline
	// 修改:
	// ------------------------------------------------------------------------------------------------
	public void Save() {
		try {
			FileOutputStream FOS = new FileOutputStream(LocalRegistryFileName);
			LocalRegistry.store(FOS, "LocalRegistry");
			FOS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: Skyline(2001.12.29)
	// 实现: Skyline
	// 修改:
	// ------------------------------------------------------------------------------------------------
	public void Put(String KeyName, String Value) {
		LocalRegistry.put(KeyName, Value);
		Save();
	}

	// ------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: Skyline(2001.12.29)
	// 实现: Skyline
	// 修改:
	// ------------------------------------------------------------------------------------------------
	public String Get(String KeyName, String def) {
		String Value = (String) LocalRegistry.get(KeyName);
		if (Value == null) Value = def;
		return Value;
	}
	
	//------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//------------------------------------------------------------------------------------------------
	public String Get(String KeyName) {
		return (String)LocalRegistry.get(KeyName);
	}
}
