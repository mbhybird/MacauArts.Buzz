var net=require('net');
var mysql=require('./mysqlpool.js');
var host="192.168.0.106";
//var client=require('./doorclient.js');
var fileserver=require('./fileserver.js');
function execute_command(socket, command, args) {  
    switch(command){
      case "90":
        client.open();
        break;

      case "91":
        client.close();
        break;
        
      case "00":
        mysql.getBeaconInfoList(socket);
        break;

      case "01":
        mysql.getBeaconInfo(socket,args);
        break;

      case "02":
        mysql.getSysparams(socket);
        break;

      case "03":
        mysql.getUserInfo(socket,args);
        break;

      case "04":
        mysql.addSyslog(socket,args);
        break;

      case "05":
        mysql.addUser(socket,args);
        break;

      case "06":
        mysql.findUser(socket,args);
        break;

      case "07":
        mysql.getExTagList(socket);
        break;
    }
};

var s = net.createServer(function(socket) {
    console.log('server connected');

    socket.on('data', function(d) { 
      data = d.toString('utf8').trim();  
      var cmd_re = /^(\d{2})+[ ]*(.*)/g;
      cmd_match = cmd_re.exec(data)  
      if (cmd_match) {  
        var command = cmd_match[1];  
        var args = cmd_match[2];  
        console.log(command+" "+args);
        execute_command(socket, command, args);  
      } 
    });

    socket.on('end', function() {
      console.log('server disconnected');
    });

    socket.on('error', function (ex) {
      console.log("ignoring exception: " + ex);
    });

});  
s.listen(2397,host);
console.log('listen on server port:2397...');