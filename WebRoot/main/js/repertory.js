$(function(){
	M_table.init();
	M_table.getList();
	bindEvent();
})

var M_table = {
	url:publicDom.config.url,
	currentId:-1,
	status:0,//1新增，2更新
	init:function(){},
	getList:function(curr){},
	createList:function(JsonData){},
	showConfirmModal:function(){},
	getDetailInfo:function(){},
	saveInfo:function(){},
	updateInfo:function(){},
	deleteInfo:function(){},
}

M_table.init = function () {

}

M_table.getList = function (curr) {
	var newURL = M_table.url + 'toolAction=listWarehouse';

	var param = {
		whName : $('#filter').val() || '',
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
				M_table.currentId = $(this).data('id');

				M_table.getDetailInfo();

			});


		}else{
			M_table.showConfirmModal('错误','success','获取仓库列表失败！');
		}
		
	})
	

	
}

M_table.getDetailInfo = function(){
	var newURL = M_table.url + 'toolAction=getWarehouse';
	var param = {
		whId: M_table.currentId
	}

	publicDom.getData('post',newURL,param,function(JsonData){
		if(JsonData.state == true){
			console.log(JsonData.value);
			$('#whName').data('id',JsonData.value.whId);
			$('#whName').val(JsonData.value.whName);
			$('#address').val(JsonData.value.address);
			$('#personName').val(JsonData.value.personName);
			$('#whRemark').val(JsonData.value.whRemark);
			$('#whTel').val(JsonData.value.whTel);
			$('#whType').val(JsonData.value.whType);
		}else{
			M_table.showConfirmModal('错误','success','获取仓库详情失败！')
		}
	})
}

M_table.createList = function (list) {

	$('.tableDIV table tbody').empty();
	for( var i = 0 ; i < list.length ; i ++ ){
		var tableItem = '<tr data-id="'+list[i].whId+'">\
				 			<td>'+list[i].whName+'</td>\
				 			<td>'+list[i].whType+'</td>\
				 			<td>'+list[i].personName+'</td>\
				 			<td>'+list[i].whTel+'</td>\
				 		</tr>'
		$('.tableDIV table tbody').append(tableItem);

	}
	

}

M_table.saveInfo = function () {
	var newURL = M_table.url + 'toolAction=addWarehouse';
	var param = {
		whName : $('#whName').val(),
		whType : $('#whType').val(),
		whTel : $('#whTel').val(),
		personName : $('#personName').val(),
		address : $('#address').val(),
		whRemark : $('#whRemark').val()
	}

	publicDom.getData('post',newURL,param,function(data){
		if(data.state==true){
			M_table.showConfirmModal('成功','success','保存成功！')
		}else{
			M_table.showConfirmModal('错误','success','保存失败！')
		}
	})
}

M_table.updateInfo = function() {
	var newURL = M_table.url + 'toolAction=updateWarehouse';
	var param = {
		whId : $('#whName').data('id'),
		whName : $('#whName').val(),
		whType : $('#whType').val(),
		whTel : $('#whTel').val(),
		personName : $('#personName').val(),
		address : $('#address').val(),
		whRemark : $('#whRemark').val()
	}

	publicDom.getData('post',newURL,param,function(data){
		if(data.state==true){
			M_table.showConfirmModal('成功','success','修改成功！');
		}else{
			M_table.showConfirmModal('错误','success','修改失败！');
		}
	})
}

M_table.deleteInfo = function() {
	var newURL = M_table.url + 'toolAction=deleteWarehouse';
	var param = {
		whId : $('#whName').data('id')
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


var bindEvent = function () {
	$('#addBtn').click(function() {
		M_table.status = 1;
		$('.content-main').hide();
		$('.content-main-add').show();
		$('.content-main-add input,.content-main-add textarea').prop('readonly', false);
		$('#modifyBtn').hide();
		$('#saveBtn').show();
	});

	$('#modifyBtn').click(function() {
		M_table.status = 2;
		$('.content-main-add input,.content-main-add textarea').prop('readonly', false);
		$('#modifyBtn').hide();
		$('#saveBtn').show();
	});

	$('#saveBtn').click(function() {
		if(M_table.status == 1){
			M_table.saveInfo();
		}else if(M_table.status == 2){
			M_table.updateInfo();
		}else{
			M_table.showConfirmModal('错误','success','发生了问题需要从头操作！')
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
					         '<div class = "modal-body">'+msgBody+'</div>'+
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

