package nth.sprite;

/**
 * A {@link Scene} is part of a {@link Story}
 * @author nilsth
 *
 */

public class Scene extends SpriteCollection {

	private Story story;

	public Scene(Canvas canvas) {
		super(canvas);
	}

	
	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

}
