$(document).ready(function() {

    logJsonData();

    
});


async function logJsonData(){
    const response = await fetch("/jsonData/items_Original.json");
    const jsonData = await response.json();
    return jsonData;
}
