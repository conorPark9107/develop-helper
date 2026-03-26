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
        let thisE = $(this).parent().parent().children('.comment-contents').children();

        if(thisE.text() == '삭제된 댓글입니다.'){
            showAlert('이미 삭제된 댓글입니다.');
            return;
        }

        $.confirm({
            theme: 'supervan',
            title: '정말로 댓글을 삭제하시겠습니까?',
            content: '<p>삭제를 원하신다면 <u>비밀번호</u>를 입력하고 확인버튼을 누르세요.</p>' +
                     '<p>버튼 클릭시 곧 바로 <span style="color:red; opacity:0.7; font-weight: bold;">삭제</span>되니 신중하게 눌러주세요.</p>' +
                     '<input class="inputPw" type="password" name="inputPassword" />',
            buttons: {
                '삭제': function () {
                    inputPw = this.$content.find('.inputPw').val();
                    if(pw == inputPw) { // 삭제 코드.
                        $.ajax({
                            type : 'post',
                            url : '/board/deleteComment',
                            async : true,
                            dataType : 'text',
                            data : {
                                id : id
                            },
                            success : function(result) {
                                thisE.text(result);
                            },
                            error : function(request, status, error) {
                                console.log(error);
                            }
                        });
                    }else{
                        showAlert('비밀번호가 맞지 않습니다.');
                    }
                },
                '돌아가기': function () {}
            }
        });
    });

    /* 대댓글 등록 버튼 이벤트. */
    $('.comment-list').on('click', '.comment-submit_c', function(){

        const $commentArea = $(this).closest('.comment-writer.comment-area');
        const comment_group = $commentArea.closest('li').attr('id');
        const nickname = $('input[name=nickname_c]').val();
        const password = $('input[name=password_c]').val();
        const comment = $('textarea[name=comment_c]').val();
        const board_id = $('#board_id').val();

        if (!password || password.trim() === '') {
            showAlert('댓글 비밀번호를 설정해주세요.');
            $('input[name=password_c]').val('');
            return;
        }

        if (!comment || comment.trim() === '') {
            showAlert('댓글을 작성해주세요.');
            $('textarea[name=comment_c]').val('');
            return;
        }

        const data = {
            board_id: board_id,
            comment_group: comment_group,
            nickname: nickname,
            password: password,
            comment: comment
        };

        fetch('/board/registerComment', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
        .then(res => {
             if (!res.ok) {
                 if (res.status === 429) {
                     alert("1분 후 다시 시도해주세요.");
                 } else if (res.status === 400) {
                     alert("입력값을 확인해주세요.");
                 } else {
                     alert("서버 오류가 발생했습니다.");
                 }
                 throw new Error("POST 실패");
             }
             return res;
         })
        .then(resData => {
            showAlert('댓글이 등록되었습니다.');
            window.location.href =`/board/detail?id=${board_id}`;
        })
        .catch(err => {
            console.error(err);
            window.location.href =`/board/detail?id=${board_id}`;
        });
    });

    /* 댓글 등록 버튼 이벤트. */
    $('.comment-submit').on('click', function(){

        const nickname = $('input[name=nickname]').val();
        const password = $('input[name=password]').val();
        const comment = $('textarea[name=comment]').val();
        const board_id = $('#board_id').val();

        // validation
        if (!password || password.trim() === '') {
            showAlert('댓글 비밀번호를 설정해주세요.');
            $('input[name=password]').val('');
            return;
        }

        if (!comment || comment.trim() === '') {
            showAlert('댓글을 작성해주세요.');
            $('textarea[name=comment]').val('');
            return;
        }

        const data = {
            board_id: board_id,
            nickname: nickname,
            password: password,
            comment: comment
        };

        fetch('/board/registerComment', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
        .then(res => {
             if (!res.ok) {
                 if (res.status === 429) {
                     alert("1분 후 다시 시도해주세요.");
                 } else if (res.status === 400) {
                     alert("입력값을 확인해주세요.");
                 } else {
                     alert("서버 오류가 발생했습니다.");
                 }
                 throw new Error("POST 실패");
             }
             return res;
         })
        .then(resData => {
            showAlert('댓글이 등록되었습니다.');
            window.location.href =`/board/detail?id=${board_id}`;
        })
        .catch(err => {
            console.error(err);
        });
    });

    /* 추천 버튼 클릭 이벤트 */
    $('.updown-area a').on('click', function(){
        let text = $(this).text();
        let boardId = $('#h_board_id').attr('value');
        let status = '';
        if(text == '🔺'){
            status = 'p';
            if(getCookie(`${boardId}-up`) == "done"){
                showAlert("추천은 24시간에 한번씩 누를 수 있습니다.");
                return;
            }
            setCookie(`${boardId}-up`, "done" , 1 ); // 저장될 쿠키명 , 쿠키 value값 , 기간
        }else if(text == '🔻'){
            status = 'm';
            if(getCookie(`${boardId}-down`) == "done"){
                showAlert("추천은 24시간에 한번씩 누를 수 있습니다.");
                return;
            }
            setCookie( `${boardId}-down`, "done" , 1 );
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

    /* 게시판 삭제 버튼 클릭 이벤트 */
    $('#deleteBtn').on('click', function(){
        $.confirm({
          theme: 'supervan',
          title: '',
          content: '<p>게시물 삭제 위해 게시물 <u>비밀번호</u>를 입력해주세요.</p>' +
                   '<p>삭제 버튼 클릭시 곧 바로 <span style="color:red; opacity:0.7; font-weight: bold;">삭제</span>되니 신중하게 눌러주세요.</p>' +
                   '<input class="inputPw" type="password" name="inputPassword" />',
          buttons: {
              '삭제': function () {
                  inputPw = this.$content.find('.inputPw').val();
                  id = $('#h_board_id').val();
                  $.ajax({
                      type : 'post',
                      url : '/board/checkPassword',
                      async : true,
                      dataType : 'text',
                      data : {
                          id : id,
                          password : inputPw
                      },
                      success : function(result) {
                          if(result == 'T'){
                            $('#deleteForm').submit();
                          }else{
                            showAlert('비밀번호가 맞지 않습니다.');
                          }
                      },
                      error : function(request, status, error) {
                          console.log(error);
                      }
                  });
              },
              '돌아가기': function () {}
          }
        });
    });

    /* 게시판 수정 버튼 클릭 이벤트 */
    /*$('#modifyBtn').on('click', function(){
          $.confirm({
              theme: 'supervan',
              title: '',
              content: '<p>게시물 수정 위해 게시물 <u>비밀번호</u>를 입력해주세요.</p>' +
                       '<input class="inputPw" type="password" name="inputPassword" />',
              buttons: {
                  '확인': function () {
                      inputPw = this.$content.find('.inputPw').val();
                      id = $('#h_board_id').val();
                      $.ajax({
                          type : 'post',
                          url : '/board/checkPassword',
                          async : true,
                          dataType : 'text',
                          data : {
                              id : id,
                              password : inputPw
                          },
                          success : function(result) {
                              if(result == 'T'){
                                $('#modifyForm').submit();
                              }else{
                                showAlert('비밀번호가 맞지 않습니다.');
                              }
                          },
                          error : function(request, status, error) {
                              console.log(error);
                          }
                      });
                  },
                  '돌아가기': function () {}
              }
          });
    });*/

    /* alert 함수 */
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

    /* 쿠키 Set */
    function setCookie( name, value, exDay ) {
        var today = new Date();
        today.setDate( today.getDate() + exDay );
        document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + today.toGMTString() + ";"
    }

    /* 쿠키 GET */
    function getCookie(name)
    {
        var obj = name + "=";
        var x = 0;
        while ( x <= document.cookie.length )
        {
            var y = (x+obj.length);
            if ( document.cookie.substring( x, y ) == obj )
            {
                if ((endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                    endOfCookie = document.cookie.length;
                return unescape( document.cookie.substring( y, endOfCookie ) );
            }
            x = document.cookie.indexOf( " ", x ) + 1;

            if ( x == 0 ) break;
        }
        return "";
    }
});

