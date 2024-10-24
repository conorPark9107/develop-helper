$(document).ready(function(){
    const quill = new Quill('#editor', {
       modules: {
            toolbar: [
                  ['bold', 'italic', 'underline'],
                  ['image'],
                  ['blockquote'],
            ],
      },
      placeholder: '여기부터 글 작성..',
      theme: 'snow',
    });

    quill.on('text-change', function() {
        document.getElementById("hidden_input").value = quill.root.innerHTML;
    });

    $('.submit').on('click', function(){
        const delta = quill.getContents();
        const html = quill.root.innerHTML.trim();
        console.log(delta);
        console.log(html);
        quill.root.innerHTML = html + '<p>테스트하는 중입니다</p>';

        var img = $('img'); // 이미지들.
        var title = $('#title').val();
        var password = $('#password').val();

        if(title == ''){
            $('.title-warning-message').show();
            setTimeout(() => {
              $('.title-warning-message').delay(3000).hide();
            }, 3000);
            return;
        }
        if(password == ''){
             $('.password-warning-message').show();
             setTimeout(() => {
               $('.password-warning-message').delay(3000).hide();
             }, 3000);
             return;
        }

        const formData = new FormData();
        formData.append('title', title);
        for(var i = 0; i < img.length; i++){
            formData.append('images', base64toFile(img[i].src, i));
        }
        formData.append('password', password);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/board/register",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            success: (data) => {
                alert("yes : " + data);
            },
            error: function(xhr, status, error) {
                alert(xhr.responseText);
            }
        });




    });

    $('.cancel').on('click', function(){
        $.confirm({
            theme: 'supervan',
            title: '',
            content: '글 작성을 취소하시겠습니까?',
            buttons: {
                '네': function () {
                    javascript:history.go(-2);
                },
                '아니오': function () {

                }
            }
        });
    });


});

function base64toFile(base_data, filename) {

    var arr = base_data.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);

    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }

    return new File([u8arr], filename, {type:mime});
}


function clicked_Category(li){
    $('.category').css('font-size', '0.5em');
    $('.category').css('color', '');
    $(li).css('color', 'RGB(28, 120, 153)');
    $(li).css('font-size', '1em');
}
