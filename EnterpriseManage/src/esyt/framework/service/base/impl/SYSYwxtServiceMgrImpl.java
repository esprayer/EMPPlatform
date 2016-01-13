package esyt.framework.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import esyt.framework.persistence.qxgl.beans.SYSYwxt;
import esyt.framework.persistence.qxgl.mapper.SYSYwxtMapper;
import esyt.framework.service.base.SYSYwxtServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("sysYwxtServiceMgr")
public class SYSYwxtServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements SYSYwxtServiceMgr {
	@Autowired
	private SYSYwxtMapper ywxtMapper;
	
	@Override
	public List<SYSYwxt> searchYwxt(int startIndex, int count) {
		return ywxtMapper.findAll();
	}

	@Override
	public List<SYSYwxt> searchYwxt(String ywxtBhOrMc, int startIndex, int count) {
		return ywxtMapper.findByObjId(ywxtBhOrMc);
	}

	@Override
	public SYSYwxt getYwxt(String ywxtBh) {
		return ywxtMapper.load(ywxtBh);
	}

	@Override
	public void addYwxt(SYSYwxt ywxtObj) {
		ywxtMapper.insert(ywxtObj);
	}

	@Override
	public void updateYwxt(SYSYwxt ywxtObj) {
		ywxtMapper.update(ywxtObj);
	}

	@Override
	public void delYwxt(String ywxtBh) {
		ywxtMapper.delete(ywxtBh);
	}
	
}
