<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	
	$("#import").click(function(){
		 $.messager.alert('提示','正在导入，请等待...');
		 $("#import").text("正在导入");
		 $("#import").addClass("disabled","disabled");
	       $.post("/index/import", function(data){
	           if(data.status == 200){
	               $.messager.alert('提示','导入成功!');
	               $("#import").text("开始导入索引库");
	               $("#import").removeClass("disabled");
	           }
	           }); 
	   })
})
   

</script>

<div style="padding: 10px 10px 10px 10px">

    需先导入索引库，否则不能实现搜索功能<br><br>
	<a class="easyui-linkbutton" id="import" >开始导入索引库</a>
</div>
