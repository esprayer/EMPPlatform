package com.mrp.biz.dicthelp.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrp.biz.dicthelp.DictHelpServiceMgr;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import esyt.framework.com.builder.base.data.EFDataSet;

import com.mrp.persistence.sysConfigure.company.bean.HYCSZD;
import com.mrp.persistence.sysConfigure.company.mapper.HYCSZDMapper;
import com.mrp.persistence.sysConfigure.department.bean.HYBMZD;
import com.mrp.persistence.sysConfigure.department.mapper.HYBMZDMapper;
import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;
import com.mrp.persistence.sysConfigure.deport.mapper.HYCKZDMapper;
import com.mrp.persistence.sysConfigure.material.bean.HYCLZD;
import com.mrp.persistence.sysConfigure.material.mapper.HYCLZDMapper;
import com.mrp.persistence.sysConfigure.product.bean.HYCPZD;
import com.mrp.persistence.sysConfigure.product.mapper.HYCPZDMapper;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import com.mrp.persistence.sysConfigure.project.mapper.HYXMZDMapper;
import com.mrp.persistence.sysConfigure.projectProduct.bean.HYXMCP;
import com.mrp.persistence.sysConfigure.projectProduct.mapper.HYXMCPMapper;
import com.mrp.persistence.sysConfigure.user.bean.HYZGZD;
import com.mrp.persistence.sysConfigure.user.mapper.HYZGZDMapper;


@Transactional(rollbackFor = Exception.class)
@Service("dictHelpServiceMgr")
public class DictHelpServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements DictHelpServiceMgr {

	@Autowired
	private HYBMZDMapper hybmzdMapper;
	
	@Autowired
	private HYXMZDMapper hyxmzdMapper;
	
	@Autowired
	private HYCKZDMapper hyckzdMapper;
	
	@Autowired
	private HYCLZDMapper hyclzdMapper;
	
	@Autowired
	private HYCSZDMapper hycszdMapper;
	
	@Autowired
	private HYZGZDMapper hyzgzdMapper;
	
	@Autowired
	private EMPQueryHelp queryHelp;
	
	@Autowired
	private HYXMCPMapper hyxmcpMapper;
	
	@Autowired
	private HYCPZDMapper hycpzdMapper;
	
