using UnityEngine;
using System.Collections;

namespace RR {
public class CountdownManager : MonoBehaviour {

    private void endScene()
    {
        Application.LoadLevel("RRRunnerScene");
    }
}
}