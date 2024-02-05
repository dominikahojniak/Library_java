package Decorator;

import Resources.Resource;

public class RatedResource extends RatingDecorator {
    private int rating;
    public RatedResource(Resource decoratedResource, int rating) {
        super(decoratedResource,rating);
        this.rating = rating;
    }
    public Resource getDecoratedResource() {
        return decoratedResource;
    }
    public int getRating() {
        return rating;
    }
}
