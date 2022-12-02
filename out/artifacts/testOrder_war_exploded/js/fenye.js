//首页
function first(){
    var query = $("#searchName").val();
    var pageNow = 1;
    $.ajax({
        type:"get",
        url:"/items?code=viewAll&query="+query+"&pageNow="+pageNow,
        success:function (content) {
            $(".main").html(content);
        }
    })
}

//末页
function last(myPages) {
    var query = $("#searchName").val();
    var pageNow = myPages;
    $.ajax({
        type:"get",
        url:"/items?code=viewAll&query="+query+"&pageNow="+pageNow,
        success:function (content) {
            $(".main").html(content);
        }
    })
}

//上一页
function pre() {
    var query = $("#searchName").val();
    var pageNow = $("#pageNow").val();
    if(pageNow<=1){
        alert("已至首页")
    }else{
        pageNow = pageNow - 1;
    }
    $.ajax({
        type:"get",
        url:"/items?code=viewAll&query="+query+"&pageNow="+pageNow,
        success:function (content) {
            $(".main").html(content);
        }
    })
}

//下一页
function next(myPages) {
    var query = $("#searchName").val();
    var pageNow = $("#pageNow").val();
    if(pageNow>=myPages){
        alert("已至末页")
    }else{
        pageNow = pageNow - (-1);
    }
    $.ajax({
        type:"get",
        url:"/items?code=viewAll&query="+query+"&pageNow="+pageNow,
        success:function (content) {
            $(".main").html(content);
        }
    })
}

//跳转
function skip(myPages) {
    var query = $("#searchName").val();
    var pageNow = $("#pageNow").val();
    if(pageNow<1 || pageNow>myPages){
        alert("页码超出范围")
        pageNow = 1;
    }
    $.ajax({
        type:"get",
        url:"/items?code=viewAll&query="+query+"&pageNow="+pageNow,
        success:function (content) {
            $(".main").html(content);
        }
    })
}