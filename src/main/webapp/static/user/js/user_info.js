layui.use("form",function () {
    var form = layui.form;
    form.render();
    function closeIframe(){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    }
    $("#doCancel").click(function () {
        //当你在iframe页面关闭自身时
        closeIframe();
    });
    form.on('submit(*)', function(data){
        console.log(JSON.stringify(data.field)) //当前容器的全部表单字段，名值对形式：{name: value}
        //return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        $.ajax({
            type:'post',
            url:'/config/saveUser',
            contentType: "application/json;charset=UTF-8",
            data:JSON.stringify(data.field),
            success:function(res){
                layer.msg('保存成功', {icon: 1, time: 1200, offset: 'auto'}, function () {
                    closeIframe();
                })
            },
            error:function (data) {
                layer.msg('保存失败', {icon: 2, time: 1200, offset: 'auto'}, function () {
                    closeIframe();
                })
            }
        }) ;
    });



})