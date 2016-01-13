package com.efounder.form.client.component.infer;

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
public interface ICapture {
    /**
     * 视频捕捉
     * @param mode long：压缩率
     * @param Total long：总张数（没有用）
     * @param merge long：合并
     * @param fname String：文件前缀
     * @param fpath String：产生路径
     * @return String[]：返回文件
     */
    public String[] Capture(long mode, long Total, long merge, String fname, String fpath);
}
