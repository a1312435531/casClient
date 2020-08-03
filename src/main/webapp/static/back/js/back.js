layui.use('element', function(){
    var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    var layer =layui.layer;
    //var url =document.URL;
    //监听导航点击
    element.on('nav(demo)', function(elem){
        //console.log(elem)
        //console.log(url.substring(0,url.lastIndexOf("/")))
        //layer.msg(elem.text());
    });
});
$(document).ready(function() {
    $(".btn").click(function () {
        console.log(54531)
    });
    $('.content').on('click','.child',function(){
        //$(this).siblings('li').attr('class','layui-nav-itemed');
        //$(this).find("dd").removeClass("layui-this");
        $(this).parents(".layui-nav").siblings().find("dd").removeClass("layui-this");

        loadIframe($(this).find("a").attr("href"));
    });
    $('.content').on('click','.layui-nav-item ',function(){
        $(this).parents(".layui-nav").siblings().find(".layui-nav-item").removeClass("layui-nav-itemed");
    });



});

function loadIframe(url){
    var u = window.location.href;
    var end = u.indexOf("#");
    var rurl = u.substring(0,end);
    //设置新的锚点
    window.location.href = rurl + "#" + url;
}
document.addEventListener('DOMContentLoaded', function () {
    var hash = location.hash;
    var url = hash.substring(1,hash.length);
    $("#baseIframe").attr("src", url);
}, false)


