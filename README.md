# exp-android-sdk

## Usage
Gradle:
```groovy
 compile 'io.goexp:exp-android-sdk:0.0.1'
```
Exp Android SDK requires at minimum Java 7 and Android 4.3.

### Exp.start(host,uuid,secret)
Init exp connection for device with Host,Uuid,secret. 
```java
   Exp.start(host, user, password, org)
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                      Log.i("EXP CONNECTED", o.toString());
                    }
                }

```

### Exp.start(host,user,password,organization)
Init exp connection for user with Host,User,Password,Organization.
```java

  Exp.start(host,"cesar.oyarzun1@scala.com","Comm5715031","scala")
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                      Log.i("EXP CONNECTED", o.toString());
                    }
                }

```

### Exp.start(options)
Init exp connection for user with options object.
```java

  final Map<String,String> options = new HashMap<>();
        options.put("host","https://api.exp.scala.com");
        options.put("username","cesar.oyarzun1@scala.com");
        options.put("password","Comm5715031");
        options.put("organization","scala");
  Exp.start(options)
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                      Log.i("EXP CONNECTED", o.toString());
                    }
                }

```
#### Options Fields
User authentication:
"host" (optional)
"username" (required)
"password" (required)
"organization" (required)

Device authentication:
"host" (optional)
"deviceUuid" (required)
"secret" (required)

Network authentication:
"host" (optional)
"networkUuid" (required)
"apiKey" (required)


# Exp.connection
### Exp.connection(name, subscriber)
Attaches a listener for connection events. The possible events are `online` (when a connection is established to EXP) and `offline` (when the connection to EXP is lost).

```java
Subscriber socketConnection = new Subscriber() {
                            @Override
                            public void onCompleted() {}
                            @Override
                            public void onError(Throwable e) {}
                            @Override
                            public void onNext(Object o) {
                                Log.d("ONLINE", o.toString());
                            }
                        };
Exp.connection("online",socketConnection);

```

### scala.channels

There are four channels available:
- "system": Messages to/from the system. 
- "organization": Messages to/from devices across the organization.
- "experience": Messages to/from devices in the current experience.
- "location": Messages to/from devices in the current location.

### How to get channels
```java
IChannel channelSystem = Exp.getChannel(Utils.SOCKET_CHANNELS.SYSTEM); 
IChannel channelOrganization = Exp.getChannel(Utils.SOCKET_CHANNELS.ORGANIZATION);

```

###  [Channel].fling(uuid)
Fling content on a channel. UUID is the UUID of the content object you are flinging.
```java
//FLING CONTENT
  IChannel channel = Exp.getChannel(Utils.SOCKET_CHANNELS.ORGANIZATION);
  channel.fling("052a2419-0621-45ad-aa03-3747dbfe2b6d");
```

###  [Channel].listen(options, callback)
Register a callback for a message on this channel.
```java
//LISTEN FOR BROADCAST MESSAGE
Subscriber subscriberListen = new Subscriber() {
                            @Override
                            public void onCompleted() {}
                            @Override
                            public void onError(Throwable e) {}
                            @Override
                            public void onNext(Object o) {
                              Log.d("LISTEN", o.toString());
                            }
                        };
Map<String,String> message = new HashMap<String, String>();
message.put("name","testing");
IChannel channel = Exp.getChannel(Utils.SOCKET_CHANNELS.ORGANIZATION);
channel.listen(message,subscriberListen);

```
### [Channel].broadcast(options)
Broadcast a message out on this channel. 
```java
 //SEND BROADCAS MESSAGE

Map<String,String> payload = new HashMap<String,String>();
payload.put("opening","knock knock?");
Map<String,String> message = new HashMap<String, String>();
message.put("name","testing");
message.put("payload",payload);
IChannel orgchannel = Exp.getChannel(Utils.SOCKET_CHANNELS.ORGANIZATION);
orgchannel.broadcast(message);

```
Broadcasts can be recieved by any device that is connected to the same organization/experience/location on the given channel

