

async function getShops() {
    const response = await fetch('http://localhost:8080/shops');
    const responseJson = await response.json();    
    CreateHTMLTableFromJson(responseJson);
}
getShops();

function CreateHTMLTableFromJson(jsonData) {     
    var columns = [];
    for (var i = 0; i < jsonData.length; i++) {
        for (var key in jsonData[i]) {
            if (columns.indexOf(key) === -1) {
                columns.push(key);
            }
        }
    }
    
    var htmlTable = document.createElement("table");
  
    var tableRow = htmlTable.insertRow(-1);                   
    for (var i = 1; i < columns.length; i++) {
        var tableHeader = document.createElement("th");     
        tableHeader.innerHTML = columns[i];
        tableRow.appendChild(tableHeader);
    }

   
    for (var i = 0; i < jsonData.length; i++) {
      
        tableRow = htmlTable.insertRow(-1);

        for (var j = 1; j < columns.length; j++) {
            var tableCell = tableRow.insertCell(-1);
            if (j == 1) {
              
                tableCell.innerHTML = '<a href="ManageProductsInShop.html?id=' + jsonData[i]['id']+'">' + jsonData[i][columns[j]] + '</a>';
            }
            else
                tableCell.innerHTML = jsonData[i][columns[j]];
        }

        var tableEditCell = tableRow.insertCell(-1);
        tableEditCell.innerHTML = '<a href="EditShop.html?id=' + jsonData[i]['id'] +'"><i class="fa fa-edit"></i></a>';
        var tableDeleteCell = tableRow.insertCell(-1);
        tableDeleteCell.innerHTML = '<a href="DeleteShop.html?id=' + jsonData[i]['id'] + '"><i class="fa fa-trash-o"></i></a>';
    }

    
    var divHolder = document.getElementById("tableHolder");
    divHolder.innerHTML = "";
    divHolder.appendChild(htmlTable);
}