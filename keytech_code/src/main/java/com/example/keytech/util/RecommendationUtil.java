package com.example.keytech.util;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RecommendationUtil {

    private static final Logger log = LoggerFactory.getLogger(RecommendationUtil.class);

    public static void getItemBasedRecommendation(String filename, int numRecommendations)
            throws IOException, TasteException {
        File file = new File(filename);
        DataModel dataModel = new FileDataModel(file);

        File result = new File(System.getProperty("java.io.tmpdir"), "recommendation.out");

        ItemSimilarity itemSimilarity = new UncenteredCosineSimilarity(dataModel);
//        ItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
        Recommender cachingRecommender = new CachingRecommender(
                new GenericItemBasedRecommender(dataModel, itemSimilarity)
        );

        try (PrintWriter writer = new PrintWriter(result)){
            LongPrimitiveIterator iterator = dataModel.getUserIDs();
            while (iterator.hasNext()) {
                long userID = iterator.nextLong();
                List<RecommendedItem> recommendedItems = cachingRecommender.recommend(userID, numRecommendations);
                StringBuilder line = new StringBuilder(userID + ":\n");
                for (RecommendedItem recommendedItem : recommendedItems){
                    line.append(recommendedItem.getItemID()).append(": ").append(recommendedItem.getValue()).append("\n");
                }
                writer.write(line.toString());
            }
            log.info("generated result at " + result.getAbsolutePath());
        } catch (IOException e) {
            result.delete();
            throw e;
        }
    }
    public static void getUserBasedRecommendation(String filename) throws IOException, TasteException {
        File file = new File(filename);
        DataModel dataModel = new FileDataModel(file);

        File result = new File(System.getProperty("java.io.tmpdir"), "userRecommendation.csv");
        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
        Recommender cachingRecommender = new CachingRecommender(
                new GenericUserBasedRecommender(dataModel, neighborhood, similarity)
        );

//        RMSRecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
//        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
//            @Override
//            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
//                UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
//                UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
//                return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
//            }
//        };
//        double score = evaluator.evaluate(recommenderBuilder, null, dataModel, 0.7, 1.0);
//        System.out.println("RMSE score: " + score);

        try(PrintWriter writer = new PrintWriter(result)){
            for (int userID = 1; userID <= dataModel.getNumUsers(); userID++){
                List<RecommendedItem> recommendedItems = cachingRecommender.recommend(userID, 2);
                StringBuilder line = new StringBuilder(userID + " : ");
                int size = recommendedItems.size();
                for (int i = 0; i < size - 1; i++) {
                    RecommendedItem recommendedItem = recommendedItems.get(i);
                    line.append(recommendedItem.getItemID()).append(":").append(recommendedItem.getValue()).append(",");
                }
                RecommendedItem recommendedItem = recommendedItems.get(size - 1);
                line.append(recommendedItem.getItemID()).append(":").append(recommendedItem.getValue());
                writer.write(line.toString());
                writer.write('\n');
            }
        } catch (IOException e){
            result.delete();
            throw e;
        }
    }
}
