package com.mrp.biz.dailybusiness.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrp.biz.dailybusiness.ExcelStatementsServiceMgr;
import com.mrp.biz.server.plugins.util.FormCodeUtil;
import com.mrp.persistence.dailyBusiness.excelStatements.detail.bean.HYXMMX;
import com.mrp.persistence.dailyBusiness.excelStatements.detail.mapper.HYXMMXMapper;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import esyt.framework.com.builder.base.data.DataSetUtils;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JConnection;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.SYS_MDL_VAL;

import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import com.mrp.persistence.sysConfigure.project.mapper.HYXMZDMapper;
import com.etsoft.pub.util.StringUtil;


@Transactional(rollbackFor = Exception.class)
@Service("excelStatementsServiceMgr")
public class ExcelStatementsServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements ExcelStatementsServiceMgr {
	@Autowired
	private HYXMZDMapper hyxmzdMapper;

	@Autowired
	private HYXMMXMapper hyxmmxMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 项目
	 */
	public List<HYXMZD> searchHYXM(String beginDate, String endDate, String F_XMZT, int startIndex, int count) {
		List<HYXMZD> bos = new ArrayList<HYXMZD>();
		if(F_XMZT == null || F_XMZT.equals("-1")) F_XMZT = null;
		List<HYXMZD> pos = hyxmzdMapper.loadXM(beginDate, endDate, F_XMZT);
		for (HYXMZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public List<HYXMZD> searchHYXM(String beginDate, String endDate, String F_XMZT, String keywords, int startIndex, int count) {
		List<HYXMZD> bos = new ArrayList<HYXMZD>();
		if(F_XMZT == null || F_XMZT.equals("-1")) F_XMZT = null;
		List<HYXMZD> pos = hyxmzdMapper.loadXMByKey(beginDate, endDate, F_XMZT,  keywords);
		for (HYXMZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public void delHYXM(String F_XMBH) {
		hyxmzdMapper.delete(F_XMBH);
		hyxmmxMapper.deleteHYXMMXS(F_XMBH);
	}

	public HYXMZD getHYXM(String F_XMBH) {
		HYXMZD po = hyxmzdMapper.load(F_XMBH);
		return po;
	}

	//--------------------------------------------------------------------------------------------------
	//描述:项目申请单导入 ,保存项目申请单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public void updateHYXM(HYXMZD hydwObject) {
		HYXMZD po = hyxmzdMapper.load(hydwObject.getF_XMBH());
		Date now = new Date();
		po.setF_XMMC(hydwObject.getF_XMMC());
		po.setF_SQDW(hydwObject.getF_SQDW());
		po.setF_SQR(hydwObject.getF_SQR());
		po.setF_SQRMC(hydwObject.getF_SQRMC());
		po.setF_DWLD(hydwObject.getF_DWLD());
		po.setF_DWLDMC(hydwObject.getF_DWLDMC());
		po.setF_GYZX(hydwObject.getF_GYZX());
		po.setF_GYZXMC(hydwObject.getF_GYZXMC());
		po.setF_FGLD(hydwObject.getF_FGLD());
		po.setF_FGLDMC(hydwObject.getF_FGLDMC());
		po.setF_ZGLD(hydwObject.getF_ZGLD());
		po.setF_ZGLDMC(hydwObject.getF_ZGLDMC());
		po.setF_CHDATE(now);
		hyxmzdMapper.update(po);
	}

	public List<HYXMMX> loadHYXMMX(String F_XMBH, int startIndex, int count) {
		List<HYXMMX> bos = new ArrayList<HYXMMX>();
		List<HYXMMX> pos = hyxmmxMapper.loadHYXMMX(F_XMBH);
		for (HYXMMX po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public HYXMMX getHYXMMX(String F_XMBH, String F_CLBH) {
		return hyxmmxMapper.findClByXm(F_XMBH, F_CLBH);
	}

	public void addHYXMMX(HYXMMX hyxmmxObject) {
		Date now = new Date();
		hyxmmxObject.setF_CHDATE(now);
		hyxmmxObject.setF_CRDATE(now);
		hyxmmxMapper.insert(hyxmmxObject);
	}

	public void delHYXMMX(String F_XMBH, String F_CLBH) {
		hyxmmxMapper.deleteHYXMMX(F_XMBH, F_CLBH);
	}

	public List<HYXMMX> searchHYXMMX(String F_XMBH, String keywords, int startIndex, int count) {
		List<HYXMMX> bos = new ArrayList<HYXMMX>();
		List<HYXMMX> pos = hyxmmxMapper.findClByXms(F_XMBH, keywords);
		for (HYXMMX po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public void updateHYXMMX(HYXMMX hyxmmxObject) {
		HYXMMX po = hyxmmxMapper.findClByXm(hyxmmxObject.getF_XMBH(), hyxmmxObject.getF_CLBH());
		Date now = new Date();
		po.setF_SL(hyxmmxObject.getF_SL());
		po.setF_KCQK(hyxmmxObject.getF_KCQK());
		po.setF_BZ(hyxmmxObject.getF_BZ());
		po.setF_CHDATE(now);
		hyxmmxMapper.update(po);
	}

	public String[] statementsReslove(String fileName) {
		String[]      importFlag = new String[2];
		XSSFSheet          sheet = null;
		String              uuid = StringUtil.nextId();// 返回一个随机UUID。
		EFRowSet          rowset = null;
		JConnection         conn = null;
		JStatement          stmt = null;
		boolean          bCommit = false;
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			XSSFWorkbook xwb = new XSSFWorkbook(fileName);
			sheet = xwb.getSheetAt(0);    			
			HYXMZD xmzd = resloveHead(sheet, uuid);
			List<HYXMMX> list = resloveItem(sheet, uuid);
			
			hyxmzdMapper.insert(xmzd);
			hyxmmxMapper.insertbatch(list);		
			
			rowset = DataSetUtils.javaBean2RowSet(xmzd);
			String F_XMBH = FormCodeUtil.GetNewBillCode(stmt, rowset, SYS_MDL_VAL.XMDR);
			
			hyxmzdMapper.updateXMBH(uuid, F_XMBH);
			hyxmmxMapper.updateXMBH(uuid, F_XMBH);
			conn.commit();
		} catch (Exception ce) {
			importFlag[1] = "0";
			importFlag[1] = "文件导入失败！";
			try {
				conn.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ce.printStackTrace();
		} finally {
			
			try {
				conn.setAutoCommit(bCommit);
				JdbcUtils.closeStatement(stmt);
				stmt = null;
				DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		importFlag[0] = "1";
		importFlag[1] = "";
		return importFlag;
	}

	/**  
     * @Method: extractTextFromXLS2007  
     * @Description: 从excel 2007文档中提取项目申请单头信息 
     *  
     * @param   
     * @return String  
     * @throws  
     */  
    private HYXMZD resloveHead(XSSFSheet sheet, String uuid) {
    	HYXMZD xmzd = new HYXMZD();
    	Date now = new Date();
    	xmzd.setF_XMBH(uuid);
    	xmzd.setF_XMMC(getCellString(sheet, 1, 1, ""));
    	xmzd.setF_SQSJ(getCellString(sheet, 1, 4, "").substring(0, 4) + getCellString(sheet, 1, 4, "").substring(5, 7) + getCellString(sheet, 1, 4, "").substring(8, 10));
    	xmzd.setF_SQDW(getCellString(sheet, 2, 1, ""));
    	xmzd.setF_DWLDMC(getCellString(sheet, 2, 3, ""));
    	xmzd.setF_SQRMC(getCellString(sheet, 2, 5, ""));
    	xmzd.setF_GYZXMC(getCellString(sheet, 3, 1, ""));
    	xmzd.setF_FGLDMC(getCellString(sheet, 4, 1, ""));
    	xmzd.setF_ZGLDMC(getCellString(sheet, 5, 1, ""));
    	xmzd.setF_SQR("");
    	xmzd.setF_DWLD("");
    	xmzd.setF_GYZX("");
    	xmzd.setF_FGLD("");
    	xmzd.setF_ZGLD("");
    	xmzd.setF_CLXQSJ(StringUtil.formatDate("yyyy-MM-dd h:mm", getCellString(sheet, 6, 1, "")));
    	xmzd.setF_XMZT("0");
    	xmzd.setF_CHDATE(now);
    	xmzd.setF_CRDATE(now);
    	return xmzd;
    }

    /**  
     * @Method: extractTextFromXLS2007  
     * @Description: 从excel 2007文档中提取项目申请单明细信息 
     *  
     * @param   
     * @return String  
     * @throws  
     */  
    public List<HYXMMX> resloveItem(XSSFSheet sheet, String uuid) {
		List<HYXMMX> bos = new ArrayList<HYXMMX>();
    	HYXMMX xmmx = new HYXMMX();
    	String F_CLBH;
    	Date now = new Date();
    	boolean bBreak = true;
    	
    	for(int i = 8; bBreak; i++) {
    		F_CLBH = getCellString(sheet, i, 1, "");
    		if(F_CLBH.equals("")) break;
    		xmmx = new HYXMMX();
    		xmmx.setF_CLBH(F_CLBH);
    		xmmx.setF_XMBH(uuid);
    		xmmx.setF_BZ(getCellString(sheet, i, 5, ""));
    		xmmx.setF_KCQK(getCellString(sheet, i, 4, ""));
    		xmmx.setF_SL(getCellNumber(sheet, i, 3, 0.0));
    		xmmx.setF_CRDATE(now);
    		xmmx.setF_CHDATE(now);
    		bos.add(xmmx);
    	}
    	return bos;
	}

    private String getCellString(XSSFSheet sheet, int row, int col, String defVal) {
    	if(sheet.getRow(row) == null) return defVal;
    	XSSFCell cell = sheet.getRow(row).getCell(col);
    	if(cell == null) return defVal;
    	if(cell.getStringCellValue() == null) return defVal;
    	else return cell.getStringCellValue()	.trim();
    }
    
    private double getCellNumber(XSSFSheet sheet, int row, int col, double defVal) {
    	XSSFCell cell = sheet.getRow(row).getCell(col);
    	return cell.getNumericCellValue();
    }
}
