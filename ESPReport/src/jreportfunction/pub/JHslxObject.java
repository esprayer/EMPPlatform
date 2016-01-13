package jreportfunction.pub;

import java.util.*;

/**
 * <p>Title: �������Ͷ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: pansoft</p>
 * @author hufeng
 * @version 1.0
 */
public class JHslxObject  extends Hashtable {
static ResourceBundle res = ResourceBundle.getBundle("jreportfunction.pub.Language");
  private static JHslxObject hslxMap = null;

  /**
   * ȡ�������Ͷ���
   * @return JHslxHashMap
   */
  public static JHslxObject getHslxObject(){
    if(hslxMap == null){
      hslxMap = new JHslxObject();
    }
    return hslxMap;
  }

  public JHslxObject() {
    JHslxStub hslxinfo = new JHslxStub();
    //��Ŀ
//    hslxinfo.ZDTBL = "LSKMSZ";
	hslxinfo.ZDTBL = "LSKMZD";
//    hslxinfo.ZDTBL = "LSKMXX";
    hslxinfo.LXMC = res.getString("String_2");
    //hslxinfo.ZDLB = "F_SXBH";
    hslxinfo.ZDBH = "F_KMBH";
    hslxinfo.ZDMC = "F_KMMC";
    hslxinfo.STRU = "ZW_KMSTRU";
    hslxinfo.QXBH = "ZWKMQX";
    hslxinfo.YETBL = "ACKMJE";
    hslxinfo.SLTBL = "ACKMSL";
    hslxinfo.WBTBL = "ACKMWB";

    hslxinfo.FZYETBL = "ACFZJE";
    hslxinfo.FZSLTBL = "ACFZSL";
    hslxinfo.FZWBTBL = "ACFZWB";
    
    hslxinfo.YECOL = "F_KMBH";
    hslxinfo.PZCOL = "F_KMBH";
    hslxinfo.GROUP = "PSGPKM";
	hslxinfo.GROUPGL = "ACGPKM";
    hslxinfo.GPCOL = "KMGP";
    hslxinfo.CTBH = "KM";
    //hslxinfo.HSBZ  = "F_BMHS";
    this.put("KM", hslxinfo);
    //����
    hslxinfo = new JHslxStub();
    hslxinfo.ZDTBL = "ACBMZD";
    hslxinfo.LXMC = res.getString("String_16");
    hslxinfo.ZDLB = "F_SXBH";
    hslxinfo.ZDBH = "F_BMBH";
    hslxinfo.ZDMC = "F_BMMC";
    hslxinfo.STRU = "ZW_BMSTRU";
    hslxinfo.QXBH = "ZWBMZD";
//    hslxinfo.YETBL = "ACBMJE";
//    hslxinfo.SLTBL = "ACBMSL";
//    hslxinfo.WBTBL = "ACBMWB";
    hslxinfo.YETBL = "ACFZJE";
    hslxinfo.SLTBL = "ACFZSL";
    hslxinfo.WBTBL = "ACFZWB";

    hslxinfo.FZYETBL = "ACFZJE";
    hslxinfo.FZSLTBL = "ACFZSL";
    hslxinfo.FZWBTBL = "ACFZWB";
    
    hslxinfo.YECOL = "F_BMBH";
    hslxinfo.PZCOL = "F_BMBH";
    hslxinfo.GROUP = "PSGPBM";
	hslxinfo.GROUPGL = "ACGPBM";
    hslxinfo.GPCOL = "BMGP";
    hslxinfo.CTBH = "BMHS";
    this.put("BM", hslxinfo);
    //�ɱ�����
    hslxinfo = new JHslxStub();
    hslxinfo.ZDTBL = "ACCBZX";
    hslxinfo.LXMC = res.getString("String_31");
    hslxinfo.ZDLB = "";
    hslxinfo.ZDBH = "F_CBBH";
    hslxinfo.ZDMC = "F_CBMC";
    hslxinfo.STRU = "ZW_CBSTRU";
    hslxinfo.QXBH = "ZWCBZX";
//    hslxinfo.YETBL = "ACCBJE";
//    hslxinfo.SLTBL = "ACCBSL";
//    hslxinfo.WBTBL = "ACCBWB";
    hslxinfo.YETBL = "ACFZJE";
    hslxinfo.SLTBL = "ACFZSL";
    hslxinfo.WBTBL = "ACFZWB";

    hslxinfo.FZYETBL = "ACFZJE";
    hslxinfo.FZSLTBL = "ACFZSL";
    hslxinfo.FZWBTBL = "ACFZWB";

    hslxinfo.YECOL = "F_CBBH";
    hslxinfo.PZCOL = "F_CBBH";
    hslxinfo.GROUP = "PSGPCB";
	hslxinfo.GROUPGL = "ACGPCB";
    hslxinfo.GPCOL = "CBGP";
    hslxinfo.CTBH = "CBZX";
    this.put("CB", hslxinfo);
    //��Ʒ����
    hslxinfo = new JHslxStub();
    hslxinfo.ZDTBL = "LSCPZD";
    hslxinfo.LXMC = res.getString("String_46");
    hslxinfo.ZDLB = "";
    hslxinfo.ZDBH = "F_CPBH";
    hslxinfo.ZDMC = "F_CPMC";
    hslxinfo.STRU = "ZW_CPSTRU";
    hslxinfo.QXBH = "ZWCPZD";
//    hslxinfo.YETBL = "ACCPJE";
//    hslxinfo.SLTBL = "ACCPSL";
//    hslxinfo.WBTBL = "ACCPWB";
    hslxinfo.YETBL = "ACFZJE";
    hslxinfo.SLTBL = "ACFZSL";
    hslxinfo.WBTBL = "ACFZWB";
    hslxinfo.FZYETBL = "ACFZJE";
    hslxinfo.FZSLTBL = "ACFZSL";
    hslxinfo.FZWBTBL = "ACFZWB";

    hslxinfo.YECOL = "F_CPBH";
    hslxinfo.PZCOL = "F_CPBH";
    hslxinfo.GROUP = "PSGPCP";
	hslxinfo.GROUPGL = "ACGPCP";
    hslxinfo.GPCOL = "CPGP";
    hslxinfo.CTBH = "CPHS";
    this.put("CP", hslxinfo);
    //������4
    hslxinfo = new JHslxStub();
    hslxinfo.ZDTBL = "LSZGZD";
    hslxinfo.LXMC = res.getString("String_61");
    hslxinfo.ZDLB = "F_BMBH";
    hslxinfo.ZDBH = "F_ZGBH";
    hslxinfo.ZDMC = "F_NAME";
    hslxinfo.STRU = "";
    hslxinfo.QXBH = "ZWGRWL";
//    hslxinfo.YETBL = "ACZGJE";
//    hslxinfo.SLTBL = "ACZGSL";
//    hslxinfo.WBTBL = "ACZGWB";
    hslxinfo.YETBL = "ACFZJE";
    hslxinfo.SLTBL = "ACFZSL";
    hslxinfo.WBTBL = "ACFZWB";
    hslxinfo.FZYETBL = "ACFZJE";
    hslxinfo.FZSLTBL = "ACFZSL";
    hslxinfo.FZWBTBL = "ACFZWB";
    
    hslxinfo.YECOL = "F_ZGBH";
    hslxinfo.PZCOL = "F_ZG_BH";
    hslxinfo.GROUP = "PSGPZG";
	hslxinfo.GROUPGL = "ACGPZG";
    hslxinfo.GPCOL = "GRGP";
    hslxinfo.CTBH = "GRWL";
    this.put("GR", hslxinfo);
    //��4��λ
    hslxinfo = new JHslxStub();
    hslxinfo.ZDTBL = "LSWLDW_V_ALLUNIT";
    hslxinfo.LXMC = res.getString("String_76");
    hslxinfo.ZDLB = "F_FLBH";
    hslxinfo.ZDBH = "F_DWBH";
    hslxinfo.ZDMC = "F_DWMC";
    hslxinfo.STRU = "";
    hslxinfo.QXBH = "ZWWLDW";
//    hslxinfo.YETBL = "ACWLJE";
//    hslxinfo.SLTBL = "ACWLSL";
//    hslxinfo.WBTBL = "ACWLWB";
    hslxinfo.YETBL = "ACFZJE";
    hslxinfo.SLTBL = "ACFZSL";
    hslxinfo.WBTBL = "ACFZWB";
    hslxinfo.FZYETBL = "ACFZJE";
    hslxinfo.FZSLTBL = "ACFZSL";
    hslxinfo.FZWBTBL = "ACFZWB";
    
    hslxinfo.YECOL = "F_DWBH";
    hslxinfo.PZCOL = "F_DW_BH";
    hslxinfo.GROUP = "PSGPDW";
	hslxinfo.GROUPGL = "ACGPDW";
    hslxinfo.GPCOL = "DWGP";
    hslxinfo.CTBH = "WLDW";
    this.put("DW", hslxinfo);
    //�ֽ���
    hslxinfo = new JHslxStub();
    hslxinfo.ZDTBL = "ACXJLL";
    hslxinfo.LXMC = res.getString("String_91");
    hslxinfo.ZDLB = "";
    hslxinfo.ZDBH = "F_XJBH";
    hslxinfo.ZDMC = "F_XJMC";
    hslxinfo.STRU = "ZW_XJSTRU";
    hslxinfo.QXBH = "ZWXJLL";
//    hslxinfo.YETBL = "ACXJJE";
//    hslxinfo.SLTBL = "ACXJSL";
//    hslxinfo.WBTBL = "ACXJWB";
    hslxinfo.YETBL = "ACFZJE";
    hslxinfo.SLTBL = "ACFZSL";
    hslxinfo.WBTBL = "ACFZWB";
    hslxinfo.FZYETBL = "ACFZJE";
    hslxinfo.FZSLTBL = "ACFZSL";
    hslxinfo.FZWBTBL = "ACFZWB";
    
    hslxinfo.YECOL = "F_XJBH";
    hslxinfo.PZCOL = "F_XJBH";
    hslxinfo.GROUP = "PSGPXJ";
	hslxinfo.GROUPGL = "ACGPXJ";
    hslxinfo.GPCOL = "XJGP";
    hslxinfo.CTBH = "XJLL";
    this.put("XJ", hslxinfo);
    
    hslxinfo = new JHslxStub();
    hslxinfo.ZDTBL = "ACXJLL";
    hslxinfo.LXMC = res.getString("String_91");
    hslxinfo.ZDLB = "";
    hslxinfo.ZDBH = "F_XJBH";
    hslxinfo.ZDMC = "F_XJMC";
    hslxinfo.STRU = "ZW_XJSTRU";
    hslxinfo.QXBH = "ZWXJLL";
//    hslxinfo.YETBL = "ACXJJE";
//    hslxinfo.SLTBL = "ACXJSL";
//    hslxinfo.WBTBL = "ACXJWB";
    hslxinfo.YETBL = "ACXJJE";
    hslxinfo.SLTBL = "ACXJJE";
    hslxinfo.WBTBL = "ACXJJE";
    hslxinfo.FZYETBL = "ACXJJE";
    hslxinfo.FZSLTBL = "ACXJJE";
    hslxinfo.FZWBTBL = "ACXJJE";
    
    hslxinfo.YECOL = "F_XJBH";
    hslxinfo.PZCOL = "F_XJBH";
    hslxinfo.GROUP = "PSGPXJ";
	hslxinfo.GROUPGL = "ACGPXJ";
    hslxinfo.GPCOL = "XJGP";
    hslxinfo.CTBH = "XJLL";
    this.put("TMPXJ", hslxinfo);
    
    //Ҫ��̨��
    hslxinfo = new JHslxStub();
    hslxinfo.ZDTBL = "LSYSZD";
    hslxinfo.LXMC = res.getString("String_106");
    hslxinfo.ZDLB = "F_YSLB";
    hslxinfo.HSLB = "F_YSLB";
    hslxinfo.ZDBH = "F_YSBH";
    hslxinfo.ZDMC = "F_YSMC";
    hslxinfo.STRU = "ZW_YSSTRU";
    hslxinfo.QXBH = "ZWTZ";
//    hslxinfo.YETBL = "ACTZJE";
//    hslxinfo.SLTBL = "ACTZSL";
//    hslxinfo.WBTBL = "ACTZWB";
	hslxinfo.YETBL = "ACFZJE";
	hslxinfo.SLTBL = "ACFZSL";
	hslxinfo.WBTBL = "ACFZWB";
    hslxinfo.FZYETBL = "ACFZJE";
    hslxinfo.FZSLTBL = "ACFZSL";
    hslxinfo.FZWBTBL = "ACFZWB";
	
    hslxinfo.YECOL = "F_YSBH";
    hslxinfo.PZCOL = "F_TZ_BH";
    hslxinfo.CTBH = "YSTZ";
    this.put("TZ", hslxinfo);
    //ר�����\uFFFD
    hslxinfo = new JHslxStub();
    hslxinfo.ZDTBL = "LSHSZD";
    hslxinfo.LXMC = res.getString("String_120");
    hslxinfo.ZDLB = "F_HSLB";
    hslxinfo.HSLB = "F_HSLB";
    hslxinfo.ZDBH = "F_HSBH";
    hslxinfo.ZDMC = "F_HSMC";
    hslxinfo.STRU = "ZW_HSSTRU";
    hslxinfo.QXBH = "ZWHS";
//    hslxinfo.YETBL = "ACHSJE";
//    hslxinfo.SLTBL = "ACHSSL";
//    hslxinfo.WBTBL = "ACHSWB";
	hslxinfo.YETBL = "ACFZJE";
	hslxinfo.SLTBL = "ACFZSL";
	hslxinfo.WBTBL = "ACFZWB";
    hslxinfo.FZYETBL = "ACFZJE";
    hslxinfo.FZSLTBL = "ACFZSL";
    hslxinfo.FZWBTBL = "ACFZWB";
	
    hslxinfo.YECOL = "F_HSBH";
    hslxinfo.PZCOL = "F_HSBH";
    hslxinfo.CTBH = "ZXHS";
    this.put("XM", hslxinfo);

	hslxinfo = new JHslxStub();
	hslxinfo.ZDTBL = "LSKMZD";
	hslxinfo.LXMC = res.getString("String_2");
	hslxinfo.ZDBH = "F_KMBH";
	hslxinfo.ZDMC = "F_KMMC";
	hslxinfo.STRU = "ZW_KMSTRU";
	hslxinfo.QXBH = "ZWKMQX";
	hslxinfo.YETBL = "ACFZJE";
	hslxinfo.SLTBL = "ACFZSL";
	hslxinfo.WBTBL = "ACFZWB";
    hslxinfo.FZYETBL = "ACFZJE";
    hslxinfo.FZSLTBL = "ACFZSL";
    hslxinfo.FZWBTBL = "ACFZWB";
	
	hslxinfo.YECOL = "F_KMBH";
	hslxinfo.PZCOL = "F_KMBH";
	hslxinfo.GROUP = "PSGPKM";
	hslxinfo.GROUPGL = "ACGPKM";
	hslxinfo.GPCOL = "KMGP";
    hslxinfo.CTBH = "KM";
	this.put("KMFZ", hslxinfo);


    /**
     * �Զ��帨��11-20
     */
    String value;
    for(int i=1; i<=10; i++){
      value = String.valueOf(i);
      if(value.length() < 2){
        value = "0" + value;
      }
      hslxinfo = new JHslxStub();
      hslxinfo.ZDTBL = "LSZFXM" + value;
      hslxinfo.LXMC = res.getString("String_135") + value;
      hslxinfo.ZDLB = "";
	  hslxinfo.ZDBH = "F_ZFBH";
      hslxinfo.ZDMC = "F_ZFMC";
      hslxinfo.STRU = "ZW_FZSTRU" + value;
      hslxinfo.QXBH = "LSZFXM" + value;
//      hslxinfo.YETBL = "ACZFJE" + value;
//      hslxinfo.SLTBL = "ACZFSL" + value;
//      hslxinfo.WBTBL = "ACZFWB" + value;
	  hslxinfo.YETBL = "ACFZJE";
	  hslxinfo.SLTBL = "ACFZSL";
	  hslxinfo.WBTBL = "ACFZWB";
	  hslxinfo.FZYETBL = "ACFZJE";
	  hslxinfo.FZSLTBL = "ACFZSL";
	  hslxinfo.FZWBTBL = "ACFZWB";
	  
      hslxinfo.YECOL = "F_ZF" + value;
      hslxinfo.PZCOL = "F_ZF" + value;
      hslxinfo.GROUP = "PSGPZF" + value;
	  hslxinfo.GROUPGL = "ACGPZFXM" + value;
      hslxinfo.GPCOL = "ZFGP";
      hslxinfo.CTBH = "ZF"+value;
      this.put("ZF" + value, hslxinfo);
    }
    
    for(int i=11; i<=20; i++){
        value = String.valueOf(i);
        if(value.length() < 2){
          value =	 "0" + value;
        }else{
        	value = value;
        }
        hslxinfo = new JHslxStub();
        hslxinfo.ZDTBL = "LSZFXM" + value;
        hslxinfo.LXMC = res.getString("String_135") + value;
        hslxinfo.ZDLB = "";
  	  	hslxinfo.ZDBH = "F_ZFBH";
        hslxinfo.ZDMC = "F_ZFMC";
        hslxinfo.STRU = "ZW_FZSTRU" + value;
        hslxinfo.QXBH = "LSZFXM" + value;
//        hslxinfo.YETBL = "ACZFJE" + value;
//        hslxinfo.SLTBL = "ACZFSL" + value;
//        hslxinfo.WBTBL = "ACZFWB" + value;
    	hslxinfo.YETBL = "ACFZJE";
  	  	hslxinfo.SLTBL = "ACFZSL";
  	  	hslxinfo.WBTBL = "ACFZWB_SYGC";
  	  	hslxinfo.FZYETBL = "ACFZJE";
  	  	hslxinfo.FZSLTBL = "ACFZSL";
  	  	hslxinfo.FZWBTBL = "ACFZWB_SYGC";
  	  
        hslxinfo.YECOL = "F_ZF" + value;
        hslxinfo.PZCOL = "F_ZF" + value;
        hslxinfo.GROUP = "PSGPZF" + value;
  	  	hslxinfo.GROUPGL = "ACGPZFXM" + value;
        hslxinfo.GPCOL = "ZFGP";
        hslxinfo.CTBH = "ZF"+value;
        this.put("ZF" + value, hslxinfo );
      }
  }
}
