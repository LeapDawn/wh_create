$(function(){
	M_table.init();
	M_table.getList();
	bindEvent();
})

var M_table = {
	url: publicDom.config.url,
	currentId:-1,
	status:0,//1新增，2更新
	selectObj:{},//单选的仓库
	selectObj_po:{},//单选的仓位
	init:function(){},
	getList:function(curr){},
	createList:function(JsonData){},
	showConfirmModal:function(){},
	getDetailInfo:function(){},
	saveInfo:function(){},
	updateInfo:function(){},
	deleteInfo:function(){},
	getFatherTable:function(){},
	getFatherTable_po:function(){},
}

M_table.init = function () {
	var newURL = M_table.url + 'toolAction=warehouseSelect';
	var newURL_po = M_table.url + 'toolAction=listPosition';
	var param = {

	}
	var param_po = {

	}

	publicDom.getData('post',newURL,param,function(JsonData){
		if(JsonData.state == true){
			console.log(JsonData.value);
			$('.drop-select-1 ul').empty();

			var liItem1 = '<li data-id=""><a href="#">(所有仓库)</a></li>\
							<li role="separator" class="divider"></li>';
			$('.drop-select-1 ul').append(liItem1);

			for( var i = 0; i < JsonData.value.length ; i++ ){
				var liItem = '<li data-id="'+JsonData.value[i].whId+'"><a href="#">'+ JsonData.value[i].whName +'</a></li>';
				$('.drop-select-1 ul').append(liItem);
			}

			$('.drop-select-1 ul li').click(function() {
				var text = $(this).text();
				$('.drop-select-1 button').data('id',$(this).data('id'));
				$('.drop-select-1 button').html(text+' <span class="caret"></span>');
				$('#dropdownMenu2').prop('disabled', false);

				param_po.warehouseId=$('.drop-select-1 button').data('id');

				publicDom.getData('post',newURL_po,param_po,function(data){
					if(data.state == true){
						$('.drop-select-2 ul').empty();

						var liItem2 = '<li data-id=""><a href="#">(所有仓位)</a></li>\
										<li role="separator" class="divider"></li>';
						$('.drop-select-2 ul').append(liItem2);

						for( var i = 0; i < data.value.data.length ; i++ ){
							var liItem3 = '<li data-id="'+data.value.data[i].poId+'"><a href="#">'+ data.value.data[i].poName +'</a></li>';
							$('.drop-select-2 ul').append(liItem3);
						}

						$('.drop-select-2 ul li').click(function() {
							var text = $(this).text();
							$('.drop-select-2 button').data('id',$(this).data('id'));
							$('.drop-select-2 button').html(text+' <span class="caret"></span>');
							$('#dropdownMenu2').prop('disabled', false);
							M_table.getList();
						});
					}
				})

			});

		}else{
		}
	})
}

M_table.getList = function (curr) {
	var newURL = M_table.url + 'toolAction=listShelf';

	var param = {
		positionsId : $('.drop-select-2 button').data('id'),
		shName : $('#filter').val() || '',
		currentPage: curr || 1,
		rows: 12
	}

	publicDom.getData('post',newURL,param,function(JsonData){
		if(JsonData.state == true){
			console.log(JsonData.value.data.length);
			
			M_table.createList(JsonData.value.data);


			laypage({
				cont : 'page1',
				pages :JsonData.value.pages,
				curr : curr || 1,
				skin:'#337ab7',
				skip:false,
				jump : function(obj,first){
					if(!first){
						M_table.getList(obj.curr);	
					}
				}
			})

			$('.tableDIV table tbody tr').click(function() {

				$(this).addClass('bg-info').siblings().removeClass('bg-info');
				$('.content-main').hide();
				$('.content-main-add').show();
				$('#deleteBtn').show();
				$('#selectBtn').hide();
				M_table.currentId = $(this).data('id');

				M_table.getDetailInfo();

			});


		}else{
			M_table.showConfirmModal('错误','success','获取货架列表失败！');
		}
		
	})
	

	
}

