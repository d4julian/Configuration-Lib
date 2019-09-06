# Configuration-Lib
 An easy way for developers to create configurations!


## Tutorial
#### Developer Guide:

**Step 1:**
@Inject a @ConfigDir Path and use ConfigManager.setup(path) to allow the plugin to create initial folders
Example: https://gyazo.com/c3cf1fb16616d7c4f9ed453ce1f6f297

**Step 2:** 
Create a config serializable class and have it implement IConfiguration 
Example: https://gyazo.com/94a0aaa44838f1f49085875c7ab24798

**Step 3:**
Create a Configuration object using your plugin container and a configserializer object that implements IConfiguration
Example: https://gyazo.com/6726272ecc5307e661bd7488fbe1a551

**Step 4 (Optional):** 
You can allow for your configuration to be reloaded. To do this, post a ConfigurationReloadEvent with your plugin container and Sponge.getEventManager().post(event).
Example: https://gyazo.com/3608f189f2f9fb0a958266e55ce7cb65