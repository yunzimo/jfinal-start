$(function () {
    createMenu($("#dg"));
});

var createMenu = function(object) {
    var cmenu = $("#mm");
    cmenu.menu({
        onClick: function(item) {
            console.log(item);
            if (item.iconCls == 'icon-ok') {
                $(object).datagrid('hideColumn', item.name);
                cmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-empty'
                });
            } else {
                $(object).datagrid('showColumn', item.name);
                cmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-ok'
                });
            }
        }
    });
    var fields = $(object).datagrid('getColumnFields');
    for (var i = 0; i < fields.length; i++) {
        var field = fields[i];
        var col = $(object).datagrid('getColumnOption', field);
        if (!col.hidden) {
            cmenu.menu('appendItem', {
                text: col.title,
                name: field,
                iconCls: 'icon-ok'
            });
        } else {
            cmenu.menu('appendItem', {
                text: col.title,
                name: field,
                iconCls: 'icon-empty'
            });
        }
    }

};


//右击列表头出现窗口可以对现有的显示列进行隐藏和显示
//表格列头点击事件
$.fn.datagrid.defaults.onHeaderContextMenu = function(e, field) {
    e.preventDefault();
    var object = this;
    if (!object.rightMenu) {
        createColumnMenu(this);
    }
    object.rightMenu.menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};

//属性列右击菜单
var createColumnMenu = function(object) {
    var cmenu = $('<div/>').appendTo('body');
    cmenu.menu({
        onClick: function(item) {
            if (item.iconCls == 'icon-ok') {
                $(object).datagrid('hideColumn', item.name);
                cmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-empty'
                });
            } else {
                $(object).datagrid('showColumn', item.name);
                cmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-ok'
                });
            }
        }
    });
    var fields = $(object).datagrid('getColumnFields');
    for (var i = 0; i < fields.length; i++) {
        var field = fields[i];
        var col = $(object).datagrid('getColumnOption', field);
        if (!col.hidden) {
            cmenu.menu('appendItem', {
                text: col.title,
                name: field,
                iconCls: 'icon-ok'
            });
        } else {
            cmenu.menu('appendItem', {
                text: col.title,
                name: field,
                iconCls: 'icon-empty'
            });
        }
    }
    object.rightMenu = cmenu;
};
