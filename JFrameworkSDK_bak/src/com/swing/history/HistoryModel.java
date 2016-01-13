package com.swing.history;

import java.io.*;
import java.util.*;

import javax.swing.*;

public class HistoryModel
        extends DefaultListModel implements MutableListModel {

    public HistoryModel(String name) {
        this.name = name;
    }

    private static final int maxItemCount = 10;

    private String name;
    private static Hashtable models;

    private static boolean modified;

    private static String SEPARATOR = ",";
    private static String FILE_NAME = "history.properties";
    private static String FILE_PATH =
            System.getProperty("user.home") + File.separator +
            "pansoft" + File.separator +
            "localcfg" + File.separator;

    private static Properties _property;
    private static File history;
    static {
        try {
            if (models == null) {
                models = new Hashtable();
            }
            FileInputStream fis = null;
            history = new File(FILE_PATH + FILE_NAME);
            //不存在则创建
            if (!history.exists()) {
                if (!new File(FILE_PATH).exists()) {
                    new File(FILE_PATH).mkdirs();
                }
                history.createNewFile();
            }
            fis = new FileInputStream(history);
            _property = new Properties();
            _property.load(fis);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds an item to the end of this history list, trimming the list
     * to the maximum number of items if necessary.
     * When the item count > the maxItemCount,delete item from begin.
     * @param text The item
     */
    public void addItem(String text) {
        if (text == null || text.length() == 0)
            return;
        //if already exist,delete it first
        int index = indexOf(text);
        if (index != -1)
            removeElementAt(index);
        //always insert an item into the top
        insertElementAt(text, 0);
        //if item counts exceed the max,delete from begin
        while (getSize() > maxItemCount)
            removeElementAt(getSize() - 1);
    }

    /**
     * Insert an item to the model at the appointed index.If the item is
     * a null String, then discard it.
     * @param obj Object The item
     * @param index int The index
     */
    public void insertElementAt(Object obj, int index) {
        modified = true;
        if (obj == null || obj.toString().trim().length() == 0)
            return;
        super.insertElementAt(obj, index);
    }

    /**
     * Returns an item which at the appointed index from the
     * history list.
     * @param index The index
     */
    public String getItem(int index) {
        return (String) elementAt(index);
    }

    /**
     * Remove an item from the history list.
     * @param obj Object
     * @return boolean
     */
    public boolean removeElement(Object obj) {
        modified = true;
        return super.removeElement(obj);
    }

    /**
     * Remove all items from the history list.
     */
    public void removeAllElements() {
        modified = true;
        super.removeAllElements();
    }

    /**
     * Returns the name of this history list. This can be passed
     * to the HistoryTextField constructor.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a named model. If the specified model does not
     * already exist, it will be created.
     * @param name The model name
     */
    public static HistoryModel getModel(String name) {
        HistoryModel model = (HistoryModel) models.get(name);
        if (model == null) {
            model = new HistoryModel(name);
            models.put(name, model);
        }
        return model;
    }

    /**
     * 加载一个组的历史记录。一个可见的窗口内的所有
     */
    public static void loadHistory(String grpPrefix) {
        if (_property == null)
            return;
        if (grpPrefix == null)
            grpPrefix = "";
        HistoryModel currentModel = null;
        try {
            Enumeration enumration = _property.keys();
            String keyValue;
            while (enumration.hasMoreElements()) {
                keyValue = enumration.nextElement().toString();
                //只加载同一个组的文本Model对象
                if (keyValue.indexOf(grpPrefix) >= 0) {
                    currentModel = new HistoryModel(keyValue);
                    //如果已经加载了则不再load
                    if (models.containsKey(keyValue)) {
                        continue;
                    }
                    //加载该Model的数据
                    processLoadModelData(currentModel);
                    //保存起来
                    models.put(currentModel.getName(), currentModel);
                    //让垃圾收集器也有活干
                    currentModel = null;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 加载该Model的数据。
     *
     * @param currentModel HistoryModel
     */
    private static void processLoadModelData(HistoryModel currentModel) {
        if (currentModel == null)
            return;
        String keyValue;
        String[] valueArray;
        try {
            keyValue = _property.getProperty(currentModel.getName());
            valueArray = keyValue.split(SEPARATOR);
            for (int i = valueArray.length; i > 0; i--) {
                currentModel.addItem(valueArray[i - 1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Save item(s) to the history list when the history item list
     * modified.If there is nothing modified, it's unnecessary to save.
     * the method execute while exiting the programme.
     */
    public static void saveHistory() {
        if (!modified || _property == null) {
            return;
        }

        FileOutputStream fos= null;
        try {
            Enumeration modelEnum = models.elements();
            HistoryModel model = null;
            while (modelEnum.hasMoreElements()) {
                model = (HistoryModel) modelEnum.nextElement();
                //if there is nothing,return
                if (model.getSize() == 0)
                    continue;

                //串联值
                String valueItem = "";
                for (int i = 0; i < model.getSize(); i++) {
                    valueItem += model.getItem(i) + SEPARATOR;
                }
                _property.setProperty(model.getName(), valueItem);

                //make sure to avoid the JVM leak
                valueItem = null;
                model = null;
            }
            fos = new FileOutputStream(new File(FILE_PATH + FILE_NAME));
            _property.store(fos, null);
            fos.flush();
            //after saving, the modified-identifier is false
            modified = false;
        }
        catch (IOException io) {
            io.printStackTrace();
        }
        finally {
            try {
                if (fos != null)
                    fos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
