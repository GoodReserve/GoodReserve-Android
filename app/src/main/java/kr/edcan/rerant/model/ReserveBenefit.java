package kr.edcan.rerant.model;

/**
 * Created by Junseok on 2016-10-13.
 */
public class ReserveBenefit {
    private String benefitTitle, benefitContent;

    public ReserveBenefit(String benefitTitle, String benefitContent) {
        this.benefitTitle = benefitTitle;
        this.benefitContent = benefitContent;
    }

    public String getBenefitTitle() {
        return benefitTitle;
    }

    public String getBenefitContent() {
        return benefitContent;
    }
}
