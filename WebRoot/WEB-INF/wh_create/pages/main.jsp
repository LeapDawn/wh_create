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
<table id="table"></table>
</div>

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



<!-- 
<script type="text/javascript">
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
                    title: '仓库名称',
                      field: 'whName',
                      align: 'center',
                      valign: 'middle'
                  }, 
                  {
                      title: '仓库类型',
                      field: 'whType',
                      align: 'center',
                      valign: 'middle',
                  }, 
                  {
                      title: '保管人',
                      field: 'personName',
                      align: 'center'
                  },
                  {
                      title: '地址',
                      field: 'address',
                      align: 'center'
                  },
                  {
                      title: '电话',
                      field: 'whTel',
                      align: 'center',
                  },
                  {
                      title: '操作',
                      field: 'whId',
                      align: 'center',
                      formatter:function(value,row,index){  
                   var e = '<a href="#" mce_href="#" onclick="edit(\''+ whId + '\')">编辑</a> ';  
                   var d = '<a href="#" mce_href="#" onclick="del(\''+ whId +'\')">删除</a> ';  
                        return e+d;  
                    } 
                  }
              ]
               });
               </script>
                -->
</body>