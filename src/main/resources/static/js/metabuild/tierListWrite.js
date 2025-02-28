let itemList = null;
const tier = [
    ['S', 0], 
    ['A', 1], 
    ['B', 2], 
    ['C', 3], 
    ['D', 4], 
    ['E', 5],  
    ['F', 6],  
    ['Z', 7], 
];
const maxTier = tier.length;
let index = 0;

fetch('/static/jsonData/items.json')
.then(response => response.json())
.then(data => console.log(data))
.catch(error => console.log(error));



function addTier(){    
    if(index >= 0 && index < tier.length){
        const id = tier[index][0];
        const tierElement = document.getElementById(id);
        tierElement.className += ' show';
        index++;
    }else if(index == tier.length){
        showAlert('더 이상 추가할 수 없습니다.')
    }
}