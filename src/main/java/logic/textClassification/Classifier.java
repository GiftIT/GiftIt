package logic.textClassification;

import logic.Category;
import logic.webWorkers.Post;

/**
 * Created by vladislav on 3/5/16.
 */
public interface Classifier {

    Category classify(Object object, int groupId);

}
