function changeCurrency(currencyColId, valueColId) {
    var changeCurrencyFlag = (PF('selectCurrency').getJQ().find(':checked').val() == 'true');
    var currencyCol = $("[id*=" + currencyColId + "]");
    var valueCol = $("[id*=" + valueColId + "]");
    if (changeCurrencyFlag) {
        var i = 0;
        currencyCol.each(function() {
            var s = $(this);
            if ($(this).text() != 'PLN') {
                $(this).removeClass('black');
                $(this).addClass('red');
                var currency = s.html();
                var currency2 = s.html('PLN');
                if(currency == 'EUR') {
                    $(valueCol.get(i)).html((4.5 * $(valueCol.get(i)).html()).toFixed(1));
                } else if(currency == 'USD'){
                    $(valueCol.get(i)).html((4.0 * $(valueCol.get(i)).html()).toFixed(1));
                }
            }
            i++;
        });
    } else {
        restoreCurrency()
    }
}