<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title></title>
	<link rel="stylesheet" href="${resourceUrl}plugins/bootstrap-3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" href="${resourceUrl}css/font-awesome.css">
	<link rel="stylesheet" href="${resourceUrl}css/reset.css">
</head>
<script type="text/javascript">
	var fyToolUrl = '${fyToolUrl}';
</script>
<body>

	<div class="header" style="">
		
	</div>

	<div class="content">
		<div class="content-nav" style="min-height:516px;">
		<h3 style="margin-top:1px;margin-bottom:32px;font-family:'楷体';font-weight:bold;">仓库建仓工具</h3>
			<ul class="nav nav-pills nav-stacked">
			  	<li role="presentation"><a href="${fyForwardUrl}&forwardPage=main.jsp">仓库列表</a></li>
			    <li role="presentation" class="active"><a href="${fyForwardUrl}&forwardPage=position.jsp"><span class="fa fa-arrow-right"></span> 仓位列表</a></li>
			    <li role="presentation"><a href="${fyForwardUrl}&forwardPage=storage.jsp">货架列表</a></li>
			    <li role="presentation"><a href="${fyForwardUrl}&forwardPage=configuration.jsp">配置信息</a></li>
			    <li role="presentation"><a href="${fyForwardUrl}&forwardPage=materiel.jsp">物料列表</a></li>
			</ul>
		</div>

		<div class="content-main">

			<div class="tools">
				<div class="dropdown drop-select">
				  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
				     选择仓库
				    <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
				    <li><a href="#">A</a></li>
				  </ul>
				</div>

				<div class="search">
					<form class="form-inline">
					  <div class="form-group">
					    <input type="text" class="form-control" id="filter" placeholder="请输入搜索内容">
					  </div>
					  <button type="button" id="search" class="btn btn-primary" style="margin-left:4px;">搜索</button>
					</form>
				</div>
				<div class="operation">
					<button type="button" class="btn btn-success" id="addBtn"><span class="fa fa-plus"></span> 新增仓位</button>
				</div>
			</div>

			<div class="tableDIV">
				<table class="table table-bordered">
				 	<thead>
				 		<tr>
				 			<th>名称</th>
				 			<th>所属仓库</th>
				 			<th>备注</th>
				 		</tr>
				 	</thead>

				 	<tbody>
				 		<tr>
				 		</tr>
				 	</tbody>
				</table>
				<div  id="page1" align="right" style="margin-bottom:20px;margin-top:30px;">
				</div>
			</div>
		</div>

		<div class="content-main-add" style="display:none;">
			<div class="panel panel-default">		
				<div class="panel-heading">
					仓位详情
					<!-- <a href="##" class="pull-right" ><span class="fa fa-times text-danger" style="font-size:20px;"></span></a> -->
				</div>
				<div class="panel-body">
			      	  <form class="form-horizontal">
			      	  	<div class="form-group">
			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;">仓位名称</label>
			    	  	  	  <div class="col-md-4">
			    	  	  	    <input type="text" class="form-control" id="poName" readonly>
			    	  	  	  </div>&nbsp;&nbsp;&nbsp;
			    	  	  	  <label class="text-primary control-label font-w-bold mustInput" style="display:none;"><span class="fa fa-warning"></span> 必填</label>
			      	  	</div>

			      	  	<div class="form-group">
			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;">所属仓库</label>
			    	  	  	  <div class="col-md-4">
			    	  	  	    <input type="text" class="form-control" id="warehouseName" data-id="" readonly> 
			    	  	  	  </div>
			    	  	  	  <button type="button" class="btn btn-primary" id="selectBtn" disabled="disabled" style="margin-left:14px;" data-toggle="modal" data-target="#myModal">选择仓库</button>&nbsp;&nbsp;&nbsp;
			    	  	  	 <!--  <label class="text-primary control-label font-w-bold mustInput" style="display:none;"><span class="fa fa-warning"></span> 必填</label> -->

			      	  	</div>

			      	  	<div class="form-group">
			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;">备注</label>
			    	  	  	  <div class="col-md-4">
			    	  	  	    <textarea type="text" class="form-control" id="poRemark" rows="4" readonly></textarea>
			    	  	  	  </div>
			      	  	</div>
			      	  </form>

				</div>
				<div class="panel-footer">
					<div class="col-md-offset-2">
						<button type="button" class="btn btn-primary" id="saveBtn" style="display:none;">保存</button>
						<button type="button" class="btn btn-primary" id="modifyBtn" style="margin-left:10px;">修改</button>
						<button type="button" class="btn btn-danger" id="deleteBtn" style="display:none;margin-left:10px;">删除</button>
						<button type="button" class="btn btn-default" id="prevPage" style="margin-left:10px;">返回列表</button>
					</div>
					<div class="clear">
						
					</div>
				</div>
			</div>
		</div>
		
		
	</div>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title text-primary" id="myModalLabel"><span class="fa fa-tag"></span> 请选择所属仓库：</h4>
	      </div>
	      <div class="modal-body repoBelong" style="padding-left:30px;padding-right:30px;">
	        
	      	<table class="table tableInsert">
			 	<thead>
			 		<tr>
			 			<th></th>
			 			<th>仓库名称</th>
			 			<th>仓库类型</th>
			 			<th>管理员</th>
			 			<th>联系电话</th>
			 		</tr>
			 	</thead>

			 	<tbody>
			 	</tbody>
			 
			</table>
	      </div>
	      <div class="modal-footer">
	     	<div  id="page2" style="margin-top:4px;float:left;position:absolute;left:40%;"></div>
	     	<button type="button" class="btn btn-primary confirmSelect">确定</button>
	        <button type="button" class="btn btn-default cancel" data-dismiss="modal">取消</button>

	      </div>
	    </div>
	  </div>
	</div>

	<script src="${resourceUrl}js/jquery.min.js"></script>
	<script src="${resourceUrl}plugins/bootstrap-3.3.5/js/bootstrap.min.js"></script>
	<script src="${resourceUrl}plugins/laypage/laypage.js"></script>
	<script src="${resourceUrl}js/publicDom.js"></script>
	<script src="${resourceUrl}js/position.js"></script>
</body>
</html>