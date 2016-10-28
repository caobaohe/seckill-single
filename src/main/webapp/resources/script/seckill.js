
var seckill = {
    URL : {//ajax请求路径
        now : function(){
            return "/seckill/time/now";
        },
        exposer : function(seckillId){
            return "/seckill/" + seckillId +"/exposer";
        },
        execute : function(seckillId, md5){
            return "/seckill/" + seckillId + "/" + md5 + "/execute";
        }
    },
    handleSeckill : function(seckillId, node){
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId), {}, function(result){
            if(result && result.success){
                var exposer = result.data;
                if(exposer.exposed){//秒杀是否开启
                    $('#killBtn').one('click', function(){
                        $('#killBtn').addClass('disabled');
                        $.post(seckill.URL.execute(exposer['seckillId'], exposer['md5']), {}, function(result){
                            if(result && result['success']){
                                var execution = result.data;
                                node.html('<label class="label label-success">' + execution.stateInfo + '</label>');
                            }else{
                                console.log("kill execute result:");
                                console.log(result);
                            }
                        });
                    });
                    node.show();
                }else{
                    seckill.countdown(exposer.seckillId, exposer.now, exposer.start, exposer.end);//重新倒计时
                }
            }else{
                console.log(result);
            }
        });

    },
    validateUser : function(SKUserId){
        if(SKUserId && SKUserId.length == 11 && !isNaN(SKUserId)){
            return true;
        }else {
            return false;
        }
    },
    countdown : function(seckillId, nowTime, startTime, endTime){
        var seckillBox = $('#seckillBox');
        if(nowTime < startTime){
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function(event){
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown', function(){
                seckill.handleSeckill(seckillId, seckillBox);
            });
        }else if(nowTime > endTime){
            seckillBox.html("秒杀已经结束");
        }else{
            seckill.handleSeckill(seckillId, seckillBox);
        }
    },
    detail : {
        init : function(params){
            var SKUserId = $.cookie('SKUserId');//使用jquery cookie插件获取cookie
            if(!seckill.validateUser(SKUserId)){
                // 打开模态框
                $('#myModal').modal({
                    show : true,
                    backdrop : 'static',
                    keyboard : false
                });
                $('#seckillLoginBtn').click(function(){
                    var userId = $('#usernameKey').val().trim();
                    var password = $('#passwordKey').val().trim();
                    if(!userId || !password || !seckill.validateUser(userId)){
                        $('#loginMessage').hide().html('<label class="label label-danger">用户名或密码错误！</label>').show(300);
                    }else{
                        $.cookie('SKUserId', userId, {express : 7, path : '/seckill'});//存储7天，作用路径/seckill
                        window.location.reload();
                    }
                });
            }else{
                var seckillId = params.seckillId;
                var satrtTiem = params.startTime;
                //var endTime = params.endTime;
                var endTime = params['endTime'];
                $.get(seckill.URL.now(), {}, function(result){
                    if(result && result.success){
                        seckill.countdown(seckillId, result.data, satrtTiem, endTime);
                    }else{
                        console.log(result);
                    }
                });
            }

        }
    },
};