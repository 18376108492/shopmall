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


</body>

</html>