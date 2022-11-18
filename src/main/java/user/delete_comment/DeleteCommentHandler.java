package user.delete_comment;

import entity.Comment;

import java.util.ArrayList;

public class DeleteCommentHandler {

    private final String parentType;

    private final String commentId;

    private final ArrayList<Comment> comments;

    public DeleteCommentHandler(String parentType, String commentId, ArrayList<Comment> comments){
        this.parentType = parentType;
        this.commentId = commentId;
        this.comments = comments;
    }

    public ArrayList<Comment> deleteComment(){
        /*
        Takes in a String (commentId) and the Arraylist (comments) that contains the comment
        Deletes the comment from comments
        Return new Arraylist of comments
        */
        int size = this.comments.size();
        for (int i = 0; i < size; i++){
            if (this.commentId.equals(this.comments.get(i).getid())){
                this.comments.remove(i);
                break;
            }
        }
        return this.comments;
    }
}