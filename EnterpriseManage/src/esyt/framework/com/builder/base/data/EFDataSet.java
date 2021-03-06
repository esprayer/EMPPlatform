package esyt.framework.com.builder.base.data;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class EFDataSet extends ArrayList {

	/**
	   *
	   */
	private Vector<ESPRowSet> rowSetList = null;

	/**
	   *
	   */
	protected transient int currentRowIndex = -1;

	/**
	   *
	   */
	private String tableName = null;

	/**
	   *
	   */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 
	 * @return
	 */
	public static EFDataSet getInstance() {
		EFDataSet dataSet = new EFDataSet();
		return dataSet;
	}
	
	/**
	 * 
	 * @param tableName
	 * @return
	 */
	public static EFDataSet getInstance(String tableName) {
		EFDataSet dataSet = new EFDataSet();
		dataSet.tableName = tableName;
		return dataSet;
	}
	
	/**
	 * 
	 * @param tableName
	 * @return
	 */
	public static EFDataSet getInstance(String tableName,String[] primeKey) {
		EFDataSet dataSet = new EFDataSet();
		dataSet.tableName = tableName;
		dataSet.setPrimeKey(primeKey);
		return dataSet;
	}

	/**
	 * 
	 * @param tableName
	 * @return
	 */
	public static EFDataSet getInstance(String[] primeKey) {
		EFDataSet dataSet = new EFDataSet();
		dataSet.setPrimeKey(primeKey);
		return dataSet;
	}
	
	/**
	   *
	   */
	protected EFRowSet parentRowSet = null;

	/**
	 * 
	 * @param rowSet
	 *            EFRowSet
	 */
	public void setParentRowSet(EFRowSet rowSet) {
		this.parentRowSet = rowSet;
	}

	/**
	 * 
	 * @return EFRowSet
	 */
	public EFRowSet getParentRowSet() {
		return this.parentRowSet;
	}

	/**
	 * 
	 * @param rowSet
	 */
	public void insertRowSet(ESPRowSet rowSet) {
		if (rowSetList == null) {
			rowSetList = new Vector<ESPRowSet>();
		}
		int index = currentRowIndex == -1 ? rowSetList.size() + 1 : currentRowIndex + 1;
		insertRowSet(index, rowSet);
	}

	/**
	 * 
	 * @param rowSet
	 */
	public EFRowSet removeRowSet(int rowIndex) {
		EFRowSet rowset = null;
		if (rowSetList == null)
			return null;
		if (rowIndex >= rowSetList.size()) {
			return null;
		} else {
			rowset = (EFRowSet) rowSetList.get(rowIndex);
			rowSetList.remove(rowIndex);
			return rowset;
		}
	}
	
	/**
	 * 
	 * @param index
	 *            int
	 * @param rowSet
	 *            ESPRowSet
	 */
	public void insertRowSet(int index, ESPRowSet rowSet) {
		insertRowSet(index, rowSet, false);
	}

	/**
	 * 
	 * @param rowSet
	 *            ESPRowSet
	 * @param unique
	 *            boolean
	 */
	public void insertRowSet(ESPRowSet rowSet, boolean unique) {
		if (rowSetList == null) {
			rowSetList = new Vector<ESPRowSet>();
		}
		int index = currentRowIndex == -1 ? rowSetList.size() + 1
				: currentRowIndex + 1;
		insertRowSet(index, rowSet, unique);
	}

	/**
	 * 
	 * @param index
	 *            int
	 * @param rowSet
	 *            ESPRowSet
	 * @param unique
	 *            boolean
	 */
	public void insertRowSet(int index, ESPRowSet rowSet, boolean unique) {
		if (rowSetList == null) rowSetList = new Vector<ESPRowSet> ();
	    // 主键唯一
	    if (unique && containsKey(getPrimeKeyValue(rowSet)))
	      return;
	    // 插入前面一行
	    if (index <= rowSetList.size()) {
	      if (index > 0) index--;
	      rowSetList.insertElementAt(rowSet, index);
	    } else {
	      rowSetList.add(rowSet);
	    }
	    // 创建主键索引
	    buildPrimeKeyIndex(rowSet);
	    // 增加到
	    rowSet.insertDataSetManager(this);
	}

	/**
	 * @return the rowSetList
	 */
	public Vector<ESPRowSet> getRowSetList() {
		return rowSetList;
	}

	/**
	 * 
	 * @return int
	 */
	public int getRowCount() {
		if (rowSetList == null)
			return 0;
		return rowSetList.size();
	}

	/**
	 * @param rowSetList
	 *            the rowSetList to set
	 */
	public void setRowSetList(Vector<ESPRowSet> rowSetList) {
		this.rowSetList = rowSetList;
	}

	/**
	 * 
	 * @param rowSet
	 *            ESPRowSet
	 */
	public void addRowSet(ESPRowSet rowSet) {
		int index = 0;
		if (rowSetList != null)
			index = rowSetList.size() + 1;
		insertRowSet(index, rowSet);
	}

	/**
	 * 
	 * @param rowIndex
	 *            int
	 * @return EFRowSet
	 */
	public EFRowSet getRowSet(int rowIndex) {
		if (rowSetList == null || rowSetList.size() == 0)
			return null;
		if (rowIndex < 0 || rowIndex >= rowSetList.size())
			return null;
		return (EFRowSet) rowSetList.get(rowIndex);
	}

	/**
	 * 
	 * @param keyValue String
	 * @return RowSet
	 */
	public ESPRowSet getRowSet(String keyValue) {
		String[] keys = keyValue.split(",");
		if (primeKeyRowSetMap == null) return null;
		return getRowSet(keys);
	}

	/**
	 * 
	 * @param keyValue
	 *            String[]
	 * @return RowSet
	 */
	public ESPRowSet getRowSet(String[] keyValue) {
		if (primeKeyRowSetMap == null)
			return null;
		return primeKeyRowSetMap.get(getPrimeKeyValue(keyValue));
	}

	/**
	 * 
	 * @param keyValue String[]
	 * @return String
	 */
	protected String getPrimeKeyValue(String[] keyValue) {
		String value = "";
		for (int i = 0; i < keyValue.length; i++) {
			value += (((i == 0) ? "" : "-") + (keyValue[i] == null ? "" : keyValue[i]));
		}
		return value;
	}

	public int getCurrentRowIndex() {
		return currentRowIndex;
	}

	/**
	 * 
	 * @param rowIndex
	 *            int
	 * @return int
	 */
	public int goToRowNoWithCursorEvent(int rowIndex) {
		if (rowSetList == null || rowSetList.size() == 0)
			return -1;
		if (rowIndex < 0 || rowIndex >= rowSetList.size())
			return -1;
		int oldIndex = 0;
		if (currentRowIndex != rowIndex) {
			oldIndex = currentRowIndex;
			currentRowIndex = rowIndex;
		}
		return currentRowIndex;
	}

	/**
	 * 
	 * @return int
	 */
	public final boolean atFirst() {
		return (currentRowIndex == 0);
	}

	/**
	 * 
	 * @return int
	 */
	public final boolean atLast() {
		if (rowSetList == null || rowSetList.size() == 0)
			return true;
		return (currentRowIndex == (rowSetList.size() - 1));
	}

	/**
	 * 
	 * @return int
	 */
	public final int first() {
		if (rowSetList == null || rowSetList.size() == 0)
			return -1;
		// 如果数据为空，则返回-1
		return this.goToRow(0);
	}

	/**
	 * 
	 * @return int
	 */
	public final int prior() {
		int rowIndex = currentRowIndex - 1;
		if (rowIndex >= 0)
			return this.goToRow(rowIndex);
		else
			return -1;
	}

	/**
	 * 
	 * @return int
	 */
	public final int next() {
		if (rowSetList == null || rowSetList.size() == 0)
			return -1;
		int rowCount = rowSetList.size();
		int rowIndex = currentRowIndex + 1;
		if (rowIndex < rowCount)
			return this.goToRow(rowIndex);
		else
			return -1;
	}

	/**
	 * 
	 * @return int
	 */
	public final int last() {
		if (rowSetList == null || rowSetList.size() == 0)
			return -1;
		int rowIndex = rowSetList.size() - 1;
		return this.goToRow(rowIndex);
	}

	/**
	 * 
	 * @param rowIndex
	 *            int
	 * @return int
	 */
	public int goToRow(int rowIndex) {
		if (rowSetList == null || rowSetList.size() == 0)
			return -1;
		if (rowIndex < 0 || rowIndex >= rowSetList.size())
			return -1;
		int oldIndex = 0;
		if (currentRowIndex != rowIndex) {
			oldIndex = currentRowIndex;
			currentRowIndex = rowIndex;
		}
		return currentRowIndex;
	}

	protected transient java.util.List childRowSet = null;

	protected void insertChildRowSet(EFRowSet rowSet) {
		if (childRowSet == null) childRowSet = new java.util.ArrayList();
		if (childRowSet.indexOf(rowSet) == -1) childRowSet.add(rowSet);
	}

	protected EFRowSet removeChildRowSet(EFRowSet rowSet) {
		if (childRowSet == null) return null;
		if (childRowSet.remove(rowSet)) return rowSet;
		return null;
	}
	
	/**
	   *
	   */
	protected String[] primeKey = null;

	/**
	 * 多主键的情况下，记录多个主键之间的关系
	 */
	protected Map<String, Map> primeKeyKeyMap = null;

	public Map<String, Map> getPrimeKeyKeyMap() {
		return primeKeyKeyMap;
	}

	public void setPrimeKeyKeyMap(Map<String, Map> primeKeyKeyMap) {
		this.primeKeyKeyMap = primeKeyKeyMap;
	}

	/**
	 * 
	 * @return String[]
	 */
	public String[] getPrimeKey() {
		return primeKey;
	}

	/**
	 * 
	 * @param primeKey
	 *            String
	 */
	public void setPrimeKey(String[] primeKey) {
		this.primeKey = primeKey;
	}

	/**
	   *
	   */
	protected Map<String, ESPRowSet> primeKeyRowSetMap = null;

	public void setPrimeKeyRowSetMap(Map<String, ESPRowSet> primeKeyRowSetMap) {
		this.primeKeyRowSetMap = primeKeyRowSetMap;
	}

	/**
	 * 
	 * @return Map
	 */
	public Map<String, ESPRowSet> getPrimeKeyRowSetMap() {
		return primeKeyRowSetMap;
	}
	
	/**
	 * 
	 * @param rowSet
	 *            ESPRowSet
	 */
	public void buildPrimeKeyIndex(ESPRowSet rowSet) {
		if (primeKey == null || rowSet == null) return;
		if (primeKeyRowSetMap == null) primeKeyRowSetMap = new ConcurrentHashMap<String, ESPRowSet>();
		String primeKeyValue = getPrimeKeyValue(rowSet);
		if (primeKeyValue != null)
			primeKeyRowSetMap.put(primeKeyValue, rowSet);
		// KEYKEYKEY
		buildPrimeKeyKeyIndex(rowSet);
	}
	
	/**
	 * 
	 * @param rowSet
	 *            RowSet
	 * @return String
	 */
	public String getPrimeKeyValue(ESPRowSet rowSet) {
		if (primeKey == null || primeKey.length == 0) return null;
		String value = "";
		for (int i = 0; i < primeKey.length; i++) {
			value += (((i == 0) ? "" : "-") + rowSet.getObject(primeKey[i], ""));
		}
		return value;
	}
	
	/**
	   *
	   */
	public void buildPrimeKeyKeyIndex(ESPRowSet rowSet) {
		String[] primeKey = getPrimeKey();
		String key = null, val = null;
		java.util.Map<String, java.util.List> map = null;
		java.util.List<String> list = null;
		for (int i = 0; primeKey != null && i < primeKey.length; i++) {
			map = getPrimeKeyKeyMap(primeKey[i]);
			key = rowSet.getString(primeKey[i], "");
			val = getPrimeKeyValue(rowSet);
			if (map == null || val == null)
				continue;
			list = map.get(key);
			if (list == null) {
				list = new ArrayList<String>();
				map.put(key, list);
			}
			if (!list.contains(val))
				list.add(val);
		}
	}

	  /**
	   *
	   * @param primeKey String
	   * @return Map
	   */
	  public java.util.Map getPrimeKeyKeyMap(String primeKey) {
	    if (primeKeyKeyMap == null) primeKeyKeyMap = new ConcurrentHashMap<String,Map>();
	    if (primeKeyKeyMap.get(primeKey) == null) {
//	      HashMap<String, java.util.List<String>> map = new HashMap<String, java.util.List<String>> ();
	      java.util.Map<String, java.util.List<String>> map = new ConcurrentHashMap<String, java.util.List<String>> ();
	      primeKeyKeyMap.put(primeKey, map);
	    }
	    return primeKeyKeyMap.get(primeKey);
	  }
	  
	  /**
	   *
	   * @param key String
	   * @return boolean
	   */
	  public boolean containsKey(String key) {
	    if (primeKey == null || key == null || primeKeyRowSetMap == null) return false;
	    return primeKeyRowSetMap.containsKey(key);
	  }
}
