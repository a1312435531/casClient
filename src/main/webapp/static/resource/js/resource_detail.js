var saveResourceUrl ="/saveResource";

layui.use("form",function () {
    var form = layui.form;
    form.render();
    function closeIframe(){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    }
    $("#doCancel").click(function () {
        closeIframe();
    });
    form.on('submit(*)', function(data){
        //console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        //return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        $.ajax({
            type:'post',
            url:saveResourceUrl,
            contentType: "application/json;charset=UTF-8",
            data:JSON.stringify(data.field),
            success:function(res){

                layer.msg('保存成功', {icon: 1, time: 1200}, function () {
                    closeIframe();
                    var zTreeObj = parent.$.fn.zTree.getZTreeObj("treeDemo");
                    if(data.field.id!=null&&data.field.id!==''){
                        var nodeByParam = zTreeObj.getNodeByParam("id", data.field.id, null);
                        nodeByParam.name=data.field.name;
                        zTreeObj.updateNode(nodeByParam);
                    }else {
                        var treeNode = zTreeObj.getNodeByParam("id", data.field.pId, null);
                        zTreeObj.addNodes(treeNode, {id:res, pId:data.field.pId, name:data.field.name});
                    }

                })
            },
            error:function (data) {
                layer.msg('保存失败', {icon: 2, time: 1200}, function () {
                    closeIframe();
                })
            }
        }) ;
    });



})