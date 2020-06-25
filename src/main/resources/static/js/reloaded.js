$(document).ready(function () {
    const reloaded = function () {
        let location = '' + document.location;
        let id = location.replace(window.location.origin + '/', '');
        if (id === 'lk') {} else
            load_page('/' + id);
    };

    window.onload = function () {
        const loaded = sessionStorage.getItem('loaded');
        if (loaded) {
            reloaded();
        } else {
            sessionStorage.setItem('loaded', true);
        }
    }
});

function load_page(url, method) {
    $.ajax({
        url: url,
        method: method,
        dataType: "html",
        beforeSend: function () {
            $("#sh-content").addClass("d-none");
            $("#container-spinner").removeClass("d-none");
        },
        success: function (data) {
            if (data.redirect) {
                window.location.href = data.redirect;
            } else {
                $("#container-spinner").addClass("d-none");
                $("#sh-content").html(data);
                $("#sh-content").removeClass("d-none");
            }
        },
        error: function (data) {

            $("#container-spinner").addClass("d-none");
            $("#sh-content").html(data.responseText);
            $("#sh-content").removeClass("d-none");
        }
    })
}