### [Channel].request(options)
Send a request to another device. Returns a promise.
```java
 //SENT REQUEST
  Subscriber subscriberRequest = new Subscriber() {
                            @Override
                            public void onCompleted() {}
                            @Override
                            public void onError(Throwable e) {}
                            @Override
                            public void onNext(Object o) {
                                Log.d("REQUEST", o.toString());
                            }
  };

  IChannel systemChannel = Exp.getChannel(Utils.SOCKET_CHANNELS.SYSTEM);
  Map<String,String> mapReq = new HashMap<String, String>();
  mapReq.put("type","request");
  mapReq.put("name","getCurrentExperience");
  try {
    systemChannel.request(mapReq,subscriberRequest);
  } catch (JSONException e) {
      e.printStackTrace();
  }
```
For non-system channels, the target should be a [Device Object](#device-object). For the system channel, no target is necessary.

Requests can only reach devices that share the same organization/experience/location for the given channel.

### [Channel].respond(options, callback)
Respond to a request. The callback can throw an error to respond with an error. The callback can also return a promise.
```java
//RESPOND  MESSAGE
Subscriber subscriberRespon = new Subscriber() {
                            @Override
                            public void onCompleted() {}
                            @Override
                            public void onError(Throwable e) {}
                            @Override
                            public void onNext(Object o) {
                                Log.d("RESPON", o.toString());
                            }
                        };
Map<String,String> message = new HashMap<String, String>();
message.put("name","testing");
IChannel orgchannel = Exp.getChannel(Utils.SOCKET_CHANNELS.ORGANIZATION);
orgchannel.respon(message,subscriberRespon)
```
Response callbacks will only be triggered when the request was sent on the same channel.

# scala.api
### Exp.getCurrentDevice()
Get the current device. Resolves to a [Device Object](#device-object).
```java
//GET CURRENT DEVICE
  Subscriber currentDeviceSubs = new Subscriber<JSONObject>() {
                            @Override
                            public void onCompleted() {}
                            @Override
                            public void onError(Throwable e) {}
                            @Override
                            public void onNext(JSONObject o) {
                                Log.d("CURRENT DEVICE", o.toString());
                            }
                        };
  Exp.getCurrentDevice(currentDeviceSubs);
```
### Exp.getDevice(uuid:String)
Get a single device by UUID. Resolves to a [Device Object](#device-object).
```java
 //GET DEVICE
  Exp.getDevice("052a2419-0621-45ad-aa03-3747dbfe2b6d")
          .then(new Subscriber<Device>() {
              @Override
              public void onCompleted() {}
              @Override
              public void onError(Throwable e) {}

              @Override
              public void onNext(Device device) {
                Log.i("DEVICE", device.toString());
              }
  });
```

### Exp.findDevices(options)
Query for multiple devices. Resolves to a SearchResults object containing [Device Objects](#device-object).
```java
 //GET DEVICES
final Map<String,String> options = new HashMap<>();
        options.put("limit","10");
        options.put("skip", "0");
        options.put("sort", "asc");
Exp.findDevices(options)
    .then(new Subscriber<SearchResults<Device>>() {
      @Override
      public void onCompleted() {}
      @Override
      public void onError(Throwable e) {
        Log.e("error", e.toString());
      }
      @Override
      public void onNext(SearchResults<Device> resultDevice) {
        Log.i("Response", resultDevice.toString());
      }
  });
```
### Exp.getCurrentExperience()
Get the current experience. Resolves to an [Experience Object](#experience-object).
```java
//GET CURRENT EXPERIENCE
        Subscriber currentExperienceSubs = new Subscriber<JSONObject>() {
                            @Override
                            public void onCompleted() {}
                            @Override
                            public void onError(Throwable e) {}
                            @Override
                            public void onNext(JSONObject o) {
                                try {
                                    Object target = o.get("target");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
        Exp.getCurrentExperience(currentExperienceSubs);
        
```
### Exp.getExperience(uuid)
Get a single experience by UUID. Resolves to a [Experience Object](#experience-object).
```java
//GET EXPERIENCE
        Exp.getExperience("052a2419-0621-45ad-aa03-3747dbfe2b6d")
                                .then(new Subscriber<Experience>() {
                                    @Override
                                    public void onCompleted() {}
                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("error", e.toString());
                                    }

                                    @Override
                                    public void onNext(Experience experience) {
                                        Log.i("Response", experience.toString());
                                    }
                                });
```
### Exp.findExperiences(options)
Query for multiple experiences. Resolves to a SearchResults object containing [Experience Objects](#experience-object).
```java
 //GET EXPERIENCES
  final Map<String,String> options = new HashMap<>();
  options.put(LIMIT,"10");
  options.put(SKIP, "0");
  options.put(SORT, "asc");
  Exp.findExperiences(options)
            .then(new Subscriber<SearchResults<Experience>>() {
                  @Override
                  public void onCompleted() {}
                  @Override
                  public void onError(Throwable e) {
                      Log.e("error", e.toString());
                  }
                  @Override
                  public void onNext(SearchResults<Experience> resultExperience) {
                      Log.i("Response", resultExperience.toString());
                  }
  });

```

### Exp.getLocation(uuid)
Get a single location by UUID. Resolves to a [Location Object](#location-object).
```java
 //GET LOCATION
  Exp.getLocation("052a2419-0621-45ad-aa03-3747dbfe2b6d")
          .then(new Subscriber<Location>() {
              @Override
              public void onCompleted() {}
              @Override
              public void onError(Throwable e) {
                  Log.e("error", e.toString());
              }
              @Override
              public void onNext(Location location) {
                  Log.i("Response", location.toString());
              }
          });

```

### Exp.findLocations(options)
Query for multiple locations. Resolves to a SearchResults object containing [Location Objects](#location-object).
```java
//GET LOCATIONS
  Exp.findLocations(options)
        .then(new Subscriber<SearchResults<Location>>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }
            @Override
            public void onNext(SearchResults<Location> resultLocation) {
                Log.i("Response", resultLocation.toString());
            }
        });

```


### Exp.getContentNode(uuid)
Get a content node by UUID. Resolves to a [ContentNode Object](#content-object). Note: The UUID value of 'root' will return the contents of the root folder of the current organization.
```java
Exp.getContentNode("d24c6581-f3d2-4d5a-b6b8-e90a4812d7df")
        .then(new Subscriber<ContentNode>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {Log.e("error", e.toString());}

            @Override
            public void onNext(ContentNode contentNode) {
                Log.i("Response", contentNode.toString());
            }
        });
```


### Exp.getData(group:String, key:String)
Get a single data item by group and key. Resolves to a [Data Object](#data-object).
```swift
//GET DATA
 Exp.getData("cats","fluffbottom")
          .then(new Subscriber<Data>() {
                  @Override
                  public void onCompleted() {}
                  @Override
                  public void onError(Throwable e) {}

                  @Override
                  public void onNext(Data data) {
                    Log.i("Response", data.toString());
                  }
  });

```

### Exp.findData(options)
Query for multiple data items. Resolves to a SearchResults object containing [Data Objects](#data-object).
```java
//GET DATA
 Exp.findData(options)
        .then(new Subscriber<SearchResults<Data>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(SearchResults<Data> resultData) {
                Log.i("Response", resultData.toString());
            }
        });

```

### Exp.getThing(uuid)
Get a single thing by UUID. Resolves to a [Thing Object](#thing-object).
```java
 //GET THING
     Exp.getThing("052a2419-0621-45ad-aa03-3747dbfe2b6d")
          .then(new Subscriber<Thing>() {
              @Override
              public void onCompleted() {
              }

              @Override
              public void onError(Throwable e) {
                  Log.e("error", e.toString());
              }

              @Override
              public void onNext(Thing thing) {
                  Object zones = thing.get("location.zones");
                  Log.i("Response", thing.toString());
              }
          });
```

### Exp.findThings(options)
Query for multiple things. Resolves to a SearchResults object containing [Thing Objects](#thing-object).
```java
 //FIND THINGS
        Exp.findthings(options)
            .then(new Subscriber<SearchResults<Thing>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("error", e.toString());
                }

                @Override
                public void onNext(SearchResults<Thing> resultThing) {
                    Log.i("Response", resultThing.toString());
                }
            });
```


# Abstract API Objects

### ContentNode Object

##### content.uuid
The content's UUID.

##### content.getChildren()
Get the immediate children of this content node. Resolves to a list of [ContentNode Objects](#content-object).
```java
  contentNode.getChildren()
    .then(new Subscriber<List<ContentNode>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("error", e.toString());
        }

        @Override
        public void onNext(List<ContentNode> children) {
          for (ContentNode child : children) {
            Log.i("Child", child.get("name"));
          }
        }
    });

```

##### content.getUrl()
Get the absolute url to the content node data. Useful for image/video tags or to download a content file. Returns empty String for folders
```java
String url = contentNode.getUrl();
```

##### content.getVariantUrl(name:String)
Get the absolute url to the content node's variant data. Useful for image/video thumbnails or transcoded videos. Returns empty String for folders or if content does not contain the variant
```java
String variantUrl = contentNode.getVariantUrl("320.png");
```

### Device Object

##### device.uuid
The devices UUID

### Thing Object

##### thing.uuid
The thing UUID


### Location Object

##### location.uuid
The location's UUID.

##### location.getZones()
Return array of Zones Object [Zone].


### Zone Object
##### zone.uuid
The zone's UUID.

### Data Object
##### data.group
The data item's group.

##### data.key
The data item's key.

##### data.value
The data item's value.


## Author

Cesar Oyarzun, cesar.oyarzun@scala.com

## License

Exp Android SDK is available under the MIT license. See the LICENSE file for more info.
