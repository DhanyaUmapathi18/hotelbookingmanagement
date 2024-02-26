package com.data;
/**
 * Card Class for card details 
 * @author Dhanya Umapathi (Expleo)
 * @since 20 FEB 2024
 */
public class Card extends Payment {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private int cvv;

    public Card(){
        ;
    }

    public Card(Payment root){
        super(root.getId(), root.getType(), root.getAmount(), root.getBookingId(), root.getUserId(), root.getStatus());
    }

    public Card(String id, String type, double amount, String bookingId, String userId, String status,
            String cardNumber, String cardHolderName, String expirationDate, int cvv) {
        super(id, type, amount, bookingId, userId, status);
         this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public Card(Payment root,
            String cardNumber, String cardHolderName, String expirationDate, int cvv) {
        super(root.getId(), root.getType(), root.getAmount(), root.getBookingId(), root.getUserId(), root.getStatus());
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    
}