<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<link rel="stylesheet" type="text/css" href="../../static/style.css" />
<link rel="stylesheet"  href="layui/css/layui.css" media="all" >
</head>
<body>
 
<div class="workingroom">
    <div class="loginDiv">

    <c:if test="${!empty profile.id}">
        <span class="desc">你好，${profile.id}，</span>
        <a href="logout">退出</a><br>
    </c:if>
         
    <a href="listProduct">查看产品</a><span class="desc">(要有查看产品权限, zhang3有，li4 有)</span><br>
    <a href="deleteProduct">删除产品</a><span  class="desc">(要有删除产品权限, zhang3有，li4 有)</span><br>
    <a href="deleteOrder">删除订单</a><span class="desc">(要有删除订单权限, zhang3有，li4没有)</span><br>
</div>
</div>
<div class="layui-inline">

<ul class="layui-nav layui-nav-tree layui-inline" lay-filter="demo" style="margin-right: 10px;">
    <li class="layui-nav-item layui-nav-itemed">
        <a href="javascript:;">默认展开</a>
        <dl class="layui-nav-child">
            <dd><a href="javascript:;">选项一</a></dd>
            <dd><a href="javascript:;">选项二</a></dd>
            <dd><a href="javascript:;">选项三</a></dd>
            <dd><a href="">跳转项</a></dd>
        </dl>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:;">解决方案</a>
        <dl class="layui-nav-child">
            <dd><a href="">移动模块</a></dd>
            <dd><a href="">后台模版</a></dd>
            <dd><a href="">电商平台</a></dd>
        </dl>
    </li>
    <li class="layui-nav-item"><a href="">云市场</a></li>
    <li class="layui-nav-item"><a href="">社区</a></li>
</ul>
</div>
<script src="layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use('element', function(){
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
        var layer =layui.layer;
        //监听导航点击
        element.on('nav(demo)', function(elem){
            //console.log(elem)
            layer.msg(elem.text());
        });
    });
</script>
</body>
</html>