$(document).ready(function() {
    
    $('.showWriter').on('click', function(){
        let x = $(this).closest('li').children('#write-comment');
        if(x.html() != undefined){
            x.remove();
            return ;
        }
        $('li #write-comment').remove();
        $(this).closest('li').append($('#write-comment').clone());
    });

    /* 댓글, 대댓글 삭제버튼 클릭 이벤트. */
    $('li').on('click', '.deleteCommentBtn', function(){
        let id = $(this).siblings('input[name=comment_id]').val();
        let pw = $(this).siblings('input[name=comment_pw]').val();
        let inputPw = '';
        $.confirm({
            theme: 'supervan',
            title: '정말로 댓글을 삭제하시겠습니까?',
            content: '<p>삭제를 원하신다면 비밀번호를 입력하고 확인버튼을 누르세요.</p>' +
                     '<p>버튼 클릭시 곧 바로 <span style="color:red; opacity:0.7; font-weight: bold;">삭제</span>되니 신중하게 눌러주세요.</p>' +
                     '<input class="inputPw" type="password" name="inputPassword" />',
            buttons: {
                '삭제': function () {
                    inputPw = this.$content.find('.inputPw').val();
                    if(pw == inputPw) { // 삭제 코드.
                        /* TODO : 여기 해야해!!!! */
//                        $.ajax({
//                            type : 'get',
//                            url : '/board/updown',
//                            async : true,
//                            dataType : 'json',
//                            data : {
//                                id : id,
//                                status : status
//                            },
//                            success : function(result) {
//                                $('.updown').text(result);
//                            },
//                            error : function(request, status, error) {
//                                console.log(error);
//                            }
//                        });


                    }else{ // 비밀번호가 맞지 않음.
                        showAlert('비밀번호가 맞지 않습니다.');
                    }
                },
                '돌아가기': function () {}
            }
        });


    });

    /* 대댓글 등록 버튼 이벤트. */
    $('li').on('click', '.comment-submit_c', function(){
        let comment_group = $(this).closest('li').attr('id');
        let nickname = $('input[name=nickname_c]').val();
        let password = $('input[name=password_c]').val();
        if(password.trim() == ''){
            showAlert('댓글 비밀번호를 설정해주세요.');
            password.val('');
        }
        let comment = $('textarea[name=comment_c]').val();
        if(comment.trim() == ''){
            showAlert('댓글을 작성해주세요.');
            comment.val('');
        }

        $('#h_nickname').val(nickname);
        $('#h_password').val(password);
        $('#h_comment').val(comment);
        $('#h_comment_group').val(comment_group);
        $('.form').submit();
    });

    /* 댓글 등록 버튼 이벤트. */
    $('.comment-submit').on('click', function(){
        let nickname = $('input[name=nickname]').val();
        let password = $('input[name=password]').val();
        let comment = $('textarea[name=comment]').val();
        if(password.trim() == ''){
            showAlert('댓글 비밀번호를 설정해주세요.');
            password.val('');
        }
        if(comment.trim() == ''){
            showAlert('댓글을 작성해주세요.');
            comment.val('');
        }


        $('#h_nickname').val(nickname);
        $('#h_password').val(password);
        $('#h_comment').val(comment);
        $('.form').submit();
    });

    /* 추천 버튼 클릭 이벤트 */
    $('.updown-area a').on('click', function(){
        let text = $(this).text();
        let status = '';
        if(text == '🔺'){
            status = 'p';
        }else if(text == '🔻'){
            status = 'm';
        }
        let id = $('#h_board_id').val();


        $.ajax({
            type : 'get',
            url : '/board/updown',
            async : true,
            dataType : 'json',
            data : {
                id : id,
                status : status
            },
            success : function(result) {
                $('.updown').text(result);
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

