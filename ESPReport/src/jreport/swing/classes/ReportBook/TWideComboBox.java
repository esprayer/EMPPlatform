package jreport.swing.classes.ReportBook;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 * <p>Title: XBPL</p>
 *
 * <p>Description: �Զ������п��ComboBox</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: Pansoft</p>
 *
 * @author hufeng
 * @version 1.0
 */
public class TWideComboBox extends JComboBox {

	
    public TWideComboBox() {
        super();
    }

    public TWideComboBox(final Object items[]){
        super(items);
    }

    public TWideComboBox(Vector items) {
        super(items);
    }

    public TWideComboBox(ComboBoxModel aModel) {
        super(aModel);
    }

    private boolean layingOut = false;

    public void doLayout(){
        try{
            layingOut = true;
            super.doLayout();
        }finally{
            layingOut = false;
        }
    }
    

    public Dimension getSize(){
        Dimension dim = super.getSize();
        int nCount = this.getItemCount();
        if(nCount < 1){
            return dim;
        }
        //
        String value;
        JLabel label;
        int maxWidth = dim.width;
        for(int i=0; i<nCount; i++){
            value = this.getItemAt(i).toString();
            label = new JLabel(value);
            if(maxWidth < label.getPreferredSize().width){
                maxWidth = label.getPreferredSize().width;
            }
        }

        /**
         * �����Ŀ������ȴ�����Ļ��ȣ���������Ŀ������Ϊ��Ļ��ȣ���������Ļ����޷���ʾ�����ʹ�ò���
         * added by yangyang 2011.09.14 17:35
         */
        Toolkit		tk 			=	Toolkit.getDefaultToolkit();
		Dimension 	screenSize 	=	tk.getScreenSize();
		int 	screenWidth		=	screenSize.width;
        if(maxWidth > screenWidth - 5){
        	maxWidth = screenWidth - 5;
        }
        
        dim.width = maxWidth + 5;
        
        if (nCount > 8) {
        	dim.width += 20;
        }
        
        return dim;
    }
}
