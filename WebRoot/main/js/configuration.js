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
	selectObj_sh:{},//单选的货架
	selectObj_ma:{},//单选的物料
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
	getFatherTable_sh:function(){},
	getSonMaterial:function(){}
}

M_table.init = function () {

	var newURL = M_table.url + 'toolAction=warehouseSelect';
	var newURL_po = M_table.url + 'toolAction=listPosition';
	var newURL_sh = M_table.url + 'toolAction=listShelf';
	var param = {

	}
	var param_po = {

	}
	var param_sh = {

	}

	publicDom.getData('post',newURL,param,function(JsonData){
		if(JsonData.state == true){
			console.log(JsonData.value);
			$('.drop-select-1 ul').empty();

			var liItem1 = '<li data-id="" class="listAll"><a href="#">(所有仓库)</a></li>\
							<li role="separator" class="divider"></li>';
			$('.drop-select-1 ul').append(liItem1);

			for( var i = 0; i < JsonData.value.length ; i++ ){
				var liItem = '<li data-id="'+JsonData.value[i].whId+'"><a href="#">'+ JsonData.value[i].whName +'</a></li>';
				$('.drop-select-1 ul').append(liItem);
			}

			$('.listAll').click(function() {
				$('.drop-select-2 button').html('选择仓位'+' <span class="caret"></span>');
				$('.drop-select-2 button').data('id',"");
				$('.drop-select-3 button').html('选择货架'+' <span class="caret"></span>');
				$('.drop-select-3 button').data('id',"");
				M_table.getList();
			});

			$('.drop-select-1 ul li').click(function() {
				$('.drop-select-2 button').html('选择仓位'+' <span class="caret"></span>');
				$('.drop-select-2 button').data('id',"");
				$('.drop-select-3 button').html('选择货架'+' <span class="caret"></span>');
				$('.drop-select-3 button').data('id',"");
				$('.drop-select-3 button').prop('disabled', true);


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
							$('.drop-select-3 button').html('选择货架'+' <span class="caret"></span>');
							$('.drop-select-3 button').data('id',"");


							var text2 = $(this).text();
							$('.drop-select-2 button').data('id',$(this).data('id'));
							$('.drop-select-2 button').html(text2+' <span class="caret"></span>');
							$('#dropdownMenu3').prop('disabled', false);
							


							//选择货架
							param_sh.positionsId=$('.drop-select-2 button').data('id');

							publicDom.getData('post',newURL_sh,param_sh,function(_data){
								if(_data.state == true){
									$('.drop-select-3 ul').empty();

									var liItem4 = '<li data-id=""><a href="#">(所有货架)</a></li>\
													<li role="separator" class="divider"></li>';
									$('.drop-select-3 ul').append(liItem4);

									for( var i = 0; i < _data.value.data.length ; i++ ){
										var liItem5 = '<li data-id="'+_data.value.data[i].shId+'"><a href="#">'+ _data.value.data[i].shName +'</a></li>';
										$('.drop-select-3 ul').append(liItem5);
									}

									$('.drop-select-3 ul li').click(function() {
										var text3 = $(this).text();
										$('.drop-select-3 button').data('id',$(this).data('id'));
										$('.drop-select-3 button').html(text3+' <span class="caret"></span>');
										$('#dropdownMenu3').prop('disabled', false);

										M_table.getList();
									})
									
								}else{

								}
							})

						});
					}
				})

			});

		}else{
		}
	})
}

M_table.getList = function (curr) {
	var newURL = M_table.url + 'toolAction=listMaterielConfig';

	var param = {
		shelfId : $('.drop-select-3 button').data('id') || '',
		maName : $('#filter').val() || '',
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
			M_table.showConfirmModal('错误','success','获取货架列表失败！');
		}
		
	})
	

	
}

