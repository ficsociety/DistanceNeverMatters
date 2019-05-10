using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Vuforia;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

public class Synchronizer : MonoBehaviour {

    [SerializeField] private List<GameObject> trackedTargets = new List<GameObject>();
    private AndroidJavaObject fragment;
    [SerializeField] private Dictionary<string, GameObject> externalModels = new Dictionary<string, GameObject>(); // GameObjects whose location is sent to us by another object
    public float transmissionInterval = 0.5f; // En segundos
    
    private TrackableBehaviour trackableBehaviour;
    private float timeSinceLastTransmission = 0;
    private GameObject map;

	// Use this for initialization
	void Start () {
        trackableBehaviour = GetComponent<TrackableBehaviour>();
        map = transform.GetChild(0).gameObject;

        // Tracked targets
        trackedTargets = new List<GameObject>(GameObject.FindGameObjectsWithTag("target"));

        // External models
        List<GameObject> externalList = new List<GameObject>(GameObject.FindGameObjectsWithTag("external"));
        
        // Desactivar modelos externos y añadir a mapa
        foreach (GameObject external in externalList)
        {
            external.SetActive(false);
            externalModels.Add(external.name, external);
        }

        // Referencia al fragmento
        AndroidJavaClass fragmentClass = new AndroidJavaClass("apm.muei.distancenevermatters.fragments.UnityFragment");
        fragment = fragmentClass.GetStatic<AndroidJavaObject>("instance");
    }
	
	// Update is called once per frame
	void Update () {
        // Update counter
        timeSinceLastTransmission += Time.deltaTime;

        // Check if it's already time to transmit position information
        if (timeSinceLastTransmission > transmissionInterval)
        {
            timeSinceLastTransmission = 0;

            // Time to send the information!
            Debug.Log("Transmission time!");
            JObject updateInfo = new JObject();

            // Check if map is being tracked. We cannot send position information if we can't track the map
            if (trackableBehaviour.CurrentStatus == TrackableBehaviour.Status.TRACKED)
            {
                Debug.Log("Map tracked");

                // Process tracked targets
                foreach (GameObject trackedObject in trackedTargets)
                {
                    GameObject model = trackedObject.transform.GetChild(0).gameObject;

                    // Check if target is being tracked. We cannot send position information if we can't track the target
                    TrackableBehaviour targetBehaviour = trackedObject.GetComponent<TrackableBehaviour>();
                    if (targetBehaviour.CurrentStatus == TrackableBehaviour.Status.TRACKED)
                    {
                        Debug.Log("Model tracked");
                        Vector3 distance = model.transform.position - map.transform.position;
                        Debug.Log(distance);

                        // Send distance and rotation to fragment
                        updateInfo.Add()
                        // synchronizer.SetDistance(model.name, distance, model.transform.rotation);
                    }
                    else
                    {
                        // Tell fragment to deactivate this model
                        //synchronizer.Deactivate(model.name);
                    }
                }
            }
            else
            {
                // Deactivate all models associated to this map
                foreach (GameObject target in trackedTargets)
                {
                    // receiver.GetComponent<Synchronizer>().Deactivate(target.transform.GetChild(0).name);
                }
            }
        }
    }

    //public void SetDistance(string gameObjectName, Vector3 distance, Quaternion rotation)
    //{
    //    foreach (GameObject externalTarget in external)
    //    {
    //        if (externalTarget.name == gameObjectName)
    //        {
    //            Debug.Log("Setting " + gameObjectName);
    //            externalTarget.SetActive(true);
    //            externalTarget.transform.position = map.transform.position + distance;
    //            externalTarget.transform.rotation = rotation;
    //        }
    //    }
    //}

    //public void Deactivate(string gameObjectName)
    //{
    //    foreach (GameObject externalTarget in external)
    //    {
    //        if (externalTarget.name == gameObjectName)
    //        {
    //            externalTarget.SetActive(false);
    //        }
    //    }
    //}
}
