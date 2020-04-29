function changeCurrency(currencyColId) {
    var changeCurrencyFlag = (PF('selectCurrency').getJQ().find(':checked').val() == 'true');
    var currencyCol = $("[id*=" + currencyColId + "]");
    if (changeCurrencyFlag) {

    } else {
        currencyCol.each(function(){
            var s = $(this);
            if ($(this).text() == 'PLN' && $(this).hasClass('black')) {
                $(this).removeClass('black');
                $(this).addClass('red');
            } else {
                $(this).removeClass('black');
                $(this).addClass('red');
                s.html(s.text() == 'More about us' ? 'Less about us' : 'More about us');
            }
        });
    }
}