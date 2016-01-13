package com.efounder.dbc.swing.treetable;

import com.efounder.dbc.swing.tree.DataSetTreeNode;
import java.util.Comparator;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DataSetTreeNodeCompare implements  Comparator{
         boolean beq;
         String value;
         public DataSetTreeNodeCompare(boolean beq){
             this.beq=beq;
         }
        public int compare(Object o1, Object o2) {
            DataSetTreeNode fnode=(DataSetTreeNode)o1;
            String value=(String)o2;
            if (beq) {
                if (fnode.getDctBH().equals(value) ||
                    fnode.getDctMc().equals(value)) {
                    return 0;
                }
            } else {
                if (fnode.getDctBH().indexOf(value) != -1 ||
                    fnode.getDctMc().indexOf(value) != -1) {
                    return 0;
                }
            }
            return 1;
        }

        public boolean equals(Object obj) {
            return false;
        }

    }
