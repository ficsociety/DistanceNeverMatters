using System.Collections.Generic;
using UnityEngine;
using Vuforia;
using Newtonsoft.Json.Linq;

public class Synchronizer : MonoBehaviour {

    [SerializeField] private List<GameObject> trackedTargets = new List<GameObject>();
    private AndroidJavaObject fragment;
    private Dictionary<string, GameObject> externalModels = new Dictionary<string, GameObject>(); // GameObjects whose location is sent to us by another object
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
            JArray updateInfo = new JArray();

            // Check if map is being tracked. We cannot send position information if we can't track the map
            if (trackableBehaviour.CurrentStatus == TrackableBehaviour.Status.TRACKED)
            {
                Debug.Log("Map tracked");

                // Process tracked targets
                foreach (GameObject trackedObject in trackedTargets)
                {
                    GameObject model = trackedObject.transform.GetChild(0).gameObject;

                    JObject modelInfo = new JObject();
                    modelInfo.Add("target", trackedObject.name);

                    // Check if target is being tracked. We cannot send position information if we can't track the target
                    TrackableBehaviour targetBehaviour = trackedObject.GetComponent<TrackableBehaviour>();
                    if (targetBehaviour.CurrentStatus == TrackableBehaviour.Status.TRACKED)
                    {
                        Debug.Log("Model tracked");
                        // Send distance and rotation to fragment
                        // Transform model's world position to local position relative to map
                        Vector3 distance = map.transform.InverseTransformPoint(model.transform.position);
                        JObject distanceInfo = new JObject();
                        distanceInfo.Add("x", distance.x);
                        distanceInfo.Add("y", distance.y);
                        distanceInfo.Add("z", distance.z);

                        JObject rotationInfo = new JObject();
                        // Get rotation relative to map
                        Quaternion rotation = Quaternion.Inverse(map.transform.rotation) * model.transform.rotation;
                        rotationInfo.Add("x", rotation.x);
                        rotationInfo.Add("y", rotation.y);
                        rotationInfo.Add("z", rotation.z);
                        rotationInfo.Add("w", rotation.w);

                        modelInfo.Add("distance", distanceInfo);
                        modelInfo.Add("rotation", rotationInfo);

                        updateInfo.Add(modelInfo);
                    }
                    else
                    {
                        // Tell fragment to deactivate this model
                        modelInfo.Add("distance", null);
                        updateInfo.Add(modelInfo);
                    }
                }
            }
            else
            {
                // Deactivate all models associated to this map
                foreach (GameObject target in trackedTargets)
                {
                    JObject modelInfo = new JObject();
                    modelInfo.Add("target", target.name);
                    modelInfo.Add("distance", null);
                    updateInfo.Add(modelInfo);
                }

                // Deactivate all external models
                foreach (GameObject model in externalModels.Values)
                {
                    model.SetActive(false);
                }
            }

            // Send information to Android
            Debug.Log(updateInfo);
            fragment.Call("sendLocationInfo", updateInfo.ToString());
        }
    }

    public void UpdateLocation(string updateInfo)
    {
        // Update only if map is being tracked
        if (trackableBehaviour.CurrentStatus == TrackableBehaviour.Status.TRACKED)
        {
            JObject locationInfo = JObject.Parse(updateInfo);
            fragment.Call("log", locationInfo.ToString());
            string modelName = locationInfo["target"].Value<string>();
            if (externalModels.ContainsKey(modelName))
            {
                if (locationInfo["distance"].ToString() != null)
                {
                    GameObject externalModel = externalModels[modelName];
                    externalModel.SetActive(true);

                    JObject distanceInfo = (JObject)locationInfo["distance"];
                    Vector3 distance = new Vector3(distanceInfo["x"].Value<float>(), distanceInfo["y"].Value<float>(), distanceInfo["z"].Value<float>());
                    // Transform model's position relative to map to world position
                    Vector3 newPosition = map.transform.TransformPoint(distance);
                    
                    JObject rotationInfo = (JObject)locationInfo["rotation"];
                    Quaternion rotation = new Quaternion(rotationInfo["x"].Value<float>(), rotationInfo["y"].Value<float>(), rotationInfo["z"].Value<float>(),
                        rotationInfo["w"].Value<float>());
                    // Apply relative rotation to map's rotation
                    Quaternion newRotation = map.transform.rotation * rotation;

                    externalModel.transform.position = newPosition;
                    externalModel.transform.rotation = newRotation;
                }
                else
                {
                    externalModels[modelName].SetActive(false);
                }
            }
        }
    }

}
