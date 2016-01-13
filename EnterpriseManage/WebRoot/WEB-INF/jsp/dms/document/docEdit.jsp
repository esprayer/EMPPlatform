<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script src="${ctx}/styles/dwz/js/jquery-1.7.1.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/js/dwz.min.js" type="text/javascript"></script>
<link href="${ctx}/styles/dwz/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/dwz/themes/css/core.css" rel="stylesheet" type="text/css" />

<form id="form1" name="myform" action="#">
	<script language ="javascript" type ="text/javascript" for="WebOffice1" event="NotifyCtrlReady">		
		WebOffice1_NotifyCtrlReady();
    </script>

	<div height='3%' style="text-align: left;padding-left:10px;width=100%">
		<label style="width:8%;text-align:right">修改说明：</label>
		<input type="text" id="versionNote" name="versionNote" value="${F_COMMENT}" style="width:90%"/>
    </div>
    
	<div>
		<script language ="javascript" type ="text/javascript">
			var s = "";
			s += "<object id=WebOffice1 width='100%' height='97%' style='LEFT: 0px; TOP: 0px'  classid='clsid:E77E049B-23FC-4DB8-B756-60529A35FAD5' codebase='<%=basePath %>styles/weboffice/js/weboffice_v6.0.5.0.cab#Version=6,0,5,0'>";
			s +="<param name='_ExtentX' value='6350'><param name='_ExtentY' value='6350'>";
			s +="</object>";
			document.write(s);
    	</script>
    </div>
    
    <input type="hidden" id="versionNum" name="versionNum" value="-1" />
    <script language="javascript" type="text/javascript">
	    function closewin() {
		    self.opener=null;
		    self.close();
	    }
	    
        function window_onunload() {
			document.all.WebOffice1.Close();            
        }

        // 保存
        function SaveToDb(versionNum, bExit) {
            if(document.all.WebOffice1.IsSaved() == 0) {
            	//document.all.WebOffice1.SetWindowText("WebOffice正式版本", 1);
	        	var versionNote = $('#versionNote').val();
	        	versionNote = encodeURIComponent(versionNote);
				var returnValue;
				document.all.WebOffice1.OptionFlag = 0x0080;
	            document.all.WebOffice1.HttpInit();
	            document.all.WebOffice1.SetTrackRevisions(0);
	            document.all.WebOffice1.HttpAddPostString("ID", "");
	            document.all.WebOffice1.HttpAddPostString("DocTitle", "");
	            document.all.WebOffice1.HttpAddPostString("DocType", "${doc.f_TYPE}");
	            //把当前文档添加到Post元素列表中，文件的标识符䶿DocContent
	            document.all.WebOffice1.HttpAddPostCurrFile("DocContent", "");
	            document.all.WebOffice1.HttpPost("<%=basePath%>dms/doc/saveDoc?F_DOCID=${doc.f_DOCID}&USER_ID=${USER_ID}&versionNum="+versionNum+"&versionNote="+versionNote);
            } else {
				alert("文档未进行修改！");
            }
            if(bExit) closewin();
        }

        function WebOffice1_NotifyCtrlReady() {  
        	var edit = "${editFlag}";
			var print = "${printFlag}";
			var edittype = "${editType}";      
			alert(edit);    
            //LoadOriginalFile接口装载文件,
            //如果是编辑已有文件，则文件路径传给LoadOriginalFile的第一个参数       
        	 document.all.WebOffice1.HideMenuItem(0x01); //新建
        	 document.all.WebOffice1.HideMenuItem(0x02); //打开
        	 document.all.WebOffice1.HideMenuItem(0x04); //保存
        	 //document.all.WebOffice1.HideMenuItem(0x10); //打印
        	 //document.all.WebOffice1.HideMenuItem(0x20); //打印预览
        	 //document.all.WebOffice1.HideMenuItem(0x1000); //全屏
        	 document.all.WebOffice1.HideMenuItem(0x2000); //返回        	 
        	 //document.all.WebOffice1.ShowToolBar = 0; // 0 隐藏 1 显示
        	 //判断文档是否被锁定，如果已锁定，只能进行查看，不允许保存
        	 var message = '该文档已被【 ${doc.f_LOCKED_USERID}(${doc.f_LOCKED_USERNAME})】锁定，以只读方式打开！\r\n如需解锁，请联系管理员或者【${doc.f_LOCKED_USERID}(${doc.f_LOCKED_USERNAME})】将文档解锁！';
        	 if(${doc.f_LOCKED == 1}) {
				alert(message);
        	 } 	else {
        		 if(edit == '1') {
     				document.all.WebOffice1.SetCustomToolBtn(0, "保存");
     	        	document.all.WebOffice1.SetCustomToolBtn(1, "保存并退出");
     	        	document.all.WebOffice1.SetCustomToolBtn(2, "显示当前修订");
     	        	document.all.WebOffice1.SetCustomToolBtn(3, "隐藏修订");
     	        	//document.all.WebOffice1.SetCustomToolBtn(4, "修订文档");
     			}

     			if(print == '1') {
     				 document.all.WebOffice1.HideMenuItem(0x10); //打印
     	        	 document.all.WebOffice1.HideMenuItem(0x20); //打印预览
     			}
        	 }
			document.all.WebOffice1.OptionFlag |= 0x0080; //下载远程服务器文件，当设置OptionFlag |= 0x0080 会弹出文件下载进度条。
			//document.all.WebOffice1.ReadOnly = 1;
			//document.all.WebOffice1.ProtectDoc(1, 2, "12345"); 
			document.all.WebOffice1.LoadOriginalFile("<%=basePath%>dms/doc/loadDoc?F_DOCID=${doc.f_DOCID}&docVersion=${docVersion}","${doc.f_TYPE}");
			//设置当前编辑Word、Excel的用户名,主要用于修订文档，标识不同人进行的不同操作
			document.all.WebOffice1.SetCurrUserName("${USER_NAME}");
			// 接受修订  
			document.all.WebOffice1.SetTrackRevisions(1); 	

			// 隐藏修订		
			document.all.WebOffice1.ShowRevisions(0);  
			var oDoc = new Object(document.all.WebOffice1.GetDocumentObject());
			
			var oRange = oDoc.Content;

			oRange.Document.Fields.Locked = 1;
			document.all.WebOffice1.ProtectDoc(1, 2, "12345");  
			document.all.WebOffice1.PutSaved(1); 
			if(${doc.f_LOCKED == 1}){
       		 	document.all.WebOffice1.ShowToolBar = false; // 0 隐藏 1 显示
       		 	if(document.all.WebOffice1.Is2007 == '1') {
       		 		document.all.WebOffice1.HideMenuArea("hideall","","","");
       		 	} else {
       		 		document.all.WebOffice1.HideMenuArea(",",","); 
       		 	}
       	 	}
        }
    </script>
    <SCRIPT language=javascript event=NotifyToolBarClick(iIndex) for=WebOffice1>
    	var versionNum = $('#versionNum').val();
    	
    	//保存不退出
    	if(iIndex == 32776) {
    		if(versionNum == -1) {
        		$.ajax({
        			type: 'POST',
        			url:"<%=basePath%>dms/doc/getDocVersion?F_DOCID=${doc.f_DOCID}",
        			dataType:"json",
        			cache: false,
        			success: function(response){
        				versionNum = response.number;
        				versionNum++;
        				$('#versionNum').val(versionNum);
        		    	SaveToDb(versionNum, false);
        			}
        		});
        	} else {
    	   		SaveToDb(versionNum, false);
        	}
    	}

    	//保存退出
    	if(iIndex == 32777) {
    		SaveToDb(versionNum, true);
    	}    

    	else if(iIndex == 32778) {
    		document.all.WebOffice1.ShowRevisions(1);
    	}	

    	else if(iIndex == 32779) {
    		document.all.WebOffice1.ShowRevisions(0);
    	}

    	else if(iIndex == 32780) {
    		//总体的例子代码 
			  var vCount;  vCount = document.all.WebOffice1.GetRevCount();  
			  var vOpt = 0; var vDate;  
			  for(var i=1; i<= vCount; i++){   
				  vOpt = document.all.WebOffice1.GetRevInfo(i,2);  
				  if("1" == vOpt){   
					  vOpt = "插入";   
				  }else if("2" == vOpt){   
					  vOpt = "删除";
				  }else{    
					  vOpt = "未知操作";  
				  }   
				  vDate = new String(document.all.WebOffice1.GetRevInfo(i,1));  
				  alert(vDate);
				  //vDate = parseFloat(vDate);   
				  //dateObj = new Date(vDate);    
				  //alert(dateObj.getYear()   + "年" + dateObj.getMonth() + 1 + "月" + dateObj.getDate() +"日" +  dateObj.getHours() +"时" +  dateObj.getMinutes() +"分" +  dateObj.getSeconds() +"秒" );   
				  alert("用户:"+document.all.WebOffice1.GetRevInfo(i,0) + "\r\n操作:" + vOpt + "\r\n内容:" + document.all.WebOffice1.GetRevInfo(i,3));  
			}
    	}
    	
    </SCRIPT>
</form>