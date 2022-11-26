package user.comment;

import entity.Comment;
import entity.Review;
import user.comment.exceptions.ReviewNotFoundException;

import java.util.ArrayList;
import java.util.Date;

public class CommentInteractor extends CommentObservable implements ICommentInputBoundary {
    private final ICommentDataAccess dataAccess;

    public CommentInteractor(ICommentDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public CommentResponseModel create(CommentRequestModel requestModel) {
        String id = requestModel.getid();
        String userID = requestModel.getUserID();
        String reviewID = requestModel.getReviewID();
        String content = requestModel.getContent();
        Date datePosted = new Date();
        Review review;
        Comment comment = new Comment(id, userID, content, datePosted);

        try {
            review = dataAccess.getReview(reviewID);
            if (review == null) {
                throw new ReviewNotFoundException("Review does not exist.");
            }
        }
        catch (ReviewNotFoundException e) {
            return new CommentResponseModel("Failure");
        }

        ArrayList<Comment> reviewComments = review.getComments();
        reviewComments.add(comment);
        review.setComments(reviewComments);

        return new CommentResponseModel("Success");
    }
}
