<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	// 工具栏
	var toolbar = [ {
		id : 'button-view',	
		text : '查看',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-add',
		text : '新增',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}];
	//定义冻结列
	var frozenColumns = [ [ {
		field : 'id',
		checkbox : true,
		rowspan : 2
	}, {
		field : 'username',
		title : '名称',
		width : 80,
		rowspan : 2
	} ] ];


	// 定义标题栏
	var columns = [ [ {
		field : 'gender',
		title : '性别',
		width : 60,
		rowspan : 2,
		align : 'center'
	}, {
		field : 'birthdayStr',
		title : '生日',
		width : 120,
		rowspan : 2,
		align : 'center'
	}, {
		title : '其他信息',
		colspan : 2
	}, {
		field : 'telephone',
		title : '电话',
		width : 200,
		rowspan : 2
	} ], [ {
		field : 'station',
		title : '单位',
		width : 80,
		align : 'center'
	}, {
		field : 'salary',
		title : '工资',
		width : 80,
		align : 'right'
	},{
		field : 'rolenames',
		title : '角色',
		width : 200,
		rowspan : 2
	} ] ];
	$(function(){
		// 初始化 datagrid
		// 创建grid
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			toolbar : toolbar,
			url : "userAction_findAll.action",
			idField : 'id', 
			frozenColumns : frozenColumns,
			columns : columns,
			onClickRow : onClickRow,
			onDblClickRow : doDblClickRow
		});

		//修改用户窗口
		$('#editUserWindow').window({
			title: '修改用户',
			width: 700,
			modal: true,//遮罩效果
			shadow: true,//阴影效果
			closed: true,//关闭
			height: 400,
			resizable:false
		});

		$("#save").click(function(){
			$("#edituserform").submit();
		})
		
		$("body").css({visibility:"visible"});
		
	});
	// 双击
	function doDblClickRow(rowIndex, rowData) {
		var items = $('#grid').datagrid('selectRow',rowIndex);
		doView();
	}
	// 单击
	function onClickRow(rowIndex,rowData){
		$('#editUserWindow').window("open");
		$("#edituserform").form("load",rowData);
	}
	
	function doAdd() {
		location.href="${pageContext.request.contextPath}/page_admin_userinfo.action";
	}

	function doView() {
		var item = $('#grid').datagrid('getSelected');
//		alert(JSON.stringify(item));
		$('#editUserWindow').window("open");
		$("#edituserform").form("load",item);
	}

	function doDelete() {
		var ids = [];
		var items = $('#grid').datagrid('getSelections');
		for(var i=0; i<items.length; i++){
		    ids.push(items[i].id);	    
		}
		var ids_str=ids.join(",");
		$.ajax({
			url:"${pageContext.request.contextPath}/userAction_delete.action",
			data:{"ids":ids_str},
			type:"POST",
			success:function(data){
				if(data){
					$('#grid').datagrid('reload');
					$('#grid').datagrid('uncheckAll');
				}
			}
		})
	}
	
</script>		
</head>
<body class="easyui-layout" style="visibility:hidden;">
    <div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="对用户进行修改" id="editUserWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="edituserform" method="post" action="${pageContext.request.contextPath }/userAction_update.action">
				<input type="hidden" name="id">
				<table class="table-edit"  width="95%" align="center">
					<tr class="title"><td colspan="4">基本信息</td></tr>
					<tr><td>用户名:</td><td><input type="text" name="username" id="username" class="easyui-validatebox" required="true" /></td>
						<td>口令:</td><td><input type="password" name="password" id="password" class="easyui-validatebox" required="true" validType="minLength[5]" /></td></tr>
					<tr class="title"><td colspan="4">其他信息</td></tr>
					<tr><td>工资:</td><td><input type="text" name="salary" id="salary" class="easyui-numberbox" /></td>
						<td>生日:</td><td><input type="text" name="birthdayStr" id="birthday" class="easyui-datebox" /></td></tr>
					<tr><td>性别:</td><td>
						<select name="gender" id="gender" class="easyui-combobox" style="width: 150px;">
							<option value="">请选择</option>
							<option value="男">男</option>
							<option value="女">女</option>
						</select>
					</td>
						<td>单位:</td><td>
							<select name="station" id="station" class="easyui-combobox" style="width: 150px;">
								<option value="">请选择</option>
								<option value="总公司">总公司</option>
								<option value="分公司">分公司</option>
								<option value="厅点">厅点</option>
								<option value="基地运转中心">基地运转中心</option>
								<option value="营业所">营业所</option>
							</select>
						</td></tr>
					<tr>
						<td>联系电话</td>
						<td colspan="3">
							<input type="text" name="telephone" id="telephone" class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr><td>备注:</td><td colspan="3"><textarea style="width:80%"></textarea></td></tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>