d<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<header id="header">
	<TABLE border=0 cellSpacing=0 cellPadding=0 width=630 background="">
  <TBODY>
  <TR>
    <TD background=list_files/daoh.gif>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" background="">
        <TBODY>
        <TR>
          <TD width=20><IMG border=0 align=middle src="list_files/fm.gif" 
            width=16 height=16>&nbsp;</TD>
          <TD vAlign=middle>
            <P><FONT color=#ffffff>文档管理</FONT> </P></TD>
          <TD vAlign=middle align=right><FONT 
            color=#ffffff>会计处剩余文档空间:186333k字节</FONT></TD></TR></TBODY></TABLE>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" 
        bgColor=#d4d0c8><FORM name=form action=delfile.jsp>
        <TBODY>
        <TR>
          <TD 
          style="BORDER-BOTTOM: 1px outset; BORDER-LEFT: 1px outset; BORDER-TOP: 1px outset; BORDER-RIGHT: 1px outset" 
          width="30%"><IMG border=0 src="list_files/fmup2.gif" height=20> <A 
            onmouseover="document['fpAnimswapImgFP2'].imgRolln=document['fpAnimswapImgFP2'].src;document['fpAnimswapImgFP2'].src=document['fpAnimswapImgFP2'].lowsrc;" 
            onmouseout="document['fpAnimswapImgFP2'].src=document['fpAnimswapImgFP2'].imgRolln" 
            href="javascript:winopen('addfolder.jsp?bmid=000101&amp;wjid=0')"><IMG 
            id=fpAnimswapImgFP2 title=新建文件夹 border=0 
            lowsrc="img/fmnewfolder1.gif" name=fpAnimswapImgFP2 
            src="list_files/fmnewfolder.gif" 
            dynamicanimation="fpAnimswapImgFP2"></A> <A 
            onmouseover="document['fpAnimswapImgFP3'].imgRolln=document['fpAnimswapImgFP3'].src;document['fpAnimswapImgFP3'].src=document['fpAnimswapImgFP3'].lowsrc;" 
            onmouseout="document['fpAnimswapImgFP3'].src=document['fpAnimswapImgFP3'].imgRolln" 
            href="javascript:winopenaddfile('uploadfile.jsp?bmid=000101&amp;wjpid=0&amp;sysize=186333392')"><IMG 
            id=fpAnimswapImgFP3 title=上传文件 border=0 lowsrc="img/fmnewfile1.gif" 
            name=fpAnimswapImgFP3 src="list_files/fmnewfile.gif" 
            dynamicanimation="fpAnimswapImgFP3"></A> <A 
            href="http://10.1.14.155/nbgl/index.jsp" target=_top><IMG 
            title=返回内部管理 border=0 src="list_files/zhu.gif"></A> </TD>
          <TD 
          style="BORDER-BOTTOM: 1px outset; BORDER-LEFT: 1px outset; BORDER-TOP: 1px outset; BORDER-RIGHT: 1px outset">
            <P align=right><INPUT style="BACKGROUND-COLOR: #d4d0c8" onclick=CheckAll(this.form) name=chkall value="全 选" type=button> 
<INPUT style="BACKGROUND-COLOR: #d4d0c8" onclick=CheckOthers(this.form) name=chkOthers value="反 选" type=button> 
<INPUT style="BACKGROUND-COLOR: #d4d0c8" onclick="javascript:if (confirm('您确定删除这些文件吗?')) {return true} else {return false};" name=dele value="删 除" type=submit> 
            </P></TD></TR></TBODY></TABLE>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
        <TBODY>
        <TR>
          <TD 
          style="BORDER-BOTTOM: 1px outset; BORDER-LEFT: 1px outset; BORDER-TOP: 1px outset; BORDER-RIGHT: 1px outset" 
          bgColor=#d4d0c8 width=70>当前路径：</TD>
          <TD 
          style="BORDER-BOTTOM: #808080 1px ridge; BORDER-LEFT: #808080 1px ridge; BORDER-TOP: #808080 1px ridge; BORDER-RIGHT: #808080 1px ridge" 
          bgColor=#ffffff borderColorLight=#d4d0c8 vAlign=middle 
          borderColorDark=#808080>
            <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
              <TBODY>
              <TR>
                <TD width="4%"><IMG border=0 align=middle 
                  src="list_files/deskset.gif"></TD>
                <TD vAlign=middle 
        width="96%">会计处:</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TR></TBODY></TABLE>
</header>