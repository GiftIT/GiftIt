package logic.webWorkers.facebook;

import logic.Category;
import logic.webWorkers.Person;
import logic.webWorkers.Post;

/**
 * Created by vladislav on 3/5/16.
 */
public class FBPost extends Post {

    public FBPost(Category category) {
        super(category);
    }

    @Override
    public Person[] getPersons() {
        return new Person[0];
    }
}
