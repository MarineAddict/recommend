<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="copyright" content="All Rights Reserved, Copyright (C) 2013, Wuyeguo, Ltd." />
<title>YH</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/1.3.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/wu.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/icon.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-3.2.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
	<!-- begin of header -->
	<div class="wu-header" data-options="region:'north',border:false,split:true">
    	<div class="wu-header-left">
        	<h1>上海译会信息科技有限公司</h1>
        </div>
        <div class="wu-header-right">
        	<p><strong class="easyui-tooltip" title="2条未读消息">lxs</strong>，欢迎您！</p>
            <p><a href="#">网站首页</a>|<a href="#">支持论坛</a>|<a href="#">帮助中心</a>|<a href="#">安全退出</a></p>
        </div>
    </div>
    <!-- end of header -->
    <!-- begin of sidebar -->
	<div class="wu-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'"> 
    	<div class="easyui-accordion" data-options="border:false,fit:true"> 
        	<div title="理财产品" data-options="iconCls:'icon-application-cascade'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
                    <li iconCls="icon-chart-organisation"><a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="pages/product/finance/financeInfo.jsp" iframe="0">理财基本信息</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-users" data-link="pages/product/finance/yieldRatioLine.jsp" iframe="0">净值收益率查询</a></li>
                    <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-user-group" data-link="pages/product/finance/caculateRiskRatio.jsp" iframe="0">计算预期收益率风险率</a></li>
                    <li iconCls="icon-book"><a href="javascript:void(0)" data-icon="icon-book" data-link="pages/product/finance/dataCheck.jsp" iframe="0">数据校验</a></li>
                    <!-- <li iconCls="icon-cog"><a href="javascript:void(0)" data-icon="icon-cog" data-link="temp/layout-3.html" iframe="0">系统参数</a></li>
                    <li iconCls="icon-application-osx-error"><a href="javascript:void(0)" data-icon="icon-application-osx-error" data-link="temp/layout-3.html" iframe="0">操作日志</a></li> -->
                </ul>
            </div>
            <div title="基金产品" data-options="iconCls:'icon-application-form-edit'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
                	<li iconCls="icon-chart-organisation"><a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="pages/product/fund/fundInfo.jsp" iframe="0">基金基本信息</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-users" data-link="pages/product/fund/yieldRatioLine.jsp" iframe="0">净值收益率查询</a></li>
                    <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-user-group" data-link="pages/product/fund/caculateRiskRatio.jsp"" iframe="0">计算预期收益率风险率</a></li>
                    <li iconCls="icon-book"><a href="javascript:void(0)" data-icon="icon-book" data-link="pages/product/fund/dataCheck.jsp" iframe="0">数据校验</a></li>
                   <!--  <li iconCls="icon-cog"><a href="javascript:void(0)" data-icon="icon-cog" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-application-osx-error"><a href="javascript:void(0)" data-icon="icon-application-osx-error" data-link="temp/layout-3.html" iframe="0">导航标题</a></li> -->
                </ul>
            </div>
            <div title="存单产品" data-options="iconCls:'icon-creditcards'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
                	<li iconCls="icon-chart-organisation"><a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="pages/product/deposit/depositInfo.jsp" iframe="0">存单基本信息</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-users" data-link="pages/product/deposit/yieldRatioLine.jsp" iframe="0">净值收益率查询</a></li>
                    <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-user-group" data-link="pages/product/deposit/caculateRiskRatio.jsp" iframe="0">计算预期收益率风险率</a></li>
                    <li iconCls="icon-book"><a href="javascript:void(0)" data-icon="icon-book" data-link="pages/product/deposit/dataCheck.jsp" iframe="0">数据校验</a></li>
                    <!-- <li iconCls="icon-cog"><a href="javascript:void(0)" data-icon="icon-cog" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-application-osx-error"><a href="javascript:void(0)" data-icon="icon-application-osx-error" data-link="temp/layout-3.html" iframe="0">导航标题</a></li> -->
                </ul>
            </div>
            <div title="贵金属产品" data-options="iconCls:'icon-cart'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
                	<li iconCls="icon-chart-organisation"><a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="pages/product/precious/preciousInfo.jsp" iframe="0">贵金属基本信息</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-users" data-link="pages/product/precious/yieldRatioLine.jsp" iframe="0">净值收益率查询</a></li>
                    <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-user-group" data-link="pages/product/precious/caculateRiskRatio.jsp" iframe="0">计算预期收益率风险率</a></li>
                    <li iconCls="icon-book"><a href="javascript:void(0)" data-icon="icon-book" data-link="pages/product/precious/dataCheck.jsp" iframe="0">数据校验</a></li>
                    <!-- <li iconCls="icon-cog"><a href="javascript:void(0)" data-icon="icon-cog" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-application-osx-error"><a href="javascript:void(0)" data-icon="icon-application-osx-error" data-link="temp/layout-3.html" iframe="0">导航标题</a></li> -->
                </ul>
            </div>
            <div title="产品组合" data-options="iconCls:'icon-bricks'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
                	<li iconCls="icon-chart-organisation"><a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="pages\STU_JS\groupYieldRatioLine.jsp" iframe="0">查询组合产品收益率</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-users" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-user-group" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-book"><a href="javascript:void(0)" data-icon="icon-book" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-cog"><a href="javascript:void(0)" data-icon="icon-cog" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-application-osx-error"><a href="javascript:void(0)" data-icon="icon-application-osx-error" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                </ul>
            </div>
            <div title="推荐模型设计" data-options="iconCls:'icon-chart-curve'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
                	<li iconCls="icon-chart-organisation"><a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="pages/modelstrategy/mvoModel.jsp" iframe="0">MVO模型</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-users" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-user-group" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-book"><a href="javascript:void(0)" data-icon="icon-book" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-cog"><a href="javascript:void(0)" data-icon="icon-cog" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-application-osx-error"><a href="javascript:void(0)" data-icon="icon-application-osx-error" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                </ul>
            </div>
            <!-- <div title="系统设置" data-options="iconCls:'icon-wrench'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
                	<li iconCls="icon-chart-organisation"><a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-users" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-user-group" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-book"><a href="javascript:void(0)" data-icon="icon-book" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-cog"><a href="javascript:void(0)" data-icon="icon-cog" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                    <li iconCls="icon-application-osx-error"><a href="javascript:void(0)" data-icon="icon-application-osx-error" data-link="temp/layout-3.html" iframe="0">导航标题</a></li>
                </ul>
            </div> -->
        </div>
    </div>	
    <!-- end of sidebar -->    
    <!-- begin of main -->
    <div class="wu-main" data-options="region:'center'">
        <div id="wu-tabs" class="easyui-tabs" data-options="border:false,fit:true">  
            <div title="首页" data-options="href:'temp/layout-1.html',closable:false,iconCls:'icon-tip',cls:'pd3'"></div>
        </div>
    </div>
    <!-- end of main --> 
    <!-- begin of footer -->
	<div class="wu-footer" data-options="region:'south',border:true,split:true">
    	&copy; 2013 Wu All Rights Reserved
    </div>
    <!-- end of footer -->  
    <script type="text/javascript">
		$(function(){
			$('.wu-side-tree a').bind("click",function(){
				var title = $(this).text();
				var url = $(this).attr('data-link');
				var iconCls = $(this).attr('data-icon');
				var iframe = $(this).attr('iframe')==1?true:false;
				addTab(title,url,iconCls,iframe);
			});	
		})
		
		/**
		* Name 载入树形菜单 
		*/
		$('#wu-side-tree').tree({
			url:'temp/menu.php',
			cache:false,
			onClick:function(node){
				var url = node.attributes['url'];
				if(url==null || url == ""){
					return false;
				}
				else{
					addTab(node.text, url, '', node.attributes['iframe']);
				}
			}
		});
		
		/**
		* Name 选项卡初始化
		*/
		$('#wu-tabs').tabs({
			tools:[{
				iconCls:'icon-reload',
				border:false,
				handler:function(){
					$('#wu-datagrid').datagrid('reload');
				}
			}]
		});
			
		/**
		* Name 添加菜单选项
		* Param title 名称
		* Param href 链接
		* Param iconCls 图标样式
		* Param iframe 链接跳转方式（true为iframe，false为href）
		*/	
		function addTab(title, href, iconCls, iframe){
			var tabPanel = $('#wu-tabs');
			console.log(tabPanel);
			if(!tabPanel.tabs('exists',title)){
				var content = '<iframe scrolling="auto" frameborder="0"  src="'+ href +'" style="width:100%;height:100%;"></iframe>';
				if(iframe){
					tabPanel.tabs('add',{
						title:title,
						content:content,
						iconCls:iconCls,
						fit:true,
						cls:'pd3',
						closable:true
					});
				}
				else{
					tabPanel.tabs('add',{
						title:title,
						href:href,
						iconCls:iconCls,
						fit:true,
						cls:'pd3',
						closable:true
					});
				}
			}
			else
			{
				tabPanel.tabs('select',title);
			}
		}
		/**
		* Name 移除菜单选项
		*/
		function removeTab(){
			var tabPanel = $('#wu-tabs');
			var tab = tabPanel.tabs('getSelected');
			if (tab){
				var index = tabPanel.tabs('getTabIndex', tab);
				tabPanel.tabs('close', index);
			}
		}
	</script>
</body>
</html>
