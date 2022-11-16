window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.ajax = function (data, success) {
    $.ajax({
        type: "POST",
        url: "",
        dataType: "json",
        data: data,
        success: success
    });
}

window.ajaxButtonArticle = function ($button, actionName, text) {
    const data = {
        action: actionName,
        postId: $button.val(),
        userId: $button.attr('name')
    };
    ajax(data, function(response){
        if (response["redirect"]) {
            location.href = response["redirect"];
        } else {
            $button.toggleClass('show hide');
            $button.text(text);
        }
    })
}

window.ajaxButtonAdmin = function ($button, actionName, text) {
    const data = {
        action: actionName,
        userId: $button.val()
    };
    ajax(data, function(response){
        if (response["redirect"]) {
            location.href = response["redirect"];
        } else {
            $button.toggleClass('enable disable');
            $button.text(text);
        }
    })
}

window.ajaxErrAndRedir = function (data, $error) {
    ajax(data, function (response) {
        if (response["error"]) {
            $error.text(response["error"]);
        } else {
            location.href = response["redirect"];
        }
    });
}

window.ajaxErr = function (data, $error) {
    ajax(data, function (response) {
        if (response["error"]) {
            $error.text(response["error"]);
        }
    });
}

window.findBy = function (mas, key, value) {
    mas.forEach(function (el) {
            if (el[key] === value) {
                return el[key];
            }
        }
    )
    // for (let i = 0; i < mas.length; i++) {
    //     if (mas[i].key === value) {
    //         return value;
    //     }
    // }
}


