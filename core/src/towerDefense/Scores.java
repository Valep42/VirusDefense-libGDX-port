package towerDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import engine.GameComponent;
import engine.gui.SetGameModeAction;

public class Scores extends GameComponent {

	public Scores(TowerDefense game) {
		super(game);

		TextButtonStyle textButtonStyle = this.game.getTextButtonStyle();

		TextButton back = new TextButton("Back", textButtonStyle);
		// back.setColor(Color.BLACK);

		back.addListener(new SetGameModeAction(this.game, TowerDefense.MODE_MENU));
		this.addActor(back);

		String scoreString = "Highscores:\n";

		// reading preferences
		Preferences prefs = Gdx.app.getPreferences("VirusDefense");
		String[] scoresList = prefs.getString("score").split("\n");
		for (int i = 0; i < 9; ++i) {
			if (scoresList.length > i) {
				String[] parts = scoresList[i].split(", ");
				if (parts.length == 2) {
					scoreString += "  " + (i + 1) + ": " + parts[0] + ", " + parts[1] + " Punkte\n";
				}
			}
		}
		int i = 9;
		if (scoresList.length > i) {
			String[] parts = scoresList[i].split(", ");
			scoreString += (i + 1) + ": " + parts[0] + ", " + parts[1] + " Punkte\n";
		}
		Label scores = new Label(scoreString, this.game.getLabelStyle());
		float actualHeight = scores.getHeight() * scores.getText().toString().split("\n").length;

		scores.setPosition((TowerDefense.getWidth() - scores.getWidth()) / 2, (TowerDefense.getHeight() - actualHeight));
		scores.setColor(Color.BLACK);
		this.addActor(scores);

	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
	}

	// @Override
	// public void update(int delta) {
	// super.update(delta);
	// }
}
