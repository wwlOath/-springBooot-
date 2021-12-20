$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'bioaitech/sysmenu/list',
        datatype: "json",
        colModel: [			
			{ label: 'menuId', name: 'menuId', index: 'menu_id', width: 50, key: true },
			{ label: '父菜单ID，一级菜单为0', name: 'parentId', index: 'parent_id', width: 80 }, 			
			{ label: '菜单名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '菜单URL', name: 'url', index: 'url', width: 80 }, 			
			{ label: '授权(多个用逗号分隔，如：user:list,user:create)', name: 'perms', index: 'perms', width: 80 }, 			
			{ label: '类型   0：目录   1：菜单   2：按钮', name: 'type', index: 'type', width: 80 }, 			
			{ label: '菜单图标', name: 'icon', index: 'icon', width: 80 }, 			
			{ label: '排序', name: 'orderNum', index: 'order_num', width: 80 }			
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
		sysMenu: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysMenu = {};
		},
		update: function (event) {
			var menuId = getSelectedRow();
			if(menuId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(menuId)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.sysMenu.menuId == null ? "bioaitech/sysmenu/save" : "bioaitech/sysmenu/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.sysMenu),
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
			var menuIds = getSelectedRows();
			if(menuIds == null){
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
                        url: baseURL + "bioaitech/sysmenu/delete",
                        contentType: "application/json",
                        data: JSON.stringify(menuIds),
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
		getInfo: function(menuId){
			$.get(baseURL + "bioaitech/sysmenu/info/"+menuId, function(r){
                vm.sysMenu = r.sysMenu;
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