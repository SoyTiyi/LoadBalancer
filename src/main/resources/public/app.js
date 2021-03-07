var app = (function() {
    const messages = (error, data) => {
        if (error != null) {
            console.log(`Error: ${error}`);
            alert("Error al enviar el mensaje");
            return;
        }
        /* $("#avg").text(data.media); */
        $("#response").text(data.message);
    }

    const send = () => {
        var message = $("#message").val();
        postMethod(message, messages);
    }

    const postMethod = (message,messages) => {
        var promise = $.post({
            url: "/load",
            data: JSON.stringify(message),
            contentType: "application/json"
        });
        promise.then((data) => {
            console.log(data);
            messages(null, JSON.parse(data));
        },(error) => {
            messages(error, null);
        });
    }

    return {
        send: function() {
            send();
        }
    }
})();