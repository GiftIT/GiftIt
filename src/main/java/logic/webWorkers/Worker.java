package logic.webWorkers;

/**
 * Created by vlad on 12/16/15.
 */
public interface Worker {

    /**
     * Uses to check is there any posts left
     * @return availability of posts
     */
    boolean hasPost();


    /**
     * Uses to get next relevant post from group
     * @return next post
     */
    Post getNextPost();

}
