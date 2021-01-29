//加载点击事件
$(function () {
    //点击选择可选列表按钮触发
    $("#showSelectCol").click(function (e) {
        $("#mm").menu('show',{
            left: e.pageX,
            top: e.pageY
        });
    });

    /*
     * 搜索条件的添加
     */
    $("#search").click(function () {
        $('#dg').datagrid({
            queryParams: {
                orgId: orgId,
                name: $("#SearchUserName").val(),
                pwd: $("#SearchPwd").val()   //发送额外的参数
            }
        })

    });

    /*
 * 清除按钮，清空搜索框的同时，重新加载整个列表
 */
    $("#clear").click(function () {
        $("#SearchUserName").val("");
        $("#SearchPwd").val("");
        orgId=0;
        sort="addtime";
        //window.location.reload();
        $('#dg').datagrid({
            queryParams: {
                orgId: orgId,
                name: $("#SearchUserName").val(),
                pwd: $("#SearchPwd").val()   //发送额外的参数
            }
        });
        $('#orderKey').combobox({
            select:sort
        })
    });

});





//定义变量url，可以通过不同的url执行不同的操作（编辑和添加）
var url;

//弹出新建用户的窗口
function newUser(){

    $('#dlg').dialog({
        title:'新建用户',
        width:500,
        height:700
    }).dialog('open').dialog('center');
    //console.log($("input[name='user.sex']").val());
    $('#fm').form('clear');
    //editor.html("");
    url = getRootPath()+'/user/save';
}

/*
 *  弹出编辑框，并回显数据
 */
function editUser(rowEle){
    if(rowEle){
        var row=rowEle;
        //console.log(row);
    }else{
        var row = $('#dg').datagrid('getSelected');
    }
    //alert(row.id);
    if (row){
        $('#dlg').dialog({
            title:'编辑用户',
            width:500,
            height:700
        }).dialog('open').dialog('center');
        $('#fm').form('load',{
            "user.id":row.id,
            "user.name":row.name,
            "user.pwd":row.password,
            "user.email":row.email,
            "user.age":row.age,
            "user.birthday":row.birthday,
            "user.sex":row.sex,
            "user.remark":row.remark,
            "user.org_id":row.orgId

        });
        //editor.html(row.remark);
        url = getRootPath()+'/user/update?id='+row.id;
    }else {
        $.messager.alert('提示', '请选择要操作的行！', 'info');
    }
}

/*
 * 数据的保存，会根据不同的url执行不同的操作
 */
function saveUser(){
   // $('#remark').val(editor.html());
    $('#fm').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result=JSON.parse(result);
            //console.log(result.code);
            if(result.code=="Success"){
                $.messager.alert(result.code,result.msg, 'info');
                $('#dlg').dialog('close');		// close the dialog
                $('#dg').datagrid('reload');	// reload the user data
            }else{
                $.messager.alert(result.code,result.msg,'info');
            }
        }
    });
}

/*
 * 列表删除
 */

function destroyUser(rowEle){
    if(rowEle){
        var rows=rowEle;
    }else{
        var rows = $('#dg').datagrid('getSelections');

    }

    if (rows){
        $.messager.confirm('Confirm','是否确定删除用户信息?',function(r){
            if (r){
                if(rows.id){
                    $.post(getRootPath()+'/user/delete',{id:rows.id},function(result){

                        $.messager.alert(result.code,result.msg,'info');
                    },'json');
                }else {
                    var length=rows.length;
                    for(let i=0; i<rows.length; i++){
                        $.post(getRootPath()+'/user/delete',{id:rows[i].id},function(result){

                            if(result.code==='failed'){
                                $.messager.alert(result.code,result.msg,'info');
                            }else{
                                if(i===rows.length-1){
                                    $.messager.alert(result.code,result.msg,'info');
                                }
                            }
                        },'json');
                    }
                }
            }
            $('#dg').datagrid('reload');	// reload the user data
        });
    }else{
        $.messager.alert('提示', '请选择要操作的行！', 'info');
    }
}