package com.mrp.biz.sysconfigure.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;

import com.etsoft.server.EMPFormUtil;
import com.mrp.biz.sysconfigure.sysConfigureServiceMgr;
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
import com.mrp.persistence.sysConfigure.suppliers.bean.HYDWZD;
import com.mrp.persistence.sysConfigure.suppliers.mapper.HYDWZDMapper;
import com.mrp.persistence.sysConfigure.user.bean.HYZGZD;
import com.mrp.persistence.sysConfigure.user.mapper.HYZGZDMapper;

@Transactional(rollbackFor = Exception.class)
@Service("sysConfigureServiceMgr")
public class sysConfigureServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements sysConfigureServiceMgr {
	@Autowired
	private HYDWZDMapper hydwzdMapper;
	
	@Autowired
	private HYBMZDMapper hybmzdMapper;

	@Autowired
	private HYZGZDMapper hyzgzdMapper;
	
	@Autowired
	private HYCKZDMapper hyckzdMapper;
	
	@Autowired
	private HYCLZDMapper hyclzdMapper;
	
	@Autowired
	private HYXMZDMapper hyxmzdMapper;
	
	@Autowired
	private HYCSZDMapper hycszdMapper;
	
	@Autowired
	private HYCPZDMapper hycpzdMapper;
	
	@Autowired
	private HYXMCPMapper hyxmcpMapper;
	
	@Autowired
	private EMPFormUtil empFormUtil;
	
	/**
	 * 部门
	 */
	@Override
	public void addHYBM(HYBMZD po) {
		Date now = new Date();
		po.setF_CHDATE(now);
		po.setF_CRDATE(now);
		po.setF_SYZT(1);
		hybmzdMapper.insert(po);
		
	}

	@Override
	public void delHYBM(String F_BMBH) {
		hybmzdMapper.delete(F_BMBH);
	}

	@Override
	public HYBMZD getHYBM(String F_BMBH) {
		HYBMZD po = hybmzdMapper.load(F_BMBH);
		return po;
	}

