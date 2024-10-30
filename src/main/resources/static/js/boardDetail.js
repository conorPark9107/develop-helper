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

    $('li').on('click', '.comment-submit_c', function(){
        let comment_group = $(this).closest('li').attr('id');
        let nickname = $('input[name=nickname_c]').val();
        let password = $('input[name=password_c]').val();
        let comment = $('textarea[name=comment_c]').val();

        $('#h_nickname').val(nickname);
        $('#h_password').val(password);
        $('#h_comment').val(comment);
        $('#h_comment_group').val(comment_group);
        $('.form').submit();
    });

    $('.comment-submit').on('click', function(){
        let nickname = $('input[name=nickname]').val();
        let password = $('input[name=password]').val();
        let comment = $('textarea[name=comment]').val();

        $('#h_nickname').val(nickname);
        $('#h_password').val(password);
        $('#h_comment').val(comment);
        $('.form').submit();
    });

});