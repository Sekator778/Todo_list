function changeDoneItem(id) {
    var doneChecked = document.getElementById(id).checked;
    var doneToSend = "undone";
    if(doneChecked) {
        doneToSend = "done";
    }
    $.ajax("./items", {
        type: "post",
        data: JSON.stringify({
            id: id,
            done: doneToSend
        }),
        dataType: "json"
    }).done(function (data) {
        if (data.success === true) {
            alert("Task with id = " + data.id + " status of done changed to " + data.done + ".");
        }
        if (data.success === false) {
            alert("Error changed status of done");
        }
    })
}

<!--get items from db and populate table-->
function getTodoList(typeOfDone) {
    var urlGetQuery = './items?done=' + typeOfDone;
    $.ajax(urlGetQuery).done(function (data) {
        var refreshBody = '';
        var json = JSON.parse(data);
        for (item in json) {
            var idRow = json[item].id.toString();
            refreshBody += '<tr><td>' + idRow + '</td><td>' + json[item].description
                + '</td><td>' + json[item].date + '</td><td>';
            if (json[item].done.toString() === 'done') {
                refreshBody += '<input type="checkbox" id="' + idRow + '" onchange="changeDoneItem(this.id)" checked>' + '</td>';
            } else {
                refreshBody += '<input type="checkbox" id="' + idRow + '" onchange="changeDoneItem(this.id)">' + '</td>';
            }
            refreshBody += '</tr>';
        }
        $("#table_todo").html(refreshBody);
    })
}

<!--call function getTodoList with parameter, which take from group input type radio (name = typeOfGetItem)-->
function changeGetAllItems(id) {
    if (id === "allItems") {
        getTodoList("all");
    }
    if (id === "doneItems") {
        getTodoList("done");
    }
    if (id === "undoneItems") {
        getTodoList("undone");
    }
}

<!--download all items, when page is opened-->
$(
    getTodoList("all")
)

function addItem() {
    var desc = $('#newItemDescription').val();
    if (!/[^ ]/.test(desc)) {
        alert("Input description of item");
        return;
    }
    $.ajax("./save", {
        type: "post",
        data: JSON.stringify({
            description: desc
        }),
        dataType: "json"
    }).done(function (data) {
        if (data.success === true) {
            alert("Item was added");
            alert(data.created);
            var rowToAdd = '<tr><td>' + data.id + '</td><td>' + data.description + '</td><td>'
                + data.created + '</td><td>'
                + '<input type="checkbox" id="' + data.id + '" onchange="changeDoneItem(this.id)">' + '</td></tr>';
            $("#table_todo").append(rowToAdd);
        }
        if (data.success === false) {
            alert("Error adding item");
        }
    })
}