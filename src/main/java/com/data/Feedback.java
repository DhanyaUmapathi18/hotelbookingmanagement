package com.data;
/**
 * FeedBack Class for collecting feedback and it's details.
 * @author Dhanya Umapathi (Expleo)
 * @since 20 FEB 2024
 */
public class Feedback {
    private String feedback_Id;
	private String submittedBy;
    private String post;
    private int rate;

	public Feedback(String feedback_Id, String submittedBy, String post, int rate) {
		super();
		this.feedback_Id = feedback_Id;
		this.submittedBy = submittedBy;
		this.post = post;
		this.rate = rate;
	}
	public Feedback() {
		
	}
	public String getFeedback_Id() {
		return feedback_Id;
	}
	public void setFeedback_Id(String feedback_Id) {
		this.feedback_Id = feedback_Id;
	}
    public String getSubmittedBy() {
        return submittedBy;
    }
    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }
    public String getPost() {
        return post;
    }
    public void setPost(String post) {
        this.post = post;
    }
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
    

}
