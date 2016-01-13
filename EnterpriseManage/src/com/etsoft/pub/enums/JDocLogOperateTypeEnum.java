package com.etsoft.pub.enums;

public enum JDocLogOperateTypeEnum {
	UPLOAD(1),                     //上传
	EDIT(2),                       //修改
	READ(3),                       //阅读
	SAVE(4),                       //保存
	LOCK(5),                       //锁定
	UNLOCK(6),                     //解锁
	DOWNLOAD(7),                   //下载
	DELETE(10),                    //删除
	COMPLETELYDELETE(11),          //彻底删除
	REDUCE(12),                    //还原
	RENAME(13),                    //重命名
	BACK(14)                       //回退
	;

	private int value;

	private JDocLogOperateTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
