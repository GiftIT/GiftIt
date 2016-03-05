package logic.textClassification;

import logic.Category;
import logic.CategoryContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by vladislav on 3/5/16.
 */
public class TextClassifier implements Classifier {

    private Set<Category> categories;

    public TextClassifier(){
        categories = CategoryContainer.getInstance().getCategories();
    }

    @Override
    public Category classify(Object object, int groupId) {
        String text = (String)object;

        Map<Category, Integer> matches = new HashMap<>();

        for(Category c : categories){
            if(c.getAllowedSite().contains(groupId)) {
                ArrayList<String> keys = c.getKeys();

                for (String key : keys) {
                    if (text.contains(key)){
                        if(matches.containsKey(c)){
                            matches.put(c, matches.get(c)+1);
                        }
                        else {
                            matches.put(c, 1);
                        }
                    }

                }
            }
        }
        if(matches.isEmpty())
            return null;

        int max = 0;
        Category maxC = null;

        Set<Category> keys = matches.keySet();
        for(Category category : keys){
            if(matches.get(category) > max){
                max = matches.get(category);
                maxC = category;
            }
        }

        return maxC;
    }

//    public Category classify(Object object, int groupId) {
//        String text = (String) object;
//
//
//        for (Category c : categories) {
//            if (c.getAllowedSite().contains(groupId)) {
//                ArrayList<String> keys = c.getKeys();
//
//                for (String key : keys) {
//                    if (text.contains(key)) {
//                        return c;
//                    }
//                }
//            }
//        }
//        return null;
//    }
}
