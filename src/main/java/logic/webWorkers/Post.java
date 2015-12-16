package logic.webWorkers;

import logic.Category;
import logic.webWorkers.Person;

/**
 * Abstract class, which represents record about some category
 */
public abstract class Post {

    private Category category;

    public Post(Category category){
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Should return all persons which have liked a post with info about user
     * @return array of person which are interested in post
     */
    public abstract Person[] getPersons();


}
