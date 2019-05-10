using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Vuforia;

public class MapInitializer : MonoBehaviour {

    // Lista de mapas
    [SerializeField] private List<Sprite> maps;
    // Lista de targets
    [SerializeField] private List<GameObject> targets;
    private Dictionary<string, GameObject> targetsMap = new Dictionary<string, GameObject>();
    // Lista de modelos
    [SerializeField] private List<GameObject> models;
    private Dictionary<string, GameObject> modelsMap = new Dictionary<string, GameObject>();

    // TODO quitar
    private Text debug;

    private SpriteRenderer map;

    void Start () {
        // TODO quitar
        debug = FindObjectOfType<Text>();

        // Obtener referencia al GameObject del mapa
        map = transform.GetChild(0).GetComponent<SpriteRenderer>();

        // Convertir lista de targets en mapa
        foreach (GameObject target in targets)
        {
            Debug.Log("Target name: " + target.name);
            debug.text += "Target name: " + target.name + "\n";
            targetsMap.Add(target.name, target);
        }

        // Convertir lista de modelos en mapa
        foreach (GameObject model in models)
        {
            Debug.Log("Model name: " + model.name);
            debug.text += "Model name: " + model.name + "\n";
            modelsMap.Add(model.name, model);
        }

        // Crear referencia a la clase contenedora
        AndroidJavaClass fragmentClass = new AndroidJavaClass("apm.muei.distancenevermatters.fragments.UnityFragment");
        // Obtener instancia del fragmento contenedor
        AndroidJavaObject fragment = fragmentClass.GetStatic<AndroidJavaObject>("instance");

        // Pedir datos de la partida a la actividad
        fragment.Call("getGameInfo");

        // JSON de prueba con datos del juego
        //string json =
        //    "{" +
        //    "\"Map\": \"map\"," +
        //    "\"User\": \"user\"," +
        //    "\"Tracked\": [" +
        //        "{ \"Target\": \"Umbreon\", \"Model\": \"RPGHeroHP\" }" +
        //    "]," +
        //    "\"External\": [" +
        //       "{ \"Model\": \"PurpleDragon\", \"User\": \"user2\" }" +
        //    "]" +
        //    "}";
        //SetGameInfo(json);
    }
	
	// Update is called once per frame
	void Update () {
        // Esperar a tener todos los datos? Aunque también puedo hacerlo en el "callback" que
        // Android usará para comunicarse conmigo
	}

    public void SetGameInfo(string gameInfo)
    {
        JObject info = JObject.Parse(gameInfo);

        // Mapa
        string mapName = info["Map"].Value<string>();
        
        foreach (Sprite sprite in maps)
        {
            if (sprite.name == mapName)
            {
                map.sprite = sprite;
                break;
            }
        }

        // Objetos trackeados
        JArray tracked = (JArray) info["Tracked"];

        foreach (JObject target in tracked)
        {
            string targetName = target["Target"].Value<string>();
            string modelName = target["Model"].Value<string>();
            Debug.Log("Target: " + targetName + ", model: " + modelName);
            debug.text += "Target: " + targetName + ", model: " + modelName + "\n";

            if (targetsMap.ContainsKey(targetName) && modelsMap.ContainsKey(modelName)) {
                Debug.Log("Target and model found");
                debug.text += "Target and model found" + "\n";
                GameObject newTarget = Instantiate(targetsMap[targetName]);
                GameObject newModel = Instantiate(modelsMap[modelName]);

                // Añadir modelo como hijo del target
                Transform child = newTarget.transform.GetChild(0);
                newModel.transform.parent = newTarget.transform;
                newModel.transform.position = child.position;
                newModel.transform.rotation = child.rotation;

                // Eliminar hijo vacío
                Destroy(child.gameObject);

                // Cambiar nombre del target. Necesario para la sincronización más tarde
                newTarget.name = targetName;

                // Tag. Necesario para el Synchronizer
                newTarget.tag = "target";
            }
        }

        // Objetos externos
        JArray externalModels = (JArray)info["External"];
        foreach (JObject external in externalModels)
        {
            string modelName = external["Model"].Value<string>();
            string user = external["User"].Value<string>();

            Debug.Log("Model: " + modelName + ", user: " + user);
            debug.text += "Model: " + modelName + ", user: " + user + "\n";

            if (modelsMap.ContainsKey(modelName))
            {
                Debug.Log("External model found");
                debug.text += "External model found" + "\n";

                GameObject newModel = Instantiate(modelsMap[modelName]);

                // Cambiar nombre del modelo. Necesario para la sincronización más tarde
                // TODO el nombre debería ser user + target
                newModel.name = modelName + user;

                // Tag. Necesario para el Synchronizer
                newModel.tag = "external";
            }
        }

        // Añadir Synchronizer
        gameObject.AddComponent<Synchronizer>();
    }

}
