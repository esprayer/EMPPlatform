package jdcom.foundation.classes;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:该接口是对象附加信息提供接口,类似于Java中的BeanInfo!
//     它提供了某一个对象的详细信息与简要信息及其安全性信息,上下文环境
//     对象的这些信息的组织型式是XML格式,此文件的结束处对该XML文件的格式做详细的描述并有样式文件
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public interface IDComObjectInformation {
}
/*
  此处是对象信息XML文件的详细描述:
  <?xml version="1.0" encoding="gb2312" ?>
  <DComObjectInformation version="">
    <DComObjects>
      <DComObject name="" description="" class="" version="" context="" security="">
        <SimpleInformation>
        </SimpleInformation>
        <DetailedInformation>
        </DetailedInformation>
        <SecurityInforamtion>
        </SecurityInformation>
        <ContextInformation>
        </ContextInformation>
      </DcomObject>
    </DComObjects>
  </DComObjectInformation>
  注意:有关对象的描述信息可能存在多语言的问题,解决这个问题的办法是一个对象对应于多个信息描述文件,它们是
       不同语言的.而确定对象使用哪种语言描述文件,是根据上下文环境来进行的.
*/
