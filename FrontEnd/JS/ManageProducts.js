async function getAvailableProducts() {

    let url = new URL(document.URL);
    let params = url.searchParams;
    let id = params.get('id');

    const response = await fetch('http://localhost:8080/products/getAvailableProducts/' + id);
    const responseJson = await response.json();
    CreateHTMLTableFromJsonAvailableProducts(responseJson);
}

async function getProductsInShop() {

    let url = new URL(document.URL);
    let params = url.searchParams;
    let id = params.get('id');

    const response = await fetch('http://localhost:8080/shops/getProductsInShop/' + id);
    const responseJson = await response.json();
    CreateHTMLTableFromJsonProductsInShop(responseJson);
}


getAvailableProducts();
getProductsInShop();



function CreateHTMLTableFromJsonAvailableProducts(jsonData) {
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

    var tableHeader = document.createElement("th");
    tableHeader.innerHTML = 'Price';
    tableRow.appendChild(tableHeader);

    var tableHeader = document.createElement("th");
    tableHeader.innerHTML = 'Amount';
    tableRow.appendChild(tableHeader);


    for (var i = 0; i < jsonData.length; i++) {

        tableRow = htmlTable.insertRow(-1);

        for (var j = 1; j < columns.length; j++) {
            var tableCell = tableRow.insertCell(-1);
            tableCell.innerHTML = jsonData[i][columns[j]];
        }

        var tableEditCell = tableRow.insertCell(-1);
        tableEditCell.innerHTML = '<input required type="text" id="priceInput' + jsonData[i]['id'] + '"/>';
        var tableEditCell = tableRow.insertCell(-1);
        tableEditCell.innerHTML = '<input required type="text" id="amountInput' + jsonData[i]['id'] + '"/>';
        var tableEditCell = tableRow.insertCell(-1);
        tableEditCell.innerHTML = '<input type="button" value="Add product" onclick="AddProduct(' + jsonData[i]['id']+')">';
  
    }


    var divHolder = document.getElementById("tableHolderAvailableProduct");
    divHolder.innerHTML = "";
    divHolder.appendChild(htmlTable);
}

function isFloat(x) {
    return typeof x == 'number' && !isNaN(x);

}

async function AddProduct(id) {
    let price = document.getElementById('priceInput' + id).value;
    let amount = document.getElementById('amountInput' + id).value;

    let url = new URL(document.URL);
    let params = url.searchParams;
    let shopId = params.get('id');
    
    if (price == '') {
        alert('Error - please enter price');
        return;
    }
    else
        if (isNaN(price)) {
            alert('Incorrect price');
            return;
        }

    if (amount == '') {
        alert('Error - please enter amount');
        return;
    }
    else
        if (isNaN(amount)) {
            alert('Incorrect amount');
            return;
        }

    try {
        const response = await fetch('http://localhost:8080/shops/addProductToShop?shopId=' + shopId + '&productId=' + id + '&price=' + price + '&amount=' + amount, {
            method: 'PUT',           
        });
        await response.json();

        getAvailableProducts();
        getProductsInShop();
    }
    catch (e) {
        if (typeof e.json === "function") {
            e.json().then(jsonError => {
                alert("Json error from API");
                alert(jsonError);
            }).catch(genericError => {
                alert("Generic error from API");
                alert(error.statusText);
            });
        } else {
            alert("Fetch error");
            alert(e);
        }
    }
}


function CreateHTMLTableFromJsonProductsInShop(jsonData) {
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
    for (var i = 2; i < columns.length; i++) {
        var tableHeader = document.createElement("th");
        tableHeader.innerHTML = columns[i];
        tableRow.appendChild(tableHeader);
    }


    for (var i = 0; i < jsonData.length; i++) {

        tableRow = htmlTable.insertRow(-1);

        for (var j = 2; j < columns.length; j++) {
            var tableCell = tableRow.insertCell(-1);
            if (j == 2) 
                tableCell.innerHTML = jsonData[i][columns[j]]['name'];            
            else
                tableCell.innerHTML = jsonData[i][columns[j]];
        }

     
        var tableDeleteCell = tableRow.insertCell(-1);
        //tableDeleteCell.innerHTML = '<input type="button" value="Delete product" onclick="DeleteProduct(' + jsonData[i]['id'] + ')">';
        tableDeleteCell.innerHTML = '<a href="#" onclick="event.preventDefault(); DeleteProduct(' + jsonData[i]['id'] + ');"><i class="fa fa-trash-o"></i></a>';
    }


    var divHolder = document.getElementById("tableHolderSellingProduct");
    divHolder.innerHTML = "";
    divHolder.appendChild(htmlTable);
}

async function DeleteProduct(id) {
 
    let url = new URL(document.URL);
    let params = url.searchParams;
    let shopId = params.get('id');
   

    try {
        const response = await fetch('http://localhost:8080/shops/removeProductFromShop?shopId=' + shopId + '&productInShopId=' + id, {
            method: 'DELETE',
        });
        await response.json();

        getAvailableProducts();
        getProductsInShop();
    }
    catch (e) {
        if (typeof e.json === "function") {
            e.json().then(jsonError => {
                alert("Json error from API");
                alert(jsonError);
            }).catch(genericError => {
                alert("Generic error from API");
                alert(error.statusText);
            });
        } else {
            alert("Fetch error");
            alert(e);
        }
    }
}