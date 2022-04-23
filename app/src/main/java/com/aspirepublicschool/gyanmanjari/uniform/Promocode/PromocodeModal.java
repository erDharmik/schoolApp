package com.aspirepublicschool.gyanmanjari.uniform.Promocode;

public class PromocodeModal {
   /* {
        "promocode": "Textbook50",
            "heading": "50 Rs cashback",
            "priceper": "1",
            "pricerup": "100",
            "termandcond": "erstdfyguhij"
    },*/
    String promocode,heading,priceper,priceup,termandcond;

    public PromocodeModal(String promocode, String heading, String priceper, String priceup, String termandcond) {
        this.promocode = promocode;
        this.heading = heading;
        this.priceper = priceper;
        this.priceup = priceup;
        this.termandcond = termandcond;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getPriceper() {
        return priceper;
    }

    public void setPriceper(String priceper) {
        this.priceper = priceper;
    }

    public String getPriceup() {
        return priceup;
    }

    public void setPriceup(String priceup) {
        this.priceup = priceup;
    }

    public String getTermandcond() {
        return termandcond;
    }

    public void setTermandcond(String termandcond) {
        this.termandcond = termandcond;
    }

}
