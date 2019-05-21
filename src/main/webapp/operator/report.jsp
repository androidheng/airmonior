<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
  
    <link rel="stylesheet" href="<%=basePath%>assets/css/view.css"/>
     <link rel="stylesheet" href="<%=basePath%>assets/css/layui.css">
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <title></title>
</head>
<body class="layui-view-body">
    <div class="panel-body laery-seo-box">
       
        <div class="layui-form" style="padding:10px 0px;background:#fff;margin-top:50px;"></form>
            <div class="layui-form-item">
              <label class="layui-form-label">时间</label>
              <div class="layui-input-block">
                 <input type="text" class="layui-input" id="test1" placeholder="yyyy-MM-dd">
              </div>
            </div>
           
            <div class="layui-form-item">
              <label class="layui-form-label"></label>
              <div class="layui-input-block">
                <button class="layui-btn layui-btn-warm" id="btn">查询</button>
              </div>
            </div>
             
        </div>	
        
        <div class="larry-seo-stats" id="larry-seo-stats" style="width:100%;height:600px"></div>
  </div>
    <script src="<%=basePath%>assets/layui.all.js"></script>
    <script type="text/javascript" src="<%=basePath%>assets/echarts.min.js"></script>
    <script>
    layui.use('laydate', function(){
    	  var laydate = layui.laydate;
    	  //常规用法
    	  laydate.render({
    	    elem: '#test1'
    	  });
    });
   
     var myChart = echarts.init(document.getElementById('larry-seo-stats'));
        option = {
        title: {
        text: '空气统计折现图'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:[]
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    toolbox: {
       
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: []
    },
    yAxis: {
        type: 'value'
    },
    series: [
       
    ]
};      
        //getData()
      $(document).on('click','#btn',function(){
    	    getData()
      });
      
     //重新渲染表单
     function renderForm(){
          layui.use('form', function(){
             var form = layui.form;//高版本建议把括号去掉，有的低版本，需要加()
             form.render();
          });
      } 
      function getData(){
        	 let dates = $("#test1").val()
        	 $.ajax({
                   url:"<%=basePath%>data/searchHistory?dates="+dates,
                   type:'post',//method请求方式，get或者post
                   dataType:'json',//预期服务器返回的数据类型
                  
                   contentType: "application/json; charset=utf-8",
                   success:function(res){//res为相应体,function为回调函数
                	   option.xAxis.data = res.data
                	   option.series = res.series
                	   myChart.setOption(option);
                    
                   },
                   error:function(){
                     
                   }
            });
        }



    </script>
</body>
</html>