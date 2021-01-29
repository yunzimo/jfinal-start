<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/1/28
  Time: 11:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>列表页面</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/user/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/user/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/user/easyui/demo/demo.css">
    <script type="text/javascript" src="<%=request.getContextPath() %>/user/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/user/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/user/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/base.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/listShow.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/menu.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/edit.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/export.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/validator.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/kindeditor/themes/default/default.css">
    <script charset="UTF-8" src="<%=request.getContextPath() %>/plugins/kindeditor/kindeditor-all-min.js"></script>
    <script charset="UTF-8" src="<%=request.getContextPath() %>/plugins/kindeditor/lang/zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/plugins/datagrid-export.js"></script>

    <script type="text/javascript" src="<%=request.getContextPath() %>/plugins/ztree/js/jquery.ztree.all.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/ztree/css/zTreeStyle/zTreeStyle.css">
</head>
<body class="easyui-layout">
<div data-options="region:'west',title:'菜单',split:true" style="width:200px;">
    <div title="面板二">
        <!-- 展示ztree效果 :使用标准json数据构造ztree-->
        <ul id="orgTree" class="ztree"></ul>

    </div>

</div>

<div data-options="region:'center',split:false">
    <table id="dg"  style="width: auto">

    </table>
</div>


<!--    工具栏-->
<div id="toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新建用户</a>

    <!--    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改用户</a>-->
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除用户</a>

    用户名：<input required="true" id="SearchUserName">&nbsp;
    密码：<input required="true" id="SearchPwd">&nbsp;
    <a href="#" class="easyui-linkbutton" id="search">查询</a>&nbsp;&nbsp;
    <a href="#" class="easyui-linkbutton" id="clear">清空</a>&nbsp;&nbsp;
    <a href="#" class="easyui-linkbutton" id="showSelectCol2">显示列目</a>&nbsp;&nbsp;
    <a href="#" class="easyui-linkbutton" id="export" onclick="selectColu()">导出</a>&nbsp;&nbsp;
    排序：
    <select id="orderKey" class="easyui-combobox" style="width:100px;" editable="false">
        <option value="addtime">添加时间</option>
        <option value="name">名称</option>
    </select>


</div>

<!--    添加和修改的弹窗页面-->
<div id="dlg" class="easyui-dialog" style="width:400px" data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
    <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px" content="text/html;charset=utf-8">
        <h3>用户信息</h3>

        <input id="id" name="user.id" type="hidden">
        <div style="margin-bottom:10px">

            <input name="user.name" class="easyui-textbox" required="true" label="姓名:" style="width:330px" validType="checkUsername[$('#id').val()]">
        </div>
        <div style="margin-bottom:10px">
            <input id="pwd" name="user.pwd" class="easyui-textbox" required="true"  label="密码:" style="width:330px" data-options="validType:length[3,18]" >
        </div>
        <div style="margin-bottom:10px">
            <input  class="easyui-textbox" required="true"  label="确认密码:" data-options="validType:['length[3,18]','equals']" style="width:330px">
        </div>
        <div style="margin-bottom:10px">
            <input name="user.email" class="easyui-textbox" required="true"  label="邮箱:" style="width:330px" data-options="validType:['email','length[0,16]']">
        </div>
        <div style="margin-bottom:10px">
            <input name="user.age" class="easyui-numberbox" required="true"  label="年龄:" style="width:330px">
        </div>
        <div id="sex" style="margin-bottom:10px">
            性别：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input class="easyui-radiobutton"  name="user.sex" value="1" label="男:" labelWidth="30px" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input class="easyui-radiobutton" name="user.sex" value="0" label="女:" labelWidth="30px" >
        </div>
        <div style="margin-bottom:10px">
            <input id="dd" name="user.birthday" class="easyui-datetimebox" data-options="editable:false"  label="生日:" style="width:330px">
        </div>

        <div style="margin-bottom:10px">
            <input class="easyui-textbox" name="user.remark" data-options="multiline:true" style="width:330px;height:100px" label="备注:">
        </div>
        <div style="margin-bottom:10px">
            <input id="cc" name="user.org_id" label="部门:" style="width:330px" required="true">
        </div>
    </form>
</div>


<!--    列表显示选择窗口-->
<div id="list" class="easyui-dialog" data-options="closed:true,modal:true,border:'thin',buttons:'#list-buttons'">
    <table id="columnList"></table>

    <ul id="listShow" class="easyui-datalist" style="width:400px;height:250px" data-options="lines:true,checkbox:true,singleSelect:false">
        <li id="selectAll" value="全选" >全选</li>
    </ul>
</div>


<div id="dlg-buttons" >
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close');" style="width:90px">取消</a>
</div>
<div id="list-buttons" >
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveHideList()" style="width:90px" >确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#list').dialog('close');fitCoulms()" style="width:90px">取消</a>
</div>
<div id="exportList-buttons" >
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="exportEx()" style="width:90px" >确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#exportList').dialog('close');" style="width:90px">取消</a>
</div>

<!--    菜单-->
<div id="mm" class="easyui-menu" style="width:120px;">
</div>

<div id="exportList" class="easyui-dialog" data-options="closed:true,modal:true,border:'thin',buttons:'#exportList-buttons'">
    <ul id="exportListData"  class="easyui-datagrid" style="width:400px;height:250px" data-options="lines:true,checkbox:true,singleSelect:false">
        <li value="全选" >全选</li>
        <li value="id">ID</li>
        <li value="name">姓名</li>
        <li value="pwd">密码</li>
        <li value="email">邮箱</li>
        <li value="age">年龄</li>
        <li value="sex">性别</li>
        <li value="birthday">生日</li>
        <li value="remark">备注</li>
        <li value="org_id">部门</li>
        <li value="addtime">添加时间</li>
    </ul>

</div>


</body>
</html>