M_table.getDetailInfo = function(){
	var newURL = M_table.url + 'toolAction=getShelf';
	var param = {
		shId: M_table.currentId
	}

	publicDom.getData('post',newURL,param,function(JsonData){
		if(JsonData.state == true){
			console.log(JsonData.value);
			$('#shName').data('id',JsonData.value.shId);
			$('#shName').val(JsonData.value.shName);
			$('#shRemark').val(JsonData.value.shRemark);
			$('#warehouseName').val(JsonData.value.warehouseName);
			$('#warehouseName').data('id',JsonData.value.warehouseId);
			$('#positionsName').val(JsonData.value.positionsName);
			$('#positionsName').data('id',JsonData.value.positionsId);
		}else{
			M_table.showConfirmModal('错误','success','获取仓库详情失败！')
		}
	})
}

M_table.createList = function (list) {

	$('.tableDIV table tbody').empty();
	for( var i = 0 ; i < list.length ; i ++ ){
		var tableItem = '<tr data-id="'+list[i].shId+'">\
				 			<td>'+list[i].shName+'</td>\
				 			<td>'+list[i].warehouseName+'</td>\
				 			<td>'+list[i].positionsName+'</td>\
				 			<td>'+list[i].shRemark+'</td>\
				 		</tr>'
		$('.tableDIV table tbody').append(tableItem);

	}
	

}

M_table.saveInfo = function () {
	var newURL = M_table.url + 'toolAction=addShelf';
	var param = {
		positionsId : $('#positionsName').data('id'),
		shName : $('#shName').val(),
		shRemark : $('#shRemark').val()
	}

	publicDom.getData('post',newURL,param,function(data){
		if(data.state==true){
			M_table.showConfirmModal('成功','success','保存成功！');
			$('.confirm').click(function() {
				location.reload();
			});
		}else{
			M_table.showConfirmModal('错误','success','保存失败！')
		}
	})
}

M_table.updateInfo = function() {
	var newURL = M_table.url + 'toolAction=updateShelf';
	var param = {
		shId : $('#shName').data('id'),
		shName : $('#shName').val(),
		shRemark : $('#shRemark').val(),
		positionsId : $('#positionsName').data('id'),
	}
	console.log(param);

	publicDom.getData('post',newURL,param,function(data){
		if(data.state==true){
			M_table.showConfirmModal('成功','success','修改成功！');
			$('.confirm').click(function() {
				location.reload();
			});
		}else{
			M_table.showConfirmModal('错误','success','修改失败！');
		}
	})
}

M_table.deleteInfo = function() {
	var newURL = M_table.url + 'toolAction=deleteShelf';
	var param = {
		shId : $('#shName').data('id')
	}
	publicDom.getData('post',newURL,param,function(data){
		if(data.state==true){
			M_table.showConfirmModal('成功','success','删除成功！');
			$('.confirm').click(function() {
				location.reload();
			});
		}else{
			M_table.showConfirmModal('错误','success','删除失败！');
		}
	})
}

M_table.getFatherTable = function(curr){
	var newURL = M_table.url + 'toolAction=listWarehouse';
	var param = {
		currentPage : curr || 1,
		rows : 6
	}
	publicDom.getData('post',newURL,param,function(data){
		if(data.state==true){
			$('.tableInsert tbody').empty();
			for(var i=0;i<data.value.data.length;i++){
				var tableItem = '<tr>' +
									'<td><input name="selectRadio" type="radio" value="" data-id="' +  data.value.data[i].whId + '" style=""></td>' +
									'<td>' + data.value.data[i].whName + '</td>' + 
									'<td>' + data.value.data[i].whType + '</td>' + 
									'<td>' + data.value.data[i].personName + '</td>' + 
									'<td>' + data.value.data[i].whTel + '</td>' +
								 '</tr>'
				$('.tableInsert tbody').append(tableItem);
			}

			$('.tableInsert input[name="selectRadio"]').click(function() {
				M_table.selectObj.id = $(this).data('id');
				M_table.selectObj.name = $(this).parent().next().text();
				$(this).parent().parent().siblings().removeClass('text-primary');
				$(this).parent().parent().addClass('text-primary');

			});




			laypage({
				cont : 'page2',
				pages :data.value.pages,
				curr : curr || 1,
				skin:'#337ab7',
				skip:false,
				jump : function(obj,first){
					if(!first){
						M_table.getFatherTable(obj.curr);	
					}
				}
			})

		}else{
			M_table.showConfirmModal('错误','success','获取仓库列表失败！');
		}
	})
}

