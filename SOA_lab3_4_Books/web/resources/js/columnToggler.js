function fixColumnTogglerLabel() {
    var items = $(".ui-columntoggler .ui-columntoggler-items").children();
    $($(items.get(5)).children().get(1)).text("Cena");
    $($(items.get(6)).children().get(1)).text("Waluta");
    $($(items.get(7)).children().get(1)).text("Ilość stron");
}