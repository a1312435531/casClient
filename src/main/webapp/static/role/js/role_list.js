$(document).ready(function(){



});

var listRoleData = "/config/listRoleData";  //表格数据接口
var roleDetail = "/config/roleDetail";  //详情接口
var deleteRole = "/config/deleteRole";
var rolePermissionUrl="/rolePermission";

layui.use(['table','layer'], function(){
    var table = layui.table;
    var layer = layui.layer;
    //第一个实例
    table.render({
        elem: '#demo'
        ,height: 'auto'
        ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        ,width: 998
        ,url: listRoleData //数据接口
        ,page: true //开启分页
        ,cols: [
            [ //表头
                {type: 'checkbox', fixed: 'left'},
                {type: 'numbers', title: '序号', width: 60, fixed: 'left', align: 'center'}
                ,{field: 'name', title: '角色名', width:173, align:'center', sort: true}
                ,{field: 'identifier', title: '角色标识', width:150,align:'center'}
                ,{field: 'remark', title: '角色描述', width:280, align:'center'}
                ,{field: '', title: '操作',toolbar: '#barDemo', width:280, align:'center'}
            ]
        ]
    });
    table.on('tool(ope)', function(obj){
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        switch(layEvent){

            //查看
            case 'doLook':
                layer.open({
                    type: 2,
                    title :'查看角色',
                    content: roleDetail+'?id='+obj.data.id+'&action=1', //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                    offset: ['50px'],
                    area:['600px', '500px'],
                    anim: 0
                });
                break;


            //删除
            case 'doDelete':
                layer.open({
                    type: 1
                    , title: '删除角色'
                    , offset: ['120px'] //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , id: 'layerDemo' //防止重复弹出
                    , content: '<div style="padding: 20px 100px;">'
                        + '<i class="layui-icon layui-icon-help" style="font-size: 30px; color: rgb(255,87,34);"></i>'
                        + '确定要删除该角色'
                        + '</div>'
                    , btn: ['确定', '取消']
                    , btnAlign: 'c' //按钮居中
                    , yes: function () {
                        $.ajax({
                            type: 'post',
                            url: deleteRole+'?id=' + obj.data.id,
                            success: function (res) {
                                layer.closeAll();
                                layer.msg('删除成功', {icon: 1, time: 1200, offset: '170px'}, function () {
                                    reloadTable();
                                })
                            },
                            error: function (data) {
                                layer.closeAll();
                                layer.msg('删除失败', {icon: 2, time: 1200, offset: '170px'}, function () {
                                })
                            }
                        });

                    },
                    btn2: function () {
                        layer.closeAll();
                    }
                });
                break;

            //编辑
            case 'doUpdate':
                layer.open({
                    type: 2,
                    title :'编辑角色',
                    content: roleDetail+'?id='+obj.data.id+'&action=2', //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                    offset: ['50px'],
                    area:['600px', '500px'],
                    anim: 0
                });
                break;

            //权限分配
            case 'doPermission':
                layer.open({
                    type: 2,
                    title :'权限分配',
                    content: rolePermissionUrl+'?id='+obj.data.id, //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                    offset: ['50px'],
                    area:['500px', '500px'],
                    anim: 0
                });
                break;
        };
    });
    //头工具栏事件
    table.on('toolbar(ope)', function(obj){
        if (obj.event === 'doAdd') {
            layer.open({
                type: 2,
                title :'添加角色',
                content: roleDetail, //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                offset: ['50px'],
                area:['600px', '500px'],
                anim: 0
            });
        };
    });
    function reloadTable(){
        table.reload("demo",{
            url:listRoleData,
            where:{}
        });
    }

});