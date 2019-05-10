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
                    modelInfo.Add("Model", trackedObject.name);

                    // Check if target is being tracked. We cannot send position information if we can't track the target
                    TrackableBehaviour targetBehaviour = trackedObject.GetComponent<TrackableBehaviour>();
                    if (targetBehaviour.CurrentStatus == TrackableBehaviour.Status.TRACKED)
                    {
                        Debug.Log("Model tracked");
                        Vector3 distance = model.transform.position - map.transform.position;
                        Debug.Log(distance);

                        // Send distance and rotation to fragment
                        JObject distanceInfo = new JObject();
                        distanceInfo.Add("x", model.transform.position.x);
                        distanceInfo.Add("y", model.transform.position.y);
                        distanceInfo.Add("z", model.transform.position.z);

                        JObject rotationInfo = new JObject();
                        rotationInfo.Add("x", model.transform.rotation.x);
                        rotationInfo.Add("y", model.transform.position.y);
                        rotationInfo.Add("z", model.transform.rotation.z);
                        rotationInfo.Add("w", model.transform.rotation.w);

                        modelInfo.Add("Distance", distanceInfo);
                        modelInfo.Add("Rotation", rotationInfo);

                        updateInfo.Add(modelInfo);
                    }
                    else
                    {
                        // Tell fragment to deactivate this model
                        modelInfo.Add("Distance", null);
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
                    modelInfo.Add("Model", target.name);
                    modelInfo.Add("Distance", null);
                    updateInfo.Add(modelInfo);
                }
            }

            // Enviar información
            Debug.Log(updateInfo);
            // Actualización mock
            MockUpdate();
        }
    }

    private void MockUpdate()
    {
        // Poner a la izquierda del marcador umbreon, con la misma rotación
        Vector3 distance = trackedTargets[0].transform.GetChild(0).position + new Vector3(2, 0, 0) - map.transform.position;
        Quaternion rotation = trackedTargets[0].transform.GetChild(0).rotation;
        string json =
            "[" +
                "{ \"Model\": \"Chandelureuser2\"," +
                    "\"Distance\": { \"x\":" + distance.x + ", \"y\": " + distance.y + ",\"z\": " + distance.z + "}," +
                    "\"Rotation\": { \"x\":" + rotation.x + ", \"y\": " + rotation.y + ",\"z\": " + rotation.z + ",\"w\": " + rotation.w + "}" +
                "}" +
            "]";

        Debug.Log(json);

        UpdateLocations(json);
    }

    public void UpdateLocations(string updateInfo)
    {
        JArray locationInfo = JArray.Parse(updateInfo);

        foreach (JObject modelInfo in locationInfo)
        {
            string modelName = modelInfo["Model"].Value<string>();
            if (externalModels.ContainsKey(modelName))
            {
                if (modelInfo["Distance"].ToString() != null)
                {
                    externalModels[modelName].SetActive(true);

                    JObject distanceInfo = (JObject) modelInfo["Distance"];
                    Vector3 distance = new Vector3(distanceInfo["x"].Value<float>(), distanceInfo["y"].Value<float>(), distanceInfo["z"].Value<float>());

                    JObject rotationInfo = (JObject) modelInfo["Rotation"];
                    Quaternion rotation = new Quaternion(rotationInfo["x"].Value<float>(), rotationInfo["y"].Value<float>(), rotationInfo["z"].Value<float>(),
                        rotationInfo["w"].Value<float>());

                    externalModels[modelName].transform.position = map.transform.position + distance;
                    externalModels[modelName].transform.rotation = rotation;

                } else
                {
                    externalModels[modelName].SetActive(false);
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
