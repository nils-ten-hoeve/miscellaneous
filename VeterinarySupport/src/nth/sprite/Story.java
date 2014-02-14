package nth.sprite;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Story} is basically a sequence of {@link Story}'s. A user can go to the next scene or the previous scene with page up and page down buttons.
 * 
 * @author nilsth
 * 
 */
public class Story extends SpriteCollection<Scene> {

	private final ArrayList<Scene> scenes;

	public Story(Canvas canvas, ArrayList<Scene> scenes) {
		super(canvas);
		this.scenes = scenes;
		if (scenes.size() < 1) {
			throw new IllegalArgumentException("No scenes");
		}
		// add story to scenes
		for (Scene scene : scenes) {
			scene.setStory(this);
		}

		Scene firstScene = scenes.get(0);
		getSprites().add(firstScene);
	}

	public void goToNextScene() {
		Scene currentScene = (Scene) getSprites().get(0);
		int nextSceneNr = scenes.indexOf(currentScene) + 1;
		if (nextSceneNr >= scenes.size()) {
			nextSceneNr = 0;
		}
		Scene nextScene = scenes.get(nextSceneNr);
		List<Scene> sprites = getSprites();
		sprites.clear();
		sprites.add(nextScene);
	}

	public void goToPreviousScene() {
		Scene currentScene = (Scene) getSprites().get(0);
		int previousSceneNr = scenes.indexOf(currentScene) - 1;
		if (previousSceneNr < 0) {
			previousSceneNr = scenes.size()-1;
		}
		Scene previousScene = scenes.get(previousSceneNr);
		List<Scene> sprites = getSprites();
		sprites.clear();
		sprites.add(previousScene);
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		super.keyPressed(keyEvent);
		switch (keyEvent.getKeyCode()) {
		case KeyEvent.VK_PAGE_DOWN:
			keyEvent.setKeyCode(0);//discontinue, key is been handled
			goToNextScene();
			break;
		case KeyEvent.VK_PAGE_UP:
			keyEvent.setKeyCode(0);//discontinue, key is been handled
			goToPreviousScene();
			break;
		default:
			break;
		}
	}

}
