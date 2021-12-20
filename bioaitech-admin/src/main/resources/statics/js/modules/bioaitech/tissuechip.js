$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'bioaitech/tissuechip/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '芯片编号', name: 'chipId', index: 'chip_id', width: 80 }, 			
			{ label: '图片编号', name: 'imgId', index: 'img_id', width: 80 }, 			
			{ label: '芯片说明', name: 'chipExplanation', index: 'chip_explanation', width: 80 }, 			
			{ label: '样本描述', name: 'sampleDescription', index: 'sample_description', width: 80 }, 			
			{ label: '取样方式', name: 'samplingMethod', index: 'sampling_method', width: 80 }, 			
			{ label: '行数', name: 'rowNumber', index: 'row_number', width: 80 }, 			
			{ label: '列数', name: 'colNumber', index: 'col_number', width: 80 }, 			
			{ label: '点数', name: 'points', index: 'points', width: 80 }, 			
			{ label: '例数', name: 'casesNumber', index: 'cases_number', width: 80 }, 			
			{ label: '点样直径', name: 'dotDiameter', index: 'dot_diameter', width: 80 }, 			
			{ label: '组织固定方式', name: 'organizationalFixation', index: 'organizational_fixation', width: 80 }, 			
			{ label: '', name: 'qaQc', index: 'qa_qc', width: 80 }, 			
			{ label: '种属', name: 'species', index: 'species', width: 80 }, 			
			{ label: '所属分类', name: 'categoryId', index: 'category_id', width: 80 }, 			
			{ label: '应用', name: 'useTo', index: 'use_to', width: 80 }, 			
			{ label: '注意事项', name: 'precautions', index: 'precautions', width: 80 }, 			
			{ label: 'TNM说明', name: 'tnmDescription', index: 'tnm_description', width: 80 }, 			
			{ label: '是否热卖', name: 'isHotSell', index: 'is_hot_sell', width: 80 }, 			
			{ label: '是否新品', name: 'isNew', index: 'is_new', width: 80 }, 			
			{ label: '是否上架', name: 'isPutaway', index: 'is_putaway', width: 80 }, 			
			{ label: '', name: 'isDeleted', index: 'is_deleted', width: 80 }, 			
			{ label: '', name: 'createTime', index: 'create_time', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		tissueChip: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tissueChip = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.tissueChip.id == null ? "bioaitech/tissuechip/save" : "bioaitech/tissuechip/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.tissueChip),
                    success: function(r){
                        if(r.code === 0){
                             layer.msg("操作成功", {icon: 1});
                             vm.reload();
                             $('#btnSaveOrUpdate').button('reset');
                             $('#btnSaveOrUpdate').dequeue();
                        }else{
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
               if(!lock) {
                    lock = true;
		            $.ajax({
                        type: "POST",
                        url: baseURL + "bioaitech/tissuechip/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function(r){
                            if(r.code == 0){
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            }else{
                                layer.alert(r.msg);
                            }
                        }
				    });
			    }
             }, function(){
             });
		},
		getInfo: function(id){
			$.get(baseURL + "bioaitech/tissuechip/info/"+id, function(r){
                vm.tissueChip = r.tissueChip;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});