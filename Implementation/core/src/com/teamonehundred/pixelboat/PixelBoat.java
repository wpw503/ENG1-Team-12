package com.teamonehundred.pixelboat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PixelBoat extends ApplicationAdapter {
	GameObject obj;
	Texture bg;
	SpriteBatch batch;
	OrthographicCamera camera;

	// ran when the game starts
	@Override
	public void create () {
		obj = new GameObject(200, 200, 50, 100, "object_placeholder.png");
		bg = new Texture("temp_background.png");
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		camera.update();
	}

	// ran every frame
	@Override
	public void render () {
		// user input
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			camera.translate(0, (float)obj.move(5),0);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.S)){
			// boat cannot go back, there is only moving forwards, just like life...
			// in that sense, the game is a very good analogy for the struggles of life
			// constantly having ot move forward, unable to return to what was
			// constantly dodging obstacles, trying to keep moving forwards
			// maybe programming at 3am isn't such a good idea after all...

			//obj.move(-5);
			// but it can for testing
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			obj.turn(5);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			obj.turn(-5);
		}

		// drawing
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(bg, 0, 0);
		obj.getSprite().draw(batch);
		batch.end();
	}

	// ran when the game closes
	@Override
	public void dispose () {
		batch.dispose();
	}
}
