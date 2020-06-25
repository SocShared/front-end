$(document).ready(function () {
    try {
        if (ext_url == null) {}
    } catch (e) {
        let location = '' + document.location;
        let id = location.replace(window.location.origin + '/', '');
        if (id === 'lk') {} else
            load('/' + id);
    }
});

function load(url, method) {
    $.ajax({
        url: '/lk',
        method: 'get',
        dataType: "html",
        success: function (data_ext) {
            $("html").html(data_ext);
            $.ajax({
                url: url,
                method: method,
                dataType: "html",
                beforeSend: function () {
                    $("#sh-content").addClass("d-none");
                    $("#container-spinner").removeClass("d-none");
                },
                success: function (data_int) {
                    if (data_int.redirect) {
                        window.location.href = data_int.redirect;
                    } else {
                        $("#container-spinner").addClass("d-none");
                        $("#sh-content").html(data_int);
                        $("#sh-content").removeClass("d-none");
                    }
                },
                error: function (data) {
                    $("#container-spinner").addClass("d-none");
                    $("#sh-content").html(data.responseText);
                    $("#sh-content").removeClass("d-none");
                }
            })
        },
        error: function (data) {
            $('body').html($(data).find('body'));
        }
    });
}