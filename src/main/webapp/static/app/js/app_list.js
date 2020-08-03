$(document).ready(function(){



});

var listClientData = "/selectAllClient";  //表格数据接口
var appDetail = "/webAppDetail";  //详情接口
var deleteClient = "/deleteClient";

layui.use(['table','layer'], function(){
    var table = layui.table;
    var layer = layui.layer;
    //第一个实例
    table.render({
        elem: '#demo'
        ,height: 'auto'
        ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        ,width: 998
        ,url: listClientData //数据接口
        ,page: true //开启分页
        ,cols: [
            [ //表头
                {type: 'numbers', title: '序号', width: 60,  fixed: 'left', align: 'center'}
                ,{field: 'name', title: '应用名称', width:283, align:'center', sort: true}
                ,{field: 'serviceId', title: '应用URL', width:500, align:'center'
                ,templet:
                    '<div>' +
                    '{{d.serviceId.toLowerCase().substring(22,d.serviceId.lastIndexOf("."))}}' +
                    '</div>'}
                ,{field: '', title: '操作', width:150, align:'center'
                ,templet:
                    '<div>' +
                    '{{# if(d.serviceId.toLowerCase().indexOf(window.document.location.href.substring(8,window.document.location.href.lastIndexOf(":")))==-1){ }}' +

                    '    <button class=\'layui-btn layui-btn-xs layui-btn-danger\' lay-event=\'doDelete\'>删除</button>' +
                    '{{#  } else { }}' +
                    '拒绝操作' +
                    '{{# } }}'+
                    '</div>'}
            ]
        ]
    });
    table.on('tool(ope)', function(obj){
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        switch(layEvent){

            //删除
            case 'doDelete':
                layer.open({
                    type: 1
                    , title: '删除角色'
                    , offset: ['120px'] //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , id: 'layerDemo' //防止重复弹出
                    , content: '<div style="padding: 20px 100px;">'
                        + '<i class="layui-icon layui-icon-help" style="font-size: 30px; color: rgb(255,87,34);"></i>'
                        + '确定要删除该应用'
                        + '</div>'
                    , btn: ['确定', '取消']
                    , btnAlign: 'c' //按钮居中
                    , yes: function () {
                        $.ajax({
                            type: 'post',
                            url: deleteClient+'/' + obj.data.id,
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
                    content: appDetail+'?id='+obj.data.id, //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                    offset: ['50px'],
                    area:['600px', '500px'],
                    anim: 0
                });
                break;
        };
    });
    //头工具栏事件
    table.on('toolbar(ope)', function(obj){
        switch(obj.event){
            case 'doAdd':
                layer.open({
                    type: 2,
                    title :'添加应用',
                    content: appDetail, //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                    offset: ['50px'],
                    area:['600px', '500px'],
                    anim: 0
                });
                break;


            //自定义头工具栏右侧图标 - 提示
            case 'LAYTABLE_TIPS':
                layer.alert('这是工具栏右侧自定义的一个图标按钮');
                break;
        };

        });
    function reloadTable(){
        table.reload("demo",{
            url:listClientData,
            where:{}
        });
    }

});