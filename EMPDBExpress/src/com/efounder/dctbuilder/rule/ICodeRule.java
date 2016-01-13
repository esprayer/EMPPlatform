package com.efounder.dctbuilder.rule;

import com.efounder.dctbuilder.view.DictTableView;
import com.efounder.dctbuilder.view.DictView;

/**
 * <p>Title: 编码规则插件</p>
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
public interface ICodeRule {

    String            getRuleName();
    java.util.List    getAllRule();
    Object            getUnitRule(String unit);
    Object            getDictRule();
    String            getCodeByRule();
    boolean           checkValid(String code);

//    void              setNodeViewerAdapter(NodeViewerAdapter viewer);
    void              setNodeViewerAdapter(DictTableView viewer);
    void              setDictView(DictView dictView);
    void              setCodeRuleType(String type);
//    NodeViewerAdapter getViewer();
    DictView          getDictView();
    String            getCodeRuleType();
    Object            doByRule();
    String            getMessage();

}
