 [ ![Download](https://api.bintray.com/packages/eltonkola/maven/arkitekt_core/images/download.svg?version=0.0.5) ](https://bintray.com/eltonkola/maven/arkitekt_core/0.0.5/link)

# arkitekt

![Logo](/arkitekt_logo.png?raw=true "Logo")

This is a simple library to make it easier to create simple android apps, with a single library.

Like the idea? Jump in, we are getting started.

# create an application class:
```
public class MainApp extends ArkitektApp {

    public static final String PATH_ROOT = "/";
    public static final String PATH_DETAILS = "/details";

    @Override
    protected void routeConfig() {

        addScreen(PATH_ROOT, MainScreen.class);
        addScreen(PATH_DETAILS, DetailsScreen.class);

    }
    
}
```
we are defining two browsing paths, the first "/" is mandatory for any app, as it will be the main screen and the other ones can be secondary screens on yur app. Think of the as url on a website

configuring the routin, the app will have a list of possible screen you can navigate.

# create the main screen:
```
public class MainScreen extends AppScreen<Void> {

    @Override
    public int getView() {
        return R.layout.main_screen;
    }

    @Override
    public void onEntered() {
        super.onEntered();

        mRootView.findViewById(R.id.butClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });

        mRootView.findViewById(R.id.butDetails1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTo(MainApp.PATH_DETAILS, "1");
            }
        })

    }

    @Override
    public void onExit() {
        super.onExit();
    }
}
```
every screen on the app now needs to expend AppScreen<T>, there T is the input parameter you want, this will be useful on the other screen, for wich we want to pass parameters. On this case w ehave Void, as we dont care about any value.

getView() with return the layout for this screen, and onEntered() and onExit() are the only events you need to take care of. On will becalled then we screen is ready, and the second when it will be removed from the screen stack

- to close the screen, adn go back on what you had before, you call:
```
close()
```
- to naviigate on another screen we call:
```
goTo(MainApp.PATH_DETAILS, "1");
```

- to get views we can use:
```
mRootView.findViewById()
```

where the first parameter is the path we mapped on the application class and the second is the object of type T, you defined on your requested screen.

# let's create the details screen: 
```
public class DetailsScreen extends AppScreen<String> {

    @Override
    public int getView() {
        return R.layout.details_screen;
    }
    
    @Override
    public void onEntered() {
        super.onEntered();

        mRootView.findViewById(R.id.butClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });

        TextView detailTxt = mRootView.findViewById(R.id.detailTxt);
        detailTxt.setText("Detail page nr: " + mScreenParam);
        
       
    }
}
```
similar to the main screen this screen has the same methods, but now we can use:

mScreenParam, of type T, we defined on the class, and will have the value we sent from main screen.


# examples
there are two example apps, a very basic one, and a more complex one, using room and rxjava for a simple todo app, take a look at them

# more

there still a lot, to make this library productionready, but if you guys have time to play around with it, go on, and lets make this a useful library for everyone

# license

```
Copyright 2017 Elton Kola

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

