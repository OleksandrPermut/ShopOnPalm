async function GetDeletedProduct() {
    let url = new URL(document.URL);
    let params = url.searchParams;
    let id = params.get('id');

    let response = await fetch('http://localhost:8080/products/' + id);
    let responseJson = await response.json();


    for (a in responseJson) {
        document.getElementById(a).value = responseJson[a];
    }

    let response2 = await fetch('http://localhost:8080/products/isProductSelling/' + id);
    let responseJson2 = await response2.json();
    if (responseJson2 == true) {
        document.getElementById('removeForm').innerHTML = "<h3>" + responseJson.name+" is selling in some shop, so it can`t be removed. At first you must delete product from all stores</h3>";
    }
}

GetDeletedProduct();

async function DeleteProduct(event) {
    event.preventDefault();
    var formData = new FormData(event.target) 
    try {
        const response = await fetch('http://localhost:8080/products/' + formData.get('id'), {
            method: 'DELETE'
        });     
        window.location.href = "Products.html";
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