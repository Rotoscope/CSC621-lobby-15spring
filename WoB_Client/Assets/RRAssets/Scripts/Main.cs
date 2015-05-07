using UnityEngine;

using System.Collections;
using System.Collections.Generic;

namespace RR {
	public class Main : MonoBehaviour {

		private GameObject mainObject;

		void Awake() {
			DontDestroyOnLoad(gameObject);
			
			NetworkRequestTable.init();
			NetworkResponseTable.init();
		}
		
		// Use this for initialization
		void Start () {
			Application.LoadLevel("RRReadyScene");	

			ConnectionManager cManager = gameObject.GetComponent<ConnectionManager>();
			if (cManager) {
				StartCoroutine(RequestHeartbeat(1f));
			}
		}
		
		// Update is called once per frame
		void Update () {
			
		}

		public IEnumerator RequestHeartbeat(float time) {
			yield return new WaitForSeconds(time);
			
			ConnectionManager cManager = gameObject.GetComponent<ConnectionManager>();
			if (cManager) {
				RequestHeartbeat request = new RequestHeartbeat();
				request.Send();
				cManager.Send(request);
			}
			
	        //originially 1f changed to .1f
			StartCoroutine(RequestHeartbeat(1f));
		}
	}
}
