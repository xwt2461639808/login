package com.igeek.order.servlet;

import com.igeek.order.entity.Items;
import com.igeek.order.service.ItemService;
import com.igeek.order.vo.PageVO;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@MultipartConfig
@WebServlet(name = "ItemServlet" , urlPatterns = "/items" )
public class ItemServlet extends HttpServlet {

    private ItemService service = new ItemService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code!=null && !code.equals("")){
            switch (code){
                case "viewAll":
                    //查询所有 条件+分页
                    String query = request.getParameter("query");
                    String page = request.getParameter("pageNow");
                    if(query==null){
                        query = "";   //默认查询所有
                    }
                    Integer pageNow = 0;
                    if(page==null){
                        pageNow = 1;  //默认查询第一页
                    }else{
                        pageNow = Integer.valueOf(page);
                    }

                    //执行分页的业务
                    PageVO<Items> vo = service.fenye(query, pageNow);
                    request.setAttribute("vo",vo);
                    request.getRequestDispatcher(request.getContextPath()+"/items/itemsList.jsp").forward(request,response);
                    break;
                case "validate":
                    //校验商品名称是否存在
                    String name = request.getParameter("name");
                    //true不可用   false可用
                    boolean flag = service.validateName(name);
                    String message = "";
                    if(flag){
                        message  = "商品名称已占用";
                    }else{
                        message  = "商品名称未占用";
                    }
                    //将flag和message，都以JSON格式传递至页面
                    JSONObject obj = new JSONObject();
                    obj.put("flag",Boolean.toString(flag));
                    obj.put("msg",message);
                    //响应将JSON数据传输至页面
                    PrintWriter out = response.getWriter();
                    out.write(obj.toString());
                    out.flush();
                    out.close();
                    break;
                case "add":
                    //商品上架
                    //先收集普通域数据
                    name = request.getParameter("name");
                    String detail = request.getParameter("detail");
                    double price = Double.parseDouble(request.getParameter("price"));
                    String time = request.getParameter("createtime");
                    Date createtime = null;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if(time!=null && !time.equals("")){
                        try {
                            createtime = sdf.parse(time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    //封装Items对象
                    Items items = new Items(name,price,createtime,detail);
                    //再收集上传信息  此处应该与input type="file"的name属性值
                    Part part = request.getPart("file");
                    if(part!=null){
                        String oldName = part.getHeader("content-disposition");
                        if(oldName!=null && oldName.lastIndexOf(".")>0){
                            String newName = UUID.randomUUID()+oldName.substring(oldName.lastIndexOf("."),oldName.length()-1);
                            //存储至图片服务器
                            part.write("F:\\镇江极客\\java备课资料\\5.JavaWeb\\testcode\\temp\\"+newName);
                            //Items对象设置图片信息
                            items.setPic("/temp/"+newName);
                        }
                    }

                    //执行添加商品的业务
                    if(items!=null){
                        boolean add = service.add(items);
                        if(add){
                            //响应重定向至商品列表页 ， 避免重复提交表单
                            response.sendRedirect("/items?code=viewAll");
                        }else{
                            //添加失败，则回显信息
                            request.setAttribute("items",items);
                            request.getRequestDispatcher("/items/addItem.jsp").forward(request,response);
                        }
                    }

                    break;
                case "viewOne":
                    //查询商品详情
                    int id = Integer.parseInt(request.getParameter("id"));
                    items = service.viewOne(id);
                    if(items!=null){
                        request.setAttribute("items",items);
                        request.getRequestDispatcher("/items/editItem.jsp").forward(request,response);
                    }
                    break;
                case "modify":
                    //商品修改
                    //先收集普通域数据
                    id = Integer.parseInt(request.getParameter("id"));
                    name = request.getParameter("name");
                    detail = request.getParameter("detail");
                    price = Double.parseDouble(request.getParameter("price"));
                    time = request.getParameter("createtime");
                    createtime = null;
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if(time!=null && !time.equals("")){
                        try {
                            createtime = sdf.parse(time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    //封装Items对象
                    items = new Items(id,name,price,createtime,detail);
                    //再收集上传信息  此处应该与input type="file"的name属性值
                    part = request.getPart("file");
                    if(part!=null){
                        String oldName = part.getHeader("content-disposition");
                        if(oldName!=null && oldName.lastIndexOf(".")>0){
                            String newName = UUID.randomUUID()+oldName.substring(oldName.lastIndexOf("."),oldName.length()-1);
                            //存储至图片服务器
                            part.write("F:\\镇江极客\\java备课资料\\5.JavaWeb\\testcode\\temp\\"+newName);
                            //Items对象设置图片信息
                            items.setPic("/temp/"+newName);
                        }else{
                            //若没有真正上传图片，设置上商品的原图片
                            String pic = service.viewOne(id).getPic();
                            items.setPic(pic);
                        }
                    }

                    //修改
                    if(items!=null){
                        boolean b = service.modify(items);
                        if(b){
                            response.sendRedirect("/items?code=viewAll");
                        }else{
                            //通过id，获取原数据库中的商品信息
                            items = service.viewOne(id);
                            request.setAttribute("items",items);
                            request.getRequestDispatcher("/items/editItem.jsp").forward(request,response);
                        }
                    }
                    break;
                case "deleteAll":
                    //下架商品
                    String[] ids = request.getParameterValues("id");
                    service.deleteAll(ids);
                    response.sendRedirect("/items?code=viewAll");
                    break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
