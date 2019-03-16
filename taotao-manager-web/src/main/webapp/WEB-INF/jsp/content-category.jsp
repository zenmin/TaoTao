<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	 <ul id="contentCategory" class="easyui-tree">
    </ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
$(function(){
	$("#contentCategory").tree({
		url : '/content/category/list',
		animate: true,
		method : "GET",
		onContextMenu: function(e,node){              //当点击右键的时候e是时间  node是当前节点
            e.preventDefault();                     
            $(this).tree('select',node.target);         //把当前节点变为选中状态
            $('#contentCategoryMenu').menu('show',{         //在什么地方显示右键菜单
                left: e.pageX,
                top: e.pageY
            });
        },
        onAfterEdit : function(node){
        	var _tree = $(this);
        	
        	//如果是新节点
        	if(node.id == 0){
        		// 新增节点  把父节点id和新增的节点名传过去
        		$.post("/content/category/create",{parentId:node.parentId,name:node.text},function(data){
        			if(data.status == 200){
        				//如果新增成功  就更新html页面
        				_tree.tree("update",{           //update  直接更新节点属性的一个方法
            				target : node.target,       //更新父节点
            				id : data.data.id           //响应结果集中的id  设置新的id给节点
            			});
        			}else{
        				$.messager.alert('提示','创建'+node.text+' 分类失败!');
        			}
        		});
        	}else{
        		//如果节点id不为0肯定是更新了
        		$.post("/content/category/update",{id:node.id,name:node.text});
        	}
        }
	});
});
function menuHandler(item){
	var tree = $("#contentCategory");
	var node = tree.tree("getSelected");       //取选中节点
	if(item.name === "add"){
		tree.tree('append', {
            parent: (node?node.target:null),   //得到父节点
            data: [{
                text: '新建分类',
                id : 0,
                parentId : node.id
            }]
        }); 
		var _node = tree.tree('find',0);      //找到id为0的节点
		tree.tree("select",_node.target).tree('beginEdit',_node.target);      //吧它变为可编辑的节点
	}else if(item.name === "rename"){
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			if(r){
				$.post("/content/category/delete/",{parentId:node.parentId,id:node.id},function(data){
					tree.tree("remove",node.target);
					if(data.status == 200){
                        _tree.tree("update",{
                            target : node.target,       //父节点
                            id : data.data.id           //响应结果集中的id
                        });
                    }
				});	
			}
		});
	}
}
</script>