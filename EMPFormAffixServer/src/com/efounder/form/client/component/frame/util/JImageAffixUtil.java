package com.efounder.form.client.component.frame.util;

import java.awt.Image;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.form.EFFormDataModel;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.meta.bizmodel.BIZMetaData;
import com.efounder.builder.meta.bizmodel.SYS_MDL_VAL;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;


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
public class JImageAffixUtil {
    public JImageAffixUtil() {
    }

    /**
     *  缩略图按钮的宽度
     */
    final public static int IMAGE_BUTTON_WIDTH  = 200;

    /**
     * 缩略图按钮的高度
     */
    final public static int IMAGE_BUTTON_HEIGHT = 154;

    public static Icon getImageIcon(String fileName,double width, double height) throws Exception{
        File file = new File(fileName);
        Image srcImage = javax.imageio.ImageIO.read(file); // 构造Image对象
        return getImageIcon(srcImage,width,height);
    }

    public static Icon getImageIcon(byte[] bytes,double width, double height) throws Exception{
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        InputStream inputStream = new ObjectInputStream(bis);
        Image srcImage = javax.imageio.ImageIO.read(inputStream); // 构造Image对象

        return getImageIcon(srcImage,width,height);
    }

    private static Icon getImageIcon(Image srcImage,double width, double height) throws Exception{
        int srcWidth = srcImage.getWidth(null); // 得到源图宽
        int srcHeight = srcImage.getHeight(null); // 得到源图长
        BufferedImage tagImg = new BufferedImage((int) (srcWidth * width), (int) (srcHeight * height), BufferedImage.TYPE_INT_RGB);
        tagImg.getGraphics().drawImage(srcImage, 0, 0, (int) (srcWidth * width), (int) (srcHeight * height), null); // 绘制缩小后的图
        return new ImageIcon(tagImg);
    }

    //////////////////////////////

    public static Icon getImageIcon(String fileName,int width, int height) throws Exception{
        File file = new File(fileName);
        Image srcImage = javax.imageio.ImageIO.read(file); // 构造Image对象
        return getImageIcon(srcImage,width,height);
    }

    public static Icon getImageIcon(byte[] bytes,int width, int height) throws Exception{
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Image srcImage = javax.imageio.ImageIO.read(bis); // 构造Image对象
        if(srcImage == null) return null;

//        TYPE_INT_RGB
        return getImageIcon(srcImage,width,height);
    }

    private static Icon getImageIcon1(Image srcImage,int width, int height) throws Exception{
        int srcWidth = srcImage.getWidth(null); // 得到源图宽
        int srcHeight = srcImage.getHeight(null); // 得到源图长
        if (srcWidth > 200 || srcHeight > 200) { // 如果文件本身较小则取消缩略图
//            BufferedImage tagImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            tagImg.getGraphics().drawImage(srcImage, 0, 0, width, height, null); // 绘制缩小后的图

            return new ImageIcon(srcImage.getScaledInstance(width, -1, Image.SCALE_DEFAULT));
//            return new ImageIcon(tagImg);
        }else{//
            return new ImageIcon(srcImage);
        }
    }

    public static Icon getImageIcon(Image srcImage,int width, int height) throws Exception{

//        //图标按钮的宽度 除以 图片的宽度 作为缩放比例.
//        double scale = (double)200/srcImage.getWidth(null);
//        //图标太大就会造成比例太小
//        if(scale < 0.1) scale = 0.1;
//
//
//        RenderingHints   renderingHints   =   new   RenderingHints(
//        RenderingHints.KEY_INTERPOLATION,   RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//
//        //BMP 或者 PNG的图像是直接返回
//        if(((BufferedImage)srcImage).getType() == 0 ){
//           return  new ImageIcon(srcImage) ;
//        }
//
//        AffineTransformOp   scaleOp   =   new   AffineTransformOp(AffineTransform.getScaleInstance(scale,   scale),   renderingHints);
//        BufferedImage   targetImage   =   new   BufferedImage(
//        (int)(srcImage.getWidth(null)   *   scale),
//        (int)(srcImage.getHeight(null)   *   scale),   ((BufferedImage)srcImage).getType());
//        //
//
//        scaleOp.filter((BufferedImage)srcImage,   targetImage);
//
//        return   new ImageIcon(targetImage) ;
        if(width > IMAGE_BUTTON_WIDTH){
            return new ImageIcon(srcImage.getScaledInstance(IMAGE_BUTTON_WIDTH, -1, Image.SCALE_DEFAULT));
        }else{
            return new ImageIcon(srcImage);
//            return new ImageIcon(srcImage.getScaledInstance(width, -1, Image.SCALE_DEFAULT));
        }

    }

    /**
     * 获取原图像的尺寸
     * @param obj Object
     * @return int[]
     */
    public static int[] getSrcImageWH(Object obj) throws Exception{
        Image srcImage = null;
        if(obj instanceof byte[]){
            ByteArrayInputStream bis = new ByteArrayInputStream((byte[])obj);
            srcImage = javax.imageio.ImageIO.read(bis);
        }else if(obj instanceof String){
            File file = new File((String)obj);
            srcImage = javax.imageio.ImageIO.read(file);
        }else if(obj instanceof Image){
            srcImage = (Image)obj;
        }
        if(srcImage == null) return null;

        return new int[]{srcImage.getWidth(null),srcImage.getHeight(null)};
    }


//    public static String[] getKeyParams(FormModel formModel){
//        EFFormDataModel formDataModel = formModel.getFormDataModel();
//        EFDataSet dateSet = formDataModel.getBillDataSet();
//        EFRowSet rowSet = dateSet.getRowSet(0);
//
//        BIZMetaData bizMetaData=formModel.getBIZMetaData();
////        String YWLX_COL = bizMetaData.getSYS_MDL_VAL(SYS_MODEL._BILL_KEYDCT_COL_,"F_YWLXBH");
//        String YWLX_COL = "";
//        String YWLX = rowSet.getString(dateSet.getTableName() + "."+YWLX_COL,null);
//        if(YWLX == null){
//            YWLX = rowSet.getString(YWLX_COL,null);
//        }
////        String GUID_COL = bizMetaData.getSYS_MDL_VAL(SYS_MODEL._BILL_GUID_COL_,SYS_MDL_VAL.BILL_GUID);
//        String GUID_COL = "";
//        String GUID = rowSet.getString(dateSet.getTableName() + "."+GUID_COL,null);
//        if(GUID == null){
//            GUID = rowSet.getString(GUID_COL,null);
//        }
//
//        return new String[]{YWLX,GUID};
//   }

   //BufferedImage转byte[]
    public static byte[] imageToByte(BufferedImage i) throws Exception {
      ByteArrayOutputStream bot = new ByteArrayOutputStream();
      ImageIO.write(i, "jpg", bot);

      byte[] b = null;
      b = bot.toByteArray();
      return b;
   }





}