M_table.getFatherTable_po = function(curr){
	var newURL = M_table.url + 'toolAction=listPosition';
	var param = {
		warehouseId : $('#warehouseName').data('id'),
		currentPage : curr || 1,
		rows : 6
	}
	publicDom.getData('post',newURL,param,function(data){
		if(data.state==true){
			$('.tableInsert_1 tbody').empty();
			for(var i=0;i<data.value.data.length;i++){
				var tableItem = '<tr>' +
									'<td><input name="selectRadio" type="radio" value="" data-id="' +  data.value.data[i].poId + '" style=""></td>' +
									'<td>' + data.value.data[i].poName + '</td>' +
									'<td data-id="'+data.value.data[i].warehouseId+'">' + data.value.data[i].warehouseName + '</td>' +
								 '</tr>'
				$('.tableInsert_1 tbody').append(tableItem);
			}

			$('.tableInsert_1 input[name="selectRadio"]').click(function() {
				M_table.selectObj_po.id = $(this).data('id');
				M_table.selectObj_po.name = $(this).parent().next().text();
				$(this).parent().parent().siblings().removeClass('text-primary');
				$(this).parent().parent().addClass('text-primary');

			});




			laypage({
				cont : 'page2',
				pages :data.value.pages,
				curr : curr || 1,
				skin:'#337ab7',
				skip:false,
				jump : function(obj,first){
					if(!first){
						M_table.getFatherTable(obj.curr);	
					}
				}
			})

		}else{
			M_table.showConfirmModal('错误','success','获取仓库列表失败！');
		}
	})
}


var bindEvent = function () {
	$('#addBtn').click(function() {
		M_table.status = 1;

		$('.content-main-add input,.content-main-add textarea').val("");
		$('#selectBtn,#selectBtn-po').show();

		$('.content-main').hide();
		$('.content-main-add').show();
		$('.content-main-add input,.content-main-add textarea').prop('readonly', false);
		$('.content-main-add #warehouseName,.content-main-add #positionsName').prop('readonly', true);
		$('#selectBtn').prop('disabled', false);
		$('#modifyBtn').hide();
		$('#saveBtn').show();
		$('.mustInput').show();

	});

	$('#modifyBtn').click(function() {
		M_table.status = 2;
		$('.content-main-add input,.content-main-add textarea').prop('readonly', false);
		$('.content-main-add #warehouseName,.content-main-add #positionsName').prop('readonly', true);
		$('#selectBtn-po').prop('disabled', false);
		$('#modifyBtn').hide();
		$('#saveBtn').show();
		$('.mustInput').show();
	});

	$('#saveBtn').click(function() {
		if($('#warehouseName').val()==""){
			M_table.showConfirmModal('错误','success','请选择仓库！');
		}else if($('#positionsName').val()==""){
			M_table.showConfirmModal('错误','success','请选择仓位！');
		}else{
			if(M_table.status == 1){
				M_table.saveInfo();
			}else if(M_table.status == 2){
				M_table.updateInfo();
			}else{
				M_table.showConfirmModal('错误','success','发生了问题需要从头操作！')
			}
		}
		
		
	});

	$('#deleteBtn').click(function() {
		M_table.showConfirmModal('警告','delete','是否确定删除？');
		$('.deleteConfirm').click(function() {
			M_table.deleteInfo();
		});

	});


	$('#prevPage').click(function() {
		$('.content-main').show();
		$('.content-main-add').hide();
		$('.bg-info').removeClass('bg-info');
	});

	$('#search').click(function() {
		M_table.getList();
	});

	$('#filter').on('keyup', function() {
		  M_table.getList();
	});

	$('#selectBtn').click(function() {
		M_table.getFatherTable();
	});

	$('#selectBtn-po').click(function() {
		M_table.getFatherTable_po();
	});

	$('.confirmSelect').click(function(){
		$('#warehouseName').data('id',M_table.selectObj.id);
		$('#warehouseName').val(M_table.selectObj.name);
		$('.cancel').trigger('click');
		$('#positionsName').val("");

		if($('#warehouseName').val()!=''){
			$('#selectBtn-po').prop('disabled', false);
		}
	})

	$('.confirmSelect_1').click(function(){
		$('#positionsName').data('id',M_table.selectObj_po.id);
		$('#positionsName').val(M_table.selectObj_po.name);
		$('.cancel').trigger('click');

		if($('#positionsName').val()!=''){
			$('#selectBtn-po').prop('disabled', false);
		}
	})



}


