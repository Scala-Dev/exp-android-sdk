# Installation


## Usage

Gradle:
```groovy
 compile 'io.goexp:exp-android-sdk:v1.0.0'
```

Exp Android SDK requires at minimum Java 7 and Android 4.3.

# Runtime

## Starting the SDK

**`Exp.start(options)`**

Starts and returns an sdk instance. Can be called multiple times to start multiple independent instances of the sdk. The sdk can be started using user, device, or consumer app credentials. `options` is an object that supports the following properties:

- `username` The username used to log in to EXP. Required user credential.
- `password` The password of the user. Required user credential.
- `organization` The organization of the user. Required user credential.
- `uuid` The uuid of the device or consumer app.
- `secret` The device secret.
- `api_key` The consumer app api key. Required consumer app credential.
- `host` The api host to authenticate with. Defaults to `https://api.goexp.io`.
- `enableNetwork` Whether or not to establish a socket connection with the EXP network. If `false` you will not be able to listen for broadcasts. Defaults to `true`.

```java
Exp.start(host, user, password, org)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {Log.e("ERROR", e.getMessage());}
                    @Override
                    public void onNext(Boolean o) {
                        Log.i("EXP CONNECTED", o);
                    }
                });

# Init exp connection for user with Host,User,Password,Organization.
Exp.start(host,"cesar.oyarzun1@scala.com","Comm5715031","scala")
                 .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {Log.e("ERROR", e.getMessage());}
                    @Override
                    public void onNext(Boolean o) {
                        Log.i("EXP CONNECTED", o);
                    }
                });

# Init exp connection for user with options object.
final Map<String,Object> startOptions = new HashMap<>();
startOptions.put(Utils.HOST,"https://api.exp.scala.com");
startOptions.put(Utils.USERNAME,"cesar.oyarzun1@scala.com");
startOptions.put(Utils.PASSWORD,"123456");
startOptions.put(Utils.ORGANIZATION,"scala);
startOptions.put(Utils.ENABLE_EVENTS,true);
  Exp.start(options)
                 .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {Log.e("ERROR", e.getMessage());}
                    @Override
                    public void onNext(Boolean o) {
                        Log.i("EXP CONNECTED", o);
                    }
                });
```

## Stopping the SDK

**`Exp.stop()`**

Stops all running instance of the sdk, cancels all listeners and network connections

```java
Exp.stop();
```

## Authentication


**`Exp.getAuth()`**

Returns the current authentication payload. Will be null if not yet authenticated.

```swift
#GET USERNAME
Exp.getAuth().getIdentity().getUsername();
```

**`Exp.on("update",subscriber)`** 

Callback is called when authentication payload is updated.


**`Exp.on("error",subscriber)`**

Register a subscriber for when the sdk instance encounters a critical error and cannot continue. The subscriber is called with the error as the first argument. This is generally due to authentication failure.

```java
Subscriber errorSubscriber = new Subscriber<String>() {
      @Override
      public void onCompleted() {}
      @Override
      public void onError(Throwable e) {}
      @Override
      public void onNext(String o) {
          Log.d(LOG_TAG, "ERROR SDK");
      }
};
Exp.on("error", errorSubscriber);
```


# Network

## Status

**`Exp.connection(name, subscriber)`**

Attaches a listener for connection events. The possible events are `online` (when a connection is established to EXP) and `offline` (when the connection to EXP is lost).

```java
# Online Callback
Subscriber connectionOnline = new Subscriber() {
                            @Override
                            public void onCompleted() {}
                            @Override
                            public void onError(Throwable e) {}
                            @Override
                            public void onNext(Object o) {
                                Log.d("ONLINE", o.toString());
                            }
                        };
Exp.connection("online",connectionOnline);

# Offline callback
Subscriber connectionOffline = new Subscriber() {
                            @Override
                            public void onCompleted() {}
                            @Override
                            public void onError(Throwable e) {}
                            @Override
                            public void onNext(Object o) {
                                Log.d("ONLINE", o.toString());
                            }
                        };
Exp.connection("offline",connectionOffline);
```

