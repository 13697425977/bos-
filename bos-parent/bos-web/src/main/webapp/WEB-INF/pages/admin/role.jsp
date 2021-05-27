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
<!-- 导入ztree类库 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>	
<script type="text/javascript">
	$(function(){
		// 数据表格属性
		$("#grid").datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			idField : 'id',
			frozenColumns:[ [ {
				field : 'id',
				checkbox : true,
				rowspan : 2
			}] ],
			toolbar : [
				{
					id : 'add',
					text : '添加角色',
					iconCls : 'icon-add',
					handler : function(){
						location.href='${pageContext.request.contextPath}/page_admin_role_add.action';
					}
				},
				{
					id : 'edit',
					text : '修改角色',
					iconCls : 'icon-edit',
					handler : function(){
						var item = $('#grid').datagrid('getSelected');
						$('#editRoleWindow').window("open");
						$("#editRoleform").form("load",item);
					}
				},
				{
					id : 'delete',
					text : '删除角色',
					iconCls : 'icon-delete',
					handler : function(){
						doDelte();
					}
				}
			],
			url : '${pageContext.request.contextPath}/roleAction_findAll.action',
			columns : [[
				{
					field : 'name',
					title : '名称',
					width : 200
				}, 
				{
					field : 'description',
					title : '描述',
					width : 200
				} 
			]]
		});

		//修改用户窗口
		$('#editRoleWindow').window({
			title: '修改角色',
			width: 400,
			modal: true,//遮罩效果
			shadow: true,//阴影效果
			closed: true,//关闭
			height: 400,
			resizable:false
		});

		$("#save").click(function(){
			$("#editRoleform").submit();
		})
	});

	function doDelte(){
		var ids = [];
		var items = $('#grid').datagrid('getSelections');
		for(var i=0; i<items.length; i++){
			ids.push(items[i].id);
		}
		var ids_str=ids.join(",");
		$.ajax({
			url:"${pageContext.request.contextPath}/roleAction_delete.action",
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
<body class="easyui-layout">
	<div data-options="region:'center'">
		<table id="grid"></table>
	</div>
	<div class="easyui-window" title="修改角色" id="editRoleWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editRoleform" method="post" action="${pageContext.request.contextPath }/roleAction_update.action">
				<input type="hidden" name="id">
				<table class="table-edit"  width="95%" align="center">
					<tr class="title"><td colspan="4">基本信息</td></tr>
					<tr><td>角色名称：</td><td><input type="text" name="name" id="name" class="easyui-validatebox" required="true" /></td></tr>
					<tr><td>编码：</td><td><input type="text" name="code" id="code" class="easyui-validatebox" required="true" /></td></tr>
					<tr><td>角色描述：</td><td><input type="text" name="description" id="description" class="easyui-validatebox" required="true" /></td></tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>