//author: Lobby Team

using UnityEngine;
using System.Collections;

public class Season : MonoBehaviour {
	

	private GameObject mainObject;
	
	public int season_code { get; set; }
	
	void Awake() {
		mainObject = GameObject.Find("MainObject");
		mainObject.GetComponent<MessageQueue>().AddCallback(Constants.SMSG_SEASON_CHANGE, ResponseSeasonChange);
	}

	void Start () {

	}
	
	// Update is called once per frame
	void Update () {
		
	}
	
	void OnGUI() {

	}
	
	public void SetSeason(int season_id) {
		this.season_code = season_code;
	}
	
	public void ResponseSeasonChange(ExtendedEventArgs eventArgs) {
		ResponseSeasonChangeEventArgs args = eventArgs as ResponseSeasonChangeEventArgs;
		
		SetSeason(args.event_id);
	}
}