	public List<HYBMZD> searchHYBMZD() {
		List<HYBMZD> bos = new ArrayList<HYBMZD>();
		List<HYBMZD> pos = hybmzdMapper.findAll();
		for (HYBMZD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	public List<HYCKZD> searchHYCK(String F_BMBH, int startIndex, int count) {
		List<HYCKZD> bos = new ArrayList<HYCKZD>();
		List<HYCKZD> pos = hyckzdMapper.findCkByBm(F_BMBH);
		for (HYCKZD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	public List<HYCKZD> searchHYCK(String F_BMBH, String F_CKBH, int startIndex, int count) {
		List<HYCKZD> bos = new ArrayList<HYCKZD>();
		List<HYCKZD> pos = hyckzdMapper.findCkByBms(F_BMBH, F_CKBH);
		for (HYCKZD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	public EFDataSet xmclHelp(String F_XMBH, String F_CPBH, String F_CLBH, String F_CKBH, int startIndex, int coun) {
		EFDataSet dataSet;
		try {
			dataSet = queryHelp.xmclHelp(F_XMBH, F_CPBH, F_CLBH, F_CKBH, startIndex, coun);
		} catch (Exception e) {
			e.printStackTrace();
			dataSet = EFDataSet.getInstance();
			e.printStackTrace();
		}
		return dataSet;
	}
	
	public List<String[]> xmHelp(String F_XMBH, String F_CPBH, String F_CLBH, String F_CKBH, String F_XMZT, int startIndex, int coun) {
		List<String[]> dataList = new ArrayList<String[]>();
		dataList = queryHelp.xmHelp(F_XMBH, F_CPBH, F_CLBH, F_CKBH, F_XMZT, startIndex, coun);
		return dataList;
	}
	
	public EFDataSet xmclHelp(String F_XMBH, String F_CPBH, String F_CLBH, String beginDate, String endDate, int startIndex, int coun) {
		EFDataSet ds = queryHelp.xmclHelp(F_XMBH, F_CPBH, F_CLBH, beginDate, endDate, startIndex, coun);
		return ds;
	}
	
	public List<HYCLZD> clHelp(String F_CLBH, int startIndex, int coun) {
		List<HYCLZD> dataList = new ArrayList<HYCLZD>();
		dataList = hyclzdMapper.findByObjId(F_CLBH);
		return dataList;
	}
	
	public List<HYCSZD> csHelp(String F_CSBH, String F_SYZT, int startIndex, int coun) {
		List<HYCSZD> dataList = new ArrayList<HYCSZD>();
		dataList = hycszdMapper.findById(F_CSBH, F_SYZT);
		return dataList;
	}
	
	public List<HYXMCP> searchHYXMCP(String F_XMBH, int startIndex, int count) {
		List<HYXMCP> bos = new ArrayList<HYXMCP>();
		List<HYXMCP> pos = hyxmcpMapper.findCpByXm(F_XMBH, null);
		for (HYXMCP po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public List<HYXMCP> searchHYXMCP(String F_XMBH, String F_CPBH, int startIndex, int count) {
		List<HYXMCP> bos = new ArrayList<HYXMCP>();
		List<HYXMCP> pos = hyxmcpMapper.findCpByXms(F_XMBH, F_CPBH, null);
		for (HYXMCP po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public List<HYBMZD> searchHYBMZD(int startIndex, int count) {
		List<HYBMZD> bos = new ArrayList<HYBMZD>();
		List<HYBMZD> pos = hybmzdMapper.findAll();
		for (HYBMZD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	public List<HYZGZD> searchHYZGZD(String F_BMBH, int startIndex, int count) {
		return searchUseHYZGZD(F_BMBH, null, startIndex, count);
	}
	
	public List<HYZGZD> searchUseHYZGZD(String F_BMBH, String F_SYZT, int startIndex, int count) {
		List<HYZGZD> bos = new ArrayList<HYZGZD>();
		List<HYZGZD> pos = hyzgzdMapper.findZgByBm(F_BMBH, F_SYZT);
		for (HYZGZD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	public List<HYZGZD> searchHYZGZD(String F_BMBH, String F_ZGBH, int startIndex, int count) {
		return searchUseHYZGZD(F_BMBH, F_ZGBH, null, startIndex, count);
	}
	
	public List<HYZGZD> searchUseHYZGZD(String F_BMBH, String F_ZGBH, String F_SYZT, int startIndex, int count) {
		List<HYZGZD> bos = new ArrayList<HYZGZD>();
		List<HYZGZD> pos = hyzgzdMapper.findZgByBms(F_BMBH, F_ZGBH, F_SYZT);
		for (HYZGZD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	public List<HYCLZD> searchHYCLZD(int startIndex, int count) {
		List<HYCLZD> bos = new ArrayList<HYCLZD>();
		List<HYCLZD> pos = hyclzdMapper.findAll();
		for (HYCLZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public List<HYCLZD> searchHYCLZD(String F_CLBH, int startIndex, int count) {
		List<HYCLZD> bos = new ArrayList<HYCLZD>();
		List<HYCLZD> pos = hyclzdMapper.findByObjId(F_CLBH);
		for (HYCLZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public List<HYCLZD> searchHYCLZDByKey(String F_XMBH, int startIndex, int count) {
		List<HYCLZD> bos = new ArrayList<HYCLZD>();
		List<HYCLZD> pos = hyclzdMapper.findByKey(F_XMBH);
		for (HYCLZD po : pos) {
			bos.add(po);
		}
		return bos;
	}


	public EFDataSet scanningHYCLZD(String F_XMBH, String F_CLBH,  String F_DWBH, 
									String F_CPBH, String F_CSBH, String WHERE, 
									int startIndex, int count) {

		EFDataSet ds = queryHelp.scanningHYCLZD(F_XMBH, F_CLBH, F_DWBH, F_CPBH, F_CSBH, WHERE);
		return ds;
	}
	
	public EFDataSet deportMaterialHelp(String F_CKBH, String F_XMBH, String F_CLBH,
										String F_DWBH, String F_CSBH, String WHERE, 
            							int startIndex, int count) {

		EFDataSet ds = queryHelp.deportMaterialHelp(F_CKBH, F_XMBH, F_CLBH, F_DWBH, F_CSBH, WHERE);
		return ds;
	}

	
	public List<String[]> searchHYCLZDByXm(String F_XMBH, String F_CLBH, int startIndex, int count) {
		List<String[]> bos = queryHelp.xmclHelp(F_XMBH, F_CLBH);
		return bos;
	}
	
	public List<HYXMCP> searchHYXMCP(String F_XMBH, String F_CKBH, String F_CPBH, String F_XMZT, int startIndex, int count) {
		List<HYXMCP> dataList = new ArrayList<HYXMCP>();
		dataList = hyxmcpMapper.findCpByXmCk(F_XMBH, F_CKBH, F_CPBH, F_XMZT);
		return dataList;
	}


	public List<HYXMZD> searchHYXMZD(String beginDate, String endDate, String F_CLBH, String F_XMZT, String F_XMBH, int startIndex, int count) {
		List<HYXMZD> bos = new ArrayList<HYXMZD>();
		if(F_XMZT == null || F_XMZT.equals("-1")) F_XMZT = null;
		List<HYXMZD> pos = hyxmzdMapper.findByCL(beginDate,endDate,F_XMBH, F_XMZT, F_CLBH);
		for (HYXMZD po : pos) {
			bos.add(po);
		}
		return bos;
	}	

	public List<HYXMZD> searchHYXMZD(String beginDate, String endDate, String F_XMZT, int startIndex, int count) {
		List<HYXMZD> bos = new ArrayList<HYXMZD>();
		if(F_XMZT == null || F_XMZT.equals("-1")) F_XMZT = null;
		List<HYXMZD> pos = hyxmzdMapper.loadXM(beginDate, endDate, F_XMZT);
		for (HYXMZD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	public List<HYXMZD> searchXMCK(String beginDate, String endDate, String F_CKBH, String F_XMBH, String F_CLBH, String F_CRFX, String F_XMZT, int startIndex, int count) {
		List<HYXMZD> bos = new ArrayList<HYXMZD>();
		if(F_XMZT == null || F_XMZT.equals("-1")) F_XMZT = null;
		
		List<HYXMZD> pos = null;
		if(F_CRFX.equals("R")) {
			pos = hyxmzdMapper.loadRKXM(beginDate, endDate, F_CKBH, F_XMBH, F_CLBH, F_XMZT);
		} else {
			pos = hyxmzdMapper.loadCKXM(beginDate, endDate, F_CKBH, F_XMBH, F_CLBH, F_XMZT);
		}
		for (HYXMZD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	@Override
	public List<HYXMZD> searchHYXMZD(String beginDate, String endDate,String F_XMBH, String F_XMZT, int startIndex, int count) {
		List<HYXMZD> bos = new ArrayList<HYXMZD>();
		if(F_XMZT == null || F_XMZT.equals("-1")) F_XMZT = null;
		
		List<HYXMZD> pos = null;
		pos = hyxmzdMapper.loadXMByKey(beginDate, endDate, F_XMZT, F_XMBH);
		for (HYXMZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public List<HYCSZD> searchHYCS(String F_CSBH, String F_SYZT, int startIndex, int count) {
		List<HYCSZD> bos = new ArrayList<HYCSZD>();
		if(F_CSBH.equals("")) F_CSBH = null;
		List<HYCSZD> pos = hycszdMapper.findById(F_CSBH, F_SYZT);
		for (HYCSZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public List<HYCPZD> searchHYCPZD(String F_CPBH, String F_SYZT, int startIndex, int count) {
		List<HYCPZD> dataList = new ArrayList<HYCPZD>();
		dataList = hycpzdMapper.findByObjId(F_CPBH, F_SYZT);
		return dataList;
	}
	
	@Override
	public EFDataSet searchHYXMCP(String beginDate, String endDate, String F_CPBH, int startIndex, int count) {
		EFDataSet dataset = null;
		dataset = queryHelp.xmcpHelp(beginDate, endDate, F_CPBH);
		return dataset;
	}
	
	@Override
	public EFDataSet searchHYCLZD(String beginDate, String endDate, String F_CKBH, String F_CLBH, int startIndex, int count) {
		EFDataSet dataset = null;
		dataset = queryHelp.searchHYCLZD(beginDate, endDate, F_CKBH, F_CLBH);
		return dataset;
	}
	
	@Override
	public EFDataSet searchHYCLZD(String beginDate, String endDate, String F_CLBH, int startIndex, int count) {
		EFDataSet dataset = null;
		dataset = queryHelp.searchHYCLZD(beginDate, endDate, F_CLBH);
		return dataset;
	}

	public List<HYXMCP> searchHYXMCP(String beginDate, String endDate, String F_XMBH, String F_CPBH, String F_CLBH, int startIndex, int count) {		
		List<HYXMCP> dataList = new ArrayList<HYXMCP>();
		dataList = hyxmcpMapper.searchHYCPZDByXMCL(beginDate, endDate, F_XMBH, F_CPBH, F_CLBH);
		return dataList;
	}

	public EFDataSet searchHYCLZDByHYXM(String beginDate, String endDate, String F_XMBH, String F_CLBH, int startIndex, int count) {
		EFDataSet dataset = null;
		dataset = queryHelp.searchHYCLZDByHYXM(beginDate, endDate, F_XMBH, F_CLBH);
		return dataset;
	}
}
