async function GetDeletedShop() {
    let url = new URL(document.URL);
    let params = url.searchParams;
    let id = params.get('id');

    const response = await fetch('http://localhost:8080/shops/' + id);
    const responseJson = await response.json();

    for (a in responseJson) {
        document.getElementById(a).value = responseJson[a];
    }
}

GetDeletedShop();

async function DeleteShop(event) {
    event.preventDefault();
    var formData = new FormData(event.target) 
    try {
        const response = await fetch('http://localhost:8080/shops/' + formData.get('id'), {
            method: 'DELETE'
        });     
        window.location.href = "Shops.html";
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