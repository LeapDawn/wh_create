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
	init:function(){},
	getList:function(curr){},
	createList:function(JsonData){},
	showConfirmModal:function(){},
	getDetailInfo:function(){},
	saveInfo:function(){},
	updateInfo:function(){},
	deleteInfo:function(){},
	getFatherTable:function(){},
}

M_table.init = function () {
	var newURL = M_table.url + 'toolAction=warehouseSelect';
	var param = {

	}

	publicDom.getData('post',newURL,param,function(JsonData){
		if(JsonData.state == true){
			console.log(JsonData.value);
			$('.drop-select ul').empty();

			var liItem1 = '<li data-id=""><a href="#">(所有仓库)</a></li>\
							<li role="separator" class="divider"></li>';
			$('.drop-select ul').append(liItem1);

			for( var i = 0; i < JsonData.value.length ; i++ ){
				var liItem = '<li data-id="'+JsonData.value[i].whId+'"><a href="#">'+ JsonData.value[i].whName +'</a></li>';
				$('.drop-select ul').append(liItem);
			}

			$('.drop-select ul li').click(function() {
				var text = $(this).text();
				$('.drop-select button').data('id',$(this).data('id'));
				$('.drop-select button').html(text+' <span class="caret"></span>');
				M_table.getList();
			});

		}else{
		}
	})
}

M_table.getList = function (curr) {
	var newURL = M_table.url + 'toolAction=listPosition';

	var param = {
		warehouseId : $('.drop-select button').data('id'),
		poName : $('#filter').val() || '',
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
			M_table.showConfirmModal('错误','success','获取仓库列表失败！');
		}
		
	})
	

	
}

M_table.getDetailInfo = function(){
	var newURL = M_table.url + 'toolAction=getPosition';
	var param = {
		poId: M_table.currentId
	}

	publicDom.getData('post',newURL,param,function(JsonData){
		if(JsonData.state == true){
			console.log(JsonData.value);
			$('#poName').data('id',JsonData.value.poId);
			$('#poName').val(JsonData.value.poName);
			$('#poRemark').val(JsonData.value.poRemark);
			$('#warehouseName').val(JsonData.value.warehouseName);
			$('#warehouseName').data('id',JsonData.value.warehouseId);
		}else{
			M_table.showConfirmModal('错误','success','获取仓库详情失败！')
		}
	})
}

M_table.createList = function (list) {

	$('.tableDIV table tbody').empty();
	for( var i = 0 ; i < list.length ; i ++ ){
		var tableItem = '<tr data-id="'+list[i].poId+'">\
				 			<td>'+list[i].poName+'</td>\
				 			<td>'+list[i].warehouseName+'</td>\
				 			<td>'+list[i].poRemark+'</td>\
				 		</tr>'
		$('.tableDIV table tbody').append(tableItem);

	}
	

}

M_table.saveInfo = function () {
	var newURL = M_table.url + 'toolAction=addPosition';
	var param = {
		poName : $('#poName').val(),
		poRemark : $('#poRemark').val(),
		warehouseId : $('#warehouseName').data('id')
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
	var newURL = M_table.url + 'toolAction=updatePosition';
	var param = {
		poId : $('#poName').data('id'),
		poName : $('#poName').val(),
		poRemark : $('#poRemark').val()
	}

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
	var newURL = M_table.url + 'toolAction=deletePosition';
	var param = {
		poId : $('#poName').data('id')
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


var bindEvent = function () {
	$('#addBtn').click(function() {
		M_table.status = 1;

		$('.content-main-add input,.content-main-add textarea').val("");
		$('#selectBtn').show();

		$('.content-main').hide();
		$('.content-main-add').show();
		$('.content-main-add input,.content-main-add textarea').prop('readonly', false);
		$('.content-main-add #warehouseName').prop('readonly', true);
		$('#selectBtn').prop('disabled', false);
		$('#modifyBtn').hide();
		$('#saveBtn').show();
		$('.mustInput').show();
	});

	$('#modifyBtn').click(function() {
		M_table.status = 2;
		$('.content-main-add input,.content-main-add textarea').prop('readonly', false);
		$('.content-main-add #warehouseName').prop('readonly', true);
		$('#selectBtn').prop('disabled', false);
		$('#modifyBtn').hide();
		$('#saveBtn').show();
		$('.mustInput').show();
	});

	$('#saveBtn').click(function() {
		if($('#warehouseName').val()==""){
			M_table.showConfirmModal('错误','success','请选择仓库！');
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

	$('.confirmSelect').click(function(){
		$('#warehouseName').data('id',M_table.selectObj.id);
		$('#warehouseName').val(M_table.selectObj.name);
		$('.cancel').trigger('click');
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
					         '<div class = "modal-body">'+msgBody+'<span class="text-danger font-d-under font-w-bold">该操作将同时删除它的货架等信息！</span></div>'+
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

