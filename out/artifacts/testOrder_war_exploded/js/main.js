//显示登陆界面
function login() {
    //ajax 异步刷新，刷新局部
    $.ajax({
        //get方式+url?key=value    post方式+data
        type:"get",
        //异步刷新，请求的地址
        url:"login.jsp",  //---/user
        //成功后执行function回调函数
        success:function(content){
            //content形参，名字可变，代表发送请求后收到的响应内容
            //html函数，替换类名class叫做main这块区域的HTML代码
            $(".main").html(content);
        }/*,
        error:function(content){
            alert("失败")
        }*/
    })
}

//登陆功能
function loginServlet() {
    //收集普通表单数据
    var d = $(".loginForm").serialize();
    $.ajax({
        type:"post",
        url:"/user?code=login",
        data:d,
        success:function(c){
            $(".main").html(c)
        }
    })
}

//显示注册界面
function regist() {
    $.ajax({
        type:"get",
        url:"regist.jsp",
        success:function(content){
            $(".main").html(content);
        }
    })
}

//分页+搜索查看数据
function viewAll() {
    var path = $("#path").val();
    var query = $("#searchName").val();
    //若点击左侧按钮进入，没有搜索框，无法获取查询条件 ， 默认查询所有
    if(query == undefined){
        query = "";
    }
    $.ajax({
        type:"get",
        url:"/items?code=viewAll&query="+query,
        success:function (content) {
            $(".main").html(content);
        }
    })
}

//显示添加商品页面
function showAdd() {
    $.ajax({
        type:"get",
        url:"items/addItem.jsp",
        success:function (c) {
            $(".main").html(c);
        }
    })
}


/*JSON(JavaScript object Notation，JS对象简谱）是一种轻量级的数据交换格式。
在线JSON格式校验转化网址: json.cn
1.JSON语法格式:{"键1":值1,"键2":值2,....}
2.value值类型:STRING字符串、NUMIBER数值型、NULL、布尔类型TRUE/FALSE、对象、[]数组2.
3.注意: key键必须是双引号，声明json字符串必须使用单引号将其括起来
4.Java服务端提供JSON技术: FastJson、Jackson、Gson等
5.JavaScript前端提供解析技术:
    parse(string)，解析JSON串
    stringify (object)，转化成字符串

    //声明JSON字符串
    var str = '{"k1" :"v1","k2":10,"k3":null,"k4":true,"k5":false,"k6":{"name":"zs","pwd":"123"},"k7":[10,20,30]}';
    console.log(str)
    /通过JSON.parse(string)，解析JSON字符串，解析成对象
    var obj = JSON.parse(str)
    console.log(obj)
    console.log(obj.k1)             //v1
    console.log(obj.k4)             //true
    console.log(obj.k6.name)        //zs
    console.log(obj.k7)             //[10，20,30]
    //通过JSON.stringify(object)，将对象，转化成JSON字符串
    var jsonStr = JSON.stringify(obj);
    console.log(jsonStr)
*/

/*1.JSON语法格式:[值1,值2,.....]
2.value值类型:STRING字符串、NUNBER数值型、NULL、布尔类型TRUE/FALSE、{}对象、[]数组
    var string = '["v1",110,null,true,false,{ "name": "1s"},[10,20,30]]';
    var o = JSON.parse( string);
    console.log(o);
*/


//校验商品名称是否重复
function validateName() {
    $.ajax({
        type:"post",   //请求方式
        url:"/items?code=validate",  //请求url
        data:{"name":$("#name").val()},  //请求参数  {"key":value}
        dataType:"json",   //以json方式，处理响应结果
        success:function(obj){  //执行成功后，回调执行的函数
            console.log(obj)
            // var o = JSON.parse(obj);
            // console.log(o)
            // console.log(o.flag)

            if(obj.flag == "true"){
                $("#nameMsg").html("<font style='color: red'>"+obj.msg+"</font>")
            }else if(obj.flag == "false"){
                $("#nameMsg").html("<font style='color: green'>"+obj.msg+"</font>")
            }
        }
    })
}

//商品上架
function add() {
    //收集二进制表单数据
    var data = new FormData($("#f")[0]);
    $.ajax({
        type:"post",
        url:"/items?code=add",
        data:data,
        contentType:false,
        processData:false,
        success:function(content) {
            $(".main").html(content);
        }
    })
}

//商品下架
function deleteAll(s){
    var idStr = "";
    if(s=="all"){
        //删除所有
        $(".single").each(function () {
            if($(this).prop("checked")){
                var id = $(this).parents(".data").find(".id").text();
                idStr = idStr + "&id="+id;   // &id=1&id=2..
                console.log(idStr)
            }
        })
    }else{
        //删除单项
        idStr = "&id="+s;     // &id=1
        console.log(idStr)
    }
    $.ajax({
        type:"post",
        url:"/items?code=deleteAll"+idStr,
        success:function(c){
            $(".main").html(c)
        }
    })
}

//查询单个商品信息
function viewOne(id){
    $.ajax({
        type:"get",
        url:"/items?code=viewOne&id="+id,
        success:function (c) {
            $(".main").html(c);
        }
    })
}

//修改商品信息，校验商品名是否存在
function validateModifyName(oldName) {
   var newName = $("#name").val();
   if(oldName!=newName){
        $.ajax({
            type:"get",
            url:"/items?code=validate&name="+newName,
            dataType:"json",
            success:function (obj) {
                if(obj.flag == "true"){
                    $("#nameMsg").html("<font style='color: red'>"+obj.msg+"</font>")
                }else if(obj.flag == "false"){
                    $("#nameMsg").html("<font style='color: green'>"+obj.msg+"</font>")
                }
            }
        })
   }else{
       //修改后名称与修改前一致，则清空提示信息
       $("#nameMsg").html("");
   }
}

//修改商品信息
function modify() {
    var data = new FormData($("#editForm")[0]);
    $.ajax({
        type:"post",
        url:"/items?code=modify",
        data:data,
        contentType:false,
        processData:false,
        success:function (c) {
            $(".main").html(c)
        }
    })
}