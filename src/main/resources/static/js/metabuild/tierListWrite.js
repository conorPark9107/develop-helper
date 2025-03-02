let itemList = null;
const tier = [
    
];
const maxTier = tier.length;
let index = 0;

fetch('/static/jsonData/items.json')
.then(response => response.json())
.then(data => console.log(data))
.catch(error => console.log(error));



function addTier(){

    const inputTierData = document.getElementById('tierStr').value;
    
    if(inputTierData === '' || inputTierData === undefined){
        showAlert('티어명을 입력해주세요.');
        return;
    }

    if(index >= 0 && index < tier.length){
        const id = tier[index][0];
        const tierElement = document.getElementById(id);
        tierElement.className += ' show';
        index++;
    }else if(index == tier.length){
        showAlert('더 이상 추가할 수 없습니다.')
    }
}