
async function GetEditedShop() {
    let url = new URL(document.URL);
    let params = url.searchParams; 
    let id = params.get('id');

    const response = await fetch('http://localhost:8080/shops/' + id);
    const responseJson = await response.json();

    for (a in responseJson) {      
        document.getElementById(a).value = responseJson[a];
    }

}

GetEditedShop();

async function EditShop(event) {
    event.preventDefault();
    var formData = new FormData(event.target)
    var objectsData = {};
    formData.forEach((value, key) => objectsData[key] = value);
    var json = JSON.stringify(objectsData);
    try {
       
        const response = await fetch('http://localhost:8080/shops/' + formData.get('id'), {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: json
        });
        await response.json();
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