package com.efounder.object;

import java.lang.reflect.*;
/**
 * Title:        Java Framework
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Pansoft Ltd.
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//鎻忚堪: 璇ョ被鏄湰绯荤粺涓墍鏈変笉鍙绫荤殑鐖剁被,鍑℃槸鏈郴缁熶腑涓嶅彲瑙嗙殑鑷畾涔夊璞￠兘瑕佷粠姝ょ被缁ф壙
//      姝ょ被杩樺疄鐜颁簡IDComInterface鎺ュ彛
//璁捐: Skyline(2001.04.22)
//瀹炵幇: Skyline
//淇敼:
//--------------------------------------------------------------------------------------------------
public class JDComObject extends java.lang.Object implements IDComInterface {
    // 瀛樻斁瀵硅薄鎴栨帴鍙ｇ殑GUID
    protected String ObjectGUID;
    // 瀛樻斁瀵硅薄鎴栨帴鍙ｇ殑鍚嶇О
    protected String ObjectName;
    // ObjectInformation 鎺ュ彛
    protected IDComObjectInformation  ObjectInformation = null;
    //----------------------------------------------------------------------------------------------
    //鎻忚堪: 璇ュ嚱鏁版牴鎹敤鎴锋寚瀹氱殑GUID鑾峰彇鎸囧畾鐨勬帴鍙�鍗冲彲浠ョ敤GUID涔熷彲浠ョ敤鍚嶇О)
    //璁捐: Skyline(2001.12.29)
    //瀹炵幇: Skyline
    //淇敼:
    //----------------------------------------------------------------------------------------------
    public Object QueryInterface(String guid) {
      if ( ObjectGUID != null && ObjectGUID.equals(guid) )
        return this;
      else  if ( ObjectName!= null && ObjectName.equals(guid) ) return this; else return null;
    }
    //----------------------------------------------------------------------------------------------
    //鎻忚堪: 璇ュ嚱鏁拌繑鍥炴寚瀹氬璞℃垨鎺ュ彛鐨勫悕绉�    //璁捐: Skyline(2001.12.29)
    //瀹炵幇: Skyline
    //淇敼:
    //----------------------------------------------------------------------------------------------
    public String getObjectName() {
      return ObjectName;
    }
    //----------------------------------------------------------------------------------------------
    //鎻忚堪:
    //璁捐: Skyline(2001.12.29)
    //瀹炵幇: Skyline
    //淇敼:
    //----------------------------------------------------------------------------------------------
    public void setObjectName(String ObjectName) {
      this.ObjectName = ObjectName;
    }
    //----------------------------------------------------------------------------------------------
    //鎻忚堪:
    //璁捐: Skyline(2001.12.29)
    //瀹炵幇: Skyline
    //淇敼:
    //----------------------------------------------------------------------------------------------
    public void setObjectGUID(String ObjectGUID) {
      this.ObjectGUID = ObjectGUID;
    }
    //----------------------------------------------------------------------------------------------
    //鎻忚堪: 璇ュ嚱鏁拌繑鍥炴寚瀹氬璞℃垨鎺ュ彛鐨凣UID
    //璁捐: Skyline(2001.12.29)
    //瀹炵幇: Skyline
    //淇敼:
    //----------------------------------------------------------------------------------------------
    public String getObjectGUID() {
      return ObjectGUID;
    }

    //----------------------------------------------------------------------------------------------
    //鎻忚堪:
    //璁捐: Skyline(2001.12.29)
    //瀹炵幇: Skyline
    //淇敼:
    //----------------------------------------------------------------------------------------------
    public IDComObjectInformation getObjectInformation() {
      return ObjectInformation;
    }
    //----------------------------------------------------------------------------------------------
    //鎻忚堪: 瀵硅薄鍦ㄥ疄渚嬪寲鏃�鐢辩鐞嗗璞＄殑妗嗘灦鎻愪緵ObjectInformation鎺ュ彛
    //璁捐: Skyline(2001.12.29)
    //瀹炵幇: Skyline
    //淇敼:
    //----------------------------------------------------------------------------------------------
    public void setObjectInformation(IDComObjectInformation  ObjectInformation) {
      this.ObjectInformation = ObjectInformation;
    }
    //----------------------------------------------------------------------------------------------
    //鎻忚堪:
    //璁捐: Skyline(2001.12.29)
    //瀹炵幇: Skyline
    //淇敼:
    //----------------------------------------------------------------------------------------------
    public int getInterfaceType() {
        return 0;
    }
    
    
  //----------------------------------------------------------------------------------------------
  //鎻忚堪: 娲诲姩瀵硅薄澶氭柟娉曡皟鐢�妗嗘灦鍦ㄨ皟鐢ㄥ璞＄殑鏂规硶鏃堕兘鏄粡杩囪繖涓柟娉曡繘琛岀殑
  //      鏂规硶鍚嶈娉ㄦ剰澶у皬鍐�  //璁捐: Skyline(2001.12.29)
  //瀹炵幇: Skyline
  //淇敼:
  //----------------------------------------------------------------------------------------------
  public Object InvokeMethod(String MethodName,Object argsArray[]) throws Exception {
    Class cls;Method Mth;Object Res=null;
    Method[] MA;int i,Count;String MName;
    boolean b=false;
//      try {
          cls = this.getClass();
          MA = cls.getMethods();
          Count = MA.length;
          for(i=0;i<Count;i++) {
              Mth = MA[i];
              MName = Mth.getName();
              if ( MName.compareTo(MethodName) == 0 ) {
                  b=true;
                  Res = (Object)Mth.invoke(this,argsArray);
                  break;
              }
          }
//      } catch ( Exception e) {
//        e.printStackTrace();
//      }
//          if (!b &&
//              "1".equals(ParameterManager.getDefault().getSystemParam("CALL_MONITOR"))) {
//            System.out.println("not find :"+getClass().getName() +" "+MethodName+"");
//          }
      return Res;
  }
    //----------------------------------------------------------------------------------------------
    //鎻忚堪: 鍩虹绫荤殑鏋勯�鍑芥暟
    //璁捐: Skyline(2001.12.29)
    //瀹炵幇: Skyline
    //淇敼:
    //----------------------------------------------------------------------------------------------
    public JDComObject() {
    }
}
/*
瀵硅薄鍦℉KEY_CLASSES_ROOT涓婚敭鐨勫瓨鍌ㄥ瀷寮�
鍑℃槸浠嶫DComObject缁ф壙鐨勫璞￠兘鍙互缁忚繃娉ㄥ唽鍦ㄦ涓婚敭涓嬫湁鎵�弽搴�...
<HKEY_CLASSES_ROOT>
  <CLASSES_ROOT>
    <JDCOMOBJECTS>
      <objectguid name="" description="" class="" version="" context="" security="">
        <SimpleInformation>
        // 姝ゅ鏄璞＄殑绠�崟鎻忚堪,鍙互浣跨敤鐩存帴鎻忚堪涔熷彲浠ヤ娇鐢ㄨ祫婧愭爣璇�涔熷彲浠ユ槸鎻忚堪鏂囦欢鐨勮矾寰�        </SimpleInformation>
        <DetailedInformation>
        // 瀵硅薄鐨勮缁嗘弿杩�娉ㄨ鏄鎺ュ彛鍚勪釜鏂规硶鐨勮缁嗘弿杩�        </DetailedInformation>
        <SecurityInforamtion>
        // 瀵硅薄鐨勫畨鍏ㄤ俊鎭�瀵硅薄鍦ㄦ墽琛屾椂鎵�渶鐨勫畨鍏ㄧ幆澧�        </SecurityInformation>
        <ContextInformation>
        // 瀵硅薄鍦ㄦ墽琛屾椂鎵�渶鐨勪笂涓嬫枃鐜
        </ContextInformation>
        <HelpInformation>
        // 瀵硅薄鐨勫府鍔╀俊鎭�        </HelpInformation>
        <Resources>
        // 瀵硅薄鐨勮祫婧�涓�埇鏄祫婧愭枃浠剁殑浣嶇疆,鍙互鏈変笉鍚岀殑璇█鐨勮祫婧愭枃浠�        </Resources>
        // 浠ヤ笂鏄爣鍑嗕俊鎭�瀵硅薄杩樺彲浠ユ牴鎹嚜宸辩殑闇�鍦ㄦ澧炲姞鑷繁鐨勬敞鍐屼俊鎭�杩欎簺閮芥槸鍦ㄨ皟鐢ㄥ璞＄殑娉ㄥ唽杩囩▼瀹屾垚鐨�        // 瀵硅薄鍙栨秷娉ㄥ唽杩囧皢鍒犻櫎杩欎簺淇℃伅
      </objectguid>
      ...
      <objectguid>
      </objectguid>
      ...
    </JDCOMOBJECTS>
  </CLASSES_ROOT>
</HKEY_CLASSES_ROOT>
...
娉�浠ヤ笂淇℃伅鐨勫疄鐜版渶濂芥槸鐢ㄥ閮ㄦ枃浠剁殑鏂瑰紡,杩欐牱鍙互鍑忓皯娉ㄥ唽琛ㄧ殑瀹归噺,濡傛灉淇℃伅寰堝皯,涔熷彲浠ュ湪娉ㄥ唽琛ㄤ腑鐩存帴瀹炵幇
*/
