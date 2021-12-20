$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/medicalrecord/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '', name: 'medicalRecordId', index: 'medical_record_id', width: 80 }, 			
			{ label: '', name: 'tissueChipId', index: 'tissue_chip_id', width: 80 }, 			
			{ label: '位置', name: 'position', index: 'position', width: 80 }, 			
			{ label: '', name: 'age', index: 'age', width: 80 }, 			
			{ label: '', name: 'sex', index: 'sex', width: 80 }, 			
			{ label: '器官', name: 'organ', index: 'organ', width: 80 }, 			
			{ label: '病理诊断', name: 'pathologicalDiagnosis', index: 'pathological_diagnosis', width: 80 }, 			
			{ label: '', name: 'grade', index: 'grade', width: 80 }, 			
			{ label: '', name: 'tnm', index: 'tnm', width: 80 }, 			
			{ label: '', name: 'stage', index: 'Stage', width: 80 }, 			
			{ label: '组织类型', name: 'organizationType', index: 'organization_type', width: 80 }, 			
			{ label: '手术日期', name: 'surgeryDate', index: 'surgery_date', width: 80 }, 			
			{ label: '临床诊断', name: 'clinicalDiagnosis', index: 'clinical_diagnosis', width: 80 }, 			
			{ label: '简要病史', name: 'briefMedicalHistory', index: 'brief_medical_history', width: 80 }, 			
			{ label: '肿瘤来自原发/转移', name: 'tumorFrom', index: 'tumor_from', width: 80 }, 			
			{ label: '远处转移部位', name: 'distantMetastasis', index: 'distant_metastasis', width: 80 }, 			
			{ label: '淋巴结转移情况', name: 'lymphNodeMetastasis', index: 'lymph_node_metastasis', width: 80 }, 			
			{ label: '阳性淋巴结数', name: 'positiveLymphNodes', index: 'positive_lymph_nodes', width: 80 }, 			
			{ label: '肿瘤大小', name: 'tumorSize', index: 'tumor_size', width: 80 }, 			
			{ label: '肿瘤部位', name: 'tumorSite', index: 'tumor_site', width: 80 }, 			
			{ label: '取材描述', name: 'materialDescription', index: 'material_description', width: 80 }, 			
			{ label: '', name: 'ihcMarker1', index: 'ihc_marker1', width: 80 }, 			
			{ label: '', name: 'ihcMarker2', index: 'ihc_marker2', width: 80 }, 			
			{ label: '', name: 'ihcMarker3', index: 'ihc_marker3', width: 80 }, 			
			{ label: '', name: 'ihcMarker4', index: 'ihc_marker4', width: 80 }, 			
			{ label: '', name: 'ihcMarker5', index: 'ihc_marker5', width: 80 }			
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
		medicalRecord: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.medicalRecord = {};
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
                var url = vm.medicalRecord.id == null ? "sys/medicalrecord/save" : "sys/medicalrecord/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.medicalRecord),
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
                        url: baseURL + "sys/medicalrecord/delete",
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
			$.get(baseURL + "sys/medicalrecord/info/"+id, function(r){
                vm.medicalRecord = r.medicalRecord;
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