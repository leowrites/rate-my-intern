package usecases.sort;
import java.util.*;
import entity.Review;

/** A response model for the sort use case that frames the output data into an object. It holds
 * the newly sorted ArrayList of reviews.
 */
public class SortResponseModel {

    private final ArrayList<Review> sortedOutput;

    public SortResponseModel(ArrayList<Review> sortedOutput){this.sortedOutput = sortedOutput;}

    public ArrayList<Review> getSortedOutput(){return sortedOutput;}
}