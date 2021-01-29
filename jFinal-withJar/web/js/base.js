let orgId;
$(function () {


    $('#dg').datagrid({
        url: getRootPath()+'/user/getUserList',//请求数据的URL 代码附后
        title:"MyUser",
        toolbar:'#toolbar',
        loadMsg:'正在加载',
        columns: [[
            { field:'ck',title: '复选框', checkbox:'true'},
            { field: 'id', title: 'ID',width:'10%',align: 'center' ,sortable:true}, //User_Name为数据库表中的字段名称 下同
            { field: 'name', title: '姓名',width:'10%', align: 'center' },
            { field: 'password', title: '密码',width:'10%', align: 'center' },
            { field: 'email', title: '邮箱',width:'20%', align: 'center' },
            { field: 'orgId', title: '所属部门',width:'20%', align: 'center',
                formatter:function (value) {
                    return org[value];
                }
            },
            { field: 'age', title: '年龄',width:'10%', align: 'center' ,sortable:true},
            { field: 'sex', title: '性别',width:'5%', align: 'center',
                formatter:function (value,row,index) {

                    if(row.sex==1){
                        return "男";
                    }else{
                        return "女";
                    }
                }},
            { field: 'birthday', title: '生日',width:'15%', align: 'center' ,sortable: true},
            { field: 'addtime', title: '添加时间',width:'15%', align: 'center' ,sortable: true},
            { field: 'operate',title: '操作',width:'20%',align: 'center',
                formatter:function (value,row,index) {
                    var JsonRow=JSON.stringify(row);
                    var str = "<a href='javascript:void(0)' name='edit' class='easyui-linkbutton' onclick='editUser("+JsonRow+")'></a>" +
                        "<a href='javascript:void(0)' name='remove' class='easyui-linkbutton' onclick='destroyUser("+JsonRow+")'></a>";
                    return str;
                }
            }
        ]],
        onLoadSuccess:function(data){
            $("a[name='edit']").linkbutton({text:'编辑',plain:true,iconCls:"icon-edit"});
            $("a[name='remove']").linkbutton({text:'删除',plain:true,iconCls:"icon-remove"});
            showOrHide();
        },
        // data-options="region:'center',split:true"
        queryParams: {
            order: order,
            orgId: orgId,
            sort: sort,
            name: $("#SearchUserName").val(),
            pwd: $("#SearchPwd").val()   //发送额外的参数
        },
        resizeHandle:"right",
        striped: true, pagination: true, rownumbers: true, singleSelect: false, pageNumber: 1, pageSize: 8, pageList: [1, 2, 4, 8, 16, 32], showFooter: true
    });

    $("#cc").combobox({
       url:getRootPath()+'/org/getOrgList',
        valueField:'id',
        textField:'name'
    });

    $('#orderKey').combobox({
        select : sort,
        onChange: function (newV,oldV) {
            sort=newV;
            $('#dg').datagrid({
                queryParams: {
                    order: order,
                    orgId: orgId,
                    sort: sort,
                    name: $("#SearchUserName").val(),
                    pwd: $("#SearchPwd").val()   //发送额外的参数
                }
            })
        }
    });


    //页面加载完成后，执行这段代码----动态创建ztree
    var setting = {
        callback:{
            onClick : zTreeOnClick
        }
    };
    var url=getRootPath()+"/org/getTree";
    $.post(url,{},function (data) {
        $.fn.zTree.init($("#orgTree"), setting, data);
    },'json');

});


//点击ztree节点修改orgId同时重新加载数据
function zTreeOnClick(event,treeId,treeNode) {
    orgId=treeNode.id;
    $('#dg').datagrid({
        queryParams: {
            orgId: orgId,
            name: $("#SearchUserName").val(),
            pwd: $("#SearchPwd").val()   //发送额外的参数
        }
    })
}

orgId = 0;
org = [];
var sort = "addtime";
var order = "asc";


//数据储存在全局变量，把部门编号转变为部门名称
$.post(
    getRootPath()+"/org/getOrgList",function (data,status){
        if(status==="success"){
            //console.log(data);
            for(var i=0;i<data.length;i++){
                org[data[i].id]=data[i].name;
            }
        }
    },'json'
);

/*
 *  列表的自适应代码
 */

$(function(){　　//初始加载，表格宽度自适应
    $(document).ready(function(){
        fitCoulms();

    });　　//浏览器窗口大小变化后，表格宽度自适应
    $(window).resize(function(){
        fitCoulms();

    });
});
//表格宽度自适应，这里的#dg是datagrid表格生成的div标签
function fitCoulms(){
    $('#dg').datagrid({
        fitColumns:true
    });
}


/**
 * 给时间框控件扩展一个清除的按钮
 */
$.fn.datetimebox.defaults.cleanText = '清空';

(function ($) {
    var buttons = $.extend([], $.fn.datetimebox.defaults.buttons);
    buttons.splice(1, 0, {
        text: function (target) {
            return $(target).datetimebox("options").cleanText
        },
        handler: function (target) {
            $(target).datetimebox("setValue", "");
            $(target).datetimebox("hidePanel");
        }
    });
    $.extend($.fn.datetimebox.defaults, {
        buttons: buttons
    });

})(jQuery);


//限制选择的时间不能超过当前时间
$(function(){
    $('#dd').datetimebox().datetimebox('calendar').calendar({
        validator: function(date){
            var now = new Date();
            var d2 = new Date(now.getFullYear(), now.getMonth(), now.getDate(),now.getHours(),now.getMinutes(),now.getSeconds());

            return  date<=d2;
        }
    });
});

function getRootPath() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPaht + projectName);
}