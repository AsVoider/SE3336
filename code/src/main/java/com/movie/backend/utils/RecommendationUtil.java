package com.movie.backend.utils;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
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
                StringBuilder line = new StringBuilder(userID + " ");
                for (RecommendedItem recommendedItem : recommendedItems){
                    line.append(recommendedItem.getItemID()).append(" ");
                }
                line.deleteCharAt(line.length() - 1);
                line.append("\n");
                writer.write(line.toString());
            }
            log.info("generated result at " + result.getAbsolutePath());
        } catch (Exception e) {
            if (file.exists())
                file.delete();
            throw e;
        } finally {
            if (file.exists())
                file.delete();
        }
    }
}
