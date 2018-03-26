// client.js
var net = require("net");
var closeCmd='3000000009999999999999';
var openCmd='3010000009999999999999';
var client = net.connect({port: 4660,host:'192.168.0.199'}, function(){
  console.log('door client connected');
});
client.on('data', function(data) {
  console.log(data.toString());
  //client.end();
});
client.on('end', function() {
  console.log('door client disconnected');
});

this.open=function(){
	console.log('open door');
	client.write(closeCmd);
}
this.close=function(){
	console.log('close door');
	client.write(openCmd);
}