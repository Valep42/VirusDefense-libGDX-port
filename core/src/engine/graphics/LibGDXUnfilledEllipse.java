package engine.graphics;

import towerDefense.TowerDefense;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LibGDXUnfilledEllipse extends LibGDXEllipse {

	public LibGDXUnfilledEllipse(float width, float height, Color color) {
		super(width, height, color);
	}

	public LibGDXUnfilledEllipse(float width, float height, float r, float g, float b) {
		super(width, height, r, g, b);
	}

	public LibGDXUnfilledEllipse(float width, float height) {
		super(width, height);
	}

	@Override
	public void draw(float x, float y, float globalScale, SpriteBatch batch) {
		batch.end();
		// this.graphics.draw(new Ellipse(x, y, this.width / 2 * globalScale, this.height / 2 * globalScale), new GradientFill(0, 0,
		// this.color, this.width / 2 * globalScale, this.height / 2 * globalScale, this.color));
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, TowerDefense.getWidth(), TowerDefense.getHeight());
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(this.color.r, this.color.g, this.color.b, 1); // r g b a
		// shapeRenderer.line(x, y, x2, y2);
		shapeRenderer.circle(x, TowerDefense.getHeight() - y, this.width / 2 * globalScale);
		// shapeRenderer.circle(x, y, radius);
		shapeRenderer.end();
		batch.begin();
	}
}
