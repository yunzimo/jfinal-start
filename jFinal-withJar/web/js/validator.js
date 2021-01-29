$.extend($.fn.validatebox.defaults.rules, {
    checkUsername : {
        validator : function(value, param) {
            var username=myTrim(value);
            var id=param[0];
            var code = "";
            $.ajax({
                type : 'post',
                async : false,
                url : getRootPath()+'/user/checkUserName',
                data : {
                    "name" : username,
                    "id" : id
                },
                success : function(result) {
                    code = result.code;

                }
            });
            return code!=="success";
        },
        message : '用户名已经被占用'
    },
    equals: {
        validator: function(value,param){
            return value === $("#pwd").val();
        },
        message: '两次输入的密码不一致'
    }
});

function myTrim(str) {
    if(String.prototype.trim) {
        return str.trim();
    }
    return str.replace(/^\s+(.*?)\s+$/g, "$1");
    //or
    //return str.replace(/^\s+/, "").replace(/\s+$/, "");
}