var net = require('net');
var host = '192.168.0.202';//things.buzz

var userColor = [
    {'userid': '001', 'color': 'FFFF00'}
  , {'userid': '002', 'color': '0066CC'}
  , {'userid': '003', 'color': 'FF0000'}
  , {'userid': '004', 'color': '99CC00'}
  , {'userid': '005', 'color': '333399'}
  , {'userid': '006', 'color': 'CCCC00'}
  , {'userid': '007', 'color': '99CC00'}
  , {'userid': '008', 'color': '999933'}
  , {'userid': '009', 'color': 'FF6666'}
  , {'userid': '010', 'color': '663366'}
]

var playerCount = 0;
var gameRule=[];
var card5Ready=false;
var card52Ready=false;
var slotReady=false;

Date.prototype.dateAdd = function(sInterval, number) {     
    var dtTemp = this;    
    switch (sInterval) {     
        case 's' :return new Date(Date.parse(dtTemp) + (1000 * number));    
        case 'n' :return new Date(Date.parse(dtTemp) + (60000 * number));    
        case 'h' :return new Date(Date.parse(dtTemp) + (3600000 * number));    
        case 'd' :return new Date(Date.parse(dtTemp) + (86400000 * number));    
        case 'w' :return new Date(Date.parse(dtTemp) + ((86400000 * 7) * number));    
        case 'q' :return new Date(dtTemp.getFullYear(), (dtTemp.getMonth()) + number*3, dtTemp.getDate(), dtTemp.getHours(), dtTemp.getMinutes(), dtTemp.getSeconds());    
        case 'm' :return new Date(dtTemp.getFullYear(), (dtTemp.getMonth()) + number, dtTemp.getDate(), dtTemp.getHours(), dtTemp.getMinutes(), dtTemp.getSeconds());    
        case 'y' :return new Date((dtTemp.getFullYear() + number), dtTemp.getMonth(), dtTemp.getDate(), dtTemp.getHours(), dtTemp.getMinutes(), dtTemp.getSeconds());    
    }    
  
}   

// 对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子:
// (new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.format = function (fmt) { //author: meizz
  var o = {
    "M+": this.getMonth() + 1,                 //月份
    "d+": this.getDate(),                    //日
    "h+": this.getHours(),                   //小时
    "m+": this.getMinutes(),                 //分
    "s+": this.getSeconds(),                 //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    "S": this.getMilliseconds()             //毫秒
  };
  if (/(y+)/.test(fmt))
    fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt))
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}

/*
function gameRuleGenerator(startTime,itemCount,cpDuration,c52Duration,interval){
  startTime=startTime.dateAdd('s',10);
  for (var i=0;i<itemCount;i++){
    if(i%2==0){
      gameid='CardProject';
      st=(typeof(et)=='undefined')?startTime:et.dateAdd('s',interval);
      et=st.dateAdd('s',cpDuration);
    }
    else{
      gameid='Card52';
      st=et.dateAdd('s',interval);
      et=st.dateAdd('s',c52Duration);
    }
    var json={'begin': st.format('hh:mm:ss'), 'end':et.format('hh:mm:ss') , 'capacity': '10', 'gameid': gameid}
    gameRule.push(json)
  };
  console.log(gameRule);
} 

gameRuleGenerator(new Date(),100,2*60,2*60,10);

function gameCountDown() {
  setTimeout(function () {
    var json = {};
    var curTimeStr = new Date().format("hh:mm:ss");
    for (var key in gameRule) {
      if (curTimeStr == gameRule[key].begin) {
        json.gameid = gameRule[key].gameid;
        json.game = "01";
        cs.write(JSON.stringify(json));
      }
      if (curTimeStr == gameRule[key].end) {
        json.gameid = gameRule[key].gameid;
        json.game = "02";
        cs.write(JSON.stringify(json));
        playerCount = 0;
      }
    }
    gameCountDown();
  }, 1000);
}
*/

