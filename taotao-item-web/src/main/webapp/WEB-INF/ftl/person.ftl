<html>

<body>
    <#list person as l>
    <#if l_index%2==0>
        偶数行<br>
        <#else>
        奇数行<br>
    </#if>
        序号:${l_index} 
        姓名：${l.name}  
        年龄:${l.age}  
        性别:${l.sex}  
      <br> <br>
    </#list>
  
 <br> <br>
 日期的处理：${date?date} <br>
 日期的处理：${date?time}<br>
  日期的处理：${date?datetime} <br>
  自定义日期的处理：${date?string("yyyy/MM/dd HH/mm/ss")}<br>
NUll值的处理(!后面指定默认值)：${abc!123} <br>
     判断是否有值：<#if abc??>
        有值<#else>
        无值
     </#if>
     <br>
     incude标签测试：
     <#include "helloworld.ftl">
     
     
</body>




</html>