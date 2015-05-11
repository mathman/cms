function Refresh ()
{
    $.getJSON('/status',
    function(data) {
        var status = document.getElementsByClassName("realm_online");
        for (i = 0; i < status.length; i++) {
            status[i].innerHTML = data[i*4];
        }
        var bar = document.getElementsByClassName("realm_bar");
        for (i = 0; i < bar.length; i++) {
            bar[i].innerHTML = "<div class='realm_bar_fill_a' style='width:" + data[i*4 + 2] + "%'></div><div class='realm_bar_fill_h' style='width:" + data[i*4 + 3] + "%'></div>";
        }
    });
}

$(function()
{
	Refresh();
	setInterval("Refresh()", 10000);

    $.getJSON('/realms',
        function(data) {
            var realms = document.getElementsByClassName("realms");
            for (i = 0; i < data.length; i++)
            {
                realms[0].innerHTML = realms[0].innerHTML + "<div class='realm'><div class='realm_online'></div>" + data[i].name + "<div class='realm_bar'></div><div class='side_divider'></div></div>";
            }
            var realmlist = document.getElementsByClassName("realmlist");
            realmlist[0].innerHTML = "set realmlist " + data[0].address;
        });
});