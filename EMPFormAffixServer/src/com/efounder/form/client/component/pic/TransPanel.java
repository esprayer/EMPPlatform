package com.efounder.form.client.component.pic;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

import javax.swing.*;

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

//显示图像的面板
public class TransPanel extends JPanel implements MouseMotionListener, MouseListener {
    Image image;
    public BufferedImage bufImage; //用于显示的缓冲区图像
    public BufferedImage originalBufImage; //原始缓冲区图像
    public Graphics2D bufImageG; //缓冲区图像的图形环境
    public double scaleX = 1.0; //图像水平方向的缩放因子
    public double scaleY = 1.0; //图像竖直方向的缩放因子
    private double currentAngle; //当前角度
    private Dimension theSize = new Dimension(800, 600);
    double w = theSize.getWidth();
    double h = theSize.getHeight();
    int flipX = 1;
    final public static int DEF_WIDTH = 680;
    final public static int DEF_HEIGHT = 500;

    //正常状态:true , 局部放大查看状态:false
    boolean isGeneralStaus = true;

    public TransPanel() {
        addMouseMotionListener(this);
        addMouseListener(this);
        this.setBackground(Color.GRAY);
    }




    //截入图像
    public void loadImage(String fileName) {
        image = this.getToolkit().getImage(fileName); //取得图像
        MediaTracker mt = new MediaTracker(this); //实例化媒体加载器
        mt.addImage(image, 0); //增加图像到加载器中
        try {
            mt.waitForAll(); //等待图片加载
        } catch (Exception ex) {
            ex.printStackTrace(); //输出出错信息
        }
        initImage();
    }

    public void loadPicRes(Object obj){
    	if(obj == null) {
    		image = null;
    		repaint(); //重绘组件
    		return;
    	}
        if(obj instanceof String){
            loadPicRes((String)obj);
        }else if(obj instanceof byte[]){
            loadPicRes((byte[])obj);
        }else if(obj instanceof Image){
            loadPicRes((Image)obj);
        }
    }

