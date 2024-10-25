


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
        $("#hidden_input").val(quill.root.innerHTML);
        var img = $('img');
        if(img.length > 0){
            var sumFileSize = 0;

            for(var i = 0; i < img.length; i++){
                var file = base64toFile(img[i].src, i);
                sumFileSize += file.size;

                if(sumFileSize > 52428800){
                    $.confirm({
                        theme: 'supervan',
                        title: '',
                        content: '게시글에는 50MB까지 이미지등록을 허용합니다.',
                        buttons: {
                            '돌아가기': function () {
                                $("img :last-child").remove();
                                return;
                            }
                        }
                    });
                }
            }
        }
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
        for(var i = 0; i < img.length; i++){
            var file = base64toFile(img[i].src, uuidv4() + ".png");
            var url = uploadFile(file, file.name);
            console.log(url)






//            formData.append('images', file);

        }

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/board/register",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            success: (data) => {

                formData.append('title', title);
                formData.append('password', password);

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

/* supabase */

const SUPABASE_URL = 'https://umzwbuofpryvyimxrxem.supabase.co';
const SUPABASE_ANON_KEY = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVtendidW9mcHJ5dnlpbXhyeGVtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mjk4Mjg3MzQsImV4cCI6MjA0NTQwNDczNH0.-023sQ4iwStD9lIQggNwwtYyolpQiF8tNdZo8gAfkwk';
const PUBLIC_STORAGE_BUCKET = 'AH_Board_Images';
const client = supabase.createClient(SUPABASE_URL, SUPABASE_ANON_KEY);


async function uploadFile(file, fileName){
    const { data, error } = await client
    .storage.from(PUBLIC_STORAGE_BUCKET)
    .upload(fileName, file, {
        cacheControl: '3600',
        upsert: false
    });
    return downloadFile(data.path);
}

async function downloadFile(fileName){

    const { data, error } = client.storage.from(PUBLIC_STORAGE_BUCKET).getPublicUrl(fileName, {})
    return data.publicUrl;
}
