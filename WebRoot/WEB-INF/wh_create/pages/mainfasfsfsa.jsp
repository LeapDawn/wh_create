<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <script src="http://libs.baidu.com/jquery/1.9.1/jquery.js"></script>
    <link rel='stylesheet' href='${resourceUrl}bootstrap-3.3.5-dist/css/bootstrap.css'/> 
    <script src="${resourceUrl}bootstrap-3.3.5-dist/js/bootstrap.js"></script>
    <link rel="stylesheet" href="${resourceUrl}bootstrap-table-master/dist/bootstrap-table.min.css"/>

<script src="${resourceUrl}bootstrap-table-master/dist/bootstrap-table.min.js"></script>
<script src="${resourceUrl}bootstrap-table-master/dist/bootstrap-table-zh-CN.js"></script>

</head>
<body>
      <img src="${resourceUrl}img/header.png" class="img-responsive" alt="Responsive image">
<hr>
    <h2 class="active-tab text-center text-primary"><strong>仓库工具</strong></h2>
<hr>
<div class="container">    
   <div id="toolbar" class="btn-group">
   <button id="btn_add" type="button" class="btn btn-default">
    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
   </button>
   <button id="btn_edit" type="button" class="btn btn-default">
    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
   </button>
   <button id="btn_delete" type="button" class="btn btn-default">
    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
   </button>
  </div>
  <table id="tb_departments"></table>
</div>  


<script type="text/javascript">
$(function () {
 
 //1.初始化Table
 var oTable = new TableInit();
 oTable.Init();
 
 //2.初始化Button的点击事件
 var oButtonInit = new ButtonInit();
 oButtonInit.Init();
 
});
 
 
var TableInit = function () {
 var oTableInit = new Object();
 //初始化Table
 oTableInit.Init = function () {
  var fyToolUrl = '${fyToolUrl}';
  $('#tb_departments').bootstrapTable({
   url: fyToolUrl + '&toolAction=listWarehouse',   //请求后台的URL（*）
   method: 'post',      //请求方式（*）
   toolbar: '#toolbar',    //工具按钮用哪个容器
   striped: true,      //是否显示行间隔色
   cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
   pagination: true,     //是否显示分页（*）
   sortable: false,      //是否启用排序
   sortOrder: "asc",     //排序方式
   queryParams: oTableInit.queryParams,//传递参数（*）
   sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
   pageNumber:1,      //初始化加载第一页，默认第一页
   pageSize: 10,      //每页的记录行数（*）
   pageList: [10, 25, 50, 100],  //可供选择的每页的行数（*）
   search: true,      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
   strictSearch: true,
   showColumns: true,     //是否显示所有的列
   showRefresh: true,     //是否显示刷新按钮
   minimumCountColumns: 2,    //最少允许的列数
   clickToSelect: true,    //是否启用点击选中行
   height: 500,      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
   uniqueId: "ID",      //每一行的唯一标识，一般为主键列
   showToggle:true,     //是否显示详细视图和列表视图的切换按钮
   cardView: false,     //是否显示详细视图
   detailView: false,     //是否显示父子表
   columns: [
                  {
                    title: '地址',
                      field: 'address',
                      align: 'center',
                      valign: 'middle'
                  }, 
                  {
                      title: '保管人',
                      field: 'personName',
                      align: 'center',
                      valign: 'middle',
                  }, 
                  {
                      title: '仓库情况',
                      field: 'whDiscard',
                      align: 'center'
                  },
                   {
                      title: '仓库ID',
                      field: 'whId',
                      align: 'center'
                  },
                  {
                      title: '仓库名',
                      field: 'whName',
                      align: 'center'
                  },
                  {
                      title: '仓库remark',
                      field: 'whRemark',
                      align: 'center'
                  },
                  {
                      title: '电话',
                      field: 'whTel',
                      align: 'center',
                  },
                  {
                      title: '仓库类型',
                      field: 'whType',
                      align: 'center',
                  }
              ]
  });
 };
 
 //得到查询的参数
 oTableInit.queryParams = function (params) {
  var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   limit: params.limit, //页面大小
   offset: params.offset, //页码
   departmentname: $("#txt_search_departmentname").val(),
   statu: $("#txt_search_statu").val()
  };
  return temp;
 };
 return oTableInit;
};
 
 
var ButtonInit = function () {
 var oInit = new Object();
 var postdata = {};
 
 oInit.Init = function () {
  //初始化页面上面的按钮事件
 };
 
 return oInit;
};

</script>  
<!-- 
<script type="text/javascript">
$(function () {
var $table = $('#table');
var fyToolUrl = '${fyToolUrl}';
    $table.bootstrapTable({
    url:fyToolUrl + '&toolAction=listWarehouse',
    method:"post",
    dataType: "json",
    pagination: true, //分页
    singleSelect: false,
    search: true, //显示搜索框
    sidePagination: "server", //服务端处理分页
          columns: [
                  {
                    title: '地址',
                      field: 'address',
                      align: 'center',
                      valign: 'middle'
                  }, 
                  {
                      title: '保管人',
                      field: 'personName',
                      align: 'center',
                      valign: 'middle',
                  }, 
                  {
                      title: '仓库情况',
                      field: 'whDiscard',
                      align: 'center'
                  },
                   {
                      title: '仓库ID',
                      field: 'whId',
                      align: 'center'
                  },
                  {
                      title: '仓库名',
                      field: 'whName',
                      align: 'center'
                  },
                  {
                      title: '仓库remark',
                      field: 'whRemark',
                      align: 'center'
                  },
                  {
                      title: '电话',
                      field: 'whTel',
                      align: 'center',
                  },
                  {
                      title: '仓库类型',
                      field: 'whType',
                      align: 'center',
                  }
              ]
               });
});
               </script>
             
</script>  
 -->
</body>