(function ($) {

    function polymorph() {
        var len2func = [];
        for (var i = 0; i < arguments.length; i++)
            if (typeof(arguments[i]) == "function")
                len2func[arguments[i].length] = arguments[i];
        return function () {
            return len2func[arguments.length].apply(this, arguments);
        }
    }

    $.fn.myMenu = polymorph(
        function () {
            var $menu = this;
            return {
                add: function (item) {
                    var liElement = $("<li>");
                    if ($("#" + item.id).length == 0) {
                        liElement.html(item.name).attr("id", item.id).appendTo($menu);
                    } else {
                        liElement.uniqueId();
                        liElement.html(item.name).appendTo($menu);
                    }
                    if (item.handlers != null) {
                        for (var handler in item.handlers) {
                            liElement.on(handler, item.handlers[handler]);
                        }
                    }

                    return liElement;
                },

                get: function (id) {
                     return $("#" + id);
                },

                remove: function (item) {
                    var $li = $("#" + item.id);
                    $li.remove();

                    return this;
                },

                clear: function () {

                    $($menu).html("");
                },

                replaceAll: function (items) {

                    $($menu).myMenu().clear();

                    for (var i = 0; i < items.length; i++) {
                        $($menu).myMenu().add(items[i]);
                    }
                    return this;
                },

                setProvider: function ($provider) {
                    var $deferred = new $.Deferred();
                    $provider.get($deferred);

                    $deferred.promise().done(function (items) {
                        for (var i = 0; i < items.length; i++) {
                            $($menu).myMenu().add(items[i]);
                        }
                    });
                }

            };
        },

        function (funcName, param) {
            try {
                $(this).myMenu()[funcName](param);
            } catch (err) {
                console.log(err);
                alert("func: \"" + funcName + "\" isn't exist \n" + err);
            }
            return this
        },

        function (items) {
            $(this).myMenu().replaceAll(items);
        }
    );
})(jQuery);