M_table.showConfirmModal=function(info,status,msgBody){
	var gobalModal = "";
	$("#confirm").remove();
	var font_icon = '';
	if(info == '成功'){
		font_icon = 'glyphicon glyphicon-ok';
	}else if(info == '警告'){
		font_icon = 'glyphicon glyphicon-warning-sign';
	}
	else{
		font_icon = 'glyphicon glyphicon-remove';
	}


	if(status!="success"&&status!="delete"){
		gobalModal = '<button type="button" id="confirmBtn" class="btn btn-default comFirmModal hide" data-toggle = "modal" data-target = "#confirm">确定</button>'+
			'<div class = "modal fade" id = "confirm" tabindex = "-1" role = "dialog" aria-labelledby = "myModalLabel" aria-hidden = "true">'+
				'<div class = "modal-dialog">'+
			      '<div class = "modal-content">'+
			         '<div class = "modal-header">'+
			            '<button type = "button" class = "close" data-dismiss = "modal" aria-hidden = "true">&times;</button>'+  
			            '<h4 class = "modal-title text-danger" id = "myModalLabel">&nps<span class="'+ font_icon +'"></span> '+info+'</h4>'+
			         '</div>'+
			         '<div class = "modal-body">'+msgBody+'</div>'+
			         '<div class = "modal-footer">'+
			            '<button type = "button" class = "btn btn-danger confirm" data-dismiss = "modal">确定</button>'+
			         '</div>'+
			      '</div>'+
			   '</div>'+
			'</div>';
	}
	else if(status=="delete"){
		gobalModal = '<button type="button" id="confirmBtn" class="btn btn-default comFirmModal hide" data-toggle = "modal" data-target = "#confirm">确定</button>'+
					'<div class = "modal fade" id = "confirm" tabindex = "-1" role = "dialog" aria-labelledby = "myModalLabel" aria-hidden = "true">'+
						'<div class = "modal-dialog">'+
					      '<div class = "modal-content">'+
					         '<div class = "modal-header">'+
					            '<button type = "button" class = "close" data-dismiss = "modal" aria-hidden = "true">&times;</button>'+  
					            '<h4 class = "modal-title text-primary" id = "myModalLabel"><span class="'+ font_icon +'"></span> '+info+'</h4>'+
					         '</div>'+
					         '<div class = "modal-body">'+msgBody+'<span class="text-danger font-d-under font-w-bold">该操作将同时删除它的配置项等信息！</span></div>'+
					         '<div class = "modal-footer">'+
					            '<button type = "button" class = "btn btn-primary deleteConfirm" data-dismiss = "modal">确定</button>'+
					            '<button type = "button" class = "btn btn-default confirm" data-dismiss = "modal">取消</button>'+
					         '</div>'+
					      '</div>'+
					   '</div>'+
					'</div>';
	}
	else{
		gobalModal = '<button type="button" id="confirmBtn" class="btn btn-default comFirmModal hide" data-toggle = "modal" data-target = "#confirm">确定</button>'+
			'<div class = "modal fade" id = "confirm" tabindex = "-1" role = "dialog" aria-labelledby = "myModalLabel" aria-hidden = "true">'+
				'<div class = "modal-dialog">'+
			      '<div class = "modal-content">'+
			         '<div class = "modal-header">'+
			            '<button type = "button" class = "close" data-dismiss = "modal" aria-hidden = "true">&times;</button>'+  
			            '<h4 class = "modal-title text-primary" id = "myModalLabel"><span class="'+ font_icon +'"></span> '+info+'</h4>'+
			         '</div>'+
			         '<div class = "modal-body">'+msgBody+'</div>'+
			         '<div class = "modal-footer">'+
			            '<button type = "button" class = "btn btn-primary confirm" data-dismiss = "modal">确定</button>'+
			         '</div>'+
			      '</div>'+
			   '</div>'+
			'</div>';
	}
	$("body").append(gobalModal);
	$("#confirmBtn").trigger('click');
}

