package Decorator;

import Resources.Resource;

import java.time.LocalDate;

public abstract class RatingDecorator extends Resource {
    protected Resource decoratedResource;

    public RatingDecorator(Resource decoratedResource, int rating) {
        super(decoratedResource.getTitle(), decoratedResource.getAuthor(), String.valueOf(LocalDate.now()));
        this.decoratedResource = decoratedResource;
    }
}