M_table.getDetailInfo = function(){
	var newURL = M_table.url + 'toolAction=getMaterielConfig';
	var param = {
		cmId: M_table.currentId
	}

	publicDom.getData('post',newURL,param,function(JsonData){
		if(JsonData.state == true){
			console.log(JsonData.value);
			$('#maName').data('id',JsonData.value.cmId);
			$('#maName').val(JsonData.value.maName);
			$('#maModel').val(JsonData.value.maModel);
			$('#maSpec').val(JsonData.value.maSpec);
			$('#cmRemark').val(JsonData.value.cmRemark);
			$('#warehouseName').val(JsonData.value.warehouseName);
			$('#warehouseName').data('id',JsonData.value.warehouseId);
			$('#positionsName').val(JsonData.value.positionsName);
			$('#positionsName').data('id',JsonData.value.positionsId);
			$('#shelfName').val(JsonData.value.shelfName);
			$('#shelfName').data('id',JsonData.value.shelfId);
		}else{
			M_table.showConfirmModal('错误','success','获取仓库详情失败！')
		}
	})
}

M_table.createList = function (list) {

	$('.tableDIV table tbody').empty();
	for( var i = 0 ; i < list.length ; i ++ ){
		var tableItem = '<tr data-id="'+list[i].cmId+'">\
				 			<td>'+list[i].maName+'</td>\
				 			<td>'+list[i].maModel+'</td>\
				 			<td>'+list[i].maSpec+'</td>\
				 			<td>'+list[i].warehouseName+'</td>\
				 			<td>'+list[i].positionsName+'</td>\
				 			<td>'+list[i].shelfName+'</td>\
				 		</tr>'
		$('.tableDIV table tbody').append(tableItem);

	}
	

}

M_table.saveInfo = function () {
	var newURL = M_table.url + 'toolAction=addMaterielConfig';
	var param = {
		shelfId : $('#shelfName').data('id'),
		maId : $('#maName').data('id'),
		cmRemark : $('#cmRemark').val()
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
	// var newURL = M_table.url + 'toolAction=updateMaterielConfig';
	// var param = {
	// 	cmId : $('#maName').data('id'),
	// 	cmRemark : $('#cmRemark').val()
	// }
	// console.log(param);

	// publicDom.getData('post',newURL,param,function(data){
	// 	if(data.state==true){
	// 		M_table.showConfirmModal('成功','success','修改成功！');
	// 	}else{
	// 		M_table.showConfirmModal('错误','success','修改失败！');
	// 	}
	// })
}

M_table.deleteInfo = function() {
	var newURL = M_table.url + 'toolAction=deleteMaterielConfig';
	var param = {
		cmId : $('#maName').data('id')
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
						M_table.getFatherTable_po(obj.curr);	
					}
				}
			})

		}else{
			M_table.showConfirmModal('错误','success','获取仓库列表失败！');
		}
	})
}

M_table.getFatherTable_sh = function(curr){
	var newURL = M_table.url + 'toolAction=listShelf';
	var param = {
		positionsId : $('#positionsName').data('id') || '',
		currentPage : curr || 1,
		rows : 6
	}
	publicDom.getData('post',newURL,param,function(data){
		if(data.state==true){
			$('.tableInsert_2 tbody').empty();
			console.log(data.value.data.length,data.value.data[0].shName);
			for(var i=0;i<data.value.data.length;i++){
				var tableItem = '<tr>' +
									'<td><input name="selectRadio" type="radio" value="" data-id="' +  data.value.data[i].shId + '" style=""></td>' +
									'<td>' + data.value.data[i].shName + '</td>' +
									'<td data-id="'+data.value.data[i].warehouseId+'">' + data.value.data[i].warehouseName + '</td>' +
									'<td data-id="'+data.value.data[i].positionsId+'">' + data.value.data[i].positionsName + '</td>' +
								 '</tr>'
				$('.tableInsert_2 tbody').append(tableItem);
			}

			$('.tableInsert_2 input[name="selectRadio"]').click(function() {
				M_table.selectObj_sh.id = $(this).data('id');
				M_table.selectObj_sh.name = $(this).parent().next().text();
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
						M_table.getFatherTable_sh(obj.curr);	
					}
				}
			})

		}else{
			M_table.showConfirmModal('错误','success','获取仓库列表失败！');
		}
	})
}

