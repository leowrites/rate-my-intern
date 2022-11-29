package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TimeZone;

public class ReviewDAO implements IReviewDAO{

    @Autowired
    JdbcTemplate jdbcTemplate;

    final String INSERT_QUERY = "INSERT INTO reviews (data) values (?)";
    final String DATA_QUERY = "select data from reviews where id = ? ";
    final String QUERY_ALL = "select * from reviews";

    /**
     * Gets the review given the review id
     * @param reviewId the id of the review
     * @return a review object
     */
    @Override
    public Review getReview(String reviewId){
        return jdbcTemplate.queryForObject(DATA_QUERY, new ReviewDaoMapper(), Integer.parseInt(reviewId));
    }

    /**
     * @return all reviews
     */
    @Override
    public ArrayList<Review> getAllReviews() {
        return (ArrayList<Review>) jdbcTemplate.query(QUERY_ALL, new ReviewDaoMapper());
    }

    /**
     * Creates a new review in the db given a review object
     * @param review the review to be saved
     * @return the id of the created review
     */
    @Override
    public String saveReview(Review review) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try{
            // serialize the date to ISO-8601

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
            mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
            String reviewString = mapper.writeValueAsString(review);
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, reviewString);
                return statement;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKeys()).get("id").toString();
        } catch(JsonProcessingException e){
            System.out.println("Json process error!");
        }
        return null;
    }
}