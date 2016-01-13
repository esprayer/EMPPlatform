package com.efounder.bz.dbform.datamodel.footer;

import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.datamodel.column.*;
import com.efounder.bz.dbform.event.FormFunctionObject;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ColumnFooterGroup extends ColumnGroup implements DataComponent, DataModelComponent {
    /**
     *
     */
    private int startRow;
    /**
     *
     */

    private int endRow;
    /**
     *
     */
    private int startCol;
    /**
     *
     */
    private int endCol;
    /**
     *
     */
    public ColumnFooterGroup() {
    }

    /**
     *
     * @param r1 int
     * @param r2 int
     * @param c1 int
     * @param c2 int
     */
    public ColumnFooterGroup(int r1, int r2, int c1, int c2) {
        this.startRow = r1;
        this.endRow = r2;
        this.startCol = c1;
        this.endCol = c2;
    }

    /**
     *
     * @param startRow int
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     *
     * @param endRow int
     */
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    /**
     *
     * @param startCol int
     */
    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    /**
     *
     * @param endCol int
     */
    public void setEndCol(int endCol) {
        this.endCol = endCol;
    }

    /**
     *
     * @return int
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     *
     * @return int
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     *
     * @return int
     */
    public int getStartCol() {
        return startCol;
    }

    /**
     *
     * @return int
     */
    public int getEndCol() {
        return endCol;
    }

	@Override
	public boolean canAddChild(DataComponent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FormFunctionObject getCustomFunction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataComponent getDataComponent(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataComponent getDataComponent(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataContainer getDataContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataComponent getParentDataComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertDataComponent(DataComponent arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeDataComponent(DataComponent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCustomFunction(FormFunctionObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDataContainer(DataContainer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParentDataComponent(DataComponent arg0) {
		// TODO Auto-generated method stub
		
	}

}
