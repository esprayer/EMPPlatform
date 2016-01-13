package com.efounder.form.client.component.action;

import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.actions.ContextActionProvider;
import com.efounder.action.ActiveObjectAction;
import javax.swing.Action;
import com.efounder.action.ActionGroup;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class ImagePreviewActionProvider implements ContextActionProvider {
  ImagePreviewActionObject imageViewCompActionObject = new ImagePreviewActionObject();
  public ImagePreviewActionProvider() {
    super();
  }

  public Action getContextAction(Object frame, Object[] objectArray) {
    ActionGroup ag = new ActionGroup();

    Action action = new ActiveObjectAction(imageViewCompActionObject, frame,
                                    "imageSaveAs", "另存为...", '0', "图像另存",
                                    ExplorerIcons.getExplorerIcon("jprofiler/action_saveSnapshot_16.png"));
    ag.add(action);



    action = new ActiveObjectAction(imageViewCompActionObject, frame,
                                    "rotateImage1", "逆时针", '0',"逆时针",
                                    ExplorerIcons.getExplorerIcon("jprofiler/undo_16.png"));
    ag.add(action);
//
    action = new ActiveObjectAction(imageViewCompActionObject, frame,
                                    "rotateImage2", "顺时针", '0',"顺时针",
                                    ExplorerIcons.getExplorerIcon("jprofiler/redo_16.png"));

    ag.add(action);


    action = new ActiveObjectAction(imageViewCompActionObject, frame,
                                    "autoFitImage", "自适应", '0',"自适应",
                                    ExplorerIcons.getExplorerIcon("J2EEApp.gif"));
    ag.add(action);
//
    action = new ActiveObjectAction(imageViewCompActionObject, frame,
                                    "resetImage", "原图", '0',"原图",
                                    ExplorerIcons.getExplorerIcon("DataSetView_Color16.gif"));

    ag.add(action);



    action = new ActiveObjectAction(imageViewCompActionObject, frame,
                                    "previousImage", "上一个", '0',"上一个",
                                    ExplorerIcons.getExplorerIcon("jprofiler/browser_backward_16.png"));
    ag.add(action);

    action = new ActiveObjectAction(imageViewCompActionObject, frame,
                                    "nextImage", "下一个", '0',"下一个",
                                    ExplorerIcons.getExplorerIcon("jprofiler/browser_forward_16.png"));
    ag.add(action);

    action = new ActiveObjectAction(imageViewCompActionObject, frame,
                                    "imageZoomIn", "放大", '0',"放大",
                                    ExplorerIcons.getExplorerIcon(
                                      "oicons/zoomin.png"));
    ag.add(action);

    action = new ActiveObjectAction(imageViewCompActionObject, frame,
                                    "imageZoomOut", "缩小", '0', "缩小",
                                    ExplorerIcons.getExplorerIcon("oicons/zoomout.png"));
    ag.add(action);

    return ag;

  }
}
