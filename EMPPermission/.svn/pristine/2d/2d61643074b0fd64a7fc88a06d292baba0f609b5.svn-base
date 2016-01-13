package com.persistence.qxgl.beans;

import java.util.ArrayList;
import java.util.List;

public class TestMenu {
	/**
	 * 测试菜单
	 * @param args
	 */
	public static void main(String[] args) {
		SYSMenu root = new SYSMenu();
		root.setMENU_BH("#ROOT");
		root.setMENU_MC("系统菜单");
		root.setF_MX("0");

		
		List<SYSMenu> menus = new ArrayList<SYSMenu>();

		SYSMenu menu1 = new SYSMenu();
		menu1.setMENU_BH("01");
		menu1.setMENU_MC("01菜单");
		menu1.setF_JS(1);
		menu1.setF_MX("0");
		menu1.setF_PARENT("#ROOT");
		menus.add(menu1);
		
		SYSMenu menu1_1 = new SYSMenu();
		menu1_1.setMENU_BH("0101");
		menu1_1.setMENU_MC("0101菜单");
		menu1_1.setF_PARENT("01");
		menu1_1.setF_JS(2);
		menu1_1.setF_MX("0");
		menus.add(menu1_1);

		
		SYSMenu menu1_1_1 = new SYSMenu();
		menu1_1_1.setMENU_BH("010101");
		menu1_1_1.setMENU_MC("010101菜单");
		menu1_1_1.setF_JS(3);
		menu1_1_1.setF_MX("1");
		menu1_1_1.setF_PARENT("0101");
		menus.add(menu1_1_1);

		
		SYSMenu menu1_2 = new SYSMenu();
		menu1_2.setMENU_BH("0102");
		menu1_2.setMENU_MC("0102菜单");
		menu1_2.setF_PARENT("01");
		menu1_2.setF_JS(2);
		menu1_2.setF_MX("1");
		menus.add(menu1_2);
		findSubMenu(menus,root,0);
		System.out.println(root.toString());
	}
	public static int findSubMenu(List<SYSMenu> menus,SYSMenu parent,int index){
		int i=index;
		for(;i<menus.size();i++){
			SYSMenu menu = menus.get(i);
			if(parent.getMENU_BH().equals(menu.getF_PARENT())){
				parent.addSubMenu(menu);
				if(parent.getF_MX().equals("0")){
					if(i+1>=menus.size()) break;
					findSubMenu(menus,menu,i+1);
				}else{
					break;
				}
			}
		}
		return i;
	}

}
