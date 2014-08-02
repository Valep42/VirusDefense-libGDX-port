package towerDefense;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.GameComponent;
import engine.Level;
import engine.SoundHandler;

public class TowerDefense {

	protected static SoundHandler soundHandler = new SoundHandler();
	private SpriteBatch batch;
	public static final int MODE_MENU = 0;
	public static final int MODE_GAME = 1;
	public static final int MODE_MAPS = 2;
	public static final int MODE_SETTINGS = 3;
	public static final int MODE_SCORES = 4;
	public static boolean FULLSCREEN = false;
	private static int HEIGHT;
	private static int WIDTH;

	private Gameplay gameplay;
	private Menu menu;
	private ChooseLevel maps;
	private Settings settings;
	private GameComponent currentGameComponent;
	private Scores scores;

	private boolean quitGame = false;
	private static boolean applet;

	private int mode;

	public TowerDefense(boolean applet) {
		// super("Virus Defense");
	}

	// @Override
	public void init() {
		this.batch = new SpriteBatch();
		// container.setShowFPS(false);
		long time = System.nanoTime();
		// if (!container.isFullscreen()) {/* "./data/graphics/icons/icon24.png", (this may be necessary for other platforms(mac)) */
		// String[] icons = { "./data/graphics/icons/icon16.png", "./data/graphics/icons/icon32.png" };
		// try {
		// container.setIcons(icons);
		// } catch (SlickException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		this.initSounds();
		TowerDefense.updateDimensions();
		// this.reinitMenu(container);
		// this.reinitChooseLevel(container);
		this.mode = TowerDefense.MODE_MENU;
		this.currentGameComponent = this.menu;
		long passedTime = System.nanoTime() - time;
		// System.out.println(passedTime / 1000000000.0);
		// AppGameContainer gameContainer = (AppGameContainer) container;
		// try {
		// gameContainer.setDisplayMode(TowerDefense.getWidth(), TowerDefense.getHeight(), TowerDefense.isFULLSCREEN());
		this.reinitMenu();
		this.reinitChooseLevel();
		this.reinitComponents();
		TowerDefense.updateDimensions();
		// } catch (SlickException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private void initSounds() {
		TowerDefense.soundHandler.addWav("press");
		TowerDefense.soundHandler.add("place", "place.wav");
		TowerDefense.soundHandler.addWav("bad");
		TowerDefense.soundHandler.addWav("death");
		TowerDefense.soundHandler.addWav("spawn");

		TowerDefense.soundHandler.addWav("explode");
		TowerDefense.soundHandler.addWav("shotT1");
		TowerDefense.soundHandler.addWav("shotT2");
	}

	// @Override
	public void update(int delta) {
		// if (this.quitGame) {
		// container.exit();
		// AL.destroy();
		// }
		if (this.mode == TowerDefense.MODE_GAME) {
			this.currentGameComponent = this.gameplay;
		} else if (this.mode == TowerDefense.MODE_MAPS) {
			this.currentGameComponent = this.maps;

		} else if (this.mode == TowerDefense.MODE_SETTINGS) {
			if (this.currentGameComponent != this.settings) {
				// this.settings.activate(container);
				this.settings = new Settings(this);
			}
			this.currentGameComponent = this.settings;
		} else if (this.mode == TowerDefense.MODE_MENU) {
			if (this.currentGameComponent != this.menu) {
				// this.menu.activate(container);
			}
			this.currentGameComponent = this.menu;
		} else if (this.mode == TowerDefense.MODE_SCORES) {
			if (this.currentGameComponent != this.scores) {
				this.scores = new Scores(this);
			}
			this.currentGameComponent = this.scores;
		}
		// System.out.println(delta);
		this.currentGameComponent.update(delta);

	}

	// @Override
	public void render() {
		this.batch.begin();
		this.currentGameComponent.render(this.batch);
		this.batch.end();

	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void quitGame() {
		this.quitGame = true;
	}

	public static SoundHandler getSoundHandler() {
		return TowerDefense.soundHandler;
	}

	public static int getHeight() {
		return TowerDefense.HEIGHT;
	}

	public static int getWidth() {
		return TowerDefense.WIDTH;
	}

	public static void updateDimensions() {
		// TowerDefense.HEIGHT = container.getHeight();
		// TowerDefense.WIDTH = container.getWidth();
		TowerDefense.HEIGHT = 768;
		TowerDefense.WIDTH = 1024;
	}

	public void setLevel(Level level) {
		this.gameplay.setLevel(level);
	}

	public void initGameplay(Level level) {
		this.gameplay = new Gameplay(this, level);
		// try {
		// this.gameplay.init(container);
		// } catch (SlickException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		this.currentGameComponent = this.gameplay;
	}

	public Gameplay getGameplay() {
		return this.gameplay;
	}

	public void deactivateSettings() {
		this.settings.deactivate();
	}

	public static boolean isFULLSCREEN() {
		return FULLSCREEN;
	}

	public static void setFULLSCREEN(boolean fULLSCREEN) {
		FULLSCREEN = fULLSCREEN;
	}

	public void reinitMenu() {

		this.menu = new Menu(this);
		this.menu.init();
	}

	public void reinitChooseLevel() {

		this.maps = new ChooseLevel(this);

	}

	public void deactivateMenu() {
		// this.menu.deactivate();
	}

	public static void writeSettingsToFile() {
		PrintWriter writer;
		writer = new PrintWriter(Gdx.files.internal("data/files/settings.txt").writer(false, "UTF-8"));
		writer.println(TowerDefense.getWidth());
		writer.println(TowerDefense.getHeight());
		if (TowerDefense.isFULLSCREEN()) {
			writer.println(1);
		} else {
			writer.println(0);
		}
		writer.close();

	}

	public static void writeScoreToFile(String name, int score) {
		// reading Preferences
		Preferences prefs = Gdx.app.getPreferences("VirusDefense");
		String[] savedScores = prefs.getString("score").split("\n");
		String[][] scores = new String[savedScores.length + 1][2];
		for (int i = 0; i < savedScores.length; ++i) {
			String[] parts = savedScores[i].split(", ");
			scores[i][0] = parts[0];

			scores[i][1] = parts[1];

		}
		// adding new score
		scores[savedScores.length][0] = name;
		scores[savedScores.length][1] = new Integer(score).toString();

		// converts the String in the second column to ints and then sorts them by that int value
		Arrays.sort(scores, new Comparator<String[]>() {
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
				final Integer compare1 = Integer.parseInt(entry1[1]);
				final Integer compare2 = Integer.parseInt(entry2[1]);
				return compare1.compareTo(compare2) * -1;
			}
		});

		// saving in preferences
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < scores.length; ++i) {
			builder.append(scores[i][0] + ", " + scores[i][1] + "\n");
		}
		String s = builder.toString();

		prefs.putString("score", s);
		prefs.flush();
	}

	public void resetScores() {
		this.scores = new Scores(this);
	}

	public void reinitComponents() {
		TowerDefense.updateDimensions();
		// this.gameplay = new Gameplay(this);
		this.reinitMenu();
		this.reinitChooseLevel();
		// this.settings = new Settings(this, container);
		this.currentGameComponent = this.menu;
		this.scores = new Scores(this);
	}

	public String getPlayerName() {
		return this.menu.getPlayerName();
	}

	public void setLost(int score, String name) {
		this.menu.setLost(score, name);
	}

	public void setWon(int score, String name) {
		this.menu.setWon(score, name);
	}

	public Menu getMenu() {
		return this.menu;
	}

	public static boolean isApplet() {
		return applet;
	}

}
