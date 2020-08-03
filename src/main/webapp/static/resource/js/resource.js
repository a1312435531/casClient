var saveResourcePageUrl="/saveResourcePage";
var deleteResourceUrl="/deleteResource";
layui.use(['form','layer'],function () {
    var layer=layui.layer;

    $("#doAdd").click(function () {
        var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
        saveResource("新增顶层节点",'pId',null);
    });

    function zTreeOnClick(event,treeId,treeNode){
        //console.log(treeNode);
        //var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
        //zTreeObj.cancelSelectedNode(treeNode);
        //zTreeObj.expandNode(treeNode);
    };
    var setting = {
        view: {
            dblClickExpand: true,//双击节点时，是否自动展开父节点的标识
            showLine: true,//是否显示节点之间的连z线
            fontCss:{'font-weight':'600','font-size':'20px'},//字体样式函数
            selectedMulti: true ,//设置是否允许同时选中多个节点
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
        },
        /*check:{
            //chkboxType: { "Y": "ps", "N": "ps" },
            chkboxType: { "Y": "s", "N": "ps" },
            chkStyle: "checkbox",//复选框类型
            enable: true //每个节点上是否显示 CheckBox
        },*/
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 12
            },
            key:{
                url:'xUrl'
            }
        },
        callback:{
            onClick:zTreeOnClick,
            beforeClick:function(){
            },
            onAsyncSuccess:onAsyncSuccess,
            beforeRemove:beforeRemove,
        },
        async:{
            enable: true,
            url:'/loadList',
            autoParam:["id"],
        }
    };


    var newCount = 1;
    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("."+treeNode.tId+"_group").length>0) return;
        var addStr ="<span class='button add "+treeNode.tId+"_group' id='" + treeNode.tId
            + "_add' title='新增' onfocus='this.blur();'></span>";
        addStr+= "<span class='button edit "+treeNode.tId+"_group' id='" + treeNode.tId
            + "_edit' title='编辑' onfocus='this.blur();'></span>";
        if(treeNode.children==null){
            addStr+= "<span class='button remove "+treeNode.tId+"_group' id='" + treeNode.tId
                + "_remove' title='删除' onfocus='this.blur();'></span>";
        }

        sObj.after(addStr);
        var addBtn = $("#"+treeNode.tId+"_add");
        var editBtn = $("#"+treeNode.tId+"_edit");
        var removeBtn = $("#"+treeNode.tId+"_remove");
        if (addBtn){
            addBtn.bind("click", function(){
                saveResource("新增节点",'pId',treeNode);
                return false;
            });
        }
        if (editBtn){
            editBtn.bind("click", function(){
                saveResource("编辑节点",'id',treeNode);
                return false;
            });
        }
        if (removeBtn){
            removeBtn.bind("click", function(){
                deleteResource(treeNode);
                return false;
            });

        }

    };
    function removeHoverDom(treeId, treeNode) {
        $("#"+treeNode.tId+"_add").unbind().remove();
        $("#"+treeNode.tId+"_edit").unbind().remove();
        $("#"+treeNode.tId+"_remove").unbind().remove();
    };

    function onAsyncSuccess(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        showZtreeNum(true,zTree,2);
        //classLevel即你动态赋值的想要展开的确定的等级
        // 展开到确定的层数 思想如下 记住你跳转时选中的层级
        function showZtreeNum(status,childNodes,level){
        // 调用时首次调用是showZtreeNum(true,zTree,classLevel) 获取总的结点
        // 然后递归调用其孩子结点
            if(status){
                var rootNodes = zTree.getNodes();
                showZtreeNum(false,rootNodes,level);//递归
            }else{
                if(childNodes){
                    var len=childNodes.length;
                    if(level<childNodes[0].level+1){
                        return;
                    }
                    for (var i = 0; i < len; i++) {
                        zTree.expandNode(childNodes[i], true, false, false, true);
                        var child=childNodes[i].children;
                        showZtreeNum(false,child,level);//递归
                    }
                }
            }
        }
    }

    function beforeRemove(treeId,treeNode){
        console.log(treeNode)
        return false;
    }

    //增加和编辑页面
    function saveResource(title,id,treeNode){
        if(treeNode==null){
            layer.open({
                type: 2,
                title: title,
                content: saveResourcePageUrl, //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                offset: '100px',
                area: ['600px', '450px'],
                anim: 0
            });
        }else {
            layer.open({
                type: 2,
                title: title,
                content: saveResourcePageUrl+'?'+id+'='+treeNode.id, //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                offset: '100px',
                area: ['600px', '450px'],
                anim: 0
            });
        }

    }

    //删除
    function deleteResource(treeNode){
        layer.open({
            type: 1
            , title: '删除角色'
            , offset: ['120px'] //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , id: 'layerDemo' //防止重复弹出
            , content: '<div style="padding: 20px 100px;">'
                + '<i class="layui-icon layui-icon-help" style="font-size: 30px; color: rgb(255,87,34);"></i>'
                + '确定删除该节点吗'
                + '</div>'
            , btn: ['确定', '取消']
            , btnAlign: 'c' //按钮居中
            , yes: function () {
                $.ajax({
                    type: 'get',
                    url: deleteResourceUrl+'/' +treeNode.id,
                    success: function (res) {
                        layer.closeAll();
                        layer.msg('删除成功', {icon: 1, time: 1200,offset:'170px'});
                        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                        zTree.removeNode(treeNode);
                    },
                    error: function (data) {
                        layer.closeAll();
                        layer.msg('删除失败', {icon: 2, time: 1200,offset:'170px'})
                    }
                });

            },
            btn2: function () {
                layer.closeAll();
            }
        });

    }

    $.fn.zTree.init($("#treeDemo"), setting);

});


$(document).ready(function(){

});