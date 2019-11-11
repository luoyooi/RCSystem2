//保存接收的电影信息
var mvInfo;	

// 展示电影信息
function showMvInfo() {
	var url = "http://localhost:8080/RcSystem/MvInfoServlet";
	var infoHtml = '<div class="mvs clearfix">';
	$.get(url,function(jsonStr){
				// 解析json
				mvInfo = JSON.parse(jsonStr);
				
				//生成首页数据分析
				peoAnalysis(mvInfo);
				
				var j = 0;
				for (i = 0; i < mvInfo.length; i++) {
					// 处理电影名
					var name = mvInfo[i]["name"];
					if (name.length > 7)
						name = name.substring(0, 8) + "...";

					// 处理海报地址
					var r = /p\d+/;
					var picEno = mvInfo[i]["posterAdrr"].match(r);
					var poster = "img/posters/" + picEno + ".jpg";
					
					// 生成html
					
					infoHtml += '<div class="mv">'+
								'<p>'+name+'</p><span>'+mvInfo[i]["score"]+'</span>'+
								'<p>'+mvInfo[i]["redate"]+'</p>'+
								'<img src="'+poster+'" alt="海报"/>'+
								'<a onclick=moreInfo("'+picEno+'")>More Info</a>'+
								'</div>';
					
					j = j + 1;
					if (j % 4 == 0) {
						infoHtml += '</div>';
						if(j!=mvInfo.length)
						{
							infoHtml += '<div class="mvs clearfix">';
						}
					}
				}
				
				if(mvInfo.length % 4 != 0)
				{
					infoHtml += '</div>';
				}
				
				// 替换原有的html
				document.getElementById("movies").innerHTML = infoHtml;
			});
	
	
}

//首页数据分析
function peoAnalysis(mvInfo)
{
	var names = [];
	var peoples = [];
	
	for (i=0; i < mvInfo.length; i++) 
	{
		names.push(mvInfo[i]["name"]);
		peoples.push(mvInfo[i]["commPeopleNum"]);
	}
	
	var myChart = echarts.init(document.getElementById('people'));
	option = {
		    title : {
		        text: '各电影评价人数汇总'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['总人数']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : names
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'总人数',
		            type:'bar',
		            data:peoples,
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        }
		    ]
		};

	// 将配置应用到图表上
	myChart.setOption(option);
}

//数据分析
function moreInfo(choice) {	
	var i = 0;
	for (; i < mvInfo.length; i++) {
		if (mvInfo[i]["posterAdrr"].substring(59, 70) == choice)
			break;
	}
	
	// 电影名
	var name = mvInfo[i]["name"];
	// 导演
	var directors = mvInfo[i]["directors"].join("，");
	// 编剧
	var screenwriters = mvInfo[i]["screenwriters"].join("，");
	// 语言
	var language = mvInfo[i]["language"];
	// 时长
	var mvLong = mvInfo[i]["mvLong"];
	// 上映日期
	var redate = mvInfo[i]["redate"];
	// 类型
	var types = mvInfo[i]["types"].join("，");
	// 别名
	var othername = mvInfo[i]["othername"];
	// 国家
	var country = mvInfo[i]["country"];
	// 主演2
	var tostars2 = mvInfo[i]["tostars"].join("，");
	// 海报地址
	var poster = "img/posters/" + choice + ".jpg";
	
	var html = 	'<div class="clearfix">'+
				'<h3 id ="moreinfo_title">' + name + '</h3>'+
			 	'<img id = "moreinfo_poster" alt="海报" src="'+ poster + '" alt="海报">'+
			 	'<div id ="moreinfo_info">' + 
				'<p><span>演员：</span>'+ tostars2 +'</p>' + 
				'<p><span>导演：</span>' + directors + '</p>' + 
				'<p><span>编剧：</span>'+ screenwriters + '</p>' + 
				'<p><span>国家：</span>' + country + '</p>' + 
				'<p><span>语言：</span>'+ language + '</p>' + 
				'<p><span>类型：</span>' + types + '</p>' + 
				'<p><span>时长：</span>' + mvLong+ '</p>' + 
				'<p><span>上映日期：</span>' + redate + '</p>' + 
				'<p><span>别名：</span>' + othername+ '</p>' + 
				'</div>'+
				'</div>'+
			  '<div id="analyse1">'+
				'</div>'+
				'<p>--- 评价人数：'+mvInfo[i]["commPeopleNum"]+' ---</p>'+
			  '<div id="analyse2"></div>';

	document.getElementById("movies").innerHTML = html;
	
	//加入数据分析2，雷达图
	StarsAnalysis(mvInfo[i]["stars"]);
	//加入数据分析3，柱状图
	adjAnalysis(name);
	
}

//评价星级分布
function StarsAnalysis(starData)
{
	//将字符串转为数字
	for(i=0; i<5; i++)
	{
		starData[i] = Number(starData[i].replace("%",""));
	}
	
	var myChart = echarts.init(document.getElementById('analyse1'));
	option = {
		    title: {
		        text: '星级分布'
		    },
		    tooltip: {},
		    legend: {
		        data: ['评价等级分布']
		    },
		    radar: {
		        // shape: 'circle',
		        name: {
		            textStyle: {
		                color: '#fff',
		                backgroundColor: '#999',
		                borderRadius: 3,
		                padding: [3, 5]
		           }
		        },
		        indicator: [
		           { name: '5星', max: 100},
		           { name: '4星', max: 100},
		           { name: '3星', max: 100},
		           { name: '2星', max: 100},
		           { name: '1星', max: 100}
		        ]
		    },
		    series: [{
		        name: '预算 vs 开销（Budget vs spending）',
		        type: 'radar',
		        // areaStyle: {normal: {}},
		        data : [
		            {
		                value : starData,
		                name : '评价等级分布'
		            }
		        ]
		    }]
		};
	
	// 将配置应用到图表上
	myChart.setOption(option);	
}

//生成形容词分析图表
function adjAnalysis(mvname)
{
	var url = "http://localhost:8080/RcSystem/AnalysisServlet";
	$.post(url,{name:mvname},function(jsonStr){
		var obj=JSON.parse(jsonStr);
		var xAxisData=obj.words;
		var yAxisData=obj.times;		
		
		var myChart = echarts.init(document.getElementById('analyse2'));
	
		// 图表的配置
		option = {
		    title: {
		        text: '形容词频次分析'
		    },
		    legend: {
		        data: ['bar'],
		        align: 'left'
		    },
		    toolbox: {
		        // y: 'bottom',
		        feature: {
		            magicType: {
		                type: ['stack', 'tiled']
		            },
		            dataView: {},
		            saveAsImage: {
		                pixelRatio: 2
		            }
		        }
		    },
		    tooltip: {},
		    xAxis: {
		        data: xAxisData,
		        silent: false,
		        splitLine: {
		            show: false
		        }
		    },
		    yAxis: {
		    },
		    series: [{
		        name: 'ADJ',
		        type: 'bar',
		        data: yAxisData,
		        animationDelay: function (idx) {
		            return idx * 10;
		        }
		    }],
		    animationEasing: 'elasticOut',
		    animationDelayUpdate: function (idx) {
		        return idx * 5;
		    }
		};
	
		// 将配置应用到图表上
		myChart.setOption(option);
	});
}