<html>
<head>
    <title>student</title>
</head>
<body>
    <div>
        学生信息：<br>
        学生ID：${student.id}<br>
        学生姓名：${student.name}<br>
        学生年龄：${student.age}<br>
        学生地址：${student.add}<br>
    </div>
    <div>
        学生信息表单：<br>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>姓名</th>
                <th>年龄</th>
                <th>地址</th>
            </tr>
            <#list studentList as student>
            <#if student.id%2==0>
            <tr bgcolor="#ff4500">
            <#else>
            <tr bgcolor="orange">
            </#if>
                <th>${student.id}</th>
                <th>${student.name}</th>
                <th>${student.age}</th>
                <th>${student.add}</th>
            </tr>

            </#list>
        </table>

    </div>
<div>
    日期:${date?date}
    日期:${date?string("yyyy/MM/dd HH:mm:ss")}<br>
    null值的处理:${val!"val的值为null"}<br>
    <#--加?或！来处理-->
    判断val的值是否为null:
    <#if val??>
       val的值不为空
       <#else>
       val的值为null
    </#if>
</div>

    <#--模板的引用-->
<div>
    引用模板测试:<#include "hello.ftl">
</div>

</body>

</html>