$(document).ready(function() {

    $('#categoryList > li').on('click', function(){
        var c = $(this).attr("value");
        $('.selectedCate').attr("value", c);
        $('#contentsArea img').remove();
        $('#secondContentsArea img').remove();
        $('.content-searchDetail').hide(500);
        $('.itemArea table tbody tr').remove();
        $('.itemArea').hide();

        var uri = "";
        var tier = "";
        var arr_show;
        // T4_SHOES_LEATHER_SET1.png?quality=5
        switch(c){
            case 'warrior' :
                tier = 'T8_';
                arr_show = weaponWarriorCate;
            break;
            case 'helmet' :
                 tier = 'T8_';
                 uri = 'HEAD_PLATE_';
                 arr_show = plateCate;
            break;
            case 'armor' :
                tier = 'T8_';
                uri = 'ARMOR_PLATE_';
                arr_show = plateCate;
            break;
            case 'boots' :
                tier = 'T8_';
                uri = 'T8_SHOES_PLATE_';
                arr_show = plateCate;
            break;

            case 'hunter' :
                tier = 'T8_';
                arr_show = weaponHunterCate;
            break;
            case 'hood' :
                tier = 'T8_';
                uri = 'HEAD_LEATHER_';
                arr_show = leatherCate;
            break;
            case 'jacket' :
                tier = 'T8_';
                uri = 'ARMOR_LEATHER_';
                arr_show = leatherCate;
            break;
            case 'shoes' :
                tier = 'T8_';
                uri = 'SHOES_LEATHER_';
                arr_show = leatherCate;
            break;

            case 'mage' :
                tier = 'T8_';
                arr_show = weaponMageCate;
            break;
            case 'cowl' :
                tier = 'T8_';
                uri = 'HEAD_CLOTH_';
                arr_show = clothCate;
            break;
            case 'robe' :
                tier = 'T8_';
                uri = 'ARMOR_CLOTH_';
                arr_show = clothCate;
            break;
            case 'sandals' :
                tier = 'T8_';
                uri = 'SHOES_CLOTH_';
                arr_show = clothCate;
            break;

            case 'cape' :
                tier = 'T8_';
                arr_show = capeCate;
            break;
            case 'bag' :
                tier = 'T8_';
                arr_show = bagCate;
            break;
            case 'potion' :
                tier = 'T8_';
                arr_show = potionCate;
            break;
            case 'food' :
                tier = 'T8_';
                arr_show = foodCate;
            break;
        }



        for(var i = 0; i < arr_show.length; i++){
//            var url = uri + arr_show[i] + ".png?quality=5";
//            $('#contentsArea').append('<a class="itemImage" href="https://render.albiononline.com/v1/item/' + url + '" />');

            var url = "/image/" + tier + uri + arr_show[i] + ".png";
            $('#contentsArea').append('<img class="itemImage" src="' + url + '" value="' + uri + arr_show[i] + '" />');
        }
    });

    $('#contentsArea').on('click', '.itemImage' ,function(){
        var val = $(this).attr("value");
        $('#secondContentsArea img').remove();
        $('.content-searchDetail').hide();
        $('.itemArea table tbody tr').remove();
        $('.itemArea').hide();

        var selectedCategory = $('.selectedCate').attr("value");


        if("_" + val in itemTree) {
            var arr = itemTree["_" + val];

            if(selectedCategory == 'potion' || selectedCategory == 'food'){

            }else{
                for(var i = 0; i < arr.length; i++){
    //              var url = "T8_" + arr[i] + ".png?quality=5";
    //              $('#secondContentsArea').append('<a class="itemImage" href="https://render.albiononline.com/v1/item/' + url + '" />');
                    var url = "/image/T8_" + arr[i] + ".png";
                    $('#secondContentsArea').append('<img class="itemImage" src="' + url + '"  value="' + arr[i] + '" />');
                }
            }

        } else {
            $('.itemArea table tbody tr').remove();
            $('.itemArea').hide();
            $('.itemImage').removeClass('selctedItem');
            $(this).addClass('selctedItem');

            showMarketData();
        }
    });

    $('#secondContentsArea').on('click', '.itemImage' ,function(){
        $('.itemArea table tbody tr').remove();
        $('.itemArea').hide();
        $('.itemImage').removeClass('selctedItem');
        $(this).addClass('selctedItem');

        showMarketData();
    });

    function showMarketData(){
         //            var city = $('input[name="cities"]:checked').val();
         //            if(city == undefined || city == null){
         //                alert('도시를 선택해주세요.');
         //                return;
         //            }
         //            console.log("도시 : " + city);

         var server = $('input[name="server"]:checked').val();
         var quality = $('input[name="quality"]:checked').val();
         var tier = $('#tier option:checked').val();
         var dotTier = $('#dotTier option:checked').val();
         var itemName = $('.selctedItem').attr('value');

         $.ajax({
             type : 'get',
             url : '/market/getPrice',
             async : true,
             dataType : 'json',
             data : {
                 server : server,
                 quality : quality,
                 tier : tier,
                 dotTier : dotTier,
                 itemName : itemName
             },
             beforeSend : function(){
                turnLoading();
             },
             success : function(result) {
                 console.log(result.length);
                 for(var i = 0; i < result.length; i++){
                    var city = result[i].city;
                    var sellMin = result[i].sell_price_min;
                    var sellMax = result[i].sell_price_max;
                    var buyMin = result[i].buy_price_min;
                    var buyMax = result[i].buy_price_max;

                    $('.itemArea table caption img').attr("src", "https://render.albiononline.com/v1/item/" + tier + "_" + itemName + dotTier + "?quality=" + quality);
                    $('.itemArea table tbody').append("<tr class='"+city+"'><tr>");
                    $('.itemArea table tbody .' + city).append("<td>" + sellMin.toLocaleString() +  "</td>");
                    $('.itemArea table tbody .' + city).append("<td>" + sellMax.toLocaleString() +  "</td>");
                    $('.itemArea table tbody .' + city).append("<td>" + buyMin.toLocaleString() +  "</td>");
                    $('.itemArea table tbody .' + city).append("<td>" + buyMax.toLocaleString() +  "</td>");
                    $('.itemArea table tbody').append("<tr class='space'><tr>");
                 }
                 $('.itemArea').fadeIn(500);
                 turnLoading();

                 $('.content-searchDetail').fadeIn(500);
             },
             error : function(request, status, error) {
                 console.log(error);
             }
         });


    }



});