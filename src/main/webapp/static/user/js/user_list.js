$(document).ready(function () {


});
var listDataUrl="/config/listUserData";
var userDetailUrl="/config/userDetail";
var deleteUserUrl="/config/deleteUser";

layui.use(['table', 'layer'], function () {
    var table = layui.table;
    var layer = layui.layer;
    //第一个实例
    table.render({
        elem: '#demo'
        , height: 'auto'
        , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        , width: 998
        , url: listDataUrl //数据接口
        , page: true //开启分页
        , cols: [
            [ //表头
                {type: 'checkbox', fixed: 'left'},
                {type: 'numbers', title: '序号', width: 60,  fixed: 'left', align: 'center'}
                , {field: 'username', title: '用户名', width: 150, align: 'center',sort: true}
                , {field: 'nickname', title: '昵称', width: 146, align: 'center'}
                , {field: 'role', title: '用户角色', width: 230, align: 'center'
                ,templet:'<div>' +
                    '{{# if(d.roleId!=null){ }}' +
                    '{{d.role.name}}' +
                    '{{# } }}'+
                    '</div>'}
                , {
                field: 'createDate', title: '创建时间', width: 175, sort: true, align: 'center'
                , templet: '<div>' +
                    '{{#  if(d.createDate !== null){ }}' +
                    '{{layui.util.toDateString(d.createDate, \'yyyy-MM-dd HH:mm\')}}' +
                    '{{#  } }} ' +
                    '</div>'
            }
                , {field: '', title: '操作', toolbar: '#barDemo', width: 180, align: 'center'}
            ]
        ]
    });
    table.on('tool(ope)', function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        switch (layEvent) {
            case 'doLook':
                layer.open({
                    type: 2,
                    title: '查看用户',
                    content: userDetailUrl+'?id=' + obj.data.id + '&action=1', //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                    offset: ['50px'],
                    area: ['600px', '500px'],
                    anim: 0
                });
                break;
            case 'doDelete':
                var ids=[];
                ids.push(data.id);
                doDeleteUser('确定要删除该用户',ids);
                break;
            case 'doUpdate':
                layer.open({
                    type: 2,
                    title: '编辑用户',
                    content: userDetailUrl+'?id=' + obj.data.id + '&action=2', //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                    offset: ['50px'],
                    area: ['600px', '500px'],
                    anim: 0
                });
                break;
        }
        ;
    });
    //头工具栏事件
    table.on('toolbar(ope)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'doAdd':
                layer.open({
                    type: 2,
                    title: '添加用户',
                    content: userDetailUrl, //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                    offset: ['50px'],
                    area: ['600px', '500px'],
                    anim: 0
                });
                break;
            case 'getCheckData':
                var data = checkStatus.data;
                if(data.length>0){
                    var ids=[];
                    for(var i = 0 ;i<data.length;i++){
                        ids.push(data[i].id);
                    }
                    doDeleteUser('确定删除选中项',ids);
                }else{
                    layer.msg("请先勾选需要删除项！",{icon:2,offset:'170px'});
                }

                break;
            case 'doSelect':

               table.reload("demo",{
                   url:listDataUrl,
                   where:{
                       name:$("#name").val(),
                       roleId:$("#roleId").val()
                   }
               });
                break;
            case 'isAll':
                layer.msg(checkStatus.isAll ? '全选' : '未全选');
                break;

            //自定义头工具栏右侧图标 - 提示
            case 'LAYTABLE_TIPS':
                layer.alert('这是工具栏右侧自定义的一个图标按钮');
                break;
        }
        ;

    });
    function reloadTable(){
        table.reload("demo",{
            url:listDataUrl,
            where:{}
        });
    }
    function doDeleteUser(mes,ids) {
        layer.open({
            type: 1
            , title: '删除用户'
            , offset: ['150px'] //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , id: 'layerDemo' //防止重复弹出
            , content: '<div style="padding: 20px 100px;">'
                + '<i class="layui-icon layui-icon-help" style="font-size: 30px; color: rgb(255,87,34);"></i>'
                + mes
                + '</div>'
            , btn: ['确定', '取消']
            , btnAlign: 'c' //按钮居中
            , yes: function () {
                $.ajax({
                    type: 'post',
                    url: deleteUserUrl,
                    contentType: "application/json;charset=UTF-8",
                    data:JSON.stringify(ids),
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
    }
});