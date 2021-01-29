$(function () {
    //点击按钮选择要显示的列表
    $("#showSelectCol2").click(function () {
        $("#list").dialog('open').dialog('center').dialog('setTitle','选择要显示的列');
        createList($("#dg"));
    });

    $("#listShow").datalist({
        onClickRow: function (index,item) {
            if(index===0){
                if(item.value==="checked"){
                    $("#listShow").datalist('unselectAll');
                    $("#listShow").datalist('updateRow',{
                        index: 0,
                        row:{
                            value:'unchecked'
                        }
                    })
                }else {
                    $("#listShow").datalist('selectAll');
                    $("#listShow").datalist('updateRow',{
                        index: 0,
                        row:{
                            value:'checked'
                        }
                    })
                }
            }

        }
    });
});

//创建datalist数据表格
var createList = function(object) {

    //加载数据 过去所有的列名push到一个json变量中，手动加载
    var datas=[{text:'全选',value:'全选'}];
    var fields = $(object).datagrid('getColumnFields');
    console.log(fields);
    for (var i = 0; i < fields.length; i++) {

        var field = fields[i];
        if(field!=='ck'){
            var col = $(object).datagrid('getColumnOption', field);
            datas.push({text: col.title,value: field});
        }
    }
    $("#listShow").datalist('loadData',datas);

    //处理选中状态，隐藏的列名不选
    for (var i = 0; i < fields.length; i++) {
        var field = fields[i];
        var col = $(object).datagrid('getColumnOption', field);
        if (!col.hidden) {
            $("#listShow").datalist('selectRow',i+1);
        } else {
            $("#listShow").datalist('unselectRow',i+1);
        }
    }

};

function saveHideList() {

    var rows=$("#listShow").datalist('getSelections');
    console.log(rows);
    var list=new Array();
    if(rows[0].text==='全选'){
        for(var i=1;i<rows.length;i++){
            list[i-1]=rows[i].value;
        }
    }else{
        for(var i=0;i<rows.length;i++){
            list[i]=rows[i].value;
        }
    }

    //把要显示的列存储到redis中
    $.ajax({
        type : 'post',
        async : false,
        url : getRootPath()+'/user/saveShowList',
        data : {
            "url": "user",
            "list" : list
        },
        success : function(result) {
            code = result.code;
            showOrHide();
            $("#list").dialog('close');
        }
    });
}

//设置要显示或隐藏的列
function showOrHide() {

    $(function () {
        var  rowsAll= $("#dg").datagrid('getColumnFields');

        for(var i=1;i<rowsAll.length;i++){
            $("#dg").datagrid('hideColumn',rowsAll[i]);
        }

        //ajax从redis中获取要显示的列
        $.ajax({
            type : 'post',
            async : false,
            url : getRootPath()+'/user/getShowList',
            dataType: 'json',
            data : {
                "url": "user",
            },
            success : function(result) {
                var showList=result.data;
                for(var i=0;i<showList.length;i++){
                    if(showList[i]!=='checked'){
                        $("#dg").datagrid('showColumn',showList[i]);
                    }
                }
            }
        });
    })


}