$(function () {
    $("#exportListData").datalist({
        onClickRow: function (index,item) {
            if(index===0){
                if(item.value==="checked"){
                    $("#exportListData").datalist('unselectAll');
                    $("#exportListData").datalist('updateRow',{
                        index: 0,
                        row:{
                            value:'unchecked'
                        }
                    })
                }else {
                    $("#exportListData").datalist('selectAll');
                    $("#exportListData").datalist('updateRow',{
                        index: 0,
                        row:{
                            value:'checked'
                        }
                    })
                }
            }else {
                if($('#exportListData').datalist('getSelections')[0].text==='全选'){
                    $("#exportListData").datalist('updateRow',{
                        index: 0,
                        row:{
                            value:'unchecked'
                        }
                    });
                    $("#exportListData").datalist('unselectRow',0);
                }
            }

        }
    });
});

function selectColu() {
    //$("#exportList").dialog('open').dialog('center').dialog('setTitle',"选择要导出的列");
    var rows = $("#dg").datagrid('getSelections');
    if(rows.length<=0){
        $.messager.confirm('Confirm','是否确定导出全部信息?',function(r){
           if(r){
               var page = $("#dg").datagrid('getPager').data('pagination').options;
               console.log(page.pageSize);
               doExcel('user',rows);
           }
        });
    }else{
        $.messager.confirm('Confirm','确定导出?',function(r){
            if(r){
                console.log(rows);
                doExcelSimple('user',rows);
            }
        });
    }
}
function doExcel(url,rows) {
    var form=$("<form>");
    form.attr("style","display:none");
    form.attr("target","");
    form.attr("method","post");
    form.attr("action",getRootPath()+"/export/exportData");

    var input1=$("<input>");
    input1.attr("type","hidden");
    input1.attr("name","rows");
    input1.attr("value",JSON.stringify(rows));

    var input2=$("<input>");
    input2.attr("type","hidden");
    input2.attr("name","url");
    input2.attr("value",url);

    var input3=$("<input>");
    input3.attr("type","hidden");
    input3.attr("name","name");
    input3.attr("value",$("#SearchUserName").val());

    var input4=$("<input>");
    input4.attr("type","hidden");
    input4.attr("name","pwd");
    input4.attr("value",$("#SearchPwd").val());

    var input5=$("<input>");
    input5.attr("type","hidden");
    input5.attr("name","sort");
    input5.attr("value",sort);

    var input6=$("<input>");
    input6.attr("type","hidden");
    input6.attr("name","order");
    input6.attr("value",order);

    var input7=$("<input>");
    input7.attr("type","hidden");
    input7.attr("name","orgId");
    input7.attr("value",orgId);

    var page = $("#dg").datagrid('getPager').data('pagination').options;

    var input8=$("<input>");
    input8.attr("type","hidden");
    input8.attr("name","pageNum");
    input8.attr("value",page.pageNumber);

    var input9=$("<input>");
    input9.attr("type","hidden");
    input9.attr("name","pageSize");
    input9.attr("value",page.pageSize);

    $("body").append(form);
    form.append(input1);
    form.append(input2);
    form.append(input3);
    form.append(input4);
    form.append(input5);
    form.append(input6);
    form.append(input7);
    form.append(input8);
    form.append(input9);
    form.submit();
}

function doExcelSimple(url,rows) {
    var form=$("<form>");
    form.attr("style","display:none");
    form.attr("target","");
    form.attr("method","post");
    form.attr("action",getRootPath()+"/export/exportData");

    var input1=$("<input>");
    input1.attr("type","hidden");
    input1.attr("name","rows");
    input1.attr("value",JSON.stringify(rows));

    var input2=$("<input>");
    input2.attr("type","hidden");
    input2.attr("name","url");
    input2.attr("value",url);


    $("body").append(form);
    form.append(input1);
    form.append(input2);
    form.submit();
}