**`Exp.isConnected()`**

Whether or not you are connected to the network.

## Channels

**`Exp.getChannel(name, system, consumerApp)`** 
 
 Returns a channel with the given name with two flags: `consumerApp` and `system`. Consumer devices can only listen and broadcast on consumer channels. System channels are listen only and can receive broadcasts about system events.

 ```java
    IChannel channel = Exp.getChannel("my-channel",false,true);
```

**`channel.broadcast(name, payload, timeout)`** 

Sends a broadcast with given `name` and `payload` on the channel. Waits for responses for `timeout` milliseconds and resolves with an array of responses.

```java
Map<String, Object> payload = new HashMap<String, Object>();
payload.put("test", "nice to meet you!");
channel.broadcast("hi", payload, 2000);
```

**`channel.listen(name, callback)`** 

Registers a [listener](#listeners) callback for events on the channel with the given `name`. Resolves to a [listener](#listeners) when the callback is registered and the network connection has subscribed to the channel.

The callback is called with the broadcast payload as the first argument and a `subscriber` method as the second argument.

```java
channel.listen("hi", new Subscriber() {
                            @Override
                            public void onCompleted() {}

                            @Override
                            public void onError(Throwable e) {
                                Log.e("Error", e.getMessage());
                            }

                            @Override
                            public void onNext(Object o) {
                                Log.d("LISTEN", o.toString());
                            }
                        });
```

**`channel.fling(payload)`** 

Fling an app launch payload on the channel.

```java
Map<String, Object> payload = new HashMap<String, Object>();
payload.put("uuid", "myUuid");
channel.fling(payload)
```

# API

## Devices

Devices inherit all [common resource methods and attributes](#resources).

**`Exp.getDevice(uuid)`**

Get a single device by UUID. Resolves to a [Device](#devices).

```java
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

**`Exp.findDevices(options)`**

Query for multiple devices. Resolves to a SearchResults object containing [Devices](#devices).

```java
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

## Things

**`Exp.getThing(uuid)**`

Get a single thing by UUID. Resolves to a [Thing](#things).

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

**`Exp.findThings(options)`**

Query for multiple things. Resolves to a SearchResults object containing [Things](#things).

```java
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

## Experiences


**`Exp.getExperience(uuid)`**

Get a single experience by UUID. Resolves to a [Experience](#experiences).

```java
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

**`Exp.findExperiences(options)`**

Query for multiple experiences. Resolves to a SearchResults object containing [Experiences](#experiences).

```java
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

## Locations

**`Exp.getLocation(uuid)`**

Get a single location by UUID. Resolves to a [Location](#locations).

```java
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

**`Exp.findLocations(options)`**

Query for multiple locations. Resolves to a SearchResults object containing [Locations](#locations).

```java
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

**`location.getZones()`**

Resolves to an array of [zones](#zones) that are part of this location.

**`location.getLayoutUrl()`**

Returns a url pointing to the location's layout image.

## Zones

**`zone.getKey()`**

The zone's key.

**`zone.getName()`**

The zone's name.

## Feeds

**`Exp.getFeed(uuid)`**

Get a single feed by UUID. Resolves to a [Feed](#feed-object).

```java
Exp.getFeed("052a2419-0621-45ad-aa03-3747dbfe2b6d")
        .then(new Subscriber<Feed>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }
            @Override
            public void onNext(Feed feed) {
                Log.i("Response", feed.toString());
            }
        });
```

**`ExpSwift.findFeeds(params:[String:AnyObject])`**

Query for multiple feeds. Resolves to an array of [Feeds](#feed-object).
```java
Exp.findFeeds(options)
        .then(new Subscriber<SearchResults<Feed>>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }
            @Override
            public void onNext(SearchResults<Feed> resultFeed) {
                Log.i("Response", resultFeed.toString());
            }
        });
```

## Feed Object

**`feed.get("uuid")`**

The feed's UUID

**`feed.getData()`**

Get the feed's data. Resolves to the output of the feed query.
```java
feed.getData().then(new Subscriber<Map>() {
        @Override
        public void onCompleted() {}
        @Override
        public void onError(Throwable e) {}

        @Override
        public void onNext(Map feedData) {
          Log.i("Response", feedData.toString());
        }
    });
```

## Data

**`Exp.getData(group:String, key:String)`**

Get a single data item by group and key. Resolves to a [Data](#data).

```java
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

**`Exp.findData(options)`**

Query for multiple data items. Resolves to a SearchResults object containing [Data](#data).

```java
Exp.findData(options)
    .then(new Subscriber<SearchResults<Data>>() {
        @Override
        public void onCompleted() {}

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

## Content

**`Exp.getContentNode(uuid)`**

Get a content node by UUID. Resolves to a [Content](#content). Note: The UUID value of 'root' will return the contents of the root folder of the current organization.

```java
Exp.getContent("d24c6581-f3d2-4d5a-b6b8-e90a4812d7df")
      .then(new Subscriber<Content>() {
          @Override
          public void onCompleted() {}
          @Override
          public void onError(Throwable e) {Log.e("error", e.toString());}

          @Override
          public void onNext(Content content) {
              Log.i("Response", content.toString());
          }
});
```

**`ExpSwift.findContent(options)`**

```java
Exp.findContent(options)
      .then(new Subscriber<Content>() {
          @Override
          public void onCompleted() {}
          @Override
          public void onError(Throwable e) {Log.e("error", e.toString());}

          @Override
          public void onNext(SearchResults<Content>  contentResult) {
              Log.i("Response", contentResult.toString());
          }
});
```

## Content Object

**`content.get("uuid")`**

The content's UUID.

**`content.getChildren()`**

Get the immediate children of this content node. Resolves to a list of [Content](#content-object).

```java
content.getChildren()
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

**`content.getUrl()`**

Get the absolute url to the content node data. Useful for image/video tags or to download a content file. Returns empty String for folders

```java
String url = contentNode.getUrl();
```

**`content.getVariantUrl(name)`**

Get the absolute url to the content node's variant data. Useful for image/video thumbnails or transcoded videos. Returns empty String for folders or if content does not contain the variant

```java
String variantUrl = contentNode.getVariantUrl("320.png");
```


# LOGGING

Android uses Proguard for packaging Apps, If you want to remove the ExpSwift logs before you publish your app you need to change **build.gradle** under your project and add the file **proguard-android-optimize.txt** under build tpyes proguardFiles, this will activate the proguard rules that you can define in the file **proguard-rules.pro**, in this file you can remove the logs that you want. The configuration should luke like this 
```xml
 buildTypes {
        release {
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
```
For removing the logs in your release APK you need to add this line into the file **proguard-rules.pro**
```xml
-assumenosideeffects class android.util.Log {
public static boolean isLoggable(java.lang.String, int);
public static int v(...);
public static int i(...);
public static int w(...);
public static int d(...);
public static int e(...);
}
```
Since you're using Exp SDK you need to add some extra configuration for third party library logs, after adding this part in your proguard-rules.pro you need to add this so the minify will work
```xml
-optimizations !class/unboxing/enum

-dontwarn android.support.**
-dontwarn com.scala.exp.**
-dontwarn com.fasterxml.**
-dontwarn io.jsonwebtoken.**
-dontwarn okio.**
-dontwarn retrofit.**
-dontwarn rx.internal.util.**


# Application classes that will be serialized/deserialized over Gson
-keep class ph.reggis.FEDT.model.api.** { *; }
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class ph.reggis.FEDT.model.api.** { *; }

##---------------End: proguard configuration for Gson  ----------

## RX JAVA Config
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
```

## Author

Cesar Oyarzun, cesar.oyarzun@scala.com

## License

Exp Android SDK is available under the MIT license. See the LICENSE file for more info.
