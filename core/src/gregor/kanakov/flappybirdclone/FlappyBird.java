package gregor.kanakov.flappybirdclone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import jdk.internal.net.http.common.Log;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;

	Texture[] bird;
	Texture topTube;
	Texture bottomTube;

	int birdLocation = 2;
	int birdStateFlag = 0;
	float flyHeight;
	float fallingSpeed = 0;
	int gameStateFlag = 0;
	int spaceBetweenTubes = 870; // растояние между трубами

	Random random;
	int tubeSpeed = 5;

	int tubesNumber = 5;
	float tubeX [];
	float tubeShift[];
	float distanceBetweenTubes;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture[2];
		bird[0] = new Texture("bird_wings_up.png");
		bird[1] = new Texture("bird_wings_down.png");
		flyHeight = Gdx.graphics.getHeight()/2 -
				bird[0].getHeight() /2;  //начальная высота
		topTube = new Texture("top_tube.png");
		bottomTube = new Texture("bottom_tube.png");
		random = new Random();
		tubeX = new float[tubesNumber];
		tubeShift = new float[tubesNumber];

		distanceBetweenTubes = Gdx.graphics.getWidth() / 2; // растояние между трубами
		for (int i = 0; i < tubesNumber; i++){

			Gdx.app.log("MyLog", "1");
			tubeX[i] = Gdx.graphics.getWidth() /2 -
					topTube.getWidth() /2 + i * distanceBetweenTubes;
			tubeShift[i] = (random.nextFloat() - 0.5f) *
					(Gdx.graphics.getHeight() - spaceBetweenTubes - 200); //рагдомное число от 0.2 до 0.5
			Gdx.app.log("MyLog", "2");
		}
	}

	@Override
	public void render () { // метод рендер отображает картинки на экране(работает как бесконечный цикл)
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());// установка background
		if(Gdx.input.justTouched()){ // метод нажатия по экрану
			Gdx.app.log("MyLog", "Oops");
			gameStateFlag = 1;
		}

		if(gameStateFlag == 1) { // игра начинается (пользователь коснулся экрана)


			if(Gdx.input.justTouched()){
				fallingSpeed = -30;

			}if(flyHeight > 0 || fallingSpeed < 0) {
				fallingSpeed++;
				flyHeight -= fallingSpeed;
			}

		}else{
			if(Gdx.input.justTouched()){ // метод нажатия по экрану
				Gdx.app.log("MyLog", "Oops");
				gameStateFlag = 1;
			}
		}


		for (int i = 0; i < tubesNumber; i++) {

			if(tubeX[i] < -topTube.getWidth()){
				tubeX[i] = tubesNumber * distanceBetweenTubes;
			}else {
				tubeX[i] -= tubeSpeed;
			}

			batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + spaceBetweenTubes / 2 + tubeShift[i]);

			batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - spaceBetweenTubes / 2
					- bottomTube.getHeight() + tubeShift[i]);
		}

		if(birdStateFlag == 0){
			birdStateFlag = 1;
			birdLocation = 2;
		}else {
			birdStateFlag = 0;
			birdLocation = 3;
		}


		batch.draw(bird[birdStateFlag], Gdx.graphics.getWidth() /2 -
						bird[birdStateFlag].getWidth() /2,
				flyHeight); //отцентровка изображения птицы
		batch.end();
	}
	
	@Override
	public void dispose () {

	}
}
