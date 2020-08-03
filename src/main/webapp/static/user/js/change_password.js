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

        var res=$.ajax({
                            type:'post',
                            url:'/config/checkPassword?id='+data.field.id,
                            async:false,
                            success:function (res) {
                                return res;
                            }
                        });
        var md5Password=res.responseText;
        console.log($.md5(data.field.oldPassword).toString())
        if($.md5(data.field.oldPassword).toString()===md5Password){
            if(data.field.password===data.field.confirmPassword){
                var user={
                    id:data.field.id,
                    password:$.md5(data.field.password)
                };

                $.ajax({
                    type:'post',
                    url:'/config/changePassword',
                    contentType: "application/json;charset=UTF-8",
                    data:JSON.stringify(user),
                    success:function(res){
                        layer.msg('修改成功', {icon: 1, time: 1200, offset: '170px'}, function () {
                            closeIframe();
                        })
                    },
                    error:function (data) {
                        layer.msg('修改失败', {icon: 2, time: 1200, offset: '170px'}, function () {
                        })
                    }
                }) ;
            }else{
                layer.msg('确认密码输入错误，请重新确认新密码！', {icon: 0, time: 1200, offset: '170px'})
            }
        }else{
            layer.msg('原密码错误，请重新输入原密码！', {icon: 0, time: 1200, offset: '170px'})
        }

        //return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。

    });



})