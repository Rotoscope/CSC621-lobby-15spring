using UnityEngine;
using System.Collections;

public class SpawnPredator : MonoBehaviour
{
	int currIndex;
	int maxIndex;
	int nextSpawn;
	int spawnOne;
	int spawnTwo;
	int spawnThree;

	Vector3 spawnPointOne;
	Vector3 spawnPointTwo;
	Vector3 spawnPointThree;

	float time = 0;

	bool gameOver = false;

	/*
	 * hintBoard = GameObject.Find ("HintBoard");
		hintBoardScript = hintBoard.GetComponent<HintBoard>();
		Acquire object like this.
	 * */

	void Update()
	{
		if (!gameOver) {
			time += Time.deltaTime;
			//Debug.Log (time);
		}
	}

	public float getRunTime() {
		return time;
	}

	public void stopRunTime() {
		gameOver = true;
	}


	void Start ()
	{
		StartCoroutine("MyEvent");
	}

	void createCreature(int spawnLevel, int creatureId) {
		spawnPointOne.x = 8.7f;
		spawnPointOne.y = 1.4f;
		spawnPointOne.z = 0f;

		spawnPointTwo.x = 8.7f;
		spawnPointTwo.y = -0.45f;
		spawnPointTwo.z = 0f;

		spawnPointThree.x = 8.7f;
		spawnPointThree.y = -1.9f;
		spawnPointThree.z = 0f;

		GameObject spawnedPredator;


		Vector3 currSpawnPoint;
		if (spawnLevel == 1) {
			currSpawnPoint = spawnPointOne;
		} else if (spawnLevel == 2) {
			currSpawnPoint = spawnPointTwo;
		} else {
			currSpawnPoint = spawnPointThree;
		}


		//spawnedPredator = (GameObject) GameObject Instantiate(Resources.Load("FooPrefab"));
		switch (creatureId)
		{
		case 1:
			spawnedPredator = (GameObject) Instantiate(Resources.Load("Prefabs/Carnivore/Lion"), currSpawnPoint,  Quaternion.identity);
			break;
		case 2:
			spawnedPredator = (GameObject) Instantiate(Resources.Load("Prefabs/Carnivore/DwarfSandSnake"), currSpawnPoint,  Quaternion.identity);
			break;
		case 3:
			spawnedPredator = (GameObject) Instantiate(Resources.Load("Prefabs/Carnivore/Leopard"), currSpawnPoint,  Quaternion.identity);
			break;
		case 4:
			spawnedPredator = (GameObject) Instantiate(Resources.Load("Prefabs/Carnivore/AfricanWildDog"), currSpawnPoint,  Quaternion.identity);
			break;
		case 5:
			spawnedPredator = (GameObject) Instantiate(Resources.Load("Prefabs/Carnivore/ServalCat"), currSpawnPoint,  Quaternion.identity);
			break;
		default:
			spawnedPredator = (GameObject) Instantiate(Resources.Load("Prefabs/Carnivore/Lion"), currSpawnPoint,  Quaternion.identity);
			break;
		}
	}

	
	public IEnumerator MyEvent()
	{
		int[,] spawnArray = new int[,] {{2, 0, 1, 0}, 
										{3, 2, 0, 0}, 
										{15, 0, 0, 4},
										{10, 0, 3, 0},
										{30, 5, 0, 0},
										{45, 0, 0, 1}};
		
		//Debug.Log (spawnArray);
		
		currIndex = 0;
		nextSpawn = 0;
		maxIndex = spawnArray.GetLength (0);

		HintBoard hintBoardScript;
		GameObject hintBoard;

		
		hintBoard = GameObject.Find ("HintBoard");
		hintBoardScript = hintBoard.GetComponent<HintBoard>();


		while(currIndex < maxIndex && !gameOver)
		{
			nextSpawn = spawnArray[currIndex,0];
			spawnOne = spawnArray[currIndex,1];
			spawnTwo = spawnArray[currIndex,2];
			spawnThree = spawnArray[currIndex,3];

			//Set hint screen to image

			if (spawnOne != 0) {
				hintBoardScript.setNextPredator(spawnOne);
			} else if (spawnTwo != 0) {
				hintBoardScript.setNextPredator(spawnTwo);
			} else if (spawnThree != 0) {
				hintBoardScript.setNextPredator(spawnThree);
			}

			yield return new WaitForSeconds(nextSpawn); // wait x seconds
			//Debug.Log ("Waited " + nextSpawn + " seconds!");
			if (!gameOver) {
				for (int i = 1; i < 4; i++) {
					if (spawnArray[currIndex,i] != 0) {
						createCreature(i, spawnArray[currIndex,i]);
					}
				}
				currIndex++; //Move onto next predator
			}
		}

		//No more predators, Win game!
	}
}

