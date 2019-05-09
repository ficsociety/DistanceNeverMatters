using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Vuforia;

public class Synchronizer : MonoBehaviour {

    // TODO en ejecución normal habrá que inicializarlo por código
    // TODO por ahora es de GameObjects pero en ejecución normal puede ser de ImageTargets directamente
    public List<GameObject> tracked = new List<GameObject>();
    public List<GameObject> receivers = new List<GameObject>(); // GameObjects to send information to
    // TODO para esto probablemente es mejor usar un mapa
    public List<GameObject> external = new List<GameObject>(); // GameObjects whose location is sent to us by another object
    public float transmissionInterval = 0.5f; // En segundos
    
    private TrackableBehaviour trackableBehaviour;
    private float timeSinceLastTransmission = 0;
    private GameObject map;

	// Use this for initialization
	void Start () {
        trackableBehaviour = GetComponent<TrackableBehaviour>();
        map = transform.GetChild(0).gameObject;

        // Deactivate external models until first information is received
        foreach (GameObject externalModel in external)
        {
            externalModel.SetActive(false);
        }
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

            // Check if map is being tracked. We cannot send position information if we can't track the map
            if (trackableBehaviour.CurrentStatus == TrackableBehaviour.Status.TRACKED)
            {
                Debug.Log("Map tracked");
                // Send info to each receiver
                foreach (GameObject receiver in receivers)
                {
                    Synchronizer synchronizer = receiver.GetComponent<Synchronizer>();

                    // Process tracked targets
                    foreach (GameObject trackedObject in tracked)
                    {
                        GameObject model = trackedObject.transform.GetChild(0).gameObject;

                        // Check if target is being tracked. We cannot send position information if we can't track the target
                        TrackableBehaviour targetBehaviour = trackedObject.GetComponent<TrackableBehaviour>();
                        if (targetBehaviour.CurrentStatus == TrackableBehaviour.Status.TRACKED)
                        {
                            Debug.Log("Model tracked");
                            Vector3 distance = model.transform.position - map.transform.position;
                            Debug.Log(distance);
                            
                            synchronizer.SetDistance(model.name, distance, model.transform.rotation);
                        }
                        else
                        {
                            // Deactivate its model on the other side
                            synchronizer.Deactivate(model.name);
                        }
                    }
                }
            } else
            {
                foreach (GameObject receiver in receivers)
                {
                    // Deactivate all models associated to this map
                    foreach (GameObject target in tracked)
                    {
                        receiver.GetComponent<Synchronizer>().Deactivate(target.transform.GetChild(0).name);
                    }
                }
            }
        }
	}

    public void SetDistance(string gameObjectName, Vector3 distance, Quaternion rotation)
    {
        foreach (GameObject externalTarget in external)
        {
            if (externalTarget.name == gameObjectName)
            {
                Debug.Log("Setting " + gameObjectName);
                externalTarget.SetActive(true);
                externalTarget.transform.position = map.transform.position + distance;
                externalTarget.transform.rotation = rotation;
            }
        }
    }

    public void Deactivate(string gameObjectName)
    {
        foreach (GameObject externalTarget in external)
        {
            if (externalTarget.name == gameObjectName)
            {
                externalTarget.SetActive(false);
            }
        }
    }
}
