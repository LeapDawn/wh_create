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
			<ul class="nav nav-pills nav-stacked">
			  	<li role="presentation"><a href="${fyForwardUrl}&forwardPage=main.jsp">仓库列表</a></li>
			    <li role="presentation"><a href="${fyForwardUrl}&forwardPage=position.jsp">仓位列表</a></li>
			    <li role="presentation"><a href="${fyForwardUrl}&forwardPage=storage.jsp">货架列表</a></li>
			    <li role="presentation"><a href="${fyForwardUrl}&forwardPage=configuration.jsp">配置信息</a></li>
			    <li role="presentation" class="active"><a href="${fyForwardUrl}&forwardPage=materiel.jsp"><span class="fa fa-arrow-right"></span> 物料列表</a></li>
			</ul>
		</div>

		<div class="content-main">

			<div class="tools">
				<div class="search">
					<form class="form-inline">
					  <div class="form-group">
					    <input type="text" class="form-control" id="filter" placeholder="请输入搜索内容">
					  </div>
					  <button type="button" id="search" class="btn btn-primary" style="margin-left:4px;">搜索</button>
					</form>
				</div>
				<div class="operation">
					<!-- <button type="button" class="btn btn-success">新增</button> -->
				</div>
			</div>

			<div class="tableDIV">
				<table class="table table-bordered">
				 	<thead>
				 		<tr>
				 			<th>物料名称</th>
				 			<th>物料模型</th>
				 			<th>物料规格</th>
				 		</tr>
				 	</thead>

				 	<tbody>
				
				 	</tbody>
				</table>
				<div  id="page1" align="right" style="margin-bottom:20px;margin-top:30px;">
				</div>
			</div>
		</div>
		
		
		
	</div>

	<script src="${resourceUrl}js/jquery.min.js"></script>
	<script src="${resourceUrl}plugins/bootstrap-3.3.5/js/bootstrap.min.js"></script>
	<script src="${resourceUrl}plugins/laypage/laypage.js"></script>
	<script src="${resourceUrl}js/publicDom.js"></script>
	<script src="${resourceUrl}js/materiel.js"></script>
</body>
</html>