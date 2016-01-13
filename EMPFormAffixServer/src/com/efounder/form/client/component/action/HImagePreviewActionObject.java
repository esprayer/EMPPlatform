package com.efounder.form.client.component.action;

import javax.swing.Action;
import java.awt.event.ActionEvent;

import com.efounder.form.client.component.frame.HPreViewImagePanel;
import com.efounder.form.client.component.frame.HPreViewImagePanel;

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

public class HImagePreviewActionObject {
  public HImagePreviewActionObject() {
    super();
  }


  public void imageSaveAs(Object actionObject, HPreViewImagePanel preView,
                                  Action action, ActionEvent actionevent) {
      preView.getPreView().savaAs();
  }

  public void rotateImage1(Object actionObject, HPreViewImagePanel preView,
                                  Action action, ActionEvent actionevent) {
      preView.getPreView().RotateImage(false);

  }

  public void rotateImage2(Object actionObject, HPreViewImagePanel preView,
                                  Action action, ActionEvent actionevent) {
      preView.getPreView().RotateImage(true);
  }

  public void resetImage(Object actionObject, HPreViewImagePanel preView,
                                  Action action, ActionEvent actionevent) {
      preView.getPreView().resetAction();
  }

  public void autoFitImage(Object actionObject, HPreViewImagePanel preView,
                                  Action action, ActionEvent actionevent) {
      preView.getPreView().autoFitAction();
  }



  public void previousImage(Object actionObject, HPreViewImagePanel preView,
                                  Action action, ActionEvent actionevent) {
      preView.previousImageAction();
  }

  public void nextImage(Object actionObject, HPreViewImagePanel preView,
                                  Action action, ActionEvent actionevent) {
      preView.nextImageAction();
  }

  public void imageZoomIn(Object actionObject, HPreViewImagePanel preView,
                                  Action action, ActionEvent actionevent) {
      preView.getPreView().zoomInAction();
  }

  public void imageZoomOut(Object actionObject, HPreViewImagePanel preView,
                                  Action action, ActionEvent actionevent) {
      preView.getPreView().zoomOutAction();
  }

}
