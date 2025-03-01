$(document).ready(function () {
    $('.right').on('click', function(e) { 
        let div = $(this).siblings('.admin-answer');
        
        if(div.css('display') == 'block'){
            div.fadeOut(500);
        }else{
            div.fadeIn(500);
        }
        
    });

    $('.btn').on('click', function () {

        let text = $('.input-text').val().trim();
        
        if(text == null || text == ''){
            showAlert('문의 & 피드백할 내용을 입력해주세요.');
            return;    
        }

        if($.cookie('register-inquire') == "done"){
            showAlert('5분 후에 다시 작성하실 수 있습니다.');
            return;
        }

        $.ajax({
            type : 'post',
            url : '/inquire/register',
            async : true,
            dataType : 'json',
            data : {
                text : text
            },
            beforeSend: function() {
                turnLoading();
            },
            success : function(result) {

                var date = new Date();
                date.setTime(date.getTime() + 5*60*1000); // 1분
                $.cookie('register-inquire', 'done', { expires: date, path : '/' });

                let li = $('#exam').clone(true);
                li.children('.text').text(result.content);
                li.children('.admin-answer').children('.answer').text(result.answer);
                li.css('display', '');

                $('.inquireList').append(li);
                turnLoading();

            },
            error : function(request, status, error) {
                console.log(error);
            }
        });
    });


    function showAlert(msg){
        $.confirm({
            theme: 'supervan',
            title: '',
            content: msg,
            buttons: {
                '네': function () {}
            }
        });
    }
});