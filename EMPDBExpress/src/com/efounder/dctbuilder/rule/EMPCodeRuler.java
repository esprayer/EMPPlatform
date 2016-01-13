package com.efounder.dctbuilder.rule;

import java.util.List;

import com.core.xml.StubObject;
import com.efounder.buffer.DictDataBuffer;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.dbc.ClientDataSet;
import com.efounder.dctbuilder.data.DictMetaDataEx;
import com.efounder.dctbuilder.util.DictBHUtil;
import com.efounder.dctbuilder.util.DictUtil;
import com.efounder.dctbuilder.variant.VariantUtil;
import com.efounder.dctbuilder.view.DictTableView;
import com.efounder.dctbuilder.view.DictView;
import com.efounder.eai.data.JParamObject;
import com.efounder.sql.JConnection;
import com.pub.util.StringFunction;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class EMPCodeRuler extends StubObject implements ICodeRule {
	//编码规则类型
	public static final String      CODERULE = "CodeRule";     //真正的编码规则
	public static final String    TEMPCODERULE = "TempCodeRule"; //数据添加临时编码规则
	public static final String   INPUTCODERULE = "InputCodeRule";//数据导入临时编码规则

	protected DictView                dictView;
	
	protected int                    lshlength;
	
	protected String              codeRuleType;
	
    public EMPCodeRuler() {
    }

    /**
     * 根据规则生成编码
     *
     * @param rule StubObject
     * @return String
     */
    public String getCodeByRule(JParamObject PO) {
    	String codeRule = PO.GetValueByParamName("CODERULE", "");
        if (codeRule == null || codeRule.trim().length() == 0) return "";
        String[]  items = codeRule.split("[+]");
        StringBuffer sb = new StringBuffer();
        for (int i = 0, n = items.length; i < n; i++) {
            // 计算流水号时认为前缀已经生成完，流水号只能放到最后
            if ("LSH".equals(items[i])){
            	PO.SetValueByParamName("DCT_BM_PREFIX", sb.toString());
            }
            sb.append(getCodeByRule(PO, items[i], sb.toString()));
        }
        return sb.toString();
    }

    /**
     * 根据规则生成编码
     *
     * @param rule StubObject
     * @return String
     */
    public String getCodeByRule(StubObject rule) {
        if (rule == null || rule.getString("DCT_VALUE", "").trim().length() == 0)
            return "";
        String[]  items = rule.getString("DCT_VALUE", "").split("[+]");
        StringBuffer sb = new StringBuffer();
        for (int i = 0, n = items.length; i < n; i++) {
            // 计算流水号时认为前缀已经生成完，流水号只能放到最后
            if ("LSH".equals(items[i])){
                getDictView().getDictModel().getCdsParam().setString("DCT_BM_PREFIX", sb.toString());
            }
            sb.append(getCodeByRule(items[i], sb.toString()));
        }
        return sb.toString();
    }
    
    /**
     * 根据规则获取编码前缀
     *
     * @param rule StubObject
     * @return String
     */
    public String getPrefixByRule(JParamObject PO, String codeRule) {
        if (codeRule == null || codeRule.trim().length() == 0) return "";
        String[]  items = codeRule.split("[+]");
        StringBuffer sb = new StringBuffer();
        for (int i = 0, n = items.length; i < n; i++) {
            // 计算流水号时认为前缀已经生成完，流水号只能放到最后
            if ("LSH".equals(items[i])){
            	return sb.toString();
            }
            sb.append(getCodeByRule(PO, items[i], sb.toString()));
        }
        return sb.toString();
    }
    
    /**
     *
     * @param rule String 规则
     * @param code String 已经生成的编码
     * @return String
     */
    public String getCodeByRule(JParamObject PO, String rule, String code) {
        rule = rule.trim();
        if (rule.startsWith("@") && rule.endsWith("@")) {
            rule = rule.substring(1, rule.length() - 1);
            //变量可能是这种格式TABLE.COLUMN
            return getVariant(PO, rule);
        } else if (rule.toUpperCase().startsWith("LSH")) {
            return getCodeLSH(PO, code);
        }
        return rule;
    }

    /**
    *
    * @param rule String 规则
    * @param code String 已经生成的编码
    * @return String
    */
   public String getCodeByRule(String rule, String code) {
       DictModel     model = dictView.getDictModel();
       DictMetaDataEx  dmd = model.getMetaData();
       ClientDataSet   cds = model.getDataSet();
       rule = rule.trim();
       if (rule.startsWith("@") && rule.endsWith("@")) {
           rule = rule.substring(1, rule.length() - 1);
           //变量可能是这种格式TABLE.COLUMN
           return getVariant(cds, rule);
       } else if (rule.toUpperCase().startsWith("LSH")) {
           return getCodeLSH(code, cds.getRowCount() - 1);
       } else if (rule.toUpperCase().startsWith("GUID")){
           return getGUID();
       }
       return rule;
   }
   
   /**
    * 获取最大流水号
    * @param code String
    * @param row int
    * @return String
    */
   private String getCodeLSH(String code, int row){
       DictModel     model = dictView.getDictModel();
       DictMetaDataEx  dmd = model.getMetaData();
       String   serverName = model.getCdsParam().getServerName();
       String     nextValB = "", nextValF = "";
       int rest = dmd.getTotalLength() - code.length();
       // 编码总长度为0，说明没有编码规则
       if (dmd.getTotalLength() == 0 || rest < 0) {
           rest = 4;
       }
       // 根据元数据的定义决定是从前台还是后台取最大编号
       // 后台获取的下一个流水号
       boolean isGetMaxFromDB = isGetMaxFromDB(dmd);
       // 前台先用界面的最大流水号代替，保存时从SYS_AUTOBH中获取最大编号真正生成流水号
       if (isGetMaxFromDB) {
//           nextValB = getNextLshFromDB(dmd, code, serverName);
       	String lsh = "";
       	for(int i=0; i<rest; i++){
       		lsh += "*";
       	}
       	return lsh;
       }
       
       //前台获取下一个流水号
       nextValF = getNextLshFromView(model, dmd, code);
       lshlength = rest;
       int numberB = 0, numberF = 0, number = 0;
       try {
           String restvF = nextValF.substring(nextValF.length() - rest);
           numberF = Integer.parseInt(restvF);
       } catch (Exception ex) {
           numberF = 0;
       }
       try {
           String restvB = nextValB.substring(nextValB.length() - rest);
           numberB = Integer.parseInt(restvB);
       } catch (Exception ex) {
       	numberB = 0;
       }
       if (numberF > numberB)
           number = numberF;
       else
           number =numberB;
       String lsh = StringFunction.FillZeroFromBegin(number, rest);

       return lsh;
   }
   
   /**
    * 判断是否需要从后台获取最大编号
    * @param dmd DictMetaDataEx
    * @return boolean
    */
   private boolean isGetMaxFromDB(DictMetaDataEx dmd) {
       if (dmd != null && dmd.getObject("SYS_DCT_CST", null) != null) {
           java.util.List cst = (List) dmd.getObject("SYS_DCT_CST", null);
           for (int i = 0, n = cst.size(); i < n; i++) {
               StubObject so = (StubObject) cst.get(i);
               if (so.getString("DCT_KEY", "").toUpperCase().equals("CODERULEEXACT")) {
                   return "1".equals(so.getString("DCT_VALUE", ""));
               }
           }
       }
       return false;
   }
   
   /**
    * 前台获取的最大编号
    * @param  dmd DictMetaDataEx
    * @param code String
    * @return     String
    */
   private String getNextLshFromView(DictModel model, DictMetaDataEx dmd, String code) {
   	String nextlsh = "";
   	int lshLength = 0;
   	if(code == null || code.length() == 0){
   		lshLength = dmd.getTotalLength();
   	}else{
   		lshLength = dmd.getTotalLength() - code.length();
   	}
   	if(lshLength <= 0) lshLength = 4;
   	int step = dmd.getDctBMStep();
   	String maxbm = DictUtil.getMaxValue(model.getDataSet(), dmd.getDctBmCol(), code, true);
   	if(maxbm == null || maxbm.length() == 0){
   		nextlsh = StringFunction.FillZeroFromBegin(1, lshLength);
   	}else{
	    	String maxlsh = maxbm.substring(code.length());
                   int nextlshInt = step;
                   try {
                       long lon = Long.parseLong(maxlsh);
                       nextlshInt = nextlshInt + (int)lon;
                   } catch (Exception e) {
//                       e.printStackTrace();
                   }
//	    	int nextlshInt = Long.valueOf(maxlsh).intValue() + step;
	    	nextlsh = StringFunction.FillZeroFromBegin(nextlshInt, lshLength);
   	}
   	return nextlsh;
   }
   
    /**
     * 根据变量获取值
     *
     * @param  cds ClientDataSet
     * @param rule String
     * @return     String
     */
    private String getVariant(JParamObject PO, String rule) {
        rule = rule.trim();
//        if (rule.indexOf(",") > 0) {
//            //@UNIT_ID,SYS_UNITS.UNIT_NAME@
//            String src = rule.substring(0, rule.indexOf(","));
//            src = src.trim();
//            String tmp = rule.substring(rule.indexOf(",") + 1);
//            tmp = tmp.trim();
//            String tab = "", col = tmp;
//            if (tmp.indexOf(".") > 0) {
//                tab = tmp.substring(0,  tmp.indexOf("."));
//                tab = tab.trim();
//                col = tmp.substring(tmp.indexOf(".") + 1);
//                col = col.trim();
//                return getVariantFromBuffer(src, tab, col);
//            }
//        } else {
        	return VariantUtil.getDefaultValue(PO, "", "@"+rule+"@");
//        }
//        return "";
    }
    
    /**
     * 根据变量获取值
     *
     * @param  cds ClientDataSet
     * @param rule String
     * @return     String
     */
    private String getVariant(ClientDataSet cds, String rule) {
        rule = rule.trim();
        if (rule.indexOf(",") > 0) {
            //@UNIT_ID,SYS_UNITS.UNIT_NAME@
            String src = rule.substring(0, rule.indexOf(","));
            src = src.trim();
            if (cds.hasColumn(src) != null) {
                src = cds.getRowSet().getString(src, "");
            }
            String tmp = rule.substring(rule.indexOf(",") + 1);
            tmp = tmp.trim();
            String tab = "", col = tmp;
            if (tmp.indexOf(".") > 0) {
                tab = tmp.substring(0,  tmp.indexOf("."));
                tab = tab.trim();
                col = tmp.substring(tmp.indexOf(".") + 1);
                col = col.trim();
                return getVariantFromBuffer(src, tab, col);
            } else {
                if (cds.hasColumn(col) != null) {
                    return cds.getRowSet().getString(col, "");
                }
            }
        } else {
            if (cds.hasColumn(rule) != null) {
            	Column column = cds.getColumn(rule);
            	if(column.getDataType().equals("D"))
            		return String.valueOf(cds.getRowSet().getDate(rule, null).getTime());
                return cds.getRowSet().getString(rule, "");
            }else{
            	return VariantUtil.getDefaultValue("", "@"+rule+"@", getDictView().getDictModel());
            }
        }
        return "";
    }
    
    /**
    *
    * @param src String
    * @param tab String
    * @param col String
    * @return    String
    */
   private String getVariantFromBuffer(String src, String tab, String col) {
       String serverName = dictView.getDictModel().getCdsParam().getServerName();
       JParamObject PO = JParamObject.Create();
       PO.setEAIServer(serverName);
       EFRowSet rowSet = DictDataBuffer.getDefault(PO).getDictDataItem(tab, src);
       if (rowSet != null) 
       	return rowSet.getString(col, "");
       return src;
   }
    /**
     * 获取最大流水号
     * @param code String
     * @param row int
     * @return String
     */
    private String getCodeLSH(JParamObject PO, String code){
        int     totalLength = Integer.parseInt(PO.GetValueByParamName("DCT_BMSTRU_LENGTH", "6"));
        int            rest = totalLength - code.length();
        // 编码总长度为0，说明没有编码规则
        if (totalLength == 0 || rest < 0) {
            rest = 4;
        }
        // 根据元数据的定义决定是从前台还是后台取最大编号
        // 后台获取的下一个流水号
        // 前台先用界面的最大流水号代替，保存时从SYS_AUTOBH中获取最大编号真正生成流水号
        String lsh = "";
    	for(int i=0; i<rest; i++){
    		lsh += "*";
    	}
    	return lsh;
    }

    /**
     * 后台获取的最大编号
     * @param  dmd DictMetaDataEx
     * @param code String
     * @return     String
     */
    public String getNextDCTBHFromDB(JConnection conn, JParamObject PO) {
    	int       lshLength = 0;
    	int     totalLength = Integer.parseInt(PO.GetValueByParamName("DCT_BMSTRU_LENGTH", "6"));//编码长度
    	String     codeRule = PO.GetValueByParamName("CODERULE", "");//编码规则
    	String     prefixBH = "";//编码前缀
    	
    	//如果编码规则为空，则默认为编码长度为6
    	if(codeRule == null || codeRule.length() == 0){
    		lshLength = 6;
    	}
    	prefixBH = getPrefixByRule(PO, codeRule);
    	lshLength = totalLength - prefixBH.length();
    	String newBH = DictBHUtil.getNewBH(conn, PO, prefixBH, PO.GetValueByParamName("OBJ_ID", ""), PO.GetValueByParamName("COL_ID", ""), lshLength);
    	newBH = prefixBH + newBH;
    	return newBH;
    }
    
    /**
     * 后台获取的最大编号
     * @param  dmd DictMetaDataEx
     * @param code String
     * @return     String
     */
    public String getNextModelBHFromDB(JConnection conn, JParamObject PO) {
    	int       lshLength = 0;
    	int     totalLength = Integer.parseInt(PO.GetValueByParamName("DCT_BMSTRU_LENGTH", "6"));//编码长度
    	String     codeRule = PO.GetValueByParamName("CODERULE", "");//编码规则
    	String     prefixBH = "";//编码前缀
    	
    	//如果编码规则为空，则默认为编码长度为6
    	if(codeRule == null || codeRule.length() == 0){
    		lshLength = 6;
    	}
    	prefixBH = getPrefixByRule(PO, codeRule);
    	lshLength = totalLength - prefixBH.length();
    	String newBH = DictBHUtil.getModelNewBH(conn, PO, prefixBH, lshLength);
    	newBH = prefixBH + newBH;
    	return newBH;
    }

	public String getRuleName() {
		if(codeRuleType != null && codeRuleType.trim().length() > 0){
    		return codeRuleType;
    	}
        return "CodeRule";
	}

	public List getAllRule() {
		DictModel     model = dictView.getDictModel();
        DictMetaDataEx  dmd = model.getMetaData();
        return dmd.getExtendProperties(getRuleName());
	}

	public Object getUnitRule(String unit) {
		return null;
	}

	public Object getDictRule() {
		java.util.List allrule = getAllRule();
        for (int i = 0, n = allrule.size(); i < n; i++) {
            StubObject so = (StubObject) allrule.get(i);
            if (so.getString("UNIT_ID", "").trim().length() == 0) {
                return so;
            }
        }
        return null;
	}

	public String getCodeByRule() {
		DictModel     model = dictView.getDictModel();
        DictMetaDataEx  dmd = model.getMetaData();
        ClientDataSet   cds = model.getDataSet();
        /**
         * 一类规则可能是多个，因为可能按使用单位定义规则，根据当前行数据的使用单位的值
         * 取出来当前使用的规则和使用单位为空的规则（如果有的话）
         */
        //先使用字典的规则生成编码，然后再使用单位的规则生成编码
        StubObject dictrule = (StubObject) getDictRule();
//        // 优先使用使用单位个性的规则
//        boolean  enableunit = cds.hasColumn("UNIT_ID") != null;
//        if (enableunit){
//            Object unitRule = getUnitRule(cds.getString("UNIT_ID"));
//        	if(unitRule != null){
//        		dictrule = (StubObject) unitRule;
//        	}
//    	}
        //获取编码
        if (dictrule != null)
            return getCodeByRule(dictrule);
        return "";
	}

	public boolean checkValid(String code) {
		return false;
	}

	public void setNodeViewerAdapter(DictTableView viewer) {
		
	}

	public void setCodeRuleType(String type) {
		
	}

	public String getCodeRuleType() {
		return null;
	}

	/**
     * setDictView
     *
     * @param dictView DictView
     * @todo Implement this com.efounder.dctbuilder.rule.ICodeRule method
     */
    public void setDictView(DictView dictView) {
        this.dictView = dictView;
    }

    /**
     * getDictView
     *
     * @return DictView
     * @todo Implement this com.efounder.dctbuilder.rule.ICodeRule method
     */
    public DictView getDictView() {
        return this.dictView;
    }
    
	public Object doByRule() {
		DictModel     model = dictView.getDictModel();
        DictMetaDataEx  dmd = model.getMetaData();
        ClientDataSet   cds = model.getDataSet();
//        String code = getCodeByRule();
//        if (code != null && code.trim().length() > 0) {
//            cds.setString(dmd.getDctBmCol(), code);
//        }
        
        return getCodeByRule();
	}

	public String getMessage() {
		return null;
	}
	
	/**
    *
    * @return String
    */
   private String getGUID() {
       return com.efounder.util.GUID.randomGUID().toString();
   }
}
