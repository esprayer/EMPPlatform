package esyt.framework.persistence.qxgl.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dwz.dal.object.AbstractDO;
/**
 * 用户bean
 * @author frog
 *
 */
public class SYSMenu extends AbstractDO{
	private String MENU_BH;
	private String MENU_MC;
	private String MODULE_BH;
	private String APP_ID;
	private int F_JS;
	private String F_MX;
	private String F_PARENT;
	private String F_DISP;
	private String URL;
	private String IMAGE;
	private String SHORTCUT;
	private String F_TARGET;
	private String F_REL;
	private String F_ATTR;
	private Date F_CRDATE;
	private Date F_CHDATE;
	private List<SYSMenu> subMenus;
	private String F_OP = "0";
	public String getF_OP() {
		return F_OP;
	}
	public void setF_OP(String f_OP) {
		F_OP = f_OP;
	}
	public String getMENU_BH() {
		return MENU_BH;
	}
	public void setMENU_BH(String mENU_BH) {
		MENU_BH = mENU_BH;
	}
	public String getMENU_MC() {
		return MENU_MC;
	}
	public void setMENU_MC(String mENU_MC) {
		MENU_MC = mENU_MC;
	}
	public String getMODULE_BH() {
		return MODULE_BH;
	}
	public void setMODULE_BH(String mODULE_BH) {
		MODULE_BH = mODULE_BH;
	}
	public String getAPP_ID() {
		return APP_ID;
	}
	public void setAPP_ID(String aPP_ID) {
		APP_ID = aPP_ID;
	}
	public int getF_JS() {
		return F_JS;
	}
	public void setF_JS(int f_JS) {
		F_JS = f_JS;
	}
	public String getF_MX() {
		return F_MX;
	}
	public void setF_MX(String f_MX) {
		F_MX = f_MX;
	}
	public String getF_PARENT() {
		return F_PARENT;
	}
	public void setF_PARENT(String f_PARENT) {
		F_PARENT = f_PARENT;
	}
	public String getF_DISP() {
		return F_DISP;
	}
	public void setF_DISP(String f_DISP) {
		F_DISP = f_DISP;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getIMAGE() {
		return IMAGE;
	}
	public void setIMAGE(String iMAGE) {
		IMAGE = iMAGE;
	}
	public String getSHORTCUT() {
		return SHORTCUT;
	}
	public void setSHORTCUT(String sHORTCUT) {
		SHORTCUT = sHORTCUT;
	}
	public Date getF_CRDATE() {
		return F_CRDATE;
	}
	public void setF_CRDATE(Date f_CRDATE) {
		F_CRDATE = f_CRDATE;
	}
	public Date getF_CHDATE() {
		return F_CHDATE;
	}
	public void setF_CHDATE(Date f_CHDATE) {
		F_CHDATE = f_CHDATE;
	}
	public List<SYSMenu> getSubMenus() {
		return subMenus;
	}
	public void setSubMenus(List<SYSMenu> subMenus) {
		this.subMenus = subMenus;
	}
	
	public void addSubMenu(SYSMenu menu){
		if(null==subMenus){
			subMenus = new ArrayList();
		}
		subMenus.add(menu);
	}
	public String toString(){
		String menu ="\n";
		menu+=this.MENU_BH+"_"+this.MENU_MC+"\n";
		if(null!=subMenus){
			for(int i=0;i<subMenus.size();i++){
				SYSMenu obj = subMenus.get(i);
				menu += obj.toString();
			}
		}
		return menu;
	}
	public String getF_TARGET() {
		return F_TARGET;
	}
	public void setF_TARGET(String f_TARGET) {
		F_TARGET = f_TARGET;
	}
	public String getF_REL() {
		return F_REL;
	}
	public void setF_REL(String f_REL) {
		F_REL = f_REL;
	}
	public String getF_ATTR() {
		return F_ATTR;
	}
	public void setF_ATTR(String f_ATTR) {
		F_ATTR = f_ATTR;
	}
	public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SYSMenu other = (SYSMenu) obj;
         if(this.getMENU_BH()!=other.getMENU_BH())
            return false;
        return true;
   }
}