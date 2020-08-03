layui.use(['element','layer'], function(){
    var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    var layer =layui.layer;
    //var url =document.URL;
    //监听导航点击
    element.on('nav(layadmin-layout-right)', function(elem){
        //console.log(elem)
        //console.log(url.substring(0,url.lastIndexOf("/")))
        //layer.msg(elem.text());
    });

    $('.top').on('click','.layui-nav-child dd',function(){
        $(this).removeClass("layui-this");
        console.log()
        if($(this).index()===1){
            openChangePassword();
        }

        if($(this).index()===0){
            openMyInfo();
        }
    });

    function openMyInfo() {
        layer.open({
            type: 2,
            title: '个人信息',
            content: '/config/userInfo?id='+$("#id").val()  , //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            offset: 'auto',
            area: ['600px', '450px'],
            skin: "layui-layer-molv",
            anim: 0
        });
    }

    function openChangePassword() {
        layer.open({
            type: 2,
            title: '修改密码',
            content: '/config/changePasswordPage?id='+$("#id").val()  , //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            offset: 'auto',
            area: ['600px', '300px'],
            skin: "layui-layer-molv",
            anim: 0
        });
    }
});
$(document).ready(function() {

})