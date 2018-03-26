var mysql = require('mysql');

var pool = mysql.createPool({     
  host     : 'things.buzz',
  user     : 'root',
  password : 'P@ssw0rd',
  port     : '3306',
  database : 'beacondb'
}); 

//00
this.getBeaconInfoList=function(socket){
    pool.getConnection(function(err,connection){

      var sql='select * from beacon';

      var sql_t =' select beaconid,(select paramvalue from sysparams where paramkey=b.triggertype'
          sql_t+=" and paramgroup='triggertype' limit 0,1) as triggertype"
          sql_t+=' ,b.triggercount,b.triggerfrequency'
          sql_t+=' ,(select paramvalue from sysparams where paramkey=c.contenttype'
          sql_t+=" and paramgroup='contenttype' limit 0,1) as contenttype,c.serverpath,c.clientpath,c.filename"
          sql_t+=' ,c.description_cn,c.description_en,c.description_tw,c.description_pt'
          sql_t+=' ,c.title_cn,c.title_en,c.title_tw,c.title_pt,c.artist_cn,c.artist_en,c.artist_tw,c.artist_pt'
          sql_t+=' ,year,c.id from beaconaction a'
          sql_t+=' left join `trigger` b'
          sql_t+=' on a.triggerid=b.id'
          sql_t+=' left join content c'
          sql_t+=' on a.contentid=c.id';

      var json={};

      connection.query(sql,function(err,rows){
        if(err){
          console.log('[getBeaconInfoList-beacons error] - ',err.message);
        }
        console.log(rows);
        json.beacon=rows;

      });
      connection.query(sql_t,function(err,rows){
        if(err){
          console.log('[getBeaconInfoList-actions error] - ',err.message);
        }

        console.log(rows);
        json.action=rows;
        socket.write(JSON.stringify(json));
        connection.release();
      });
    })
}

//01
this.getBeaconInfo=function(socket,params){
    pool.getConnection(function(err,connection){
        var sql='select * from beacon where major=? and minor=? limit 0,1;';

        var sql_t =' select b.* from beaconaction a'
            sql_t+=' left join `trigger` b'
            sql_t+=' on a.triggerid=b.id'
            sql_t+=' where a.beaconid'
            sql_t+=' in(select beaconid from beacon where major=? and minor=?) order by a.triggerid';

        var sql_c =' select b.* from beaconaction a'
            sql_c+=' left join `content` b'
            sql_c+=' on a.contentid=b.id'
            sql_c+=' where a.beaconid'
            sql_c+=' in(select beaconid from beacon where major=? and minor=?) order by a.triggerid';

        var arr=params.split(',');
        var json={};
        if(arr.length==2){
          connection.query(sql,arr,function(err,rows){
            if(err){
              console.log('[getBeaconInfo error] - ',err.message);
            }
            console.log(rows);  
            json.beacon=rows;
            //socket.write(JSON.stringify(rows));
            //connection.release();
          });

          connection.query(sql_t,arr,function(err,rows){
            if(err){
              console.log('[getBeaconInfo_trigger error] - ',err.message);
            }
            console.log(rows);  
            json.triggers=rows;
            //socket.write(JSON.stringify(json));
            //connection.release();
          });

          connection.query(sql_c,arr,function(err,rows){
            if(err){
              console.log('[getBeaconInfo_content error] - ',err.message);
            }
            console.log(rows);  
            json.contents=rows;
            socket.write(JSON.stringify(json));
            connection.release();
          });

        }
    })
}

//02
this.getSysparams=function(socket){
    pool.getConnection(function(err,connection){
      var sql='select * from sysparams';
      connection.query(sql,function(err,rows){
        if(err){
          console.log('[getSysparams error] - ',err.message);
        }
        console.log(rows);  
        socket.write(JSON.stringify(rows));
        connection.release();
      });
    })
}

//03
this.getUserInfo=function(socket,params){
    pool.getConnection(function(err,connection){
      var sql ='select userid, username_cn, username_en, username_tw, username_pt, nickname, voicelang'
          sql+=', defaultlang, email from user where userid=? and password=? and isauthorized=1 limit 0,1';
      var arr=params.split(',');
      if(arr.length==2){
        connection.query(sql,arr,function(err,rows){
          if(err){
            console.log('[getUserInfo error] - ',err.message);
          }
          console.log(rows);  
          socket.write(JSON.stringify(rows));
          connection.release();
        });
      }
    })
}

//04
this.addSyslog=function(socket,params){
    pool.getConnection(function(err,connection){
      var sql = 'INSERT syslog(userid,beaconid,logtime,triggertype) VALUES (?,?,?,?)';
      var arr=params.split(',');
      if(arr.length==4){
        connection.query(sql,arr,function(err,result){
          if(err){
            console.log('[addSyslog error] - ',err.message);
          }
          console.log(result);  
          socket.write(JSON.stringify(result));
          connection.release();
        });
      }
    });
}

//05
this.addUser=function(socket,params){
    pool.getConnection(function(err,connection){
      var sql = 'INSERT `user`(userid,password,nickname,voicelang,defaultlang,email) VALUES (?,?,?,?,?,?)';
      var arr=params.split(',');
      if(arr.length==5){
        arr.push(arr[0]);
        connection.query(sql,arr,function(err,result){
          if(err){
            console.log('[addUser error] - ',err.message);
          }
          console.log(result);  
          socket.write(JSON.stringify(result));
          connection.release();
        });
      }
    });
}

//06
this.findUser=function(socket,params){
    pool.getConnection(function(err,connection){
      var sql = 'select count(*) as count from user where userid=?';
      connection.query(sql,params,function(err,rows){
        if(err){
          console.log('[findUser error] - ',err.message);
        }
        console.log(rows);  
        socket.write(JSON.stringify(rows[0]));
        connection.release();
      });
    })
}

//07
this.getExTagList=function(socket){
    pool.getConnection(function(err,connection){
      var sql = ' select a.extag,a.description_cn,a.description_en,a.description_pt,a.description_tw'
          sql+= ' ,b.serverpath,b.clientpath,b.filename,a.title_cn,a.title_en,a.title_tw,a.title_pt'
          sql+= ' from exmaster a'
          sql+= ' left join content b'
          sql+= ' on a.contentid=b.id'
          sql+= ' where DATEDIFF(now(),a.expireddate)<=0'

        connection.query(sql,function(err,result){
          if(err){
            console.log('[getExTagList error] - ',err.message);
          }
          console.log(result);  
          socket.write(JSON.stringify(result));
          connection.release();
        });
    });
}

/*
this.query=function(socket){
    pool.getConnection(function(err,connection){
        connection.query('select beaconid from beacon limit 0,1',function(err,rows){
          if(err){
            console.log('[query error] - ',err.message);
          }
          console.log('The beaconid is: ', rows[0].beaconid);  
          socket.write(rows[0].beaconid);
          connection.release();
        });
    })

};

this.insert=function(socket){
  pool.getConnection(function(err,connection){
    var sql = 'insert user(userid,usernamecn,usernameen) values(?,?,?)';
    var params = ['testuid','testcn','testen'];
    connection.query(sql,params,function(err,result){
      if(err){
        console.log('[insert error] - ',err.message);
      }
      console.log('insert');  
      socket.write('insert');
      connection.release();
    });
  });
};

this.update=function(socket){
  pool.getConnection(function(err,connection){
    var sql = 'update user set userid=? where id>=?';
    var params = ['testuid_u',3];
    connection.query(sql,params,function(err,result){
      if(err){
        console.log('[update error] - ',err.message);
      }
      console.log('update');  
      socket.write('update');
      connection.release();
    });
  });
};
*/