	@Override
	public List<HYBMZD> searchHYBMZD(int startIndex, int count) {
		List<HYBMZD> bos = new ArrayList<HYBMZD>();
		List<HYBMZD> pos = hybmzdMapper.findAll();
		for (HYBMZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public List<HYBMZD> searchBMZD(String F_SYZT, int startIndex, int count) {
		List<HYBMZD> bos = new ArrayList<HYBMZD>();
		List<HYBMZD> pos = hybmzdMapper.findBySYZT(F_SYZT);
		for (HYBMZD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	@Override
	public List<HYBMZD> searchHYBMZD(String F_BMBH, int startIndex, int count) {
		List<HYBMZD> bos = new ArrayList<HYBMZD>();
		List<HYBMZD> pos = hybmzdMapper.findByObjId(F_BMBH);
		for (HYBMZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public void updateHYBM(HYBMZD hybmObject) {
		Date now = new Date();
		HYBMZD po = hybmzdMapper.load(hybmObject.getF_BMBH());
		po.setF_BMMC(hybmObject.getF_BMMC());
		po.setF_JS(hybmObject.getF_JS());
		po.setF_MX(hybmObject.getF_MX());
		po.setF_SYZT(hybmObject.getF_SYZT());
		po.setF_CHDATE(now);
		hybmzdMapper.update(po);
	}
	
	
	/**
	 * 厂商
	 */
	@Override
	public void addHYCS(HYCSZD po) {
		Date now = new Date();
		po.setF_CHDATE(now);
		po.setF_CRDATE(now);
		po.setF_SYZT(1);
		hycszdMapper.insert(po);
		
	}

	@Override
	public void delHYCS(String F_CSBH) {
		hycszdMapper.delete(F_CSBH);
	}

	@Override
	public HYCSZD getHYCS(String F_CSBH) {
		HYCSZD po = hycszdMapper.load(F_CSBH);
		return po;
	}

	@Override
	public List<HYCSZD> searchHYCSZD(String F_CSBH, String F_SYZT, int startIndex, int count) {
		if(F_CSBH.trim().equals("")) F_CSBH = null;
		if(F_SYZT.trim().equals("")) F_SYZT = null;
		
		List<HYCSZD> bos = new ArrayList<HYCSZD>();
		List<HYCSZD> pos = hycszdMapper.findById(F_CSBH, F_SYZT);
		for (HYCSZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public void updateHYCS(HYCSZD hycsObject) {
		Date now = new Date();
		HYCSZD po = hycszdMapper.load(hycsObject.getF_CSBH());
		po.setF_CSMC(hycsObject.getF_CSMC());
		po.setF_SYZT(hycsObject.getF_SYZT());
		po.setF_CHDATE(now);
		hycszdMapper.update(po);
	}
	
	/**
	 * 供应商
	 */
	public void addHYDW(HYDWZD hydwObject) {
		Date now = new Date();
		hydwObject.setF_CHDATE(now);
		hydwObject.setF_CRDATE(now);
		hydwObject.setF_SYZT(1);
		hydwzdMapper.insert(hydwObject);
	}

	public void updateHYDW(HYDWZD hydwObject) {
		Date now = new Date();
		HYDWZD po = hydwzdMapper.load(hydwObject.getF_DWBH());
		po.setF_DWMC(hydwObject.getF_DWMC());
		po.setF_DWJC(hydwObject.getF_DWJC());
		po.setF_LXR(hydwObject.getF_LXR());
		po.setF_LXDH(hydwObject.getF_LXDH());
		po.setF_CZ(hydwObject.getF_CZ());
		po.setF_DZ(hydwObject.getF_DZ());
		po.setF_CHDATE(now);
		hydwzdMapper.update(po);
		
	}
	
	public void delHYDW(String F_DWBH) {
		hydwzdMapper.delete(F_DWBH);
	}

	public List<HYDWZD> searchHYDW(String F_DWBH, int startIndex, int count) {
		List<HYDWZD> bos = new ArrayList<HYDWZD>();
		List<HYDWZD> pos = hydwzdMapper.findByObjId(F_DWBH);
		for (HYDWZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public HYDWZD getHYDW(String F_DWBH) {
		HYDWZD po = hydwzdMapper.load(F_DWBH);
		return po;
	}

	public List<HYDWZD> searchHYDW(int startIndex, int count) {
		List<HYDWZD> bos = new ArrayList<HYDWZD>();
		List<HYDWZD> pos = hydwzdMapper.findAll();
		for (HYDWZD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	
	
	
	
	
	
	
	

	/**
	 * 用户管理
	 */
	@Override
	public void addHYZG(HYZGZD hybmObject) {
		Date now = new Date();
		hybmObject.setF_CHDATE(now);
		hybmObject.setF_CRDATE(now);
		hybmObject.setF_SYZT(1);
		hyzgzdMapper.insert(hybmObject);
	}

	@Override
	public void delHYZG(String F_BMBH, String F_ZGBH) {
		hyzgzdMapper.deleteHYZG(F_BMBH, F_ZGBH);
	}

	@Override
	public HYZGZD getHYZG(String F_BMBH, String F_ZGBH) {
		HYZGZD po = hyzgzdMapper.loadHYZG(F_BMBH, F_ZGBH);
		return po;
	}
	
	@Override
	public List<HYZGZD> loadHYZGByBh(String F_ZGBH) {
		List<HYZGZD> bos = new ArrayList<HYZGZD>();
		List<HYZGZD> pos = hyzgzdMapper.loadHYZGByBh(F_ZGBH);
		for (HYZGZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public List<HYZGZD> searchHYZG(String F_BMBH, int startIndex, int count) {
		List<HYZGZD> bos = new ArrayList<HYZGZD>();
		List<HYZGZD> pos = hyzgzdMapper.findZgByBm(F_BMBH, null);
		for (HYZGZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public List<HYZGZD> searchHYZG(String F_BMBH, String F_ZGBH,
			int startIndex, int count) {
		List<HYZGZD> bos = new ArrayList<HYZGZD>();
		List<HYZGZD> pos = hyzgzdMapper.findZgByBms(F_BMBH, F_ZGBH, null);
		for (HYZGZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public void updateHYZG(HYZGZD hyzgObject) {
		Date now = new Date();
		HYZGZD po = hyzgzdMapper.loadHYZG(hyzgObject.getF_SXBM(), hyzgObject.getF_ZGBH());
		po.setF_ZGMC(hyzgObject.getF_ZGMC());
		po.setF_SJHM(hyzgObject.getF_SJHM());
		po.setF_BGDH(hyzgObject.getF_BGDH());
		po.setF_YHYX(hyzgObject.getF_YHYX());
		po.setF_SFGLY(hyzgObject.getF_SFGLY());
		po.setF_SYZT(hyzgObject.getF_SYZT());
		po.setF_CHDATE(now);
		hyzgzdMapper.update(po);
	}
	
	
	
	
	
	
	/**
	 * 仓库管理
	 */
	@Override
	public void addHYCK(HYCKZD hyckObject) {
		Date now = new Date();
		hyckObject.setF_CHDATE(now);
		hyckObject.setF_CRDATE(now);
		hyckObject.setF_SYZT(1);
		hyckzdMapper.insert(hyckObject);
	}

	@Override
	public void delHYCK(String F_BMBH, String F_CKBH) {
		hyckzdMapper.deleteHYCK(F_BMBH, F_CKBH);
	}

	public HYCKZD getHYCK(String F_BMBH, String F_CKBH) {
		HYCKZD po = hyckzdMapper.loadHYCK(F_BMBH, F_CKBH);
		return po;
	}

	public HYCKZD getHYCKZD(String F_CKBH) {
		HYCKZD po = hyckzdMapper.loadHYCKZD(F_CKBH);
		return po;
	}
	
	@Override
	public List<HYCKZD> searchHYCK(String F_BMBH, int startIndex, int count) {
		List<HYCKZD> bos = new ArrayList<HYCKZD>();
		List<HYCKZD> pos = hyckzdMapper.findCkByBm(F_BMBH);
		for (HYCKZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public List<HYCKZD> searchHYCK(String F_BMBH, String F_CKBH,
			int startIndex, int count) {
		List<HYCKZD> bos = new ArrayList<HYCKZD>();
		List<HYCKZD> pos = hyckzdMapper.findCkByBms(F_BMBH, F_CKBH);
		for (HYCKZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public void updateHYCK(HYCKZD hyckObject) {
		Date now = new Date();
		HYCKZD po = hyckzdMapper.loadHYCK(hyckObject.getF_BMBH(), hyckObject.getF_CKBH());
		po.setF_CKMC(hyckObject.getF_CKMC());
		po.setF_ZGBH(hyckObject.getF_ZGBH());
		po.setF_MS(hyckObject.getF_MS());
		po.setF_SYZT(hyckObject.getF_SYZT());
		po.setF_CHDATE(now);
		hyckzdMapper.update(po);
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 电气材料管理
	 */
	@Override
	public void addHYCL(HYCLZD po) {
		Date now = new Date();
		po.setF_CHDATE(now);
		po.setF_CRDATE(now);
		hyclzdMapper.insert(po);
		
	}

	@Override
	public void delHYCL(String F_CLBH) {
		hyclzdMapper.delete(F_CLBH);
	}

	@Override
	public HYCLZD getHYCL(String F_CLBH) {
		HYCLZD po = hyclzdMapper.load(F_CLBH);
		return po;
	}

	@Override
	public List<HYCLZD> searchHYCLZD(int startIndex, int count) {
		List<HYCLZD> bos = new ArrayList<HYCLZD>();
		List<HYCLZD> pos = hyclzdMapper.findAll();
		for (HYCLZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public List<HYCLZD> searchHYCLZD(String F_CLBH, int startIndex, int count) {
		List<HYCLZD> bos = new ArrayList<HYCLZD>();
		List<HYCLZD> pos = hyclzdMapper.findByObjId(F_CLBH);
		for (HYCLZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public void updateHYCL(HYCLZD hyclObject) {
		Date now = new Date();
		HYCLZD po = hyclzdMapper.load(hyclObject.getF_CLBH());
		po.setF_CLMC(hyclObject.getF_CLMC());
		po.setF_GGXH(hyclObject.getF_GGXH());
		po.setF_JLDW(hyclObject.getF_JLDW());
		po.setF_DWBH(hyclObject.getF_DWBH());
		po.setF_CSBH(hyclObject.getF_CSBH());
		po.setF_LRR(hyclObject.getF_LRR());
		po.setF_SYZT(hyclObject.getF_SYZT());
		po.setF_CHDATE(now);
		hyclzdMapper.update(po);
	}

	
	
	
	
	
	
	/**
	 * 项目
	 */
	@Override
	public List<HYXMZD> searchHYXM(String beginDate, String endDate, String F_XMZT, int startIndex, int count) {
		List<HYXMZD> bos = new ArrayList<HYXMZD>();
		if(F_XMZT == null || F_XMZT.equals("-1")) F_XMZT = null;
		List<HYXMZD> pos = hyxmzdMapper.loadXM(beginDate, endDate, F_XMZT);
		for (HYXMZD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	@Override
	public List<HYXMZD> searchHYXM(String beginDate, String endDate, String F_MXBH, String F_XMZT, int startIndex, int count) {
		List<HYXMZD> bos = new ArrayList<HYXMZD>();
		if(F_XMZT == null || F_XMZT.equals("-1")) F_XMZT = null;
		List<HYXMZD> pos = hyxmzdMapper.loadXMByKey(beginDate, endDate, F_XMZT, F_MXBH);
		for (HYXMZD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	@Override
	public HYXMZD getHYXM(String F_XMBH) {
		HYXMZD po = hyxmzdMapper.load(F_XMBH);
		return po;
	}
	
	/**
	 * 产品
	 */
	public void addHYCP(HYCPZD hycpObject) {
		Date now = new Date();
		hycpObject.setF_CHDATE(now);
		hycpObject.setF_CRDATE(now);
		hycpObject.setF_SYZT(1);
		hycpzdMapper.insert(hycpObject);
	}

	public void updateHYCP(HYCPZD hycpObject) {
		Date now = new Date();
		HYCPZD po = hycpzdMapper.loadHYCP(hycpObject.getF_CPBH(), null);
		po.setF_CPMC(hycpObject.getF_CPMC());
		po.setF_SYZT(hycpObject.getF_SYZT());
		po.setF_CHDATE(now);
		hycpzdMapper.update(po);
		
	}
	
	public void delHYCP(String F_CPBH) {
		hycpzdMapper.deleteHYCP(F_CPBH);
	}

	public List<HYCPZD> searchHYCP(String F_CPBH, int startIndex, int count) {
		List<HYCPZD> bos = new ArrayList<HYCPZD>();
		List<HYCPZD> pos = hycpzdMapper.findByObjId(F_CPBH, null);
		for (HYCPZD po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public HYCPZD getHYCP(String F_CPBH) {
		HYCPZD po = hycpzdMapper.loadHYCP(F_CPBH, null);
		return po;
	}

	public List<HYCPZD> searchHYCP(int startIndex, int count) {
		List<HYCPZD> bos = new ArrayList<HYCPZD>();
		List<HYCPZD> pos = hycpzdMapper.findByObjId("", null);
		for (HYCPZD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	/**
	 * 项目产品管理
	 */
	@Override
	public void addHYXMCP(HYXMCP hycpObject) {
		Date now = new Date();
		hycpObject.setF_CHDATE(now);
		hycpObject.setF_CRDATE(now);
		hyxmcpMapper.insert(hycpObject);
	}

	@Override
	public void delHYXMCP(String F_XMBH, String F_CPBH) {
		hyxmcpMapper.deleteHYXMCP(F_XMBH, F_CPBH);
	}

	@Override
	public HYXMCP getHYXMCP(String F_XMBH, String F_CPBH) {
		HYXMCP po = hyxmcpMapper.loadHYXMCP(F_XMBH, F_CPBH);
		return po;
	}

	@Override
	public List<HYXMCP> searchHYXMCP(String F_XMBH, int startIndex, int count) {
		return searchHYXMCP(F_XMBH, null);
	}
	
	@Override
	public List<HYXMCP> searchHYXMCP(String F_XMBH, String F_SYZT) {
		List<HYXMCP> bos = new ArrayList<HYXMCP>();
		List<HYXMCP> pos = hyxmcpMapper.findCpByXm(F_XMBH, F_SYZT);
		for (HYXMCP po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public List<HYXMCP> searchHYXMCP(String F_XMBH, String F_CPBH, int startIndex, int count) {		
		return searchHYXMCP(F_XMBH, F_CPBH, null, startIndex, count);
	}

	public List<HYXMCP> searchHYXMCP(String F_XMBH, String F_CPBH, String F_SYZT, int startIndex, int count) {
		List<HYXMCP> bos = new ArrayList<HYXMCP>();
		List<HYXMCP> pos = hyxmcpMapper.findCpByXms(F_XMBH, F_CPBH, F_SYZT);
		for (HYXMCP po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	@Override
	public void updateHYXMCP(HYXMCP hyckObject) {
		Date now = new Date();
		HYXMCP po = hyxmcpMapper.loadHYXMCP(hyckObject.getF_XMBH(), hyckObject.getF_CPBH());
		po.setF_CPMC(hyckObject.getF_CPMC());
		po.setF_XMBH(hyckObject.getF_XMBH());
		po.setF_SYZT(hyckObject.getF_SYZT());
		po.setF_CHDATE(now);
		hyxmcpMapper.update(po);
	}

	public void doCompleteProject(String F_XMBH, String F_WCR) {
		empFormUtil.doCompleteProject(F_XMBH, F_WCR);
	}
}