    /**
     * 根据文件路径生成图片
     * @param imageFile String
     */
    public void loadPicRes(String imageFile) {
        File picFile = new File(imageFile);
        try {
            image = javax.imageio.ImageIO.read(picFile);
            initImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据字节生成图片
     * @param data byte[]
     */
    public void loadPicRes(byte[] data) {
        try {
            InputStream inStream = new ByteArrayInputStream(data);
            image = javax.imageio.ImageIO.read(inStream);
            if(image == null) return;

            initImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 直接传递图像
     * @param image Image
     */
    public void loadPicRes(Image image){
        this.image  = image;
        initImage();
    }

    private void initImage() {
//        originalBufImage = new BufferedImage(image.getWidth(this),
//                                             image.getHeight(this),
//                                             BufferedImage.TYPE_INT_ARGB); //创建原始缓冲区图像
        originalBufImage = new BufferedImage(image.getWidth(this),
                                             image.getHeight(this),
                                             BufferedImage.TYPE_3BYTE_BGR); //创建原始缓冲区图像
        //TYPE_3BYTE_BGR:JPG 格式的图像


        bufImage = originalBufImage;

        //初始图像先不放中央
//        setInitPosValue();

        //初始图片的时候按照自适应模式
//        autoFitAction();
        bufImageG = bufImage.createGraphics(); //创建bufImage的图形环境
        bufImageG.drawImage(image, 0, 0, this); //传输源图像数据到缓冲区图像中
        repaint(); //重绘组件
    }

    public void savaAs(){
        JPicAttachFunction.SaveFile(originalBufImage);
    }

    public void flipX(){
        flipX = -1 * flipX;
        repaint(); //重绘组件
    }

    /**
     * 放大Action
     */
    public void zoomInAction(){
        this.scaleX *= 1.10;//1.05 1.25; //图像x轴方向放大因子
        this.scaleY *= 1.10;//1.05 1.25; //图像y轴方向放大因子
        this.applyFilter(); //过滤图像
    }

    /**
     * 缩小Action
     */
    public void zoomOutAction(){
        this.scaleX *= 0.90;//0.95; //图像x轴方向放大因子
        this.scaleY *= 0.90;//0.95; //图像y轴方向放大因子
        this.applyFilter(); //过滤图像
    }
    /**
     * 复原Action
     */
    public void resetAction(){
        this.currentAngle = 0;
        this.scaleX = 1.0; //图像x轴方向放大因子还原为1.0
        this.scaleY = 1.0; //图像y轴方向放大因子还原为1.0
        this.applyFilter(); //过滤图像
    }



    /**
     * 自适应Action
     */
    public void autoFitAction(){
//        this.revalidate();
        //自适应就是根据图片的大小使用当前屏幕的大小,应该用比例换算
        double winWidth = this.getWidth(),winHeight = this.getHeight();
        if(winWidth == 0) winWidth   = DEF_WIDTH;
        if(winHeight == 0) winHeight = DEF_HEIGHT;

        //应该用图像的大小来换算
        double xRate =  1 ,yRate = 1;
        double imageWidth = originalBufImage.getWidth(),imageHeight = originalBufImage.getHeight();
        if(imageWidth > winWidth && imageHeight > winHeight){
            xRate = (double)winWidth/imageWidth;
            yRate = (double)winHeight/imageHeight;
            if(xRate > yRate){
                xRate = yRate;
            }else{
                yRate = xRate;
            }
        }
        else if(imageWidth > winWidth && imageHeight < winHeight){
            xRate = (double)winWidth/imageWidth;//既然超出屏幕大小就应该缩小图像
            yRate = xRate;
        }
        else if(imageWidth < winWidth && imageHeight > winHeight){
            yRate = (double)winHeight/imageHeight;
            xRate = yRate;
        }

        this.scaleX = xRate; //图像x轴方向放大因子还原为1.0
        this.scaleY = yRate; //图像y轴方向放大因子还原为1.0
        this.applyFilter(); //过滤图像
    }

    //过滤图像
    public void applyFilter() {
        if (bufImage == null) {
            return; //如果bufImage为空则直接返回
        }


        BufferedImage filteredBufImage = new BufferedImage(
                (int) (image.getWidth(this) * scaleX),
                (int) (image.getHeight(this) * scaleY),
                BufferedImage.TYPE_INT_ARGB); //过滤后的图像
        AffineTransform transform = new AffineTransform(); //仿射变换对象
        transform.setToScale(scaleX, scaleY); //设置仿射变换的比例因子
//        transform.scale(scaleX, scaleY);//经过测试这句话与上面是一样的

        AffineTransformOp imageOp = new AffineTransformOp(transform, null); //创建仿射变换操作对象
        imageOp.filter(originalBufImage, filteredBufImage); //过滤图像，目标图像在filteredBufImage
        bufImage = filteredBufImage; //让用于显示的缓冲区图像指向过滤后的图像

        //放大缩小的时候把图片放在屏幕的中心位置
        setInitPosValue();

    }

    public void RotateImage(boolean isRight) {
        //旋转的时候把图片放在屏幕的中心位置
        setInitPosValue();

        if (isRight) {
            currentAngle += 90.0;//30.0
        } else {
            currentAngle -= 90.0;//30.0
        }

        if (currentAngle >= 360.0) {
            currentAngle = 0;
        }

        repaint();
    }

    //重载容器的paintComponent()方法
    public void paint(Graphics g) {
        super.paintComponent(g);
        if (bufImage != null) {
            Graphics2D g2 = (Graphics2D) g;

            AffineTransform origXform = g2.getTransform();
            AffineTransform newXform = (AffineTransform) (origXform.clone());
            int xRot = this.getWidth() / 2;
            int yRot = this.getHeight() / 2;

            newXform.rotate(Math.toRadians(currentAngle), xRot, yRot);

            g2.setTransform(newXform);//图像切换到新仿射变换对象

            if (flipX == -1) {
                newXform.scale( -1, 1); // 第一个值表示水平，-1表示等宽水平翻转，Math.abs(m_nFlipXScale)的值越大，出来的图片就越宽
            }else{
                newXform.scale( 1, 1);
            }


//            i′=cosθi+sinθj，j′=cos（+θ）i+sin（+θ）   j=-sinθi+cosθj
//            int tmpIx = (int)Math.cos(currentAngle)*ixImage+(int)Math.sin(currentAngle)*iyImage;
//            int tmpIy = (int)Math.cos(currentAngle)*tmpIx+(int)Math.sin(currentAngle)*iyImage;


            g2.drawImage(bufImage, ixImage, iyImage, this); //绘制图片
//             g2.drawImage(bufImage, tmpIx, tmpIy, this); //绘制图片
            //刚加上去
            g2.dispose();

            //出现滚动条:出现滚动条在窗口中展示图像不好:因为图像缩放的时候,出现滚动条的时候,图像位置出现问题.
//            this.setPreferredSize(new Dimension(bufImage.getWidth(), bufImage.getHeight()));
            this.updateUI();

        }
    }

    protected void setInitPosValue(){
        int width  = getWidth();
        int height = getHeight();

        if(width == 0) width = DEF_WIDTH;
        if(height == 0) height = DEF_HEIGHT;

        ixImage= (width  - bufImage.getWidth())  / 2;
        iyImage= (height - bufImage.getHeight()) / 2;
    }


    boolean inThePic = false;

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (image != null && isGeneralStaus) {
            startPoint = e.getPoint();


//            startPoint=SwingUtilities.convertPoint(this,e.getPoint(),this.getParent());
            if (inPicBounds(e.getX(), e.getY())) {
               inThePic = true;
            }else{
                inThePic = false;
            }
        }
    }

    private boolean inPicBounds(int px, int py) {
        if(bufImage == null) return false;

        if (px >= ixImage && px <= ixImage + bufImage.getWidth() &&
            py >= iyImage && py <= iyImage + bufImage.getHeight()) {
            return true;
        }
        else {
            return false;
        }
    }


    public boolean checkPoint(int px, int py) {
        if (px < 0 || py < 0) {
            return false;
        }
        if (px > getWidth() || py > getHeight()) {
            return false;
        }
        return true;
    }


    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    private Point startPoint = null;
    private Point endPoint = null;

    private int ixImage = getX();
    private int iyImage = getY();

    public void mouseDragged(MouseEvent e) {
        if(isGeneralStaus){
          endPoint = e.getPoint();
          if (inThePic && checkPoint(e.getX(), e.getY())) { //判断选择图片范围内的时候可以拖拽
              double leftAngle = (360 + currentAngle)%360;
              if(leftAngle == 90){
                  ixImage = ixImage + (endPoint.y - startPoint.y);
                  iyImage = iyImage + (endPoint.x - startPoint.x)*(-1);
              }else if(leftAngle == 180){
                  ixImage = ixImage + (endPoint.x - startPoint.x)*(-1);
                  iyImage = iyImage + (endPoint.y - startPoint.y)*(-1);
              }else if(leftAngle == 270){
                  ixImage = ixImage + (endPoint.y - startPoint.y)*(-1);
                  iyImage = iyImage + (endPoint.x - startPoint.x);
              }else{
                  ixImage = ixImage + (endPoint.x - startPoint.x);
                  iyImage = iyImage + (endPoint.y - startPoint.y);
              }

            repaint();
            startPoint = endPoint;
          }
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public BufferedImage getBufImage() {
        return bufImage;
    }

  public boolean isIsGeneralStaus() {
    return isGeneralStaus;
  }

  public void setIsGeneralStaus(boolean isGeneralStaus) {
    this.isGeneralStaus = isGeneralStaus;
  }

    public void setBufImage(BufferedImage bufImage) {
        this.bufImage = bufImage;
    }


    /**
     * 图像缩放
       对于一个已经存在的Image对象，得到它的一个缩放的Image对象可以使用Image的getScaledInstance方法：

      Image scaledImage=sourceImage. getScaledInstance(100,100, Image.SCALE_DEFAULT);//得到一个100X100的图像

      Image doubledImage=sourceImage. getScaledInstance(sourceImage.getWidth(this)*2,sourceImage.getHeight(this)*2, Image.SCALE_DEFAULT);//得到一个放大两倍的图像,这个程序一般在一个swing的组件中使用，而类Jcomponent实现了图像观察者接口ImageObserver，所有可以使用this。

     */
    }