M_table.getSonMaterial = function(curr) {
	var newURL = M_table.url + 'toolAction=listMateriel';
	var param = {
		shelfId : $('#shelfName').data('id'),
		maName : $('#filter_ma').val() || '',
		currentPage : curr || 1,
		rows : 7
	}
	publicDom.getData('post',newURL,param,function(data){
		if(data.state==true){
			$('.tableInsert_3 tbody').empty();
			for(var i=0;i<data.value.data.length;i++){
				var tableItem = '<tr>' +
									'<td><input name="selectRadio" type="radio" value="" data-id="' +  data.value.data[i].maId + '" style=""></td>' +
									'<td>' + data.value.data[i].maName + '</td>' +
									'<td>' + data.value.data[i].maModel + '</td>' +
									'<td>' + data.value.data[i].maSpec + '</td>' +
								 '</tr>'
				$('.tableInsert_3 tbody').append(tableItem);
			}

			$('.tableInsert_3 input[name="selectRadio"]').click(function() {
				M_table.selectObj_ma.id = $(this).data('id');
				M_table.selectObj_ma.name = $(this).parent().next().text();
				M_table.selectObj_ma.model = $(this).parent().next().next().text();
				M_table.selectObj_ma.spec = $(this).parent().next().next().next().text();
				$(this).parent().parent().siblings().removeClass('text-primary');
				$(this).parent().parent().addClass('text-primary');

			});




			laypage({
				cont : 'page4',
				pages :data.value.pages,
				curr : curr || 1,
				skin:'#337ab7',
				skip:false,
				jump : function(obj,first){
					if(!first){
						M_table.getSonMaterial(obj.curr);	
					}
				}
			})

		}else{
			M_table.showConfirmModal('错误','success','获取仓库列表失败！');
		}
	})
}

let bindEvent = function () {
	$('#addBtn').click(function() {
		M_table.status = 1;
		$('.content-main').hide();
		$('.content-main-add').show();
		$('.content-main-add input,.content-main-add textarea').prop('readonly', false);
		$('#warehouseName,#positionsName,#maName,#maModel,#maSpec,#shelfName').prop('readonly', true);
		$('#selectBtn,#selectBtn-ma').prop('disabled', false);
		$('#modifyBtn').hide();
		$('#saveBtn').show();
	});

	$('#modifyBtn').click(function() {
		M_table.status = 2;
		$('.content-main-add input,.content-main-add textarea').prop('readonly', false);
		$('#warehouseName,#positionsName,#maName,#maModel,#maSpec,#shelfName').prop('readonly', true);
		$('#selectBtn,#selectBtn-ma').prop('disabled', false);
		
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

	$('#filter_ma').on('keyup', function() {
		  M_table.getSonMaterial();
	});

	$('#selectBtn').click(function() {
		M_table.getFatherTable();
	});

	$('#selectBtn-po').click(function() {
		M_table.getFatherTable_po();
	});

	$('#selectBtn-sh').click(function() {
		M_table.getFatherTable_sh();
	});

	$('#selectBtn-ma').click(function() {
		M_table.getSonMaterial();
	});

	$('.confirmSelect').click(function(){
		$('#warehouseName').data('id',M_table.selectObj.id);
		$('#warehouseName').val(M_table.selectObj.name);
		M_table.selectObj_po = {};
		M_table.selectObj_sh = {};

		$('.cancel').trigger('click');
		$('#positionsName').val("");
		$('#shelfName').val("");

		if($('#warehouseName').val()!=''){
			$('#selectBtn-po').prop('disabled', false);
		}
	})

	$('.confirmSelect_1').click(function(){
		$('#positionsName').data('id',M_table.selectObj_po.id);
		$('#positionsName').val(M_table.selectObj_po.name);
		M_table.selectObj_sh = {};

		$('.cancel').trigger('click');
		$('#shelfName').val("");

		if($('#positionsName').val()!=''){
			$('#selectBtn-sh').prop('disabled', false);
		}
	})

	$('.confirmSelect_2').click(function(){
		$('#shelfName').data('id',M_table.selectObj_sh.id);
		$('#shelfName').val(M_table.selectObj_sh.name);
		$('.cancel').trigger('click');

	})

	$('.confirmSelect_3').click(function(){
		// $('#maName').data('id',M_table.selectObj_ma.id);
		$('#maName').val(M_table.selectObj_ma.name);
		$('#maModel').val(M_table.selectObj_ma.model);
		$('#maSpec').val(M_table.selectObj_ma.spec);
		$('.cancel').trigger('click');

		if($('#maName').val()!=''){
			$('#selectBtn-ma').prop('disabled', false);
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

