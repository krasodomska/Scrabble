package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.system.*;

import java.util.LinkedList;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Player playerI = new Player();
	Player playerII = new Player();
	Direction down= Direction.DOWN;
	Direction across= Direction.ACROSS;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		try {
            Dictionary.createDictionary();
			LetterBag.createBag();
			Board.crateBoards();
			int[] position = {0,0};
			playerI.addWord("doggos", position, across);
			playerI.addWord("doggy", position, across);

			position[1] = 2;
			playerII.addWord("lass", position, down);

			position[0] = 3;
			playerI.addWord("treet",position, across);

			position[1] = 7;
			playerII.addWord("ee", position, down);

			position[0] = 6;
            System.out.println(" ");

            playerII.addWord("sun", position, across);
            Board.showBoard(Board.letterBoard);
			System.out.println("Player I score:" + playerI.score);
			playerI.showMyWord();

			System.out.println("Player II score:"+ playerII.score);
			playerII.showMyWord();




//			playerI.getWordScore("AGA");
//            LetterBag.letterExchange(forTesting);

            //playerI.getScore();
			//playerI.startWithRandomLetter();
			//LetterBag.takeRandomLetterFromBag(5);



		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
	}
}