function executeCommand(socket, command, args) {
  var json = {};
  switch (command) {
    case "01"://beacon-in
    case "02"://beacon-out
      var arr=args.split(',');
      json.beaconid = arr[0];
      json.state = command;
      json.userid=arr[1];
      if(arr[1].indexOf("01 ")>0){
        json.userid=arr[1].substr(0,arr[1].indexOf("01 "))
      }
      else if(arr[1].indexOf("02 ")>0){
        json.userid=arr[1].substr(0,arr[1].indexOf("02 "))
      }
      result = JSON.stringify(json);
      console.log(result);
      socket.write(result);
      //cs.write('client:'+result);
      cs.write(result);
      break;

    case "03"://useraction='01' then assign userid & color
      var arr = args.split(',');
      if (arr[0] == "01") {
        var curTimeStr = new Date().format("hh:mm:ss");
        var gameid = arr[1];
        var serVal = "02";
        var userid = "";
        var color = "";
        /*
        for (var key in gameRule) {
          if (gameRule[key].gameid == arr[1]
            &&curTimeStr >= gameRule[key].begin 
            && curTimeStr <= gameRule[key].end 
            && playerCount <= parseInt(gameRule[key].capacity)) {

            serVal = "01";
            playerCount++;
            userid = arr[2];
            color = userColor[playerCount - 1].color;
            break;
          }
        }*/

        if(gameid=="Card52"){
          serVal="01";
          playerCount++;
          userid=arr[2];
          color = userColor[playerCount - 1].color;
        }else if(gameid=="TigerGame"){
          serVal="01";
          userid=arr[2];
        }

        json.server = serVal;
        json.userid = userid;
        json.color = color;

        result = JSON.stringify(json);
        cs.write(result);
      }
      break;

    case "04"://user play start
      var arr = args.split(',');
      json.userid = arr[0];
      json.state = arr[1];
      result = JSON.stringify(json);
      cs.write(result);
      break;

    case "05"://user play end
      var arr = args.split(',');
      json.userid = arr[0];
      json.state = arr[1];
      result = JSON.stringify(json);
      cs.write(result);
      break;

    case "06"://user view result
      var arr = args.split(',');
      playerCount--;
      json.userid = arr[0];
      json.state = arr[1];
      result = JSON.stringify(json);
      cs.write(result);
      break;
  }
};

clientList = [];
mbList = [];

function broadcast(message, client) {
  try{
      var cleanup = [];
      for (var i = 0; i < clientList.length; i++) {
        if (client !== clientList[i]) {
          if (clientList[i].writable) {
            clientList[i].write(message);
          } else {
            cleanup.push(clientList[i]);
            clientList[i].destroy();
          }

        }
      }

      for (i = 0; i < cleanup.length; i++) {
        clientList.splice(clientList.indexOf(cleanup[i]), 1);
      }
  }
  catch(ex){
  }
}

function broadcastmb(message, client) {
  try{
      var cleanup = [];
      for (var i = 0; i < mbList.length; i++) {
        if (client !== mbList[i]) {
          if (mbList[i].writable) {
            mbList[i].write(message);
          } else {
            cleanup.push(mbList[i]);
            mbList[i].destroy();
          }

        }
      }

      for (i = 0; i < cleanup.length; i++) {
        mbList.splice(mbList.indexOf(cleanup[i]), 1);
      }
  }
  catch(ex){
  }
}

var c = net.createServer(function (socket) {
  console.log('client:2222 connected');
  socket.on('data', function (d) {
    data = d.toString('utf8').trim();
    console.log(data);
    broadcast(data, socket);
    ss.write(data);
  });

  socket.on('end', function () {
    console.log('client:2222 disconnected');
    try {
      clientList.splice(clientList.indexOf(client), 1);
    } catch (ex) {
    }
  });

  socket.on('error', function (ex) {
    console.log("client ignoring exception: " + ex);
  });
});

c.on('connection', function (client) {
  client.name = client.remoteAddress + ':' + client.remotePort
  //client.write('Hi ' + client.name + '!\n');
  clientList.push(client);
});

c.listen(2222, host);
console.log('client listen on ' + host + ',port:2222...');
var cs = net.connect({port: 2222, host: host});
//gameCountDown();

var s = net.createServer(function (socket) {
  console.log('server:1111 connected');

  socket.on('data', function (d) {
    data = d.toString('utf8').trim();
    var cmd_re = /^(\d{2})+[ ]*(.*)/g;
    cmd_match = cmd_re.exec(data)
    if (cmd_match) {
      var command = cmd_match[1];
      var args = cmd_match[2];
      console.log(command + " " + args);
      executeCommand(socket, command, args);
    }
    else {
      if (data.indexOf("beaconid") < 0) {
        broadcastmb(data, socket);
      }

      if(data=='{"gameid":"CardFive","game":"01"}'){
        card5Ready=true;
        slotReady=false;
        card52Ready=false;
      }

      if(data=='{"gameid":"CardFive","game":"02"}'){
        card5Ready=false;
      }

      if(data=='{"gameid":"Card52","game":"01"}'){
        card52Ready=true;
        playerCount=0;
        slotReady=false;
        card5Ready=false;
      } 

      if(data=='{"gameid":"Card52","game":"02"}'){
        card52Ready=false;
      } 

      if(data=='{"gameid":"TigerGame","game":"01"}'){
        slotReady=true;
        card5Ready=false;
        card52Ready=false;
      } 

      if(data=='{"gameid":"TigerGame","game":"02"}'){
        //slotReady=false;
      } 
    }
  });

  socket.on('end', function () {
    console.log('server:1111 disconnected');
    try {
      mbList.splice(mbList.indexOf(client), 1);
    } catch (ex) {
    }
  });

  socket.on('error', function (ex) {
    console.log("ignoring exception: " + ex);
  });

});

s.on('connection', function (client) {
  client.name = client.remoteAddress + ':' + client.remotePort
  //client.write('Hi ' + client.name + '!\n');
  mbList.push(client);
});

s.listen(1111, host);
var ss = net.connect({port: 1111, host: host});
console.log('server listen on ' + host + ',port:1111...');
