package jreportwh.jdof.classes.common.util;

import java.util.Vector;
import java.util.Enumeration;

/**
 * <p>Title: CharSet</p>
 * <p>Description: char容器，用来存储离散或者连续的char</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class CharSet
{
    private Vector mCharSet=new Vector();

    public CharSet()
    {
    }
    public CharSet(char ch){
        this.add(ch);
    }
    public CharSet(char char0,char char1){
        int min=StringUtil.getID(Character.toString(char0));
        int max=StringUtil.getID(Character.toString(char1));
        if(min>max){
            int temp=max;
            max=min;
            min=temp;
        }
        for(int i=min;i<max;i++){
            char ch=Integer.toString(i).charAt(0);
            Character character = new Character(ch);
            if (!this.contain(ch)) {
                mCharSet.addElement(character);
            }
        }
    }

    public CharSet(String str){
        this.add(str);
    }
    public CharSet(String[] strs){
        this.add(strs);
    }
    public void add(char ch){
        Character character = new Character(ch);
        if (!this.contain(ch)) {
            mCharSet.addElement(character);
        }

    }
    public void add(String str){
        for(int i=0;i<str.length();i++){
            char ch=str.charAt(0);
            Character character = new Character(ch);
            if (!this.contain(ch)) {
                mCharSet.addElement(character);
            }
        }
    }

    public void add(String[] strs){
        for(int i=0;i<strs.length;i++){
            add(strs[i]);
        }
    }
    public void remove(char ch){
        Character character = new Character(ch);
        if (!this.contain(ch)) {
            for(int i=0;i<mCharSet.size();i++){
                if(((Character) mCharSet.get(i)).charValue()==ch){
                    mCharSet.removeElementAt(i);
                }
            }
        }
    }
    public void remove(String str){
        for(int i=0;i<str.length();i++){
            char ch=str.charAt(i);
            remove(ch);
        }
    }

    public boolean contain(char ch){
        for(int i=0;i<mCharSet.size();i++){
            if(((Character) mCharSet.get(i)).charValue()==ch){
                return true;
            }
        }
        return false;
    }
    public boolean contain(String str){
        for(int i=0;i<str.length();i++){
            if(contain(str.charAt(i))){
                return true;
            }
        }
        return false;
    }
    public boolean contain(String[] strs){
        for(int i=0;i<strs.length;i++){
            if(contain(strs[i])){
                return true;
            }
        }
        return false;
    }
    public boolean equals(Object obj){
        return false;
    }
    public String toString(){
        StringBuffer str=new StringBuffer();
        for(Enumeration enume=mCharSet.elements();enume.hasMoreElements();){
            Character character=(Character)enume.nextElement();
            str.append(character.charValue());
        }
        return str.toString();
    }
}
