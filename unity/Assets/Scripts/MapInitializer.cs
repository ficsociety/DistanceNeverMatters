using System.Collections.Generic;
using UnityEngine;
using Newtonsoft.Json.Linq;

public class MapInitializer : MonoBehaviour {

    // Lista de mapas
    [SerializeField] private List<Sprite> maps;
    // Lista de targets
    [SerializeField] private List<GameObject> targets;
    private Dictionary<string, GameObject> targetsMap = new Dictionary<string, GameObject>();
    // Lista de modelos
    [SerializeField] private List<GameObject> models;
    private Dictionary<string, GameObject> modelsMap = new Dictionary<string, GameObject>();

    private SpriteRenderer map;
    private AndroidJavaObject fragment;

    void Start () {

        // Obtener referencia al GameObject del mapa
        map = transform.GetChild(0).GetComponent<SpriteRenderer>();

        // Convertir lista de targets en mapa
        foreach (GameObject target in targets)
        {
            Debug.Log("Target name: " + target.name);
            targetsMap.Add(target.name, target);
        }

        // Convertir lista de modelos en mapa
        foreach (GameObject model in models)
        {
            Debug.Log("Model name: " + model.name);
            modelsMap.Add(model.name, model);
        }

        // Crear referencia a la clase contenedora
        AndroidJavaClass fragmentClass = new AndroidJavaClass("apm.muei.distancenevermatters.fragments.UnityFragment");
        // Obtener instancia del fragmento contenedor
        fragment = fragmentClass.GetStatic<AndroidJavaObject>("instance");

        // Pedir datos de la partida a la actividad
        fragment.Call("getGameInfo");
    }

    public void SetGameInfo(string gameInfo)
    {
        JObject info = JObject.Parse(gameInfo);

        // Mapa
        string mapName = info["map"].Value<string>();
        
        foreach (Sprite sprite in maps)
        {
            if (sprite.name == mapName)
            {
                map.sprite = sprite;
                break;
            }
        }

        // Objetos trackeados
        JArray tracked = (JArray) info["tracked"];
        string currentUser = info["user"].Value<string>();

        foreach (JObject target in tracked)
        {
            string targetName = target["target"].Value<string>();
            string modelName = target["model"].Value<string>();
            Debug.Log("Target: " + targetName + ", model: " + modelName);

            if (targetsMap.ContainsKey(targetName) && modelsMap.ContainsKey(modelName)) {
                Debug.Log("Target and model found");
                GameObject newTarget = targetsMap[targetName];
                GameObject newModel = Instantiate(modelsMap[modelName]);

                // Añadir modelo como hijo del target
                Transform child = newTarget.transform.GetChild(0);
                newModel.transform.parent = newTarget.transform;
                newModel.transform.position = child.position;

                // Eliminar hijo vacío
                Destroy(child.gameObject);

                // Cambiar nombre del target. Necesario para la sincronización más tarde
                newTarget.name = targetName + currentUser;

                // Tag. Necesario para el Synchronizer
                newTarget.tag = "target";

                // Sacar del mapa, ya que no se va a volver a usar
                targetsMap.Remove(targetName);
            }
        }

        // Destruir targets no utilizados (los que aún están en el mapa)
        foreach (GameObject unusedTarget in targetsMap.Values)
        {
            unusedTarget.SetActive(false);
        }

        // Objetos externos
        JArray externalModels = (JArray)info["external"];
        if (externalModels != null)
        {
            foreach (JObject external in externalModels)
            {
                string modelName = external["model"].Value<string>();
                string user = external["user"].Value<string>();
                string targetName = external["target"].Value<string>();

                Debug.Log("Model: " + modelName + ", user: " + user);

                if (modelsMap.ContainsKey(modelName))
                {
                    Debug.Log("External model found");

                    GameObject newModel = Instantiate(modelsMap[modelName]);

                    // Cambiar nombre del modelo. Necesario para la sincronización más tarde
                    newModel.name = targetName + user;

                    // Tag. Necesario para el Synchronizer
                    newModel.tag = "external";
                }
            }
        }

        // Añadir Synchronizer
        gameObject.AddComponent<Synchronizer>();
    }

}
