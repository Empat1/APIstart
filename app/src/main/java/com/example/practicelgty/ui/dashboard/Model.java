package com.example.practicelgty.ui.dashboard;

public class Model {
    String baseCurrency;
    String currency;
    double saleRateNB;
    double purchaseRateNB;
    double saleRate;
    double purchaseRate;

    public double getPurchaseRate() {
        return purchaseRate;
    }

    @Override
    public String toString() {
        return "Model{" +
                "baseCurrency='" + baseCurrency + '\'' +
                ", currency='" + currency + '\'' +
                ", saleRateNB=" + saleRateNB +
                ", purchaseRateNB=" + purchaseRateNB +
                ", saleRate=" + saleRate +
                ", purchaseRate=" + purchaseRate +
                '}';
    }
}
