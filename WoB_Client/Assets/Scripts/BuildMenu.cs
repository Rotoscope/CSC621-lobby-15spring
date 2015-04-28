using UnityEngine;
using System.Collections;

public class BuildMenu : MonoBehaviour {

	// Currently building...
	public static BuildInfo currentlyBuilding;

	// Currently about to delete?
	public static bool currentlyDeleting = false;

	// Player's current resource amount
	public static int currentResources = 100;

	//Player's current score amount
	public static int score = 0;

	// Plant prefabs
	public BuildInfo[] plants;

	// Prey prefabs
	public BuildInfo[] prey;

	void OnGUI() {

		// draw resource menu
		GUILayout.BeginArea (new Rect (150, 90, 155, 200));
		GUILayout.BeginHorizontal ("box");

		// draw resource counter
		GUILayout.Button (new GUIContent ("Resources: " + currentResources.ToString ()), GUILayout.Height(70));

		// end GUI for resource menu
		GUILayout.EndHorizontal ();
		GUILayout.EndArea ();

		// draw score menu
		GUILayout.BeginArea (new Rect (800, 90, 155, 200));
		GUILayout.BeginHorizontal ("box");
		
		// draw score counter
		GUILayout.Button (new GUIContent ("Score: " + score.ToString ()), GUILayout.Height(70));
		
		// end GUI for score menu
		GUILayout.EndHorizontal ();
		GUILayout.EndArea ();

		// draw the plant menu
		GUILayout.BeginArea (new Rect (40, 165, 100, 400));
		GUILayout.BeginVertical ("box");

		// Draw each plant's build info
		foreach (BuildInfo info in plants) {
			GUI.enabled = currentResources >= info.price;
			// if button is clicked, then set currentlyBuilding to the info of the button you clicked
			if (GUILayout.Button(new GUIContent(info.price.ToString(), info.previewImage))) currentlyBuilding = info;
		}

		// End GUI for plant menu
		GUILayout.EndVertical();
		GUILayout.EndArea ();

		// Now, draw prey menu
		GUILayout.BeginArea (new Rect (300, 90, 500, 220));
		GUILayout.BeginHorizontal ("box");

		// draw each prey's build info
		foreach (BuildInfo info in prey) {
			GUI.enabled = currentResources >= info.price;
			if (GUILayout.Button(new GUIContent(info.price.ToString(), info.previewImage))) currentlyBuilding = info;
		}

		// End GUI for prey menu
		GUILayout.EndVertical();
		GUILayout.EndArea();
	}

	// this method increases score every 2s
	void increaseResources() {
		currentResources += 50;
	}

	// Use this for initialization
	void Start () {
		// set resources to grow over time
		Game.StartEnterTransition ();
		InvokeRepeating ("increaseResources", 2, 3.0F);

	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